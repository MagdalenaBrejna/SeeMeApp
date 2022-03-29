package mb.seeme.services;

import mb.seeme.emails.EMailService;
import mb.seeme.repositories.AvailableServiceRepository;
import mb.seeme.repositories.ClientRepository;
import mb.seeme.repositories.ServiceProviderRepository;
import mb.seeme.repositories.TermRepository;
import mb.seeme.services.services.AvailableServiceService;
import mb.seeme.services.services.AvailableServiceServiceImpl;
import mb.seeme.services.terms.TermServiceImpl;
import mb.seeme.services.users.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ServiceConfiguration {

    @Bean
    UserAuthenticationService userAuthenticationService(final ClientRepository clientRepository, final ServiceProviderRepository providerRepository, PasswordEncoder passwordEncoder, EMailService emailService) {
        return new UserAuthenticationService(clientRepository, providerRepository, passwordEncoder, emailService);
    }

    @Bean
    ImageServiceImpl imageServiceImpl(final ServiceProviderRepository providerRepository) {
        return new ImageServiceImpl(providerRepository);
    }

    @Bean
    TermServiceImpl termServiceImpl(final TermRepository termRepository, final AvailableServiceService availableService, EMailService emailService) {
        return new TermServiceImpl(termRepository, availableService, emailService);
    }

    @Bean
    ClientServiceImpl clientServiceImpl(final ClientRepository clientRepository) {
        return new ClientServiceImpl(clientRepository);
    }

    @Bean
    AvailableServiceServiceImpl availableServiceServiceImpl(final AvailableServiceRepository serviceRepository) {
        return new AvailableServiceServiceImpl(serviceRepository);
    }

    @Bean
    ServiceProviderServiceImpl serviceProviderServiceImpl(final ServiceProviderRepository providerRepository, final TermRepository termRepository) {
        return new ServiceProviderServiceImpl(providerRepository, termRepository);
    }
}
