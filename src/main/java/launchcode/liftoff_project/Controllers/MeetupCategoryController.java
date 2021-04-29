package launchcode.liftoff_project.Controllers;

import launchcode.liftoff_project.Model.MeetupCategory;
import launchcode.liftoff_project.Model.data.MeetupCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("meetupCategories")
public class MeetupCategoryController {

    @Autowired
    private MeetupCategoryRepository meetupCategoryRepository;

    @GetMapping
    public String displayAllMeetupCategories(Model model) {
        model.addAttribute("title", "All Meetup Categories");
        model.addAttribute("categories", meetupCategoryRepository.findAll());
        return "meetupCategories/index";
    }

    @GetMapping("create")
    public String renderCreateMeetupCategoryForm(Model model) {
        model.addAttribute("title", "Create A New Meetup Category");
        model.addAttribute(new MeetupCategory());
        return "meetupCategories/create";
    }

    @PostMapping("create")
    public String processCreateMeetupCategoryForm(@Valid @ModelAttribute MeetupCategory meetupCategory,
                                                  Errors errors, Model model) {
        if(errors.hasErrors()) {
            model.addAttribute("title", "Create A New Meetup Category");
            model.addAttribute(new MeetupCategory());
            return "meetupCategories/create";
        }

        meetupCategoryRepository.save(meetupCategory);
        return "redirect:";
    }

    @GetMapping("delete")
    public String displayDeleteMeetupsCategoryForm(Model model) {
        model.addAttribute("title", "Delete A Meetup Category");
        model.addAttribute("categories", meetupCategoryRepository.findAll());
        return "meetupCategories/delete";
    }

    @PostMapping("delete")
    public String processDeleteMeetupsCategoryForm(@RequestParam(required = false) int[] meetupCategoryIds) {
        if(meetupCategoryIds != null) {
            for (int id: meetupCategoryIds) {
                meetupCategoryRepository.deleteById(id);
            }
        }
        return "redirect:";
    }
}
