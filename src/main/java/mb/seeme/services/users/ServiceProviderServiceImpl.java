package mb.seeme.services.users;

import mb.seeme.model.users.ServiceProvider;
import mb.seeme.repositories.ServiceProviderRepository;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

@Service
public class ServiceProviderServiceImpl implements ServiceProviderService {

    private final ServiceProviderRepository providerRepository;

    public ServiceProviderServiceImpl(ServiceProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    @Override
    public Set<ServiceProvider> findAll() {
        Set<ServiceProvider> providers = new HashSet<>();
        providerRepository.findAll().forEach(providers::add);
        return providers;
    }

    @Override
    public ServiceProvider findById(Long id) {
        return providerRepository.findById(id).orElse(null);
    }

    @Override
    public ServiceProvider save(ServiceProvider provider) {
        return providerRepository.save(provider);
    }

    @Override
    public void delete(ServiceProvider provider) {
        providerRepository.delete(provider);
    }

    @Override
    public void deleteById(Long id) {
        providerRepository.deleteById(id);
    }
}
