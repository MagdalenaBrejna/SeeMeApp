package mb.seeme.controllers;

import mb.seeme.model.terms.Term;
import mb.seeme.services.terms.TermService;
import mb.seeme.services.users.ServiceProviderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@Controller
public class ServiceProviderController {

    private final ServiceProviderService providerService;
    private final TermService termService;

    public ServiceProviderController (ServiceProviderService providerService, TermService termService) {
        this.providerService = providerService;
        this.termService = termService;
    }

    @GetMapping({"providers/account", "providers/account.html"})
    public String getAccountDetails(){
        return "providers/account";
    }

    @GetMapping({"providers/archive/{id}", "providers/archive/{id}.html"})
    public String getProviderArchive(Model model, @PathVariable Long id) {
        List<Term> results = termService.findAllPastAppointedByProviderId(id);
        if (!results.isEmpty())
            model.addAttribute("selections", results);

        return "providers/archive";
    }
}
