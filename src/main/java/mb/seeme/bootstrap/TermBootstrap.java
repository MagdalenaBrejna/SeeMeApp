package mb.seeme.bootstrap;

import lombok.extern.slf4j.Slf4j;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static mb.seeme.security.ApplicationUserRole.CLIENT;
import static mb.seeme.security.ApplicationUserRole.PROVIDER;

@Slf4j
@Component
public class TermBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final TermRepository termRepository;
    private final ServiceProviderRepository providerRepository;
    private final AvailableServiceRepository availableServiceRepository;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public TermBootstrap(TermRepository termRepository, ServiceProviderRepository providerRepository, AvailableServiceRepository availableServiceRepository, ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.termRepository = termRepository;
        this.providerRepository = providerRepository;
        this.availableServiceRepository = availableServiceRepository;
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        termRepository.saveAll(getTerms());
    }

    @Transactional
    public List<Term> getTerms(){
        Client clientA = Client.builder().id(1l).name("clientA").email("AC@gmail.com").password(passwordEncoder.encode("passAC")).userRole(CLIENT.getUserRole()).telephone("111111111").build();
        Client clientB = Client.builder().id(2l).name("clientB").email("BC@gmail.com").password(passwordEncoder.encode("passBC")).userRole(CLIENT.getUserRole()).telephone("222222222").build();
        clientRepository.save(clientA);
        clientRepository.save(clientB);

        byte[] array = null;
        try {
            array = Files.readAllBytes(Paths.get("src/main/resources/static/resources/images/doctor.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ServiceProvider providerA = ServiceProvider.builder().id(1l).name("providerA").email("AP@gmail.com").password(passwordEncoder.encode("passAP")).userRole(PROVIDER.getUserRole()).address("ul.Osobowicka 5").city("Wrocław").providerField("mechanik samochodowy").telephone("333333333").providerImage(array).providerDescription("Jestem mechanikiem samochodowym z wieloletnim stazem. Naprawiam auta osobowe.").build();
        ServiceProvider providerB = ServiceProvider.builder().id(2l).name("providerB").email("BP@gmail.com").password(passwordEncoder.encode("passBP")).userRole(PROVIDER.getUserRole()).address("ul.Czekoladowa 1").providerField("fryzjer damski").city("Warszawa").telephone("444444444").build();
        providerRepository.save(providerA);
        providerRepository.save(providerB);

        AvailableService service1 = AvailableService.builder().id(1l).serviceProvider(providerA).serviceName("service1").build();
        AvailableService service2 = AvailableService.builder().id(2l).serviceProvider(providerB).serviceName("service2").build();
        availableServiceRepository.save(service1);
        availableServiceRepository.save(service2);

        Term term1 = Term.builder().id(1l).termDate(LocalDate.parse("2021-11-19")).termTime(LocalTime.parse("12:32", DateTimeFormatter.ISO_TIME)).service(service1).client(clientB).termRealizedStatus(Status.FULL).termDescription("Naprawa Skoda Superb").build();
        Term term2 = Term.builder().id(2l).termDate(LocalDate.parse("2021-11-19")).termTime(LocalTime.parse("13:32", DateTimeFormatter.ISO_TIME)).service(service2).client(clientA).termRealizedStatus(Status.FULL).termDescription("Strzyzenie meskie").build();
        Term term3 = Term.builder().id(3l).termDate(LocalDate.parse("2021-11-20")).termTime(LocalTime.parse("14:32", DateTimeFormatter.ISO_TIME)).service(service1).client(clientA).termRealizedStatus(Status.FULL).termDescription("Wymiana opon").build();
        Term term4 = Term.builder().id(4l).termDate(LocalDate.parse("2021-11-19")).termTime(LocalTime.parse("09:32", DateTimeFormatter.ISO_TIME)).service(service1).client(clientA).termRealizedStatus(Status.FULL).termDescription("Uzupelnienie filtrow").build();
        Term term5 = Term.builder().id(5l).termDate(LocalDate.parse("2022-11-22")).termTime(LocalTime.parse("09:32", DateTimeFormatter.ISO_TIME)).service(service2).termRealizedStatus(Status.FREE).termDescription("Farbowanie wlosow").build();
        Term term6 = Term.builder().id(6l).termDate(LocalDate.parse("2022-11-15")).termTime(LocalTime.parse("09:32", DateTimeFormatter.ISO_TIME)).service(service1).termRealizedStatus(Status.FREE).termDescription("Montaz kamery tylniej").build();
        Term term7 = Term.builder().id(7l).termDate(LocalDate.parse("2022-11-23")).termTime(LocalTime.parse("09:32", DateTimeFormatter.ISO_TIME)).service(service1).termRealizedStatus(Status.FREE).termDescription("Naprawa szkody Fiat 500 z ubezpieczenia").build();
        Term term8 = Term.builder().id(8l).termDate(LocalDate.parse("2022-11-14")).termTime(LocalTime.parse("09:32", DateTimeFormatter.ISO_TIME)).service(service1).termRealizedStatus(Status.FULL).client(clientA).termDescription("Lakierowanie maski").build();

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
