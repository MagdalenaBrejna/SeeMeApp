package mb.seeme.services.terms;

import mb.seeme.model.terms.Term;
import mb.seeme.services.BaseService;

import java.time.LocalDate;
import java.util.List;

public interface TermService extends BaseService<Term, Long> {

    List<Term> findAllFutureByClientId(Long id);

    List<Term> findAllPastByClientId(Long id);


    List<Term> findAllFutureFreeByProviderId(Long id);

    List<Term> findAllFutureFreeByProviderIdFromDate(Long id, LocalDate date);

    List<Term> findAllFutureAppointedByProviderId(Long id);

    List<Term> findAllFutureAppointedByProviderIdFromDate(Long id, LocalDate date);
}
