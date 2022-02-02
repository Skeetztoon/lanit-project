package ru.lanit.bpm.jedu.hrjedi.adapter.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lanit.bpm.jedu.hrjedi.app.api.vacation.FindVacationsToApproveInbound;

import java.util.Set;

@RestController
@RequestMapping("/hr-rest/vacations")
@RequiredArgsConstructor
public class VacationController {
    private final FindVacationsToApproveInbound findVacationsToApproveInbound;

    @GetMapping("/approve/{approver}")
    public Set<String> vacationsToApprove(@PathVariable("approver") String approver) {
        return findVacationsToApproveInbound.execute(approver);
    }
}
