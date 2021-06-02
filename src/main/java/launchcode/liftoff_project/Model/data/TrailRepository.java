package launchcode.liftoff_project.Model.data;

import launchcode.liftoff_project.Model.Trail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrailRepository extends JpaRepository<Trail, Integer> {

}
