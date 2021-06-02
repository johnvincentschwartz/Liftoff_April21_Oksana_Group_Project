package launchcode.liftoff_project.Controllers;

import launchcode.liftoff_project.Model.Trail;
import launchcode.liftoff_project.Model.data.TrailRepository;
import launchcode.liftoff_project.Model.TrailData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("search")
public class SearchController {

    @Autowired
    private TrailRepository trailRepository;

    @RequestMapping("")
    public String search(Model model){
        model.addAttribute("trails", trailRepository.findAll());
        return "search";
    }

    @PostMapping("results")
    public String displaySearchResults(Model model, @RequestParam String searchTerm){
        Iterable<Trail> trails;
        trails = TrailData.searchValue(searchTerm, trailRepository.findAll());
        model.addAttribute("trails", trails );
        return "search";
    }
}
