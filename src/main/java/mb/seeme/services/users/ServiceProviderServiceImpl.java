package mb.seeme.services.users;

import mb.seeme.model.users.ServiceProvider;
import mb.seeme.repositories.ServiceProviderRepository;
import mb.seeme.repositories.TermRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ServiceProviderServiceImpl implements ServiceProviderService {

    private final ServiceProviderRepository providerRepository;
    private final TermRepository termRepository;

    public ServiceProviderServiceImpl(ServiceProviderRepository providerRepository, TermRepository termRepository) {
        this.providerRepository = providerRepository;
        this.termRepository = termRepository;
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
    @Transactional
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

    private List<ServiceProvider> getServiceProvidersInAllTermOrder(LocalDate selectedDate) {
        List<ServiceProvider> providers = termRepository.findAllFromDate(selectedDate)
                .stream()
                .sorted((term1, term2) -> term1.getTermDate().compareTo(term2.getTermDate()))
                .map(term -> term.getService().getServiceProvider())
                .distinct()
                .collect(Collectors.toList());
        return providers;
    }

    @Override
    public List<ServiceProvider> findAllByNameLikeInTermOrder(String name) {
        List<ServiceProvider> providers = getServiceProvidersInAllTermOrder(LocalDate.now())
                .stream()
                .filter(provider -> provider.getName().equals(name))
                .collect(Collectors.toList());
        return providers;
    }

    @Override
    public List<ServiceProvider> findAllByFieldLikeInTermOrder(String field) {
        List<ServiceProvider> providers = getServiceProvidersInAllTermOrder(LocalDate.now())
                .stream()
                .filter(provider -> provider.getProviderField().equals(field))
                .collect(Collectors.toList());
        return providers;
    }

    @Override
    public List<ServiceProvider> findAllWithTermsFromDateInTermOrder(LocalDate selectedDate) {
        return getServiceProvidersInAllTermOrder(selectedDate);
    }
}
