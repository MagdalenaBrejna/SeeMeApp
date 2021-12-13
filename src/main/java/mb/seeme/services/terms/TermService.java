package mb.seeme.services.terms;

import mb.seeme.model.terms.Term;
import mb.seeme.services.BaseService;

import java.time.LocalDate;
import java.util.List;

public interface TermService extends BaseService<Term, Long> {

    List<Term> findAllFutureByClientName(String clientName);

    List<Term> findAllPastByClientName(String clientName);


    List<Term> findAllFutureByProviderId(Long providerId);

    List<Term> findAllFutureFreeByProviderId(Long providerId);

    List<Term> findAllFutureFreeByProviderIdFromDate(Long providerId, LocalDate selectedDate);

    List<Term> findAllFutureAppointedByProviderId(Long providerId);

    List<Term> findAllFutureAppointedByProviderIdFromDate(Long providerId, LocalDate selectedDate);


    List<Term> findAllPastAppointedByProviderId(Long providerId);

    List<Term> findAllPastAppointedByDateAndProviderId(Long providerId, LocalDate selectedDate);

    List<Term> findAllPastAppointedByProviderIdAndClientName(Long providerId, String clientName);

    List<Term> findAllPastAppointedByDateAndProviderIdAndClientName(Long providerId, String clientName, LocalDate selectedDate);
}
