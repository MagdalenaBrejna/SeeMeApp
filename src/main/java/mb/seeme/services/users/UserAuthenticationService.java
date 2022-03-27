package mb.seeme.services.users;

import mb.seeme.exceptions.UserAlreadyExistException;
import mb.seeme.model.users.Client;
import mb.seeme.model.users.Person;
import mb.seeme.model.users.ServiceProvider;
import mb.seeme.model.users.UserDto;
import mb.seeme.repositories.ClientRepository;
import mb.seeme.repositories.ServiceProviderRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static mb.seeme.security.ApplicationUserRole.CLIENT;
import static mb.seeme.security.ApplicationUserRole.PROVIDER;

public class UserAuthenticationService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final ClientRepository clientRepository;
    private final ServiceProviderRepository providerRepository;

    public UserAuthenticationService(ClientRepository clientRepository, ServiceProviderRepository providerRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.providerRepository = providerRepository;
        this.passwordEncoder = passwordEncoder;
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

    public Long getAuthenticatedClientId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client client = clientRepository.selectClientByUsername(authentication.getName());
        return client.getId();
    }


    public void registerNewUserAccount(UserDto userDto) throws UserAlreadyExistException{
        if (!emailExist(userDto.getEmail())) {
           if(userDto.getRole().equals("CLIENT")) {
               Client newClient = Client.builder()
                       .name(userDto.getName())
                       .email(userDto.getEmail())
                       .password(passwordEncoder.encode(userDto.getPassword()))
                       .userRole(CLIENT.getUserRole()).build();
               clientRepository.save(newClient);
           }else {
               ServiceProvider newProvider = ServiceProvider.builder()
                       .name(userDto.getName())
                       .email(userDto.getEmail())
                       .password(passwordEncoder.encode(userDto.getPassword()))
                       .userRole(PROVIDER.getUserRole()).build();
               providerRepository.save(newProvider);
           }
        }else
            throw new UserAlreadyExistException("There is an account with that email address: " + userDto.getEmail());
    }

    private boolean emailExist(String email) {
        return providerRepository.selectProviderByUsername(email) != null || clientRepository.selectClientByUsername(email) != null;
    }

}
