package mb.seeme.services.terms;

import mb.seeme.model.terms.Term;
import mb.seeme.services.BaseService;
import java.util.List;

public interface TermService extends BaseService<Term, Long> {

    List<Term> findAllFutureByClientId(Long id);

    List<Term> findAllPastByClientId(Long id);
}
