package mb.seeme.services.users;

import mb.seeme.model.terms.Term;
import mb.seeme.model.users.ServiceProvider;
import mb.seeme.repositories.ServiceProviderRepository;
import mb.seeme.repositories.TermRepository;
import java.time.LocalDate;
import java.util.ArrayList;
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


    private List<ServiceProvider> getServiceProvidersInAllTermOrder() {
        List<Term> terms = new ArrayList<Term>();
        termRepository.findAll().forEach(terms::add);

        return terms.stream()
                .sorted((term1, term2) -> term1.getDate().compareTo(term2.getDate()))
                .map(term -> term.getService().getServiceProvider())
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<ServiceProvider> findAllByNameLikeInTermOrder(String name) {
        List<ServiceProvider> providers = getServiceProvidersInAllTermOrder()
                .stream()
                .filter(provider -> provider.getName().equals(name))
                .collect(Collectors.toList());
         return providers;
    }

    @Override
    public List<ServiceProvider> findAllByFieldLikeInTermOrder(String field) {
        List<ServiceProvider> providers = getServiceProvidersInAllTermOrder()
                .stream()
                .filter(provider -> provider.getField().equals(field))
                .collect(Collectors.toList());
        return providers;
    }

    @Override
    public List<ServiceProvider> findAllWithTermsFromDateInTermOrder(LocalDate date) {
        List<Term> termsFromDate = termRepository.findAllFromDate(date);
        List<ServiceProvider> providers =
                termsFromDate.stream()
                    .sorted((term1, term2) -> term1.getDate().compareTo(term2.getDate()))
                    .map(term -> term.getService().getServiceProvider())
                    .distinct()
                    .collect(Collectors.toList());
        return providers;
    }
}
