package mb.seeme.services.terms;

import mb.seeme.model.terms.Term;
import mb.seeme.model.users.Person;
import mb.seeme.model.users.ServiceProvider;
import mb.seeme.services.BaseService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TermService extends BaseService<Term, Long> {

    void cancelById(Long termId, Person person);

    void deleteByTermId(Long termId, Person person);

    void bookTermByClientId(Long clientId, Long termId);

    void addNewTerms(ServiceProvider provider, LocalDateTime firstTermDateAndTime, int termsNumber, int termDuration, String serviceName);

    void addTermDescription(String termDescription, Long termId);


    List<Term> findAllFutureByClientName(String clientName);

    List<Term> findAllPastByClientName(String clientName);

    List<Term> findAllFutureByClientId(Long clientId);

    List<Term> findAllPastByClientId(Long clientId);


    List<Term> findAllFutureByProviderId(Long providerId);

    List<Term> findAllFutureByProviderIdFromDate(Long providerId, LocalDate selectedDate);

    List<Term> findAllFutureFreeByProviderId(Long providerId);

    List<Term> findAllFutureFreeByProviderIdFromDate(Long providerId, LocalDate selectedDate);

    List<Term> findAllFutureAppointedByProviderId(Long providerId);

    List<Term> findAllFutureAppointedByProviderIdFromDate(Long providerId, LocalDate selectedDate);


    List<Term> findAllPastAppointedByProviderId(Long providerId);

    List<Term> findAllPastAppointedByDateAndProviderId(Long providerId, LocalDate selectedDate);

    List<Term> findAllPastAppointedByProviderIdAndClientName(Long providerId, String clientName);

    List<Term> findAllPastAppointedBeforeDateAndProviderIdAndClientName(Long providerId, String clientName, LocalDate selectedDate);
}
