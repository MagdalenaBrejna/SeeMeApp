package mb.seeme.services.users;

import mb.seeme.emails.EMailService;
import mb.seeme.exceptions.AuthException;
import mb.seeme.exceptions.UserAlreadyExistException;
import mb.seeme.messages.UserMessages;
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
    private final EMailService emailService;

    public UserAuthenticationService(ClientRepository clientRepository, ServiceProviderRepository providerRepository, PasswordEncoder passwordEncoder, EMailService emailService) {
        this.clientRepository = clientRepository;
        this.providerRepository = providerRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;

    }

    @Override
    public Person loadUserByUsername(String username) throws UsernameNotFoundException {
        ServiceProvider provider = providerRepository.selectProviderByUsername(username);
        if(provider != null)
            return provider;
        else
            return clientRepository.selectClientByUsername(username);
    }

    public Long getAuthenticatedProviderId() throws AuthException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ServiceProvider provider = providerRepository.selectProviderByUsername(authentication.getName());
        if(provider == null)
            throw new AuthException(UserMessages.BANNED_RESOURCES);
        return provider.getId();
    }

    public Long getAuthenticatedClientId() throws AuthException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client client = clientRepository.selectClientByUsername(authentication.getName());
        if(client == null)
            throw new AuthException(UserMessages.BANNED_RESOURCES);
        return client.getId();
    }

    public Person getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ServiceProvider provider = providerRepository.selectProviderByUsername(authentication.getName());
        Client client = clientRepository.selectClientByUsername(authentication.getName());
        if(provider == null)
            return client;
        else
            return provider;
    }


    public void registerNewUserAccount(UserDto userDto) throws UserAlreadyExistException {
        if (!emailExist(userDto.getEmail())) {
           if(userDto.getRole().equals("CLIENT")) {
               Client newClient = Client.builder()
                       .name(userDto.getName())
                       .email(userDto.getEmail())
                       .password(passwordEncoder.encode(userDto.getPassword()))
                       .userRole(CLIENT.getUserRole()).build();
               clientRepository.save(newClient);
               emailService.sendSimpleMessage(newClient.getEmail(), UserMessages.MAIL_WELCOME_TITLE, UserMessages.MAIL_WELCOME_MESSAGE);
           }else {
               ServiceProvider newProvider = ServiceProvider.builder()
                       .name(userDto.getName())
                       .email(userDto.getEmail())
                       .password(passwordEncoder.encode(userDto.getPassword()))
                       .userRole(PROVIDER.getUserRole()).build();
               providerRepository.save(newProvider);
               emailService.sendSimpleMessage(newProvider.getEmail(), UserMessages.MAIL_WELCOME_TITLE, UserMessages.MAIL_WELCOME_MESSAGE);
           }
        }else
            throw new UserAlreadyExistException(UserMessages.ACCOUNT_FOR_EMAIL_EXISTS + userDto.getEmail());
    }

    private boolean emailExist(String email) {
        return providerRepository.selectProviderByUsername(email) != null || clientRepository.selectClientByUsername(email) != null;
    }

}
