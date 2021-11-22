package mb.seeme.repositories;

import mb.seeme.model.users.ServiceProvider;
import org.springframework.data.repository.CrudRepository;

public interface ServiceProviderRepository extends CrudRepository<ServiceProvider, Long> {
}
