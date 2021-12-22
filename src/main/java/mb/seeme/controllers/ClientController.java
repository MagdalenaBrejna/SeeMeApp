package mb.seeme.controllers;

import mb.seeme.services.users.ClientService;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import javax.annotation.security.RolesAllowed;

@EnableGlobalMethodSecurity(jsr250Enabled = true)
@Controller
public class ClientController {

    private final ClientService clientService;

    public ClientController (ClientService clientService) {
        this.clientService = clientService;
    }

    @RolesAllowed("CLIENT")
    @GetMapping({"clients/account", "clients/account.html"})
    public String getAccountDetails(){
        return "clients/account";
    }
}
