package ru.lanit.bpm.jedu.hrjedi.app.impl.vacation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.lanit.bpm.jedu.hrjedi.app.api.vacation.FindVacationsToApproveInbound;

import java.util.Collections;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class FindVacationsToApproveUseCase implements FindVacationsToApproveInbound {
    @Override
    public Set<String> execute(String approverLogin) {
        return Collections.emptySet();
    }
}
