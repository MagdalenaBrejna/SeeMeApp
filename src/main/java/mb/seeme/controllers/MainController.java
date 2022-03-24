package mb.seeme.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    //@Autowired
    //AuthenticationManager authenticationManager;
    //@Autowired
    //JwtUtils jwtUtils;

    @GetMapping("/")
    public String getHomePage(){
        return "home";
    }

   @GetMapping("/login")
   public String getLogin() {
        return "login";
   }

    @PostMapping("/login")
    public String postLogin() {
        System.out.println("weszlo");
        return "redirect:/home";
    }
    //usunelam po

/*
    @RequestMapping("/logout")
    public void logOut(HttpServletRequest request, HttpServletResponse response){
        response.
    }

 */
}
