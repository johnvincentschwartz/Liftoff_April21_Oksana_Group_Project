package launchcode.liftoff_project.Controllers;

import launchcode.liftoff_project.Model.Trail;
import launchcode.liftoff_project.Model.User;
import launchcode.liftoff_project.Model.data.RatingRepository;
import launchcode.liftoff_project.Model.data.TrailRepository;
import launchcode.liftoff_project.Model.dto.TrailFilterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("alltrails")
public class TrailController {

    @Autowired
    private TrailRepository trailRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    AuthenticationController authenticationController;

    @GetMapping
    public String index(Model model, HttpServletRequest request){

        TrailFilterDTO trailFilterDTO = new TrailFilterDTO();

        HttpSession session = request.getSession(false);
        if (session != null) {
            User theUser = authenticationController.getUserFromSession(session);
            model.addAttribute("theUser", theUser);
        }

        model.addAttribute("trailFilterDTO", trailFilterDTO);
        model.addAttribute("trails", trailRepository.findAll());

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
