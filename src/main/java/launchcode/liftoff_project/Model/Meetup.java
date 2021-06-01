package launchcode.liftoff_project.Model;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


@Entity
public class Meetup extends AbstractEntity {

    @NotBlank(message = "Name field cannot be left blank.")
    @Size(min = 2, max = 80, message = "Meetup name must be between 2 and 80 characters")
    private String meetupName;

    @NotNull(message = "Date & time is required.(mm/dd/yyyy hh:mm)")
    private Date meetupDate;

    @Size(max = 500, message = "The description is too long!")
    private String meetupDescription;

    @NotBlank(message = "An email is required for this field!")
    @Email(message = "Invalid email. Please try again or enter another email.")
    private String meetupContactEmail;

    private String meetupCategory;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @ManyToOne
    @JoinColumn(name = "trail_id")
    private Trail trail;

    public Meetup(@NotBlank(message = "Name field cannot be left blank.") @Size(min = 2, max = 50, message = "Meetup name must be between 2 and 50 characters") String meetupName,
                  @NotNull(message = "Date & time is required.(mm/dd/yyyy hh:mm)") Date meetupDate, @Size(max = 500, message = "The description is too long!") String meetupDescription,
                  @NotBlank(message = "An email is required for this field!") @Email(message = "Invalid email. Please try again or enter another email.") String meetupContactEmail,
                          Trail trail) {
        super();
        this.meetupName = meetupName;
        this.meetupDate = meetupDate;
        this.meetupDescription = meetupDescription;
        this.meetupContactEmail = meetupContactEmail;
        this.trail = trail;
    }

    public Meetup(){}

    public String getMeetupName() {
        return meetupName;
    }

    public void setMeetupName(String meetupName) {
        this.meetupName = meetupName;
    }

    public Date getMeetupDate() {
        return meetupDate;
    }

    public void setMeetupDate(Date meetupDate) {
        this.meetupDate = meetupDate;
    }

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

    public String getMeetupCategory() {
        return meetupCategory;
    }

    public void setMeetupCategory(String meetupCategory) {
        this.meetupCategory = meetupCategory;
    }

    public Trail getTrail() {
        return trail;
    }

    public void setTrail(Trail trail) {
        this.trail = trail;
    }

    @Override
    public String toString() {
        return meetupName;
    }
}