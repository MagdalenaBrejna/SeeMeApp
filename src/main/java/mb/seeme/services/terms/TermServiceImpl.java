package mb.seeme.services.terms;

import mb.seeme.emails.EMailService;
import mb.seeme.exceptions.NotFoundException;
import mb.seeme.messages.UserMessages;
import mb.seeme.model.services.AvailableService;
import mb.seeme.model.terms.Status;
import mb.seeme.model.terms.Term;
import mb.seeme.model.users.Client;
import mb.seeme.model.users.Person;
import mb.seeme.model.users.ServiceProvider;
import mb.seeme.repositories.TermRepository;
import mb.seeme.security.ApplicationUserRole;
import mb.seeme.services.services.AvailableServiceService;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TermServiceImpl implements TermService {

    private final EMailService emailService;
    private final TermRepository termRepository;
    private final AvailableServiceService availableService;

    public TermServiceImpl(TermRepository termRepository, AvailableServiceService availableService, EMailService emailService) {
        this.emailService = emailService;
        this.termRepository = termRepository;
        this.availableService = availableService;
    }

    private Term findIfTermExists(Long termId) throws NotFoundException {
        Term selectedTerm = findById(termId);
        if(selectedTerm == null)
            throw new NotFoundException(UserMessages.NO_TERM);
        return selectedTerm;
    }

    @Override
    public Set<Term> findAll() {
        Set<Term> terms = new HashSet<>();
        termRepository.findAll().forEach(terms::add);
        return terms;
    }

    @Override
    public Term findById(Long termId) {
        return termRepository.findById(termId).orElse(null);
    }

    @Override
    @Transactional
    public Term save(Term term) {
        return termRepository.save(term);
    }

    @Override
    @Transactional
    public void bookTermByClientId(Long clientId, Long termId) throws NotFoundException {
        Term term = findIfTermExists(termId);
        if(term.getTermRealizedStatus().equals(Status.FREE)){
            termRepository.bookTerm(clientId, termId);
            termRepository.makeTermStatusFull(termId);
        }
    }

    @Override
    public void delete(Term term) {
        termRepository.delete(term);
    }

    @Override
    public void deleteById(Long termId) throws NotFoundException {
        findIfTermExists(termId);
        termRepository.deleteById(termId);
    }

    @Override
    public void deleteByTermId(Long termId, Person person) throws NotFoundException {
        Term term = findIfTermExists(termId);
        if(term.getTermDate().isAfter(LocalDate.now()) && person.getUserRole().equals(ApplicationUserRole.PROVIDER.getUserRole()) && term.getService().getServiceProvider() != null && term.getService().getServiceProvider().equals((ServiceProvider)person)) {
            //if(term.getClient() != null)
            //    emailService.sendSimpleMessage(term.getClient().getEmail(), UserMessages.MAIL_CANCEL_TITLE, "We are sorry to inform you that your term on " + term.getTermDate().toString() + " was cancelled");
            termRepository.deleteById(termId);
        }
    }

    @Override
    @Transactional
    public void cancelById(Long termId, Person person) throws NotFoundException {
        Term term = findIfTermExists(termId);
        if(term.getClient() != null && term.getTermDate().isAfter(LocalDate.now()) &&
                ((person.getUserRole().equals(ApplicationUserRole.PROVIDER.getUserRole()) && term.getService().getServiceProvider() != null && term.getService().getServiceProvider().equals((ServiceProvider)person)) ||
                 (person.getUserRole().equals(ApplicationUserRole.CLIENT.getUserRole()) && term.getClient() != null && term.getClient().equals((Client)person)))) {
            termRepository.makeTermStatusFree(termId);
            //emailService.sendSimpleMessage(term.getService().getServiceProvider().getEmail(), UserMessages.MAIL_CANCEL_TITLE, "You cancelled the term on " + term.getTermDate().toString());
            //emailService.sendSimpleMessage(term.getClient().getEmail(), UserMessages.MAIL_CANCEL_TITLE, "We are sorry to inform you that your term on " + term.getTermDate().toString() + " was cancelled");
        }
    }

    @Override
    public List<Term> findAllPastByClientName(String clientName) {
        List<Term> terms = findAllByClientName(clientName).stream()
                .filter(term -> term.getTermDate().isBefore(LocalDate.now()))
                .collect(Collectors.toList());
        return terms;
    }

    private List<Term> findAllByClientName(String clientName) {
        return termRepository.findAllByClientName(clientName).stream()
                .sorted((term1, term2) -> term1.compareToFuture(term2))
                .collect(Collectors.toList());
    }

    @Override
    public List<Term> findAllFutureByClientId(Long clientId) {
        List<Term> terms = termRepository.findAllByClientId(clientId).stream()
                .filter(term -> (term.getTermDate().isAfter(LocalDate.now()) || term.getTermDate().equals(LocalDate.now())))
                .sorted((term1, term2) -> term1.compareToFuture(term2))
                .collect(Collectors.toList());
        return terms;
    }

    @Override
    public List<Term> findAllPastByClientId(Long clientId) {
        List<Term> terms = termRepository.findAllByClientId(clientId).stream()
                .filter(term -> term.getTermDate().isBefore(LocalDate.now()))
                .sorted((term1, term2) -> term1.compareToPast(term2))
                .collect(Collectors.toList());
        return terms;
    }

    @Override
    public List<Term> findAllFutureByProviderIdFromDate(Long providerId, LocalDate selectedDate){
        return termRepository.findAllByProviderIdFromDate(providerId, selectedDate).stream()
                .map(term -> {
                    if(term.getClient() == null)
                        term.setClient(Client.builder().name("").telephone("").build());
                     return term;
                })
                .sorted((term1, term2) -> term1.compareToFuture(term2))
                .collect(Collectors.toList());
    }

    @Override
    public List<Term> findAllFutureFreeByProviderId(Long providerId) {
        return findFreeTermsByProviderIdFromDate(providerId, LocalDate.now());
    }

    @Override
    public List<Term> findAllFutureFreeByProviderIdFromDate(Long providerId, LocalDate selectedDate) {
        if(selectedDate.isBefore(LocalDate.now()))
            selectedDate = LocalDate.now();
        return findFreeTermsByProviderIdFromDate(providerId, selectedDate);
    }

    private List<Term> findFreeTermsByProviderIdFromDate(Long providerId, LocalDate selectedDate) {
        return termRepository.findAllFreeByProviderIdFromDate(providerId, selectedDate).stream()
                .map(term -> {
                    if(term.getClient() == null)
                        term.setClient(Client.builder().name("").telephone("").build());
                    return term;
                })
                .sorted((term1, term2) -> term1.compareToFuture(term2))
                .collect(Collectors.toList());
    }

    @Override
    public List<Term> findAllFutureAppointedByProviderIdFromDate(Long providerId, LocalDate selectedDate) {
        if(selectedDate.isBefore(LocalDate.now()))
            selectedDate = LocalDate.now();
        return findAppointedTermsByProviderIdFromDate(providerId, selectedDate);
    }

    private List<Term> findAppointedTermsByProviderIdFromDate(Long providerId, LocalDate selectedDate) {
        return termRepository.findAllAppointedByProviderIdFromDate(providerId, selectedDate).stream()
                .map(term -> {
                    if(term.getClient() == null)
                        term.setClient(Client.builder().name("").telephone("").build());
                    return term;
                })
                .sorted((term1, term2) -> term1.compareToFuture(term2))
                .collect(Collectors.toList());
    }

    @Override
    public List<Term> findAllPastAppointedBeforeDateAndProviderIdAndClientName(Long providerId, String clientName, LocalDate selectedDate) {
        List<Term> terms = findAllPastByClientName(clientName)
                .stream()
                .filter(term -> (term.getTermDate().isBefore(selectedDate) && term.getService().getServiceProvider().getId() == providerId))
                .sorted((term1, term2) -> term1.compareToPast(term2))
                .collect(Collectors.toList());
        return terms;
    }

    @Transactional
    @Override
    public void addNewTerms(ServiceProvider provider, LocalDateTime firstTermDateAndTime, int termsNumber, int termDuration, String serviceName){
        LocalDate firstTermDate = firstTermDateAndTime.atZone(ZoneId.systemDefault()).toLocalDate();
        LocalTime firstTermTime = firstTermDateAndTime.atZone(ZoneId.systemDefault()).toLocalTime();

        for(int i = 0; i < termsNumber; i++) {
            AvailableService service = AvailableService.builder().serviceName(serviceName).serviceProvider(provider).build();
            availableService.save(service);
            save(Term.builder().termDate(firstTermDate).termTime(firstTermTime).service(service).termRealizedStatus(Status.FREE).build());

            if(firstTermTime.getHour() == 23 && firstTermTime.plusMinutes(termDuration).getHour() == 00)
                firstTermDate = firstTermDate.plusDays(1);
            firstTermTime = firstTermTime.plusMinutes(termDuration);
        }
    }

    @Transactional
    @Override
    public void addTermDescription(String termDescription, Long termId) throws NotFoundException {
        Term term = findIfTermExists(termId);
        term.setTermDescription(termDescription);
        save(term);
    }
}
