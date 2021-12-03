package mb.seeme.model.services;

import mb.seeme.model.terms.Term;
import mb.seeme.model.users.ServiceProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AvailableServiceTest {

    AvailableService service;

    @BeforeEach
    void setUp() {
        service = new AvailableService();
    }

    @DisplayName("Test setting service id")
    @Test
    void getId() throws Exception {
        //given
        Long idValue = 2L;
        //when
        service.setId(2L);
        //then
        assertEquals(idValue, service.getId());
    }

    @DisplayName("Test setting service name")
    @Test
    void getServiceName() throws Exception {
        //given
        String serviceName = "testName";
        //when
        service.setServiceName(serviceName);
        //then
        assertEquals(serviceName, service.getServiceName());
    }

    @DisplayName("Test setting term set")
    @Test
    void getTerms() throws Exception {
        //given
        Set<Term> terms = new HashSet<Term>();
        //when + then
        service.setTerms(terms);
        assertEquals(service.getTerms().size(), 0);

        terms.add(new Term());
        assertEquals(service.getTerms().size(), 1);
    }

    @DisplayName("Test setting service provider")
    @Test
    void getServiceProvider() throws Exception {
        //given
        ServiceProvider provider = ServiceProvider.builder().build();
        //when
        service.setServiceProvider(provider);
        //then
        assertEquals(service.getServiceProvider(), provider);
    }

    @DisplayName("Test getting error while getting name of null service provider")
    @Test
    void getEmptyServiceProviderThrowsException() throws Exception {
        //given + when
        Exception emptyProviderException = assertThrows(NullPointerException.class, () -> {
            service.getServiceProvider().getName();
        }, "Provider is null");
        //then
        assertEquals(NullPointerException.class, emptyProviderException.getClass());
    }
}