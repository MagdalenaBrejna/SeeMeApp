package mb.seeme.repositories;

import mb.seeme.model.terms.Term;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface TermRepository extends CrudRepository<Term, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM terms WHERE term_date >= selectedDate")
    List<Term> findAllFromDate(LocalDate selectedDate);


    @Query(nativeQuery = true, value = "SELECT * FROM terms WHERE terms.client_id = ?1")
    List<Term> findAllByClientId(Long clientId);

    @Query(nativeQuery = true, value = "SELECT * FROM terms INNER JOIN clients ON terms.client_id = clients.id WHERE clients.name LIKE ?1")
    List<Term> findAllByClientName(String clientName);


    @Query(nativeQuery = true, value = "SELECT * FROM terms INNER JOIN services ON terms.service_id = services.id INNER JOIN providers ON services.service_provider_id = providers.id WHERE (services.service_provider_id=?1 AND terms.term_date >= ?2) ORDER BY terms.term_date")
    List<Term> findAllByProviderIdFromDate(Long providerId, LocalDate selectedDate);


    @Query(nativeQuery = true, value = "SELECT * FROM terms INNER JOIN services ON terms.service_id = services.id INNER JOIN providers ON services.service_provider_id = providers.id WHERE (services.service_provider_id=?1 AND terms.client_id IS NULL AND terms.term_date >= ?2)")
    List<Term> findAllFreeByProviderIdFromDate(Long providerId, LocalDate selectedDate);


    @Query(nativeQuery = true, value = "SELECT * FROM terms INNER JOIN services ON terms.service_id = services.id INNER JOIN providers ON services.service_provider_id = providers.id WHERE (services.service_provider_id=?1 AND terms.client_id IS NOT NULL AND terms.term_date >= ?2)")
    List<Term> findAllAppointedByProviderIdFromDate(Long providerId, LocalDate selectedDate);

    @Query(nativeQuery = true, value = "SELECT * FROM terms INNER JOIN services ON terms.service_id = services.id INNER JOIN providers ON services.service_provider_id = providers.id WHERE (services.service_provider_id=?1 AND terms.client_id IS NOT NULL AND terms.term_date <= ?2)")
    List<Term> findAllAppointedByProviderIdBeforeDate(Long providerId, LocalDate selectedDate);

    @Query(nativeQuery = true, value = "SELECT * FROM terms WHERE (service_provier_id = providerId and client_id != NULL and term_date = selectedDate)")
    List<Term> findAllAppointedByProviderIdAtDate(Long providerId, LocalDate selectedDate);
}
