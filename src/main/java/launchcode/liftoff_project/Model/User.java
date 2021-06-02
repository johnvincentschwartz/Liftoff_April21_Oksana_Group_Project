package launchcode.liftoff_project.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sun.istack.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class User extends AbstractEntity {
    @NotNull
    @NotBlank(message = "First name is required.")
    private String firstName;

    @NotNull
    @NotBlank(message = "First name is required.")
    private String lastName;

    @NotNull
    @NotBlank(message = "First name is required.")
    private String email;

    @NotNull
    private String pwHash;

//    @Column(name = "confirmation_token")
//    private String confirmationToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "favoriteTrails",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "trail_id"))
    private Set<Trail> favoriteTrails;

    public User() {}

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.pwHash = encoder.encode(password);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public Set<Trail> getFavoriteTrails() {
        return favoriteTrails;
    }

    public void addFavoriteTrail(Trail trail) {
        this.favoriteTrails.add(trail);
        System.out.println(favoriteTrails);
    }

    public void removeFavoriteTrail(Trail trail){
        this.favoriteTrails.remove(trail);
        System.out.println(favoriteTrails);
    }

    //    public String getConfirmationToken() {
//        return confirmationToken;
//    }

//    public void setConfirmationToken(String confirmationToken) {
//        this.confirmationToken = confirmationToken;
//    }
}