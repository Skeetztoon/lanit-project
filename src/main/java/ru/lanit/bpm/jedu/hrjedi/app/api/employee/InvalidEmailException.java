package ru.lanit.bpm.jedu.hrjedi.app.api.employee;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String message) {
        super(message);
    }
}
