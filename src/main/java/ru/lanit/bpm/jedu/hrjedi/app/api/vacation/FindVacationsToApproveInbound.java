package ru.lanit.bpm.jedu.hrjedi.app.api.vacation;

import java.util.Set;

public interface FindVacationsToApproveInbound {
    Set<String> execute(String approverLogin);
}
