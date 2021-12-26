package mb.seeme.controllers;

import mb.seeme.model.terms.Term;
import mb.seeme.model.users.Client;
import mb.seeme.services.terms.TermService;
import mb.seeme.services.users.ServiceProviderService;
import mb.seeme.services.users.UserAuthenticationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDate;
import java.util.List;

@EnableGlobalMethodSecurity(jsr250Enabled = true)
@Controller
public class ServiceProviderController {

    private final ServiceProviderService providerService;
    private final UserAuthenticationService userAuthenticationService;
    private final TermService termService;

    public ServiceProviderController (ServiceProviderService providerService, UserAuthenticationService userAuthenticationService, TermService termService) {
        this.providerService = providerService;
        this.userAuthenticationService = userAuthenticationService;
        this.termService = termService;
    }

    @RolesAllowed("PROVIDER")
    @GetMapping({"providers/account", "providers/account.html"})
    public String getAccountDetails(){
        return "providers/account";
    }

    @RolesAllowed("PROVIDER")
    @GetMapping({"providers/calendar", "providers/calendar.html"})
    public String getProviderCalendar(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate selectedTermDate, Term term, Model model) {
        Long providerId = userAuthenticationService.getAuthenticatedProviderId();
        if(selectedTermDate == null)
            selectedTermDate = LocalDate.now();

        List<Term> results;
        if(term.getTermRealizedStatus() == null)
            results = termService.findAllFutureByProviderIdFromDate(providerId, selectedTermDate);
        else if(term.getTermRealizedStatus().name().equals("FREE"))
            results = termService.findAllFutureFreeByProviderIdFromDate(providerId, selectedTermDate);
        else
            results = termService.findAllFutureAppointedByProviderIdFromDate(providerId, selectedTermDate);

        if (!results.isEmpty())
            model.addAttribute("selections", results);

        return "providers/calendar";
    }

    @RolesAllowed("PROVIDER")
    @GetMapping({"providers/archive", "providers/archive.html"})
    public String processFindForm(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate termDate, Client client, Model model){
        if(termDate == null)
            termDate = LocalDate.now();
        if (client.getName() == null)
            client.setName("");

        Long providerId = userAuthenticationService.getAuthenticatedProviderId();
        List<Term> results = termService.findAllPastAppointedBeforeDateAndProviderIdAndClientName(providerId, "%" + client.getName() + "%", termDate);
        model.addAttribute("selections", results);

        return "providers/archive";
    }
}
