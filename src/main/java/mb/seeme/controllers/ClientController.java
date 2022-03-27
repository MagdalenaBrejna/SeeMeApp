package mb.seeme.controllers;

import mb.seeme.model.terms.Term;
import mb.seeme.model.users.Client;
import mb.seeme.model.users.ServiceProvider;
import mb.seeme.model.users.ServiceProviderTerm;
import mb.seeme.services.terms.TermService;
import mb.seeme.services.users.ClientService;
import mb.seeme.services.users.ServiceProviderService;
import mb.seeme.services.users.UserAuthenticationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@Controller
public class ClientController {

    private final UserAuthenticationService userAuthenticationService;
    private final ClientService clientService;
    private final ServiceProviderService providerService;
    private final TermService termService;

    public ClientController(UserAuthenticationService userAuthenticationService, ClientService clientService, ServiceProviderService providerService, TermService termService) {
        this.userAuthenticationService = userAuthenticationService;
        this.clientService = clientService;
        this.providerService = providerService;
        this.termService = termService;
    }


    @GetMapping({"clients/account", "clients/account.html"})
    public String getClientAccountDetails(HttpServletRequest request, Model model) {
        Long clientId = userAuthenticationService.getAuthenticatedClientId();
        Client client = clientService.findById(clientId);

        model.addAttribute("client", client);
        return "clients/account";

    }


    @GetMapping({"clients/terms", "clients/terms.html"})
    public String findClientTerms(Model model){
        Long clientId = userAuthenticationService.getAuthenticatedClientId();

        List<Term> futureTerms = termService.findAllFutureByClientId(clientId);
        model.addAttribute("futureSelections", futureTerms);

        List<Term> pastTerms = termService.findAllPastByClientId(clientId);
        model.addAttribute("pastSelections", pastTerms);

        return "clients/terms";
    }


    @GetMapping({"clients/provider/{id}", "clients/provider/{id}.html"})
    public String findProviderFreeTerms(@PathVariable("id") Long providerId, Model model){
        ServiceProvider provider = providerService.findById(providerId);
        model.addAttribute("providerSelections", provider);

        List<Term> futureTerms = termService.findAllFutureFreeByProviderId(providerId);
        model.addAttribute("futureSelections", futureTerms);

        return "clients/provider";
    }


    @GetMapping({"clients/provider/book/{termId}", "clients/provider/book/{termId}.html"})
    public String bookTerm(@PathVariable("termId") Long termId, Model model){
        Long clientId = userAuthenticationService.getAuthenticatedClientId();
        termService.bookTermByClientId(clientId, termId);
        return "redirect:/clients/terms";
    }


    @GetMapping({"clients/find", "clients/find.html"})
    public String findTerms(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate selectedTermDate, ServiceProviderTerm serviceProviderTerm, Model model) {
        if(selectedTermDate == null)
            selectedTermDate = LocalDate.now();
        if(serviceProviderTerm.getProviderName() == null)
            serviceProviderTerm.setProviderName("");
        if(serviceProviderTerm.getCity() == null)
            serviceProviderTerm.setCity("");
        if(serviceProviderTerm.getProviderField() == null)
            serviceProviderTerm.setProviderField("");

        List<ServiceProviderTerm> results = providerService.findAllByNameAndCityAndFieldFromDateInTermOrder(serviceProviderTerm, selectedTermDate);

        if (!results.isEmpty())
            model.addAttribute("selections", results);

        return "clients/find";
    }

    @GetMapping({"clients/details", "clients/details.html"})
    public String getUpdatingDetailsForm(Model model){
        Long clientId = userAuthenticationService.getAuthenticatedClientId();
        Client client = clientService.findById(clientId);

        model.addAttribute("client", client);
        return "clients/details";
    }

    @PostMapping({"/clients/details/save", "/clients/details/save.html"})
    public String updateAccountDetails(Client client){
        Long clientId = userAuthenticationService.getAuthenticatedClientId();
        Client authClient = clientService.findById(clientId);

        clientService.updateClientDetails(authClient, client);
        return "redirect:/clients/account";
    }
}
