package mb.seeme.model.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class ServiceProviderTermTest {

    ServiceProviderTerm serviceProviderTerm;

    @BeforeEach
    void setUp() {
        serviceProviderTerm = new ServiceProviderTerm();
    }

    @DisplayName("Test equals and not equals of 2 objects")
    @Test
    void testEquals() {
        //when
        getProviderName();
        getCity();

        ServiceProviderTerm serviceProviderTermTestEquals = new ServiceProviderTerm();
        serviceProviderTermTestEquals.setCity("Wroclaw");
        serviceProviderTermTestEquals.setProviderName("auto-service");

        ServiceProviderTerm serviceProviderTermTestNotEquals = new ServiceProviderTerm();
        serviceProviderTermTestNotEquals.setCity("Wroclaw");
        serviceProviderTermTestNotEquals.setProviderName("auto");

        //then
        assertEquals(serviceProviderTerm, serviceProviderTermTestEquals);
        assertNotEquals(serviceProviderTerm, serviceProviderTermTestNotEquals);
    }

    @DisplayName("Test setting provider Id")
    @Test
    void getProviderId() {
        //given
        Long idValue = 2L;
        //when
        serviceProviderTerm.setProviderId(2L);
        //then
        assertEquals(idValue, serviceProviderTerm.getProviderId());
    }

    @DisplayName("Test setting provider name")
    @Test
    void getProviderName() {
        //given
        String name = "auto-service";
        //when
        serviceProviderTerm.setProviderName(name);
        //then
        assertEquals(serviceProviderTerm.getProviderName(), name);
    }

    @DisplayName("Test setting provider field")
    @Test
    void getProviderField() {
        //given
        String field = "car mechanic";
        //when
        serviceProviderTerm.setProviderField(field);
        //then
        assertEquals(serviceProviderTerm.getProviderField(), field);
    }

    @DisplayName("Test setting provider description")
    @Test
    void getProviderDescription() {
        //given
        String description = "This is a test provider description";
        //when
        serviceProviderTerm.setProviderDescription(description);
        //then
        assertEquals(serviceProviderTerm.getProviderDescription(), description);
    }

    @DisplayName("Test setting provider address")
    @Test
    void getAddress() {
        //given
        String address = "ul. Czekoladowa 54-240";
        //when
        serviceProviderTerm.setAddress(address);
        //then
        assertEquals(serviceProviderTerm.getAddress(), address);
    }

    @DisplayName("Test setting provider city")
    @Test
    void getCity() {
        //given
        String city = "Wroclaw";
        //when
        serviceProviderTerm.setCity(city);
        //then
        assertEquals(serviceProviderTerm.getCity(), city);
    }

    @DisplayName("Test setting provider term date")
    @Test
    void getTermDate() {
        //given
        LocalDate date = LocalDate.parse("2021-11-21");
        //when
        serviceProviderTerm.setTermDate(date);
        //then
        assertEquals(serviceProviderTerm.getTermDate(), date);
    }

    @DisplayName("Test getting error while setting wrong term date for provider")
    @Test
    void getWrongTermDateThrowsException() throws Exception {
        //given + when
        Exception wrongDateException = assertThrows(java.time.format.DateTimeParseException.class, () -> {
            serviceProviderTerm.setTermDate(LocalDate.parse("2021-11-41"));
        }, "Wrong date range");
        //then
        assertTrue(wrongDateException.getMessage().contains("Invalid value for DayOfMonth"));
    }

    @DisplayName("Test setting provider term time")
    @Test
    void getTermTime() {
        //given
        LocalTime now = LocalTime.now();
        //when
        serviceProviderTerm.setTermTime(now);
        //then
        assertEquals(serviceProviderTerm.getTermTime(), now);
    }

    @DisplayName("Test getting error while setting wrong term time for provider")
    @Test
    void getWrongProviderTermTimeThrowsException() throws Exception {
        //given + when
        Exception wrongTimeException = assertThrows(java.time.format.DateTimeParseException.class, () -> {
            serviceProviderTerm.setTermTime(LocalTime.parse("25:32:22", DateTimeFormatter.ISO_TIME));
        }, "Wrong time range");
        //then
        assertTrue(wrongTimeException.getMessage().contains("Invalid value for HourOfDay"));
    }
}