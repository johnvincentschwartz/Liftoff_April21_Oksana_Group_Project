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

    @NotBlank(message = "Name field cannot be left blank. Please provide a meetup name.")
    @Size(min = 2, max = 50, message = "Meetup name must be between 2 and 50 characters")
    private String meetupName;

    @DateTimeFormat(pattern="dd/MM/yyyy")
    private Date meetupDate;

    @OneToOne(cascade = CascadeType.ALL)
    @Valid
    @NotNull
    private MeetupDetails meetupDetails;



//    @ManyToOne
//    @NotNull(message = "Please select a category for the meetup.")
//    private MeetupCategory meetupCategory;

    public Meetup(@NotBlank(message = "Name field cannot be left blank. Please provide a meetup name.") @Size(min = 2, max = 50, message = "Meetup name must be between 2 and 50 characters") String meetupName, @DateTimeFormat(pattern="dd/MM/yyyy") Date meetupDate
                  /*@NotNull(message = "Please select a category for the meetup.") MeetupCategory meetupCategory*/) {
        this.meetupName = meetupName;
        this.meetupDate = meetupDate;
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