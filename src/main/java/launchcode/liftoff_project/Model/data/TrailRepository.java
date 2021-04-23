package launchcode.liftoff_project.Model.data;

import launchcode.liftoff_project.Model.Trail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrailRepository extends CrudRepository<Trail, Integer> {

}
