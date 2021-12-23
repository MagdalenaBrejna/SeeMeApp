package mb.seeme.model.users;

import mb.seeme.model.services.AvailableService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;

import static mb.seeme.security.ApplicationUserRole.PROVIDER;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ServiceProviderTest {

    ServiceProvider provider;

    @BeforeEach
    void setUp() {
        provider = new ServiceProvider();
    }

    @DisplayName("Test setting provider id")
    @Test
    void getId() throws Exception {
        //given
        Long idValue = 2L;
        //when
        provider.setId(2L);
        //then
        assertEquals(idValue, provider.getId());
    }

    @DisplayName("Test setting provider name")
    @Test
    void getName() throws Exception {
        //given
        String name = "auto-service";
        //when
        provider.setName(name);
        //then
        assertEquals(provider.getName(), name);
    }

    @DisplayName("Test setting provider telephone number")
    @Test
    void getTelephone() throws Exception {
        //given
        String telNumber = "111222333";
        //when
        provider.setTelephone(telNumber);
        //then
        assertEquals(provider.getTelephone(), telNumber);
    }

    @DisplayName("Test setting provider mail")
    @Test
    void getEmail() throws Exception {
        //given
        String email = "auto-service@gmail.com";
        //when
        provider.setEmail(email);
        //then
        assertEquals(provider.getEmail(), email);
    }

    @DisplayName("Test setting provider password")
    @Test
    void getPassword() throws Exception {
        //given
        String password = "password";
        //when
        provider.setPassword(password);
        //then
        assertEquals(provider.getPassword(), password);
    }

    @DisplayName("Test setting provider role")
    @Test
    void getUserRole() throws Exception {
        //given
        SimpleGrantedAuthority role = PROVIDER.getUserRole();
        //when
        provider.setUserRole(role);
        //then
        assertEquals(provider.getUserRole(), role);
    }

    @DisplayName("Test setting provider address")
    @Test
    void getAddress() throws Exception {
        //given
        String address = "ul. Czekoladowa 54-240";
        //when
        provider.setAddress(address);
        //then
        assertEquals(provider.getAddress(), address);
    }

    @DisplayName("Test setting provider city")
    @Test
    void getCity() throws Exception {
        //given
        String city = "Wroclaw";
        //when
        provider.setCity(city);
        //then
        assertEquals(provider.getCity(), city);
    }

    @DisplayName("Test setting provider description")
    @Test
    void getDescription() throws Exception {
        //given
        String description = "This is a test provider description";
        //when
        provider.setProviderDescription(description);
        //then
        assertEquals(provider.getProviderDescription(), description);
    }

    @DisplayName("Test setting provider field")
    @Test
    void getField() throws Exception {
        //given
        String field = "car mechanic";
        //when
        provider.setProviderField(field);
        //then
        assertEquals(provider.getProviderField(), field);
    }

    @DisplayName("Test setting services set")
    @Test
    void getServices() throws Exception {
        //given
        Set<AvailableService> services = new HashSet<>();
        AvailableService service = AvailableService.builder().id(1L).build();
        services.add(service);
        //when
        provider.setServices(services);
        //then
        assertEquals(provider.getServices().size(), 1);
    }
}