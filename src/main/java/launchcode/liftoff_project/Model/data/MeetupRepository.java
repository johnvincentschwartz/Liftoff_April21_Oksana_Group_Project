package launchcode.liftoff_project.Model.data;

import launchcode.liftoff_project.Model.Meetup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetupRepository extends CrudRepository<Meetup, Integer> {
}
