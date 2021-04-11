package org.launchcode.Liftoff_April21_Oksana_Group_Project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("")
    public String index(){
        return "index";
    }
}
