package launchcode.liftoff_project.Model.data;


import launchcode.liftoff_project.Model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByEmail(String email);
//    User findByConfirmationToken(String confirmationToken);
}
