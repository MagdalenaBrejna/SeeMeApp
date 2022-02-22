package mb.seeme.controllers;

import mb.seeme.model.terms.Term;
import mb.seeme.model.users.Client;
import mb.seeme.model.users.ServiceProvider;
import mb.seeme.services.terms.TermService;
import mb.seeme.services.users.ImageService;
import mb.seeme.services.users.ServiceProviderService;
import mb.seeme.services.users.UserAuthenticationService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@EnableGlobalMethodSecurity(jsr250Enabled = true)
@Controller
public class ServiceProviderController {

    private final ServiceProviderService providerService;
    private final UserAuthenticationService userAuthenticationService;
    private final TermService termService;
    private final ImageService imageService;

    public ServiceProviderController(ServiceProviderService providerService, UserAuthenticationService userAuthenticationService, TermService termService, ImageService imageService) {
        this.providerService = providerService;
        this.userAuthenticationService = userAuthenticationService;
        this.termService = termService;
        this.imageService = imageService;
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

    @RolesAllowed("PROVIDER")
    @PostMapping({"providers/account/image", "providers/account/image.html"})
    public String addProviderImage(@RequestParam("file") MultipartFile file){
        Long providerId = userAuthenticationService.getAuthenticatedProviderId();
        if(!file.isEmpty())
            imageService.saveProviderImage(providerId, file);

        return "redirect:/providers/account";
    }

    @RolesAllowed("PROVIDER")
    @GetMapping({"providers/account", "providers/account.html"})
    public String getAccountDetails(Model model) throws IOException {
        Long providerId = userAuthenticationService.getAuthenticatedProviderId();
        ServiceProvider provider = providerService.findById(providerId);

        byte[] encodeBase64ProviderImage = null;
        if (provider.getProviderImage() != null)
            encodeBase64ProviderImage = Base64.encodeBase64(provider.getProviderImage());
        else
            encodeBase64ProviderImage = Base64.encodeBase64(Files.readAllBytes(Paths.get("src/main/resources/static/resources/images/user.jpg")));

        String providerImage = new String(encodeBase64ProviderImage, "UTF-8");
        model.addAttribute("providerImage", providerImage);
        model.addAttribute("provider", provider);

        return "providers/account";
    }

    @RolesAllowed("PROVIDER")
    @PostMapping({"providers/calendar/newTerm", "providers/calendar/newTerm.html"})
    public String addNewTerms(@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime termDateTime, String termsNumber, String termDuration, Model model){
        Long providerId = userAuthenticationService.getAuthenticatedProviderId();
        ServiceProvider provider = providerService.findById(providerId);
        int termsToSave = Integer.valueOf(termsNumber);
        int termsToSaveDuration = Integer.valueOf(termDuration);

        if(termDateTime.isAfter(LocalDateTime.now()) && termsToSave > 0 && termsToSaveDuration > 0)
            termService.addNewTerms(provider, termDateTime, termsToSave, termsToSaveDuration);

        return "redirect:/providers/calendar";
    }

}
