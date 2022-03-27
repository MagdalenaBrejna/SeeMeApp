package mb.seeme.services.users;

import mb.seeme.model.users.Client;
import mb.seeme.repositories.ClientRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Set<Client> findAll() {
        Set<Client> clients = new HashSet<>();
        clientRepository.findAll().forEach(clients::add);
        return clients;
    }

    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public void delete(Client client) {
        clientRepository.delete(client);
    }

    @Override
    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateClientDetails(Client authClient, Client modifiedClient) {
        authClient.setName(modifiedClient.getName());
        authClient.setEmail(modifiedClient.getEmail());
        authClient.setTelephone(modifiedClient.getTelephone());
        clientRepository.save(authClient);
    }
}
