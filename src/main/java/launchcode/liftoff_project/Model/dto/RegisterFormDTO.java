package launchcode.liftoff_project.Model.dto;

import com.sun.istack.NotNull;

import javax.validation.constraints.NotBlank;

public class RegisterFormDTO extends LoginFormDTO {

    @NotBlank(message = "First name is required.")
    private String firstName;

    @NotBlank(message = "First name is required.")
    private String lastName;

    private String verifyPassword;
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


    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }
}


