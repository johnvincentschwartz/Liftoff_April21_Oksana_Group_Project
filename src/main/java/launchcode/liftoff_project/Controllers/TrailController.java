package launchcode.liftoff_project.Controllers;

import launchcode.liftoff_project.Model.Trail;
import launchcode.liftoff_project.Model.data.TrailRepository;
import launchcode.liftoff_project.Model.dto.TrailFilterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("alltrails")
public class TrailController {

    @Autowired
    private TrailRepository trailRepository;

    @GetMapping
    public String index(Model model){

        TrailFilterDTO trailFilterDTO = new TrailFilterDTO();

        model.addAttribute("trailFilterDTO", trailFilterDTO);
        model.addAttribute("trails", trailRepository.findAll());

        return "alltrails";
    }

    @PostMapping
    public String displayFilterResults(Model model, @ModelAttribute @Valid TrailFilterDTO trailFilterDTO,
                                       HttpServletRequest request, @RequestParam String sort){

        Iterable<Trail> allTrailsSorted = trailRepository.findAll(Sort.by(Sort.Direction.ASC, sort));
        Collection<Trail> results = filterTrails(trailFilterDTO, allTrailsSorted);
        String searchLocation = trailFilterDTO.getSearchLocation();
        System.out.println(searchLocation);

        model.addAttribute("searchLocation", searchLocation);
        model.addAttribute("sort", sort);
        model.addAttribute("trails", results);

        return "alltrails";

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
