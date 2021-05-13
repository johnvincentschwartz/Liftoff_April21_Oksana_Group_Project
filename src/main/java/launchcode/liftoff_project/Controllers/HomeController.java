package launchcode.liftoff_project.Controllers;

import giorg.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping()
    public String displayNavBar(@RequestParam(value = "theUser", required = false) String theUser,
                                Model model){
        if(theUser != null) {
            model.addAttribute("theUser", theUser);
        }
        model.addAttribute("All Trials", "All Trials");

        return "index";
    }
}
