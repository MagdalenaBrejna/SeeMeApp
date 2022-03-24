package mb.seeme.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

   @GetMapping("/")
   public String getHomePage(){
        return "home";
    }

   @GetMapping("/login")
   public String getLogin() {
        return "login";
   }

}
