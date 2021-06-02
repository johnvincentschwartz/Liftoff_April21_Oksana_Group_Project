package launchcode.liftoff_project.Controllers;

import launchcode.liftoff_project.Model.Trail;
import launchcode.liftoff_project.Model.data.TrailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class TrailInfoController {

    @Autowired
    private TrailRepository trailRepository;

    @GetMapping("trail/{id}")
    public String displayTrailInfoPage(Model model, @PathVariable int id){

        Optional<Trail> trailOpt = trailRepository.findById(id);
        Trail trail = null;

        if (trailOpt.isPresent()){
            trail = trailOpt.get();
        }

        model.addAttribute("trail", trail);
        return "trail";
    }

}
