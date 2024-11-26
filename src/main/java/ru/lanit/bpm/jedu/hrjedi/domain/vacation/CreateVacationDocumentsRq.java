package ru.lanit.bpm.jedu.hrjedi.domain.vacation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "CreateVacationDocumentsRq")
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateVacationDocumentsRq {
    private String login;

    @XmlElement(name = "start")
    private String startDate;

    @XmlElement(name = "end")
    private String endDate;
}
