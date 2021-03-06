package mb.seeme.controllers;

import mb.seeme.model.terms.Term;
import mb.seeme.model.users.Client;
import mb.seeme.model.users.ServiceProvider;
import mb.seeme.services.terms.TermService;
import mb.seeme.services.users.ImageService;
import mb.seeme.services.users.ServiceProviderService;
import mb.seeme.services.users.UserAuthenticationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("providers")
public class ServiceProviderController {

    private final UserAuthenticationService userAuthenticationService;
    private final ServiceProviderService providerService;
    private final ImageService imageService;
    private final TermService termService;

    public ServiceProviderController(ServiceProviderService providerService, UserAuthenticationService userAuthenticationService, TermService termService, ImageService imageService) {
        this.userAuthenticationService = userAuthenticationService;
        this.providerService = providerService;
        this.imageService = imageService;
        this.termService = termService;
    }

    @GetMapping({"/calendar", "/calendar.html"})
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

    @GetMapping({"/archive", "/archive.html"})
    public String getProviderArchive(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate termDate, Client client, Model model) {
        if(termDate == null)
            termDate = LocalDate.now();
        if (client.getName() == null)
            client.setName("");

        Long providerId = userAuthenticationService.getAuthenticatedProviderId();
        List<Term> results = termService.findAllPastAppointedBeforeDateAndProviderIdAndClientName(providerId, "%" + client.getName() + "%", termDate);

        model.addAttribute("selections", results);
        return "providers/archive";
    }

    @PostMapping({"/account/image", "/account/image.html"})
    public String addProviderImage(@RequestParam("file") MultipartFile file) {
        Long providerId = userAuthenticationService.getAuthenticatedProviderId();
        if(!file.isEmpty())
            imageService.saveProviderImage(providerId, file);

        return "redirect:/providers/details";
    }

    @GetMapping({"/account", "/account.html"})
    public String getAccountDetails(Model model) {
        Long providerId = userAuthenticationService.getAuthenticatedProviderId();
        ServiceProvider provider = providerService.findById(providerId);

        model.addAttribute("providerImage", providerService.getProviderImage(provider));
        model.addAttribute("provider", provider);
        return "providers/account";
    }

    @GetMapping({"/details", "/details.html"})
    public String getUpdatingDetailsForm(Model model) {
        Long providerId = userAuthenticationService.getAuthenticatedProviderId();
        ServiceProvider provider = providerService.findById(providerId);

        model.addAttribute("providerImage", providerService.getProviderImage(provider));
        model.addAttribute("provider", provider);

        return "providers/details";
    }

    @PostMapping({"/calendar/newTerm", "/calendar/newTerm.html"})
    public String addNewTerms(@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime termDateTime, String termsNumber, String termDuration, String serviceName) {
        Long providerId = userAuthenticationService.getAuthenticatedProviderId();
        ServiceProvider provider = providerService.findById(providerId);
        int termsToSave = Integer.valueOf(termsNumber);
        int termsToSaveDuration = Integer.valueOf(termDuration);

        if(termDateTime != null && termDateTime.isAfter(LocalDateTime.now()) && termsToSave > 0 && termsToSaveDuration > 0)
            termService.addNewTerms(provider, termDateTime, termsToSave, termsToSaveDuration, serviceName);

        return "redirect:/providers/calendar";
    }

    @PostMapping({"/calendar/addDescription/{id}", "/calendar/addDescription/{id}.html"})
    public String addTermDescription(@PathVariable("id") Long termId, @RequestParam("desc") String termDescription) {
        if(!termDescription.equals(""))
            termService.addTermDescription(termDescription, termId);
        return "redirect:/providers/calendar";
    }

    @PostMapping({"/details/save", "/details/save.html"})
    public String updateAccountDetails(ServiceProvider provider) {
        Long providerId = userAuthenticationService.getAuthenticatedProviderId();
        ServiceProvider authProvider = providerService.findById(providerId);

        providerService.updateProviderDetails(authProvider, provider);
        return "redirect:/providers/account";
    }
}
