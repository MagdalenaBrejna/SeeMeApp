package mb.seeme.repositories;

import mb.seeme.model.terms.Term;
import org.springframework.data.repository.CrudRepository;

public interface TermRepository extends CrudRepository<Term, Long> {
}
