package mb.seeme.services.users;

import mb.seeme.model.users.Person;
import mb.seeme.model.users.ServiceProvider;
import mb.seeme.repositories.ClientRepository;
import mb.seeme.repositories.ServiceProviderRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserAuthenticationService implements UserDetailsService {

    private final ClientRepository clientRepository;
    private final ServiceProviderRepository providerRepository;

    public UserAuthenticationService(ClientRepository clientRepository, ServiceProviderRepository providerRepository) {
        this.clientRepository = clientRepository;
        this.providerRepository = providerRepository;
    }

    @Override
    public Person loadUserByUsername(String username) throws UsernameNotFoundException {
        ServiceProvider provider = providerRepository.selectProviderByUsername(username);
        if(provider != null)
            return provider;
        else
            return clientRepository.selectClientByUsername(username);
    }

    public Long getAuthenticatedProviderId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ServiceProvider provider = providerRepository.selectProviderByUsername(authentication.getName());
        return provider.getId();
    }
}
