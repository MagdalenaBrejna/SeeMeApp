package mb.seeme.services;

import mb.seeme.model.services.AvailableService;
import mb.seeme.model.terms.Term;
import mb.seeme.model.users.Client;
import mb.seeme.model.users.ServiceProvider;
import mb.seeme.repositories.TermRepository;
import mb.seeme.services.terms.TermServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TermServiceImplTest {

    @Mock
    TermRepository termRepository;

    @InjectMocks
    TermServiceImpl service;

    private Term testTerm;
    private static List<Term> termListAll;
    private static List<Term> termListSameProvider;
    private static List<Term> termListSameDaySameProvider;
    private static List<Term> termListFreeSameProvider;

    @BeforeEach
    void setUp() {
        testTerm = Term.builder().id(2l).build();
    }

    @BeforeAll
    static void setTestEnvironment(){
        //given
        Client clientA = Client.builder().id(1l).name("clientA").build();

        ServiceProvider providerA = ServiceProvider.builder().id(1l).build();
        ServiceProvider providerB = ServiceProvider.builder().id(2l).build();

        AvailableService service1 = AvailableService.builder().id(1l).serviceProvider(providerA).build();
        AvailableService service2 = AvailableService.builder().id(2l).serviceProvider(providerB).build();

        Term term1 = Term.builder().id(1l).termDate(LocalDate.parse("2021-11-19")).termTime(LocalTime.parse("12:32:22", DateTimeFormatter.ISO_TIME)).service(service1).client(clientA).build();
        Term term2 = Term.builder().id(2l).termDate(LocalDate.parse("2021-11-19")).termTime(LocalTime.parse("13:32:22", DateTimeFormatter.ISO_TIME)).service(service2).client(clientA).build();
        Term term3 = Term.builder().id(3l).termDate(LocalDate.parse("2021-11-20")).termTime(LocalTime.parse("14:32:22", DateTimeFormatter.ISO_TIME)).service(service1).client(clientA).build();
        Term term4 = Term.builder().id(4l).termDate(LocalDate.parse("2021-11-19")).termTime(LocalTime.parse("09:32:22", DateTimeFormatter.ISO_TIME)).service(service1).client(clientA).build();
        Term term5 = Term.builder().id(5l).termDate(LocalDate.parse("2022-11-22")).termTime(LocalTime.parse("09:32:22", DateTimeFormatter.ISO_TIME)).service(service2).client(clientA).build();
        Term term6 = Term.builder().id(6l).termDate(LocalDate.parse("2022-11-15")).termTime(LocalTime.parse("09:32:22", DateTimeFormatter.ISO_TIME)).service(service1).client(clientA).build();
        Term term7 = Term.builder().id(7l).termDate(LocalDate.parse("2022-11-23")).termTime(LocalTime.parse("09:32:22", DateTimeFormatter.ISO_TIME)).service(service1).build();
        Term term8 = Term.builder().id(8l).termDate(LocalDate.parse("2022-11-14")).termTime(LocalTime.parse("09:32:22", DateTimeFormatter.ISO_TIME)).service(service1).build();

        termListSameDaySameProvider = new ArrayList<>();
        termListSameDaySameProvider.add(term1);
        termListSameDaySameProvider.add(term4);

        termListSameProvider = new ArrayList<>();
        termListSameProvider.addAll(termListSameDaySameProvider);
        termListSameProvider.add(term3);
        termListSameProvider.add(term6);

        termListAll = new ArrayList<>();
        termListAll.addAll(termListSameProvider);
        termListAll.add(term2);
        termListAll.add(term5);

        termListFreeSameProvider = new ArrayList<>();
        termListFreeSameProvider.add(term7);
        termListFreeSameProvider.add(term8);
    }

    @DisplayName("Test getting all terms")
    @Test
    void findAll() {
        //given
        Set<Term> testTermSet = new HashSet<>();
        testTermSet.add(Term.builder().id(1l).build());
        testTermSet.add(Term.builder().id(2l).build());

        //when
        when(termRepository.findAll()).thenReturn(testTermSet);
        Set<Term> termSet = service.findAll();

        //then
        verify(termRepository).findAll();
        verify(termRepository, never()).findById(anyLong());
        assertNotNull(termSet);
        assertEquals(2, termSet.size());
    }

    @DisplayName("Test getting term by id")
    @Test
    void findById() {
        //when
        when(termRepository.findById(anyLong())).thenReturn(Optional.of(testTerm));
        Term testTerm = service.findById(2l);

        //then
        verify(termRepository).findById(anyLong());
        verify(termRepository, never()).findAll();
        assertNotNull(testTerm);
    }

    @DisplayName("Test getting term by id which doesn't exist")
    @Test
    void findByIdNotFound() {
        //when
        when(termRepository.findById(anyLong())).thenReturn(Optional.empty());
        Term testTerm = service.findById(2l);

        //then
        verify(termRepository).findById(anyLong());
        verify(termRepository, never()).findAll();
        assertNull(testTerm);
    }

    @DisplayName("Test saving term")
    @Test
    void save() {
        //when
        when(termRepository.save(any())).thenReturn(testTerm);
        Term savedTerm = service.save(testTerm);

        //then
        assertNotNull(savedTerm);
        verify(termRepository).save(any());
    }

    @DisplayName("Test deleting term")
    @Test
    void delete() {
        //when
        service.delete(testTerm);

        //then
        verify(termRepository).delete(any());
    }

    @DisplayName("Test deleting term by id")
    @Test
    void deleteById() {
        //when
        service.deleteById(2l);

        //then
        verify(termRepository).deleteById(anyLong());
    }

    @DisplayName("Test finding all future terms of a specified client")
    @Test
    void findAllFutureByClientId() {
        //when
        when(termRepository.findAllByClientName(anyString())).thenReturn(termListAll);
        List<Term> terms = service.findAllFutureByClientName(anyString());

        //then
        assertNotNull(termListAll);
        assertEquals(2, terms.size());
        assertTrue(terms.get(0).getId() == 6l);
        assertTrue(terms.get(1).getId() == 5l);
        verify(termRepository).findAllByClientName(anyString());
    }

    @DisplayName("Test finding all past terms of a specified client")
    @Test
    void findAllPastByClientId() {
        //when
        when(termRepository.findAllByClientName(anyString())).thenReturn(termListAll);
        List<Term> terms = service.findAllPastByClientName(anyString());

        //then
        assertNotNull(termListAll);
        assertEquals(4, terms.size());
        assertTrue(terms.get(0).getId() == 1l);
        assertTrue(terms.get(3).getId() == 3l);
        assertFalse(terms.get(3).getId() == 6l);
        verify(termRepository).findAllByClientName(anyString());
    }



        @DisplayName("Test finding all future free terms of a specified provider")
        @Test
        public void findAllFutureFreeByProviderId() {
            //when
            when(termRepository.findAllFreeByProviderIdFromDate(anyLong(), any())).thenReturn(termListFreeSameProvider);
            List<Term> terms = service.findAllFutureFreeByProviderId(1l);

            //then
            assertNotNull(termListFreeSameProvider);
            assertEquals(2, terms.size());
            assertTrue(terms.get(0).getId() == 8l);
            assertTrue(terms.get(1).getId() == 7l);
            verify(termRepository).findAllFreeByProviderIdFromDate(anyLong(), any());
        }

        @DisplayName("Test finding all future free terms of a specified provider and later than specified a date")
        @Test
        public void findAllFutureFreeByProviderIdFromDate() {
            //when
            when(termRepository.findAllFreeByProviderIdFromDate(anyLong(), any())).thenReturn(termListFreeSameProvider);
            List<Term> terms = service.findAllFutureFreeByProviderIdFromDate(1l, LocalDate.parse("2022-03-18"));

            //then
            assertNotNull(termListFreeSameProvider);
            assertEquals(2, terms.size());
            assertTrue(terms.get(0).getId() == 8l);
            assertTrue(terms.get(1).getId() == 7l);
            verify(termRepository).findAllFreeByProviderIdFromDate(anyLong(), any());
        }



    @DisplayName("Test finding all future appointed terms of a specified provider")
    @Test
    public void findAllFutureAppointedByProviderId() {
        //when
        when(termRepository.findAllAppointedByProviderIdFromDate(anyLong(), any())).thenReturn(termListSameProvider);
        List<Term> terms = service.findAllFutureAppointedByProviderId(2l);

        //then
        assertNotNull(termListSameProvider);
        assertEquals(4, terms.size());
        System.out.println(terms.get(0).getId());
        assertTrue(terms.get(0).getId() == 4l);
        System.out.println(terms.get(1).getId());
        assertTrue(terms.get(1).getId() == 1l);
        verify(termRepository).findAllAppointedByProviderIdFromDate(anyLong(), any());
    }

    @DisplayName("Test finding all future appointed terms of a specified provider later than a specified date")
    @Test
    public void findAllFutureAppointedByProviderIdFromDate() {
        //when
        when(termRepository.findAllAppointedByProviderIdFromDate(anyLong(), any())).thenReturn(termListSameProvider);
        List<Term> terms = service.findAllFutureAppointedByProviderIdFromDate(2l, LocalDate.parse("2021-11-18"));

        //then
        assertNotNull(termListSameProvider);
        assertEquals(4, terms.size());
        assertTrue(terms.get(0).getId() == 4l);
        assertTrue(terms.get(1).getId() == 1l);
        verify(termRepository).findAllAppointedByProviderIdFromDate(anyLong(), any());
    }

    @DisplayName("Test finding all past appointed terms of a specified provider before a specified date")
    @Test
    public void findAllPastAppointedByProviderId() {
        //when
        when(termRepository.findAllAppointedByProviderIdBeforeDate(anyLong(), any())).thenReturn(termListSameProvider);
        List<Term> terms = service.findAllPastAppointedByProviderId(1l);

        //then
        assertNotNull(termListAll);
        assertEquals(4, terms.size());
        assertTrue(terms.get(0).getId() == 6l);
        assertTrue(terms.get(1).getId() == 3l);
        verify(termRepository).findAllAppointedByProviderIdBeforeDate(anyLong(), any());
    }

    @DisplayName("Test finding all past appointed terms of a specified provider at a specified date")
    @Test
    public void findAllPastAppointedByDateAndProviderId() {
        //when
        when(termRepository.findAllAppointedByProviderIdAtDate(anyLong(), any())).thenReturn(termListSameDaySameProvider);
        List<Term> terms = service.findAllPastAppointedByDateAndProviderId(1l, LocalDate.parse("2021-11-19"));

        //then
        assertNotNull(termListAll);
        assertEquals(2, terms.size());
        assertTrue(terms.get(0).getId() == 4l);
        assertTrue(terms.get(1).getId() == 1l);
        verify(termRepository).findAllAppointedByProviderIdAtDate(anyLong(), any());
    }

    @DisplayName("Test finding all past appointed terms of a specified provider and a specified client")
    @Test
    public void findAllPastAppointedByProviderIdAndClientId() {
        //when
        when(termRepository.findAllByClientName(anyString())).thenReturn(termListAll);
        List<Term> terms = service.findAllPastAppointedByProviderIdAndClientName(1l, "clientA");

        //then
        assertNotNull(termListAll);
        assertEquals(3, terms.size());
        assertTrue(terms.get(0).getId() == 3l);
        assertTrue(terms.get(1).getId() == 1l);
        verify(termRepository).findAllByClientName(anyString());
    }
/*
    @DisplayName("Test finding all past appointed terms of a specified provider and specified client at a specified date")
    @Test
    public void findAllPastAppointedByDateAndProviderIdAndClientId() {
        //when
        when(termRepository.findAllByClientName(anyString())).thenReturn(termListAll);
        List<Term> terms = service.findAllPastAppointedBeforeDateAndProviderIdAndClientName(1l, "clientA", LocalDate.parse("2021-11-19"));

        //then
        assertNotNull(termListAll);
        assertEquals(2, terms.size());
        assertTrue(terms.get(0).getId() == 4l);
        assertTrue(terms.get(1).getId() == 1l);
        verify(termRepository).findAllByClientName(anyString());
    }

 */
}