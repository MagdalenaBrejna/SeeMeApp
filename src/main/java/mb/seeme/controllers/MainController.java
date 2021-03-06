package mb.seeme.controllers;

import mb.seeme.exceptions.UserAlreadyExistException;
import mb.seeme.messages.UserMessages;
import mb.seeme.model.users.UserDto;
import mb.seeme.services.users.UserAuthenticationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;

@Controller
public class MainController {

   private final UserAuthenticationService userAuthService;

   public MainController(UserAuthenticationService userAuthService) {
        this.userAuthService = userAuthService;
    }

   @GetMapping("/")
   public String getHomePage(){
        return "home";
    }

   @GetMapping("/login")
   public String getLogin() {
        return "login";
   }

    @GetMapping({"/signup", "signup.html"})
    public String showRegistrationForm(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "signup";
    }

    @PostMapping("/signup")
    public String registerUserAccount(@Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors())
            return "signup";
        try {
            userAuthService.registerNewUserAccount(userDto);
        } catch (UserAlreadyExistException e) {
            model.addAttribute("message", UserMessages.USER_EXISTS);
            return "signup";
        }
        return "login";
    }
}
