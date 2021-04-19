package launchcode.liftoff_project.Model.data;


import launchcode.liftoff_project.Model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByEmail(String email);
}
