package launchcode.liftoff_project.Controllers;

import launchcode.liftoff_project.Model.Rating;
import launchcode.liftoff_project.Model.Trail;
import launchcode.liftoff_project.Model.User;
import launchcode.liftoff_project.Model.data.RatingRepository;
import launchcode.liftoff_project.Model.data.TrailRepository;
import launchcode.liftoff_project.Model.data.UserRepository;
import launchcode.liftoff_project.Model.dto.TrailFilterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("alltrails")
public class TrailController {

    @Autowired
    private TrailRepository trailRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    AuthenticationController authenticationController;

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public String index(Model model, HttpServletRequest request){

        TrailFilterDTO trailFilterDTO = new TrailFilterDTO();

        HttpSession session = request.getSession(false);
        if (session != null) {
            User theUser = authenticationController.getUserFromSession(session);
            model.addAttribute("theUser", theUser);
        }

        model.addAttribute("trailFilterDTO", trailFilterDTO);
        model.addAttribute("trails", trailRepository.findAll(Sort.by(Sort.Direction.DESC, "averageRating")));



        return "alltrails/index";
    }

    @PostMapping
    public String displayFilterResults(Model model, @ModelAttribute @Valid TrailFilterDTO trailFilterDTO,
                                       HttpServletRequest request, @RequestParam String sort){

        Iterable<Trail> allTrailsSorted = trailRepository.findAll(Sort.by(Sort.Direction.DESC, sort));
        Collection<Trail> results = filterTrails(trailFilterDTO, allTrailsSorted);
        String searchLocation = trailFilterDTO.getSearchLocation();

        HttpSession session = request.getSession(false);
        if (session != null) {
            User theUser = authenticationController.getUserFromSession(session);
            model.addAttribute("theUser", theUser);
        }

        model.addAttribute("searchLocation", searchLocation);
        model.addAttribute("sort", sort);
        model.addAttribute("trails", results);

        return "alltrails/index";
    }

    @PostMapping("modify-favorites")
    public String addFavorite(@RequestParam int trailId, @RequestParam int userId, @RequestParam String type,
                              @ModelAttribute @Valid TrailFilterDTO trailFilterDTO,
                              HttpServletRequest request, @RequestParam String sort, Model model){

        Iterable<Trail> allTrailsSorted = trailRepository.findAll(Sort.by(Sort.Direction.DESC, sort));
        Collection<Trail> results = filterTrails(trailFilterDTO, allTrailsSorted);
        String searchLocation = trailFilterDTO.getSearchLocation();
        HttpSession session = request.getSession(false);

        if (session != null) {
            User currentUser = authenticationController.getUserFromSession(session);
            model.addAttribute("theUser", currentUser);
            Optional<Trail> selectedTrailOpt = trailRepository.findById(trailId);
            if (selectedTrailOpt.isPresent()){
                Trail selectedTrail = selectedTrailOpt.get();
                if (type.equals("add")) {
                    currentUser.addFavoriteTrail(selectedTrail);
                    userRepository.save(currentUser);
                } else if (type.equals("remove")) {
                    currentUser.removeFavoriteTrail(selectedTrail);
                    userRepository.save(currentUser);
                }
            }
        }

        model.addAttribute("searchLocation", searchLocation);
        model.addAttribute("sort", sort);
        model.addAttribute("trails", results);

        return "alltrails/index";
    }

    @PostMapping("rate")
    public String rateTrail(Model model, @ModelAttribute @Valid TrailFilterDTO trailFilterDTO,
                            HttpServletRequest request, @RequestParam String sort, int trailId, int userId, int ratingValue){

        Iterable<Trail> allTrailsSorted = trailRepository.findAll(Sort.by(Sort.Direction.DESC, sort));
        Collection<Trail> results = filterTrails(trailFilterDTO, allTrailsSorted);
        String searchLocation = trailFilterDTO.getSearchLocation();
        HttpSession session = request.getSession(false);

        if (session != null) {
            User currentUser = authenticationController.getUserFromSession(session);
            model.addAttribute("theUser", currentUser);
        }

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

        model.addAttribute("searchLocation", searchLocation);
        model.addAttribute("sort", sort);
        model.addAttribute("trails", results);

        return "alltrails/index";
    }

    public static ArrayList<Trail> filterTrails(TrailFilterDTO dto, Iterable<Trail> allTrails){

        ArrayList<Trail> results = new ArrayList<>();

        for (Trail trail : allTrails){
            if (
                    (dto.getDogFriendly() && !trail.getDogs())
                            || (dto.getKidFriendly() && !trail.getFamily())
                            || (dto.getBikeFriendly() && !trail.getBikes())
                            || (dto.getNearWater() && trail.getWater().toString().equals("none"))
                            || (dto.getNearWoods() && !trail.getWoods())
            ) {continue;}

            if (dto.getMinRating() != null){
                if (
                        trail.getAverageRating() == null
                                || trail.getAverageRating() < dto.getMinRating()
                ){continue;}
            }

            if (
                    trail.getLength() > dto.getMinLength()
                            && trail.getLength() < dto.getMaxLength()
                            && dto.getDifficulty().contains(trail.getDifficulty())
                            && dto.getTrailSurface().contains(trail.getType().toString())
            ) {
                results.add(trail);
            }
        }

        return results;
    }
}