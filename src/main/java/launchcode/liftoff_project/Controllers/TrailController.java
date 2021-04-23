package launchcode.liftoff_project.Controllers;

import launchcode.liftoff_project.Model.Trail;
import launchcode.liftoff_project.Model.data.TrailRepository;
import org.hibernate.annotations.SQLInsert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

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

    @PostMapping("results")
    public String displayFilterResults(Model model, @RequestParam Double minLength, @RequestParam Double maxLength){
        Iterable<Trail> allTrails = trailRepository.findAll();
        Iterable<Trail> results = filterTrailsLength(minLength, maxLength, allTrails);

        model.addAttribute("trails", results);

        return "alltrails";

    }

    public static ArrayList<Trail> filterTrailsLength(Double minLength, Double maxLength, Iterable<Trail> allTrails){
        ArrayList<Trail> results = new ArrayList<>();

        if (minLength == null){ minLength = 0.0; }
        if (maxLength == null){ maxLength = 1000.0;}

        for (Trail trail : allTrails){
            if (trail.getLength() > minLength && trail.getLength() < maxLength) {
                results.add(trail);
            }
        }

        return results;
    }

}
