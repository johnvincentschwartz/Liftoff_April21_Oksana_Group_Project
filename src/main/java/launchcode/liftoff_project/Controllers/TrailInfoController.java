package launchcode.liftoff_project.Controllers;

import launchcode.liftoff_project.Model.Rating;
import launchcode.liftoff_project.Model.RatingKey;
import launchcode.liftoff_project.Model.Trail;
import launchcode.liftoff_project.Model.User;
import launchcode.liftoff_project.Model.data.RatingRepository;
import launchcode.liftoff_project.Model.data.TrailRepository;
import launchcode.liftoff_project.Model.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class TrailInfoController {

    @Autowired
    private TrailRepository trailRepository;

    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("trailOne")
    public String displayTrailInfoPage(Model model, HttpServletRequest request){

        HttpSession session = request.getSession(false);
        if (session != null) {
            User theUser = authenticationController.getUserFromSession(session);
            model.addAttribute("theUser", theUser);
        }

        model.addAttribute("trailOne", "trailOne");
        model.addAttribute("trails", trailRepository.findAll());

        return "trailOne";
    }

    @PostMapping("trailOne")
    public String rateTrail(Model model, @RequestParam int ratingValue, @RequestParam int trailId, @RequestParam int userId){

        Optional<Trail> ratedTrailOpt = trailRepository.findById(trailId);
        Trail ratedTrail = null;
        if (ratedTrailOpt.isPresent()){
            ratedTrail = ratedTrailOpt.get();
        }

        Optional<User> ratingUserOpt = userRepository.findById(userId);
        User ratingUser = null;
        if (ratingUserOpt.isPresent()){
            ratingUser = ratingUserOpt.get();
        }

        if (ratedTrail != null && ratingUser != null){
            Rating newRating = new Rating(ratingUser, ratedTrail, ratingValue);
            ratingRepository.save(newRating);
            ratedTrail.addRating(ratingUser, ratingValue);
        } else {
            System.out.println(ratedTrail);
            System.out.println(ratingUser);
        }

        return "trailOne";
    }

    @GetMapping("trailTwo")
    public String trailInfoTwo(Model model){

        model.addAttribute("trailTwo", "trailTwo");
        return "trailTwo";
    }

    @GetMapping("trailThree")
    public String trailInfoThree(Model model){

        model.addAttribute("trailThree", "trailThree");
        return "trailThree";
    }

    @GetMapping("trailFour")
    public String trailInfoFour(Model model){

        model.addAttribute("trailFour", "trailFour");
        return "trailFour";
    }
}
