package mb.seeme.repositories;

import mb.seeme.model.users.ServiceProvider;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ServiceProviderRepository extends CrudRepository<ServiceProvider, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM providers WHERE email = ?1")
    ServiceProvider selectProviderByUsername(String username);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE providers SET providers.image = ?1 WHERE providers.id = ?2")
    void saveImage(Byte[] byteObjects, Long providerId);
}
