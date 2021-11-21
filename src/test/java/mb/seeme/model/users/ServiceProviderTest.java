package mb.seeme.model.users;

import mb.seeme.model.services.Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServiceProviderTest {

    ServiceProvider provider;

    @BeforeEach
    void setUp() {
        provider = new ServiceProvider();
    }

    @Test
    void getId() throws Exception {
        //given
        Long idValue = 2L;
        //when
        provider.setId(2L);
        //then
        assertEquals(idValue, provider.getId());
    }

    @Test
    void getName() throws Exception {
        //given
        String name = "auto-service";
        //when
        provider.setName(name);
        //then
        assertEquals(provider.getName(), name);
    }

    @Test
    void getTelephone() throws Exception {
        //given
        String telNumber = "111222333";
        //when
        provider.setTelephone(telNumber);
        //then
        assertEquals(provider.getTelephone(), telNumber);
    }

    @Test
    void getEmail() throws Exception {
        //given
        String email = "auto-service@gmail.com";
        //when
        provider.setEmail(email);
        //then
        assertEquals(provider.getEmail(), email);
    }

    @Test
    void getPassword() throws Exception {
        //given
        String password = "password";
        //when
        provider.setPassword(password);
        //then
        assertEquals(provider.getPassword(), password);
    }

    @Test
    void getAppUserRole() throws Exception {
        //given
        AppUserRole role = AppUserRole.PROVIDER;
        //when
        provider.setAppUserRole(role);
        //then
        assertEquals(provider.getAppUserRole(), role);
    }

    @Test
    void getAddress() throws Exception {
        //given
        String address = "ul. Czekoladowa 54-240";
        //when
        provider.setAddress(address);
        //then
        assertEquals(provider.getAddress(), address);
    }

    @Test
    void getCity() throws Exception {
        //given
        String city = "Wroclaw";
        //when
        provider.setCity(city);
        //then
        assertEquals(provider.getCity(), city);
    }

    @Test
    void getDescription() throws Exception {
        //given
        String description = "This is a test provider description";
        //when
        provider.setDescription(description);
        //then
        assertEquals(provider.getDescription(), description);
    }

    @Test
    void getField() throws Exception {
        //given
        String field = "car mechanic";
        //when
        provider.setField(field);
        //then
        assertEquals(provider.getField(), field);
    }

    @Test
    void getServices() throws Exception {
        //given
        Set<Service> services = new HashSet<>();
        Service service = Service.builder().id(1L).build();
        services.add(service);
        //when
        provider.setServices(services);
        //then
        assertEquals(provider.getServices().size(), 1);
    }
}