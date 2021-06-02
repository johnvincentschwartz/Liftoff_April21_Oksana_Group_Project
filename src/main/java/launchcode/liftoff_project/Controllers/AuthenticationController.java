package launchcode.liftoff_project.Controllers;


import launchcode.liftoff_project.Model.User;
import launchcode.liftoff_project.Model.data.UserRepository;
import launchcode.liftoff_project.Model.dto.LoginFormDTO;
import launchcode.liftoff_project.Model.dto.RegisterFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AuthenticationController extends HttpServlet {

    @Autowired
    UserRepository userRepository;

    protected static final String userSessionKey = "user";

    public User getUserFromSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        if (userId == null) {
            return null;
        }

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            return null;
        }

        return user.get();
    }

    private static void setUserInSession(HttpSession session, User user) {
        session.setAttribute(userSessionKey, user.getId());
    }

    @GetMapping("/register")
    public String displayRegistrationForm(Model model) {
        model.addAttribute(new RegisterFormDTO());
        model.addAttribute("title", "Register");
        return "register";
    }

    @PostMapping("/register")
    public String processRegistrationForm(@ModelAttribute @Valid RegisterFormDTO registerFormDTO,
                                          Errors errors, HttpServletRequest request,
                                          Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Register");
            return "register";
        }

        User existingUser = userRepository.findByEmail(registerFormDTO.getEmail());

        if (existingUser != null) {
            errors.rejectValue("email", "email.alreadyexists", "A user with that email already exists");
            model.addAttribute("title", "Register");
            return "register";
        }

        String password = registerFormDTO.getPassword();
        String verifyPassword = registerFormDTO.getVerifyPassword();
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
            model.addAttribute("title", "Register");
            return "register";
        }

        User newUser = new User(registerFormDTO.getFirstName(), registerFormDTO.getLastName(),registerFormDTO.getEmail(), registerFormDTO.getPassword());
        userRepository.save(newUser);
        setUserInSession(request.getSession(), newUser);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String displayLoginForm(Model model) {
        model.addAttribute(new LoginFormDTO());
        model.addAttribute("title", "Log In");
        return "login";
    }

    @PostMapping("/login")
    public String processLoginForm(@ModelAttribute @Valid LoginFormDTO loginFormDTO, HttpSession session,
                                   Errors errors, HttpServletRequest request,
                                   Model model, RedirectAttributes redirectAttributes, @ModelAttribute @Valid RegisterFormDTO registrationFormDTO) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Log In");
            return "login";
        }

        User user = userRepository.findByEmail(registrationFormDTO.getEmail());

        if (user == null) {
            errors.rejectValue("email", "user.invalid", "The given email does not exist");
            model.addAttribute("title", "Log In");
            return "login";
        }

        String password = loginFormDTO.getPassword();

        if (!user.isMatchingPassword(password)) {
            errors.rejectValue("password", "password.invalid", "Invalid password");
            model.addAttribute("title", "Log In");
            return "login";
        }
        //This adds our user to our html to be able to use tell if logged in
        setUserInSession(request.getSession(), user);
//        session.setAttribute("email", user.getEmail());
//        session.setAttribute("firstName", user.getFirstName());
//        session.setAttribute("lastName", user.getLastName());

//        redirectAttributes.addAttribute("theUser", user.getEmail());
        return "redirect:/userProfile";

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, Model model){
        request.getSession().invalidate();
        model.addAttribute("logout", "Log out");
        return "redirect:/login";
    }



}
