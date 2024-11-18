package ru.lanit.bpm.jedu.hrjedi.domain.vacation;

import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Setter
@XmlRootElement(name = "CreateVacationDocumentsRq")
public class CreateVacationDocumentsRq {
    private String login;
    private String startDate;
    private String endDate;

    @XmlElement
    public String getLogin() {
        return login;
    }

    @XmlElement(name = "start")
    public String getStartDate() {
        return startDate;
    }

    @XmlElement(name = "end")
    public String getEndDate() {
        return endDate;
    }
}
