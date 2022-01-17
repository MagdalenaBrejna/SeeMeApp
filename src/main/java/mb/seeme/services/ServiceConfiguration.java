package mb.seeme.services;

import mb.seeme.repositories.AvailableServiceRepository;
import mb.seeme.repositories.ClientRepository;
import mb.seeme.repositories.ServiceProviderRepository;
import mb.seeme.repositories.TermRepository;
import mb.seeme.services.services.AvailableServiceServiceImpl;
import mb.seeme.services.terms.TermServiceImpl;
import mb.seeme.services.users.ClientServiceImpl;
import mb.seeme.services.users.ImageServiceImpl;
import mb.seeme.services.users.ServiceProviderServiceImpl;
import mb.seeme.services.users.UserAuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean
    UserAuthenticationService userAuthenticationService(final ClientRepository clientRepository, final ServiceProviderRepository providerRepository) {
        return new UserAuthenticationService(clientRepository, providerRepository);
    }

    @Bean
    ImageServiceImpl imageServiceImpl(final ServiceProviderRepository providerRepository) {
        return new ImageServiceImpl(providerRepository);
    }

    @Bean
    TermServiceImpl termServiceImpl(final TermRepository termRepository) {
        return new TermServiceImpl(termRepository);
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
