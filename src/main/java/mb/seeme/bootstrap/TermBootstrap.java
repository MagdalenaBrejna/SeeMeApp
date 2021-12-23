package mb.seeme.bootstrap;

import lombok.extern.slf4j.Slf4j;
import mb.seeme.auth.ApplicationUser;
import mb.seeme.auth.ApplicationUserRepository;
import mb.seeme.model.services.AvailableService;
import mb.seeme.model.terms.Status;
import mb.seeme.model.terms.Term;
import mb.seeme.model.users.Client;
import mb.seeme.model.users.ServiceProvider;
import mb.seeme.repositories.AvailableServiceRepository;
import mb.seeme.repositories.ClientRepository;
import mb.seeme.repositories.ServiceProviderRepository;
import mb.seeme.repositories.TermRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TermBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final TermRepository termRepository;
    private final ServiceProviderRepository providerRepository;
    private final AvailableServiceRepository availableServiceRepository;
    private final ClientRepository clientRepository;
    private final ApplicationUserRepository applicationUserRepository;
    private final PasswordEncoder passwordEncoder;

    public TermBootstrap(TermRepository termRepository, ServiceProviderRepository providerRepository, AvailableServiceRepository availableServiceRepository, ClientRepository clientRepository, ApplicationUserRepository applicationUserRepository, PasswordEncoder passwordEncoder) {
        this.termRepository = termRepository;
        this.providerRepository = providerRepository;
        this.availableServiceRepository = availableServiceRepository;
        this.clientRepository = clientRepository;
        this.applicationUserRepository = applicationUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        termRepository.saveAll(getTerms());
    }

    @Transactional
    public List<Term> getTerms(){
        ApplicationUser user = ApplicationUser.builder().id(1l).username("anna").password(passwordEncoder.encode("password")).role(new SimpleGrantedAuthority("ROLE_CLIENT")).build();
        ApplicationUser user2 = ApplicationUser.builder().id(2l).username("lidia").password(passwordEncoder.encode("password")).role(new SimpleGrantedAuthority("ROLE_PROVIDER")).build();
        applicationUserRepository.save(user);
        applicationUserRepository.save(user2);

        Client clientA = Client.builder().id(1l).name("clientA").build();
        Client clientB = Client.builder().id(2l).name("clientB").build();
        clientRepository.save(clientA);
        clientRepository.save(clientB);

        ServiceProvider providerA = ServiceProvider.builder().id(1l).build();
        ServiceProvider providerB = ServiceProvider.builder().id(2l).build();
        providerRepository.save(providerA);
        providerRepository.save(providerB);

        AvailableService service1 = AvailableService.builder().id(1l).serviceProvider(providerA).build();
        AvailableService service2 = AvailableService.builder().id(2l).serviceProvider(providerB).build();
        availableServiceRepository.save(service1);
        availableServiceRepository.save(service2);

        Term term1 = Term.builder().id(1l).termDate(LocalDate.parse("2021-11-19")).termTime(LocalTime.parse("12:32:22", DateTimeFormatter.ISO_TIME)).service(service1).client(clientB).termRealizedStatus(Status.FULL).build();
        Term term2 = Term.builder().id(2l).termDate(LocalDate.parse("2021-11-19")).termTime(LocalTime.parse("13:32:22", DateTimeFormatter.ISO_TIME)).service(service2).client(clientA).termRealizedStatus(Status.FREE).build();
        Term term3 = Term.builder().id(3l).termDate(LocalDate.parse("2021-11-20")).termTime(LocalTime.parse("14:32:22", DateTimeFormatter.ISO_TIME)).service(service1).client(clientA).termRealizedStatus(Status.FREE).build();
        Term term4 = Term.builder().id(4l).termDate(LocalDate.parse("2021-11-19")).termTime(LocalTime.parse("09:32:22", DateTimeFormatter.ISO_TIME)).service(service1).client(clientA).termRealizedStatus(Status.FREE).build();
        Term term5 = Term.builder().id(5l).termDate(LocalDate.parse("2022-11-22")).termTime(LocalTime.parse("09:32:22", DateTimeFormatter.ISO_TIME)).service(service2).termRealizedStatus(Status.FULL).build();
        Term term6 = Term.builder().id(6l).termDate(LocalDate.parse("2022-11-15")).termTime(LocalTime.parse("09:32:22", DateTimeFormatter.ISO_TIME)).service(service1).termRealizedStatus(Status.FULL).build();
        Term term7 = Term.builder().id(7l).termDate(LocalDate.parse("2022-11-23")).termTime(LocalTime.parse("09:32:22", DateTimeFormatter.ISO_TIME)).service(service1).termRealizedStatus(Status.FREE).client(clientB).build();
        Term term8 = Term.builder().id(8l).termDate(LocalDate.parse("2022-11-14")).termTime(LocalTime.parse("09:32:22", DateTimeFormatter.ISO_TIME)).service(service1).termRealizedStatus(Status.FULL).client(clientA).build();

        List<Term> termList = new ArrayList<Term>();
        termList.add(term1);
        termList.add(term2);
        termList.add(term3);
        termList.add(term4);
        termList.add(term5);
        termList.add(term6);
        termList.add(term7);
        termList.add(term8);

        return termList;
    }
}
