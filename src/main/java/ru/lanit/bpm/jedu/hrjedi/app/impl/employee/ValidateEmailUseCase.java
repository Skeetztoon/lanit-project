package ru.lanit.bpm.jedu.hrjedi.app.impl.employee;

import org.springframework.stereotype.Component;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.InvalidEmailException;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.ValidateEmailInbound;

@Component
public class ValidateEmailUseCase implements ValidateEmailInbound {
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9А-Яа-я.-]+@[a-zA-Z0-9А-Яа-я.-]+$";

    @Override
    public void execute(String email) {
        if (email == null || !email.matches(EMAIL_PATTERN)) {
            throw new InvalidEmailException("Email is invalid");
        }
    }
}
