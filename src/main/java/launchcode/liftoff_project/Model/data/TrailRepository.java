package launchcode.liftoff_project.Model.data;

import launchcode.liftoff_project.Model.Trail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrailRepository extends JpaRepository<Trail, Integer> {

}
