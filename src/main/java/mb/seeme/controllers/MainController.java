package mb.seeme.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String getHomePage(){
        return "home";
    }

    @RequestMapping("/login")
    public String getLogin() {
        return "login";
    }
}
