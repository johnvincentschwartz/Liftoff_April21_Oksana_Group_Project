package launchcode.liftoff_project.Model;

import com.sun.istack.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User extends AbstractEntity {

    @NotNull
    private String email;

    @NotNull
    private String pwHash;

    public User() {}

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User(String email, String password) {
        this.email = email;
        this.pwHash = encoder.encode(password);
    }

    public String getEmail() {
        return email;
    }

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }

}