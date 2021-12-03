package mb.seeme.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ServiceProviderController {

    @GetMapping({"providers/account", "providers/account.html"})
    public String getAccountDetails(){
        return "providers/account";
    }
}
