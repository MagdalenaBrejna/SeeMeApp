package mb.seeme.repositories;

import mb.seeme.model.terms.Term;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.time.LocalDate;
import java.util.List;

public interface TermRepository extends CrudRepository<Term, Long> {

    @Query(nativeQuery = true, value = "select * from terms where term_date >= selectedDate")
    List<Term> findAllFromDate(LocalDate selectedDate);


    @Query(nativeQuery = true, value = "select * from terms where client_id = clientId")
    List<Term> findAllByClientId(Long clientId);


    @Query(nativeQuery = true, value = "select * from terms where (service_provier_id = providerId and client_id = NULL and term_date >= selectedDate)")
    List<Term> findAllFreeByProviderIdFromDate(Long providerId, LocalDate selectedDate);


    @Query(nativeQuery = true, value = "select * from terms where (service_provier_id = providerId and client_id != NULL and term_date >= selectedDate)")
    List<Term> findAllAppointedByProviderIdFromDate(Long providerId, LocalDate selectedDate);

    @Query(nativeQuery = true, value = "select * from terms where (service_provier_id = providerId and client_id != NULL and term_date <= selectedDate)")
    List<Term> findAllAppointedByProviderIdBeforeDate(Long providerId, LocalDate selectedDate);

    @Query(nativeQuery = true, value = "select * from terms where (service_provier_id = providerId and client_id != NULL and term_date = selectedDate)")
    List<Term> findAllAppointedByProviderIdAtDate(Long providerId, LocalDate selectedDate);
}
