package launchcode.liftoff_project.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
public class Rating {

    @EmbeddedId
    RatingKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @MapsId("trailId")
    @JoinColumn(name = "trail_id")
    Trail trail;

    int rating;

    public Rating(User user, Trail trail, int rating) {
        this.user = user;
        this.trail = trail;
        this.rating = rating;
    }

    public Rating(){}

    public RatingKey getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Trail getTrail() {
        return trail;
    }

    public void setTrail(Trail trail) {
        this.trail = trail;
    }

    public int getRatingValue() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
