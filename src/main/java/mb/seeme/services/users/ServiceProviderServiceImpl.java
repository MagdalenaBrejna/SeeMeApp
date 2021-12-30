package mb.seeme.services.users;

import mb.seeme.model.users.ServiceProvider;
import mb.seeme.model.users.ServiceProviderTerm;
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

    @Override
    public List<ServiceProviderTerm> findAllByNameAndCityAndFieldFromDateInTermOrder(ServiceProviderTerm selectedProvider, LocalDate selectedDate) {
        List<ServiceProviderTerm> providersWithTerms = termRepository.findAllByNameAndCityAndFieldFromDate("%" + selectedProvider.getProviderName() + "%", "%" + selectedProvider.getCity() + "%", "%" + selectedProvider.getProviderField() + "%", selectedDate)
                .stream()
                .sorted((term1, term2) -> term1.getTermDate().compareTo(term2.getTermDate()))
                .map(term -> new ServiceProviderTerm(
                        term.getService().getServiceProvider().getName(),
                        term.getService().getServiceProvider().getProviderField(),
                        term.getService().getServiceProvider().getProviderDescription(),
                        term.getService().getServiceProvider().getAddress(),
                        term.getService().getServiceProvider().getCity(),
                        term.getTermDate(),
                        term.getTermTime()
                ))
                .distinct()
                .collect(Collectors.toList());
        return providersWithTerms;
    }
}