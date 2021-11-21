package mb.seeme.model.terms;

import mb.seeme.model.services.AvailableService;
import mb.seeme.model.users.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TermTest {

    Term term;

    @BeforeEach
    void setUp() {
        term = new Term();
    }

    @Test
    void getId() throws Exception {
        //given
        Long idValue = 2L;
        //when
        term.setId(2L);
        //then
        assertEquals(idValue, term.getId());
    }

    @Test
    void getDate() throws Exception {
        //given
        LocalDate date = LocalDate.parse("2021-11-21");
        //when
        term.setDate(date);
        //then
        assertEquals(term.getDate(), date);
    }

    @Test
    void getTime() throws Exception {
        //given
        LocalTime now = LocalTime.now();
        //when
        term.setTime(now);
        //then
        assertEquals(term.getTime(), now);
    }

    @Test
    void getTermDescription() throws Exception {
        //given
        String description = "This is test term description";
        //when
        term.setTermDescription(description);
        //then
        assertEquals(term.getTermDescription(), description);
    }

    @Test
    void getService() throws Exception {
        //given
        AvailableService service = AvailableService.builder().id(2L).build();
        //when
        term.setService(service);
        //then
        assertEquals(term.getService().getId(), service.getId());
    }

    @Test
    void getClient() throws Exception {
        //given
        Client client = Client.builder().name("John").build();
        //when
        term.setClient(client);
        //then
        assertEquals(term.getClient().getName(), client.getName());
    }

    @Test
    void getStatus() throws Exception {
        //given
        Status status = Status.DONE;
        //when
        term.setStatus(status);
        //then
        assertEquals(term.getStatus(), status);
    }
}