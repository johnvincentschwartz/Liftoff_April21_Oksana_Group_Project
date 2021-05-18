package launchcode.liftoff_project.Model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RatingKey implements Serializable {

    @Column(name = "user_id")
    int userId;

    @Column(name = "trail_id")
    int trailId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RatingKey rating = (RatingKey) o;
        return userId == rating.userId && trailId == rating.trailId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, trailId);
    }
}
