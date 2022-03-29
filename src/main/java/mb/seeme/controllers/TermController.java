package mb.seeme.controllers;

import mb.seeme.security.ApplicationUserRole;
import mb.seeme.services.terms.TermService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TermController {

    private final TermService termService;

    public TermController(TermService termService) {
        this.termService = termService;
    }


    @GetMapping({"cancel/{id}", "cancel/{id}.html"})
    public String cancelTerm(@PathVariable("id") Long termId){
        termService.cancelById(termId);
        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(ApplicationUserRole.CLIENT.getUserRole()))
            return "redirect:/clients/terms";
        else
            return "redirect:/providers/calendar";
    }


    @GetMapping({"delete/{id}", "delete/{id}.html"})
    public String deleteTerm(@PathVariable("id") Long termId){
        termService.deleteById(termId);
        return "redirect:/providers/archive";
    }

}
