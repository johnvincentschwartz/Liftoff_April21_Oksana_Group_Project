package launchcode.liftoff_project.Controllers;

import launchcode.liftoff_project.Model.data.TrailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TrailInfoController {

    @Autowired
    private TrailRepository trailRepository;

    @GetMapping("trailOne")
    public String displayTrailInfoPage(Model model){

        model.addAttribute("trailOne", "trailOne");
        model.addAttribute("trails", trailRepository.findAll());
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
