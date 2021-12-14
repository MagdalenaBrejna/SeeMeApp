package mb.seeme.controllers;

import mb.seeme.model.terms.Term;
import mb.seeme.model.users.Client;
import mb.seeme.services.terms.TermService;
import mb.seeme.services.users.ServiceProviderService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
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

    @GetMapping({"providers/calendar/{id}", "providers/calendar/{id}.html"})
    public String getProviderCalendar(Model model, @PathVariable Long id) {
        List<Term> results = termService.findAllFutureByProviderId(id);
        if (!results.isEmpty())
            model.addAttribute("selections", results);

        return "providers/calendar";
    }

    @GetMapping({"providers/archive", "providers/archive.html"})
    public String processFindForm(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate termDate, Client client, @RequestParam("providerId") Long id, Model model){
        if(termDate == null)
            termDate = LocalDate.now();
        if (client.getName() == null)
            client.setName("");

        List<Term> results = termService.findAllPastAppointedBeforeDateAndProviderIdAndClientName(id, "%" + client.getName() + "%", termDate);
        model.addAttribute("selections", results);

        return "providers/archive";
    }
}
