package mb.seeme.controllers;

import mb.seeme.security.ApplicationUserRole;
import mb.seeme.services.terms.TermService;
import mb.seeme.services.users.UserAuthenticationService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TermController {

    private final TermService termService;
    private final UserAuthenticationService userAuthenticationService;

    public TermController(TermService termService, UserAuthenticationService userAuthenticationService) {
        this.termService = termService;
        this.userAuthenticationService = userAuthenticationService;
    }

    @GetMapping({"cancel/{id}", "cancel/{id}.html"})
    public String cancelTerm(@PathVariable("id") Long termId) {
        termService.cancelById(termId, userAuthenticationService.getAuthenticatedUser());
        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(ApplicationUserRole.CLIENT.getUserRole()))
            return "redirect:/clients/terms";
        else
            return "redirect:/providers/calendar";
    }

    @GetMapping({"delete/{id}", "delete/{id}.html"})
    public String deleteTerm(@PathVariable("id") Long termId) {
        termService.deleteByTermId(termId, userAuthenticationService.getAuthenticatedUser());
        return "redirect:/providers/calendar";
    }
}
