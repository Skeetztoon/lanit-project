package ru.lanit.bpm.jedu.hrjedi.app.api.vacation;

public class CreateVacationDocumentsException extends Exception {
    public CreateVacationDocumentsException(String message) {
        super(message);
    }

    public CreateVacationDocumentsException(String message, Throwable cause) {
        super(message, cause);
    }
}
