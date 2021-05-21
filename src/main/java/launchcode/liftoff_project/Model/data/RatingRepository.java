package launchcode.liftoff_project.Model.data;

import launchcode.liftoff_project.Model.Rating;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends CrudRepository<Rating, Integer> {

}
