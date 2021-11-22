package mb.seeme.services.users;

import mb.seeme.model.users.ServiceProvider;
import mb.seeme.services.BaseService;

import java.time.LocalDate;
import java.util.List;

public interface ServiceProviderService extends BaseService<ServiceProvider, Long> {

    List<ServiceProvider> findAllByNameLikeInTermOrder(String name);

    List<ServiceProvider> findAllWithTermsFromDateInTermOrder(LocalDate date);

    List<ServiceProvider> findAllByFieldLikeInTermOrder(String field);


}
