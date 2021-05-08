//package launchcode.liftoff_project.Model;
//
//import javax.persistence.Entity;
//import javax.persistence.OneToMany;
//import javax.validation.constraints.Size;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//public class MeetupCategory extends AbstractEntity {
//
//    @Size(min = 2, message = "Meetup category name must be at least 2 characters long!")
//    private String meetupCategoryName;
//
//    @OneToMany(mappedBy = "meetupCategory")
//    private final List<Meetup> meetups = new ArrayList<>();
//
//    public MeetupCategory(@Size(min = 2, message = "Meetup category name must be at least 2 characters long!") String meetupCategoryName) {
//        this.meetupCategoryName = meetupCategoryName;
//    }
//
//    public MeetupCategory() {}
//
//    public String getMeetupCategoryName() {
//        return meetupCategoryName;
//    }
//
//    public void setMeetupCategoryName(String meetupCategoryName) {
//        this.meetupCategoryName = meetupCategoryName;
//    }
//
//    public List<Meetup> getMeetups() {
//        return meetups;
//    }
//
//    @Override
//    public String toString() {
//        return meetupCategoryName;
//    }
//}
