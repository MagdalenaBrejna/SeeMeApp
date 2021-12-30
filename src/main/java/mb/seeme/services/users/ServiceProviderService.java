package mb.seeme.services.users;

import mb.seeme.model.users.ServiceProvider;
import mb.seeme.model.users.ServiceProviderTerm;
import mb.seeme.services.BaseService;
import java.time.LocalDate;
import java.util.List;

public interface ServiceProviderService extends BaseService<ServiceProvider, Long> {

    List<ServiceProviderTerm> findAllByNameAndCityAndFieldFromDateInTermOrder(ServiceProviderTerm selectedProvider, LocalDate date);

}
