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

    public static class Builder {
        private String login;
        private String startDate;
        private String endDate;

        public Builder login(String login) {
            this.login = login;
            return this;
        }

        public Builder startDate(String startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(String endDate) {
            this.endDate = endDate;
            return this;
        }

        public CreateVacationDocumentsRq build() {
            CreateVacationDocumentsRq request = new CreateVacationDocumentsRq();
            request.setLogin(this.login);
            request.setStartDate(this.startDate);
            request.setEndDate(this.endDate);
            return request;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
