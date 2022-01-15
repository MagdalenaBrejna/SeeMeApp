package mb.seeme.controllers;

import mb.seeme.services.terms.TermService;
import mb.seeme.services.users.UserAuthenticationService;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import javax.annotation.security.RolesAllowed;

@EnableGlobalMethodSecurity(jsr250Enabled = true)
@Controller
public class TermController {

    private final TermService termService;
    private final UserAuthenticationService userAuthenticationService;

    public TermController(TermService termService, UserAuthenticationService userAuthenticationService) {
        this.termService = termService;
        this.userAuthenticationService = userAuthenticationService;
    }

    @RolesAllowed("PROVIDER")
    @GetMapping({"delete/{id}", "delete/{id}.html"})
    public String deleteTerm(@PathVariable("id") Long termId){
        termService.deleteById(termId);
        return "redirect:/providers/archive";
    }

    @RolesAllowed("CLIENT")
    @GetMapping({"cancel/{id}", "cancel/{id}.html"})
    public String cancelTerm(@PathVariable("id") Long termId){
        termService.cancelById(termId);
        return "redirect:/clients/terms";
    }
}
