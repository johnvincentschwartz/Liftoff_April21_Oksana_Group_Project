package launchcode.liftoff_project.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping()
    public String displayNavBar(Model model){
        model.addAttribute("All Trials", "All Trials");

        return "index";
    }
}
