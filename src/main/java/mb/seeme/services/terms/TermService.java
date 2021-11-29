package mb.seeme.services.terms;

import mb.seeme.model.terms.Term;
import mb.seeme.services.BaseService;

import java.time.LocalDate;
import java.util.List;

public interface TermService extends BaseService<Term, Long> {

    List<Term> findAllFutureByClientId(Long clientId);

    List<Term> findAllPastByClientId(Long clientId);


    List<Term> findAllFutureFreeByProviderId(Long providerId);

    List<Term> findAllFutureFreeByProviderIdFromDate(Long providerId, LocalDate selectedDate);

    List<Term> findAllFutureAppointedByProviderId(Long providerId);

    List<Term> findAllFutureAppointedByProviderIdFromDate(Long providerId, LocalDate selectedDate);


    List<Term> findAllPastAppointedByProviderId(Long providerId);

    List<Term> findAllPastAppointedByDateAndProviderId(Long providerId, LocalDate selectedDate);

    List<Term> findAllPastAppointedByProviderIdAndClientId(Long providerId, Long clientId);

    List<Term> findAllPastAppointedByDateAndProviderIdAndClientId(Long providerId, Long clientId, LocalDate selectedDate);
}
