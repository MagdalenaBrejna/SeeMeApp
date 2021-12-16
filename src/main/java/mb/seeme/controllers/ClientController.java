package mb.seeme.controllers;

import mb.seeme.services.users.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientController {

    private final ClientService clientService;

    public ClientController (ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping({"clients/account", "clients/account.html"})
    public String getAccountDetails(){
        return "clients/account";
    }
}
