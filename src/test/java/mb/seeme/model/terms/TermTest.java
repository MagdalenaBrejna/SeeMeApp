package mb.seeme.model.terms;

import mb.seeme.model.services.AvailableService;
import mb.seeme.model.users.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test term entity")
class TermTest {

    Term term;

    @BeforeEach
    void setUp() {
        term = new Term();
    }

    @DisplayName("Test setting term id")
    @Test
    void getId() throws Exception {
        //given
        Long idValue = 2L;
        //when
        term.setId(2L);
        //then
        assertEquals(idValue, term.getId());
    }

    @DisplayName("Test setting term date")
    @Test
    void getTermDate() throws Exception {
        //given
        LocalDate date = LocalDate.parse("2021-11-21");
        //when
        term.setTermDate(date);
        //then
        assertEquals(term.getTermDate(), date);
    }

    @DisplayName("Test getting error while setting wrong term date")
    @Test
    void getWrongTermDateThrowsException() throws Exception {
        //given + when
        Exception wrongDateException = assertThrows(java.time.format.DateTimeParseException.class, () -> {
            term.setTermDate(LocalDate.parse("2021-11-41"));
        }, "Wrong date range");
        //then
        assertTrue(wrongDateException.getMessage().contains("Invalid value for DayOfMonth"));
    }

    @DisplayName("Test setting term time")
    @Test
    void getTermTime() throws Exception {
        //given
        LocalTime now = LocalTime.now();
        //when
        term.setTermTime(now);
        //then
        assertEquals(term.getTermTime(), now);
    }

    @DisplayName("Test getting error while setting wrong term time")
    @Test
    void getWrongTermTimeThrowsException() throws Exception {
        //given + when
        Exception wrongTimeException = assertThrows(java.time.format.DateTimeParseException.class, () -> {
            term.setTermTime(LocalTime.parse("25:32:22", DateTimeFormatter.ISO_TIME));
        }, "Wrong time range");
        //then
        assertTrue(wrongTimeException.getMessage().contains("Invalid value for HourOfDay"));
    }

    @DisplayName("Test setting term description")
    @Test
    void getTermDescription() throws Exception {
        //given
        String description = "This is test term description";
        //when
        term.setTermDescription(description);
        //then
        assertEquals(term.getTermDescription(), description);
    }

    @DisplayName("Test setting term service")
    @Test
    void getService() throws Exception {
        //given
        AvailableService service = AvailableService.builder().id(2L).build();
        //when
        term.setService(service);
        //then
        assertEquals(term.getService().getId(), service.getId());
    }

    @DisplayName("Test setting term client")
    @Test
    void getClient() throws Exception {
        //given
        Client client = Client.builder().name("John").build();
        //when
        term.setClient(client);
        //then
        assertEquals(term.getClient().getName(), client.getName());
    }

    @DisplayName("Test getting error while getting name of null client")
    @Test
    void getEmptyClientThrowsException() throws Exception {
        //given + when
        Exception emptyClientException = assertThrows(NullPointerException.class, () -> {
            term.getClient().getName();
        }, "Client is null");
        //then
        assertEquals(NullPointerException.class, emptyClientException.getClass());
    }

    @DisplayName("Test setting term status")
    @Test
    void getStatus() throws Exception {
        //given
        Status status = Status.DONE;
        //when
        term.setTermRealizedStatus(status);
        //then
        assertEquals(term.getTermRealizedStatus(), status);
    }
}