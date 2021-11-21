package mb.seeme.repositories;

import mb.seeme.model.users.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {
}
