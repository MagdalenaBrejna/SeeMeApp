package mb.seeme.services.users;

import mb.seeme.model.users.Client;
import mb.seeme.services.BaseService;

public interface ClientService extends BaseService<Client, Long> {

    void updateClientDetails(Client authClient, Client modifiedClient);
}
