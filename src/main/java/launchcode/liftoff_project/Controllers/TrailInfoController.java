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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("trail")
public class TrailInfoController {

    @Autowired
    private TrailRepository trailRepository;

    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("{trailid}")
    public String displayTrailInfoPage(Model model, HttpServletRequest request, @PathVariable int trailid){

        HttpSession session = request.getSession(false);
        if (session != null) {
            User theUser = authenticationController.getUserFromSession(session);
            model.addAttribute("theUser", theUser);
        }

        Optional<Trail> trailOpt = trailRepository.findById(trailid);
        Trail trail = null;

        if (trailOpt.isPresent()){
            trail = trailOpt.get();

            System.out.println((getRatingsByValue(trail, 5).size()) / (trail.getRatings().size()));

            model.addAttribute("star1ratings", getRatingsByValue(trail, 1).size());
            model.addAttribute("star2ratings", getRatingsByValue(trail, 2).size());
            model.addAttribute("star3ratings", getRatingsByValue(trail, 3).size());
            model.addAttribute("star4ratings", getRatingsByValue(trail, 4).size());
            model.addAttribute("star5ratings", getRatingsByValue(trail, 5).size());
            model.addAttribute("allRatings", ratingRepository.findAll());
            model.addAttribute("trail", trail);
            model.addAttribute("trails", trailRepository.findAll());

            return "trail";

        } else {
            return "No trail at this ID.";
        }
    }

    @PostMapping("{trailid}")
    public String rateTrail(Model model, HttpServletRequest request, @PathVariable int trailid, @RequestParam int ratingValue, @RequestParam int trailId, @RequestParam int userId){

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
        }

        return "trail";
    }

    public List<Rating> getRatingsByValue(Trail trail, int ratingValue){
        List<Rating> ratingsByValue = new ArrayList<>();

        List<Rating> allRatings = (List<Rating>) ratingRepository.findAll();

        for (Rating rating : allRatings){
            if (rating.getRatingValue() == ratingValue && rating.getTrail() == trail){
                ratingsByValue.add(rating);
            }
        }

        return ratingsByValue;
    }
}

