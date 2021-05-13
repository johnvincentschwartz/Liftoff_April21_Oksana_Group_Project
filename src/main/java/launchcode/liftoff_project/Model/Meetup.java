package launchcode.liftoff_project.Model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Meetup extends AbstractEntity {

    @NotBlank(message = "Name field cannot be left blank.")
    @Size(min = 2, max = 50, message = "Meetup name must be between 2 and 50 characters")
    private String meetupName;

    @NotNull(message = "Date & time is required.(mm/dd/yyyy hh:mm)")
    private Date meetupDate;

    @OneToOne(cascade = CascadeType.ALL)
    @Valid
    @NotNull
    private MeetupDetails meetupDetails;

    @ManyToOne
    @JoinColumn(name = "trail_id")
    private Trail trail;

//    @ManyToOne
//    @NotNull(message = "Please select a category for the meetup.")
//    private MeetupCategory meetupCategory;

    public Meetup(@NotBlank(message = "Name field cannot be left blank.") @Size(min = 2, max = 50, message = "Meetup name must be between 2 and 50 characters") String meetupName,
                  @NotNull(message = "Date & time is required.(mm/dd/yyyy hh:mm)") Date meetupDate, Trail trail
                  /*@NotNull(message = "Please select a category for the meetup.") MeetupCategory meetupCategory*/) {
        super();
        this.meetupName = meetupName;
        this.meetupDate = meetupDate;
        this.trail = trail;
        //this.meetupCategory = meetupCategory;
    }

    public Meetup(){}

    public String getMeetupName() {
        return meetupName;
    }

    public void setMeetupName(String meetupName) {
        this.meetupName = meetupName;
    }

    public MeetupDetails getMeetupDetails() {
        return meetupDetails;
    }

    public void setMeetupDetails(MeetupDetails meetupDetails) {
        this.meetupDetails = meetupDetails;
    }

    public Date getMeetupDate() {
        return meetupDate;
    }

    public void setMeetupDate(Date meetupDate) {
        this.meetupDate = meetupDate;
    }

    public Trail getTrail() {
        return trail;
    }

    public void setTrail(Trail trail) {
        this.trail = trail;
    }

    //    public MeetupCategory getMeetupCategory() {
//        return meetupCategory;
//    }
//
//    public void setMeetupCategory(MeetupCategory meetupCategory) {
//        this.meetupCategory = meetupCategory;
//    }

    @Override
    public String toString() {
        return meetupName;
    }
}