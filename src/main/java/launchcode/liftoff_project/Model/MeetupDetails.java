package launchcode.liftoff_project.Model;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class MeetupDetails extends AbstractEntity {

    @Size(max = 500, message = "The description is too long!")
    private String meetupDescription;

    @NotBlank(message = "An email is required for this field!")
    @Email(message = "Invalid email. Please try again or enter another email.")
    private String meetupContactEmail;

    public MeetupDetails(@Size(max = 500, message = "The description is too long!") String meetupDescription, @NotBlank(message = "An email is required for this field!") @Email(message = "Invalid email. Please try again or enter another email.") String meetupContactEmail) {
        this.meetupDescription = meetupDescription;
        this.meetupContactEmail = meetupContactEmail;
    }

    public MeetupDetails(){}

    public String getMeetupDescription() {
        return meetupDescription;
    }

    public void setMeetupDescription(String meetupDescription) {
        this.meetupDescription = meetupDescription;
    }

    public String getMeetupContactEmail() {
        return meetupContactEmail;
    }

    public void setMeetupContactEmail(String meetupContactEmail) {
        this.meetupContactEmail = meetupContactEmail;
    }
}
