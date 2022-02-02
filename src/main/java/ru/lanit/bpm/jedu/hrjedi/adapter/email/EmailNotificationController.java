package ru.lanit.bpm.jedu.hrjedi.adapter.email;
/*
 * Copyright (c) 2008-2020
 * LANIT
 * All rights reserved.
 *
 * This product and related documentation are protected by copyright and
 * distributed under licenses restricting its use, copying, distribution, and
 * decompilation. No part of this product or related documentation may be
 * reproduced in any form by any means without prior written authorization of
 * LANIT and its licensors, if any.
 *
 * $
 */

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import ru.lanit.bpm.jedu.hrjedi.domain.employee.Employee;
import ru.lanit.bpm.jedu.hrjedi.domain.vacation.Vacation;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

@Controller
public class EmailNotificationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailNotificationController.class);
    private static final String MAIL_ENCODING = "UTF-8";
    private static final String MAIL_TYPE_HTML = "html";
    private static final String MAIL_TYPE_PNG = "image/png";
    private static final DateTimeFormatter DAY_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final String HRJEDI_LOGO_SOURCE = "/images/hr-jedi.png";
    private static final String HRJEDI_LOGO_CID = "hrjedilogo";
    private static final String VACATION_APPROVAL_MESSAGE_TEMPLATE_SOURCE = "/templates/mail-html/vacation-approval.html";
    private static final String VACATION_APPROVAL_MESSAGE_SUBJECT = "Заявка на отпуск %s-%s одобрена";

    @Value("${ru.lanit.bpm.jedu.hrjedi.email.mode}")
    private String emailSendingMode;

    @Value("${ru.lanit.bpm.jedu.hrjedi.email.mailAddress}")
    private String applicationEmailAddress;

    @Value("${ru.lanit.bpm.jedu.hrjedi.commandLineArg.mailPassword}")
    private String applicationEmailPassword;

    @Value("${ru.lanit.bpm.jedu.hrjedi.email.smtp.host}")
    private String mailSmtpHost;

    @Value("${ru.lanit.bpm.jedu.hrjedi.email.smtp.port}")
    private String mailSmtpPort;

    public void notifyOnVacationApproval(Vacation approvedVacation) {
        Employee employee = approvedVacation.getEmployee();

        LOGGER.info("Attemping to notify {} on vacation approval", employee.getLogin());

        String employeeFirstName = employee.getFirstName();
        String vacationStartString = approvedVacation.getStart().format(DAY_FORMATTER);
        String vacationEndString = approvedVacation.getEnd().format(DAY_FORMATTER);
        try {
            String body = getFilledMessageTemplate(VACATION_APPROVAL_MESSAGE_TEMPLATE_SOURCE, employeeFirstName, vacationStartString, vacationEndString);
            String subject = String.format(VACATION_APPROVAL_MESSAGE_SUBJECT, vacationStartString, vacationEndString);
            List<String> recipientsEmails = singletonList(employee.getEmail());
            List<MimeBodyPart> messageContent = asList(createHtmlBodyPart(body), createImageWithContentIdBodyPart(HRJEDI_LOGO_SOURCE, HRJEDI_LOGO_CID));

            sendMultipartEmailMessage(applicationEmailAddress, recipientsEmails, subject, messageContent);
        } catch (MessagingException e) {
            throw new IllegalStateException(
                String.format("Error during sending vacation approval for employee - %s and vacation - %s", employee.getLogin(), approvedVacation.getId()), e);
        }
        LOGGER.info("Notification of employee {} on vacation approval completed successfully", employee.getLogin());
    }

    private String getFilledMessageTemplate(String templateSource, String... templateArguments) {
        try (InputStream templateStream = getClass().getResourceAsStream(templateSource)) {
            String template = IOUtils.toString(templateStream, StandardCharsets.UTF_8);
            return String.format(template, templateArguments);
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Error occurred while trying to access the message template located at %s", templateSource), e);
        }
    }

    private MimeBodyPart createHtmlBodyPart(String text) throws MessagingException {
        MimeBodyPart textBodyPart = new MimeBodyPart();
        textBodyPart.setText(text, MAIL_ENCODING, MAIL_TYPE_HTML);
        return textBodyPart;
    }

    private MimeBodyPart createImageWithContentIdBodyPart(String imageSource, String contentId) throws MessagingException {
        try (InputStream imageDataStream = getImageSource(imageSource)) {
            byte[] imageData = IOUtils.toByteArray(imageDataStream);
            MimeBodyPart imageBodyPart = new MimeBodyPart();
            imageBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(imageData, MAIL_TYPE_PNG)));
            imageBodyPart.setHeader("Content-ID", String.format("<%s>", contentId));
            return imageBodyPart;
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Error occurred while trying to access the image located at %s", imageSource), e);
        }
    }

    protected InputStream getImageSource(String imageSource) {
        return getClass().getResourceAsStream(imageSource);
    }

    private void sendMultipartEmailMessage(String senderEmail, List<String> recipientsEmails, String subject, Collection<MimeBodyPart> messageParts)
        throws MessagingException {
        if ("off".equals(emailSendingMode)) {
            return;
        }

        Session session = createEmailSession();
        Message message = createMessage(session, senderEmail, recipientsEmails, subject, messageParts);

        Transport.send(message);
    }

    private Session createEmailSession() {
        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.host", mailSmtpHost);
        mailProperties.put("mail.smtp.port", mailSmtpPort);

        return Session.getInstance(
            mailProperties,
            new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(applicationEmailAddress, applicationEmailPassword);
                }
            });
    }

    private Message createMessage(Session session, String senderEmail, Collection<String> recipientsEmails, String subject,
        Collection<MimeBodyPart> messageParts) {
        try {
            Message message = new MimeMessage(session);
            Collection<String> finalRecipientsEmails = !"prod".equals(emailSendingMode) ? singletonList(applicationEmailAddress) : recipientsEmails;
            message.setRecipients(Message.RecipientType.TO, createInternetAddresses(finalRecipientsEmails));
            message.setFrom(createInternetAddress(senderEmail));
            message.setSubject(subject);
            message.setContent(createMultipartMessageContentFromParts(messageParts));

            return message;
        } catch (MessagingException e) {
            throw new IllegalStateException("Error in MimeMessage creation", e);
        }
    }

    private InternetAddress[] createInternetAddresses(Collection<String> emailAddresses) {
        Set<String> uniqueEmails = emailAddresses.stream()
            .map(emailAddress -> emailAddress.trim().toLowerCase())
            .collect(Collectors.toSet());

        return uniqueEmails.stream()
            .map(this::createInternetAddress)
            .toArray(InternetAddress[]::new);
    }

    private InternetAddress createInternetAddress(String emailAddress) {
        try {
            return new InternetAddress(emailAddress);
        } catch (AddressException e) {
            throw new IllegalArgumentException(String.format("Error in creation of InternetAddress from - %s", emailAddress), e);
        }
    }

    private Multipart createMultipartMessageContentFromParts(Collection<MimeBodyPart> messageParts) {
        if (isEmpty(messageParts)) {
            throw new IllegalArgumentException("Email message must include at least one message part");
        }

        Multipart messageContent = new MimeMultipart();
        messageParts.forEach(messagePart -> {
            try {
                messageContent.addBodyPart(messagePart);
            } catch (MessagingException e) {
                throw new IllegalStateException("Error in addition of message part to multipart message content", e);
            }
        });
        return messageContent;
    }
}
