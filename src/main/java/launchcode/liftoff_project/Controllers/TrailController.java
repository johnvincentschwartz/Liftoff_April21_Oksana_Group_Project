package launchcode.liftoff_project.Controllers;

import launchcode.liftoff_project.Model.Trail;
import launchcode.liftoff_project.Model.data.TrailRepository;
import org.hibernate.annotations.SQLInsert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
@RequestMapping("alltrails")

public class TrailController {

    @Autowired
    private TrailRepository trailRepository;

    @GetMapping
    public String index(Model model){

        model.addAttribute("trails", trailRepository.findAll());

        return "alltrails";
    }

    @PostMapping()
    public String displayFilterResults(Model model, @RequestParam Double minLength, @RequestParam Double maxLength,
                                       @RequestParam List<Integer> difficulty, @RequestParam String searchLocation, @RequestParam Double maxDistance, @RequestParam String sort){

        Iterable<Trail> allTrailsSorted = trailRepository.findAll(Sort.by(Sort.Direction.ASC, sort));
        Collection<Trail> results = filterTrails(minLength, maxLength, difficulty, allTrailsSorted);

        model.addAttribute("maxDistance", maxDistance);
        model.addAttribute("searchLocation", searchLocation);
        model.addAttribute("sort", sort);
        model.addAttribute("trails", results);


        return "alltrails";

    }

    public static ArrayList<Trail> filterTrails(Double minLength, Double maxLength, List<Integer> difficulty, Iterable<Trail> allTrails){
        ArrayList<Trail> results = new ArrayList<>();

        if (minLength == null){ minLength = 0.0; }
        if (maxLength == null){ maxLength = 1000.0;}
        if (difficulty == null){
            difficulty = new ArrayList<>();
            difficulty.add(1);
            difficulty.add(2);
            difficulty.add(3);
            difficulty.add(4);
            difficulty.add(5);
        }

        for (Trail trail : allTrails){

            if (
                trail.getLength() > minLength && trail.getLength() < maxLength
                && difficulty.contains(trail.getDifficulty())) {
                results.add(trail);
            }
        }

        return results;
    }

}
