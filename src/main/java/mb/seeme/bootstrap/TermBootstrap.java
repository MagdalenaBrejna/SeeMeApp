package mb.seeme.bootstrap;

import lombok.extern.slf4j.Slf4j;
import mb.seeme.model.services.AvailableService;
import mb.seeme.model.terms.Term;
import mb.seeme.model.users.Client;
import mb.seeme.model.users.ServiceProvider;
import mb.seeme.repositories.AvailableServiceRepository;
import mb.seeme.repositories.ClientRepository;
import mb.seeme.repositories.ServiceProviderRepository;
import mb.seeme.repositories.TermRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
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

    public TermBootstrap(TermRepository termRepository, ServiceProviderRepository providerRepository, AvailableServiceRepository availableServiceRepository, ClientRepository clientRepository) {
        this.termRepository = termRepository;
        this.providerRepository = providerRepository;
        this.availableServiceRepository = availableServiceRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        termRepository.saveAll(getTerms());
    }

    @Transactional
    public List<Term> getTerms(){
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

        Term term1 = Term.builder().id(1l).termDate(LocalDate.parse("2021-11-19")).termTime(LocalTime.parse("12:32:22", DateTimeFormatter.ISO_TIME)).service(service1).client(clientB).build();
        Term term2 = Term.builder().id(2l).termDate(LocalDate.parse("2021-11-19")).termTime(LocalTime.parse("13:32:22", DateTimeFormatter.ISO_TIME)).service(service2).client(clientA).build();
        Term term3 = Term.builder().id(3l).termDate(LocalDate.parse("2021-11-20")).termTime(LocalTime.parse("14:32:22", DateTimeFormatter.ISO_TIME)).service(service1).client(clientA).build();
        Term term4 = Term.builder().id(4l).termDate(LocalDate.parse("2021-11-19")).termTime(LocalTime.parse("09:32:22", DateTimeFormatter.ISO_TIME)).service(service1).client(clientA).build();
        Term term5 = Term.builder().id(5l).termDate(LocalDate.parse("2022-11-22")).termTime(LocalTime.parse("09:32:22", DateTimeFormatter.ISO_TIME)).service(service2).build();
        Term term6 = Term.builder().id(6l).termDate(LocalDate.parse("2022-11-15")).termTime(LocalTime.parse("09:32:22", DateTimeFormatter.ISO_TIME)).service(service1).build();
        Term term7 = Term.builder().id(7l).termDate(LocalDate.parse("2022-11-23")).termTime(LocalTime.parse("09:32:22", DateTimeFormatter.ISO_TIME)).service(service1).client(clientB).build();
        Term term8 = Term.builder().id(8l).termDate(LocalDate.parse("2022-11-14")).termTime(LocalTime.parse("09:32:22", DateTimeFormatter.ISO_TIME)).service(service1).client(clientA).build();

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
