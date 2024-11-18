package ru.lanit.bpm.jedu.hrjedi.domain.vacation;

import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Setter
@XmlRootElement(name = "CreateVacationDocumentsRs")
public class CreateVacationDocumentsRs {
    private String status;
    private String description;

    @XmlElement
    public String getStatus() {
        return status;
    }

    @XmlElement
    public String getDescription() {
        return description;
    }
}
