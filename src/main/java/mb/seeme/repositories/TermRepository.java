package mb.seeme.repositories;

import mb.seeme.model.terms.Term;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface TermRepository extends CrudRepository<Term, Long> {

    @Query(nativeQuery = true, value = "select * from terms where date >= selectedDate")
    List<Term> findAllFromDate(LocalDate selectedDate);
}
