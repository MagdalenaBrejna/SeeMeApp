package mb.seeme.services.users;

import mb.seeme.exceptions.NotFoundException;
import mb.seeme.model.terms.Term;
import mb.seeme.model.users.ServiceProvider;
import mb.seeme.model.users.ServiceProviderTerm;
import mb.seeme.repositories.ServiceProviderRepository;
import mb.seeme.repositories.TermRepository;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ServiceProviderServiceImpl implements ServiceProviderService{

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
    public ServiceProvider findById(Long id) throws NotFoundException {
        ServiceProvider provider = providerRepository.findById(id).orElse(null);
        if(provider == null)
            throw new NotFoundException("There is no such service provider");
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
                .sorted((term1, term2) -> term1.compareToFuture(term2))
                .map(term -> new ServiceProviderTerm(
                   term.getService().getServiceProvider().getId(),
                   term.getService().getServiceProvider().getName(),
                   term.getService().getServiceProvider().getProviderField(),
                   term.getService().getServiceProvider().getProviderDescription(),
                   term.getService().getServiceProvider().getAddress(),
                   term.getService().getServiceProvider().getCity(),
                   term.getTermDate(),
                   term.getTermTime(),
                   getProviderImage(term)))
                .distinct()
                .collect(Collectors.toList());

        return providersWithTerms;
    }

    private String getProviderImage(Term term){
        String providerPhoto = "";
        try{
            if (term.getService().getServiceProvider().getProviderImage() != null)
                providerPhoto = new String(Base64.encodeBase64(term.getService().getServiceProvider().getProviderImage()), "UTF-8");
            else
                providerPhoto = new String(Base64.encodeBase64(Files.readAllBytes(Paths.get("src/main/resources/static/resources/images/user.jpg"))));
        }catch(IOException e) {
            e.printStackTrace();
        }
        return providerPhoto;
    }

    @Override
    @Transactional
    public void updateProviderDetails(ServiceProvider authProvider, ServiceProvider modifiedProvider) {
        authProvider.setName(modifiedProvider.getName());
        authProvider.setProviderField(modifiedProvider.getProviderField());
        authProvider.setAddress(modifiedProvider.getAddress());
        authProvider.setCity(modifiedProvider.getCity());
        authProvider.setEmail(modifiedProvider.getEmail());
        authProvider.setTelephone(modifiedProvider.getTelephone());
        authProvider.setProviderDescription(modifiedProvider.getProviderDescription());
        providerRepository.save(authProvider);
    }
}