package launchcode.liftoff_project.Model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Rating {

    @EmbeddedId
    private RatingKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("trailId")
    private Trail trail;

    private int ratingValue;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String review;

    public Rating(){}

    public Rating(User user, Trail trail, int ratingValue) {
        this.id = new RatingKey(user.getId(), trail.getId());
        this.user = user;
        this.trail = trail;
        this.ratingValue = ratingValue;
    }

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
        return ratingValue;
    }

    public void setRatingValue(int ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "user=" + user +
                ", trail=" + trail +
                ", ratingValue=" + ratingValue +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating = (Rating) o;
        return Objects.equals(id, rating.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
