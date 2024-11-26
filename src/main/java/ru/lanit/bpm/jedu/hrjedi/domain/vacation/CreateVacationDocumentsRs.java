package ru.lanit.bpm.jedu.hrjedi.domain.vacation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "CreateVacationDocumentsRs")
@XmlAccessorType(XmlAccessType.FIELD)
public final class CreateVacationDocumentsRs {
    @XmlElement
    private String status;

    @XmlElement
    private String description;
}
