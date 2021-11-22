package mb.seeme.services.services;

import mb.seeme.model.services.AvailableService;
import mb.seeme.repositories.AvailableServiceRepository;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

@Service
public class AvailableServiceServiceImpl implements AvailableServiceService {

    private final AvailableServiceRepository serviceRepository;

    public AvailableServiceServiceImpl(AvailableServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public Set<AvailableService> findAll() {
        Set<AvailableService> services = new HashSet<>();
        serviceRepository.findAll().forEach(services::add);
        return services;
    }

    @Override
    public AvailableService findById(Long id) {
        return serviceRepository.findById(id).orElse(null);
    }

    @Override
    public AvailableService save(AvailableService service) {
        return serviceRepository.save(service);
    }

    @Override
    public void delete(AvailableService service) {
        serviceRepository.delete(service);
    }

    @Override
    public void deleteById(Long id) {
        serviceRepository.deleteById(id);
    }
}