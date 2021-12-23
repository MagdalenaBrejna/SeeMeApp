package mb.seeme.repositories;

import mb.seeme.model.users.ServiceProvider;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ServiceProviderRepository extends CrudRepository<ServiceProvider, Long> {

    @Query(nativeQuery = true, value = "select * from providers where email = ?1")
    ServiceProvider selectProviderByUsername(String username);
}
