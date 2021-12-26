package mb.seeme.controllers;

import mb.seeme.model.terms.Term;
import mb.seeme.services.terms.TermService;
import mb.seeme.services.users.ClientService;
import mb.seeme.services.users.UserAuthenticationService;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.annotation.security.RolesAllowed;
import java.util.List;

@EnableGlobalMethodSecurity(jsr250Enabled = true)
@Controller
public class ClientController {

    private final UserAuthenticationService userAuthenticationService;
    private final ClientService clientService;
    private final TermService termService;

    public ClientController(UserAuthenticationService userAuthenticationService, ClientService clientService, TermService termService) {
        this.userAuthenticationService = userAuthenticationService;
        this.clientService = clientService;
        this.termService = termService;
    }

    @RolesAllowed("CLIENT")
    @GetMapping({"clients/account", "clients/account.html"})
    public String getAccountDetails(){
        return "clients/account";
    }

    @RolesAllowed("CLIENT")
    @GetMapping({"clients/terms", "clients/terms.html"})
    public String findClientPastTerms(Model model){
        Long clientId = userAuthenticationService.getAuthenticatedClientId();

        List<Term> futureTerms = termService.findAllFutureByClientId(clientId);
        model.addAttribute("futureSelections", futureTerms);
        List<Term> pastTerms = termService.findAllPastByClientId(clientId);
        model.addAttribute("pastSelections", pastTerms);

        return "clients/terms";
    }
}
