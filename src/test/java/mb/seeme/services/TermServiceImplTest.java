package mb.seeme.services;

import mb.seeme.model.services.AvailableService;
import mb.seeme.model.terms.Term;
import mb.seeme.model.users.Client;
import mb.seeme.model.users.ServiceProvider;
import mb.seeme.repositories.TermRepository;
import mb.seeme.services.terms.TermServiceImpl;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TermServiceImplTest {

    @Mock
    TermRepository termRepository;

    @InjectMocks
    TermServiceImpl service;

    Term term;

    @BeforeEach
    void setUp() {
        term = Term.builder().id(2l).build();
    }

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
        assertNotNull(termSet);
        assertEquals(2, termSet.size());
    }

    @Test
    void findById() {
        //when
        when(termRepository.findById(anyLong())).thenReturn(Optional.of(term));
        Term testTerm = service.findById(2l);
        //then
        assertNotNull(testTerm);
    }

    @Test
    void findByIdNotFound() {
        //when
        when(termRepository.findById(anyLong())).thenReturn(Optional.empty());
        Term testTerm = service.findById(2l);
        //then
        assertNull(testTerm);
    }

    @Test
    void save() {
        //given
        Term termToSave = Term.builder().id(1l).build();
        //when
        when(termRepository.save(any())).thenReturn(termToSave);
        Term savedTerm = service.save(termToSave);
        //then
        assertNotNull(savedTerm);
        verify(termRepository).save(any());
    }

    @Test
    void delete() {
        //when
        service.delete(term);
        //then
        verify(termRepository).delete(any());
    }

    @Test
    void deleteById() {
        //when
        service.deleteById(2l);
        //then
        verify(termRepository).deleteById(anyLong());
    }

    @Test
    void findAllFutureByClientId() {
        //given
        Client clientA = Client.builder().id(1l).build();

        AvailableService service1 = AvailableService.builder().id(2l).build();
        AvailableService service2 = AvailableService.builder().id(3l).build();
        AvailableService service3 = AvailableService.builder().id(4l).build();

        Term term1 = Term.builder().id(2l).termDate(LocalDate.parse("2022-11-30")).service(service1).client(clientA).build();
        Term term2 = Term.builder().id(3l).termDate(LocalDate.parse("2021-11-20")).service(service2).client(clientA).build();
        Term term3 = Term.builder().id(4l).termDate(LocalDate.parse("2022-11-27")).service(service3).client(clientA).build();

        List<Term> termList = new ArrayList<>();
        termList.add(term1);
        termList.add(term2);
        termList.add(term3);

        //when
        when(termRepository.findAllByClientId(anyLong())).thenReturn(termList);
        List<Term> terms = service.findAllFutureByClientId(clientA.getId());

        //then
        assertNotNull(termList);
        assertEquals(2, terms.size());
        assertEquals(term3, terms.get(0));
        assertEquals(term1, terms.get(1));
    }

    @Test
    void findAllPastByClientId() {
        //given
        Client clientA = Client.builder().id(1l).build();

        AvailableService service1 = AvailableService.builder().id(1l).build();
        AvailableService service2 = AvailableService.builder().id(2l).build();
        AvailableService service3 = AvailableService.builder().id(3l).build();

        Term term1 = Term.builder().id(2l).termDate(LocalDate.parse("2021-11-19")).service(service1).client(clientA).build();
        Term term2 = Term.builder().id(3l).termDate(LocalDate.parse("2022-02-21")).service(service2).client(clientA).build();
        Term term3 = Term.builder().id(4l).termDate(LocalDate.parse("2021-11-20")).service(service3).client(clientA).build();

        List<Term> termList = new ArrayList<>();
        termList.add(term1);
        termList.add(term2);
        termList.add(term3);

        //when
        when(termRepository.findAllByClientId(anyLong())).thenReturn(termList);
        List<Term> terms = service.findAllPastByClientId(clientA.getId());

        //then
        assertNotNull(termList);
        assertEquals(2, terms.size());
        assertEquals(term1, terms.get(0));
        assertEquals(term3, terms.get(1));
    }

    @Test
    public void findAllFutureFreeByProviderId() {
        //given
        ServiceProvider providerA = ServiceProvider.builder().id(1l).build();

        AvailableService service1 = AvailableService.builder().id(1l).serviceProvider(providerA).build();
        AvailableService service2 = AvailableService.builder().id(2l).serviceProvider(providerA).build();
        AvailableService service3 = AvailableService.builder().id(3l).serviceProvider(providerA).build();

        Term term1 = Term.builder().id(1l).termDate(LocalDate.parse("2022-11-22")).service(service1).build();
        Term term2 = Term.builder().id(2l).termDate(LocalDate.parse("2021-11-28")).service(service2).build();
        Term term3 = Term.builder().id(3l).termDate(LocalDate.parse("2021-11-21")).service(service3).build();

        List<Term> termList = new ArrayList<>();
        termList.add(term1);
        termList.add(term2);
        termList.add(term3);

        //when
        when(termRepository.findAllFreeByProviderIdFromDate(providerA.getId(), LocalDate.parse(LocalDate.now().toString()))).thenReturn(termList);
        List<Term> terms = service.findAllFutureFreeByProviderId(providerA.getId());

        //then
        assertNotNull(termList);
        assertEquals(3, terms.size());
        assertEquals(3l, terms.get(0).getId());
        assertEquals(2l, terms.get(1).getId());
    }



    @Test
    public void findAllFutureFreeByProviderIdFromDate() {
        //given
        ServiceProvider providerA = ServiceProvider.builder().id(1l).build();

        AvailableService service1 = AvailableService.builder().id(1l).serviceProvider(providerA).build();
        AvailableService service2 = AvailableService.builder().id(2l).serviceProvider(providerA).build();
        AvailableService service3 = AvailableService.builder().id(3l).serviceProvider(providerA).build();

        Term term1 = Term.builder().id(1l).termDate(LocalDate.parse("2022-11-22")).service(service1).build();
        Term term2 = Term.builder().id(2l).termDate(LocalDate.parse("2021-11-28")).service(service2).build();
        Term term3 = Term.builder().id(3l).termDate(LocalDate.parse("2021-11-21")).service(service3).build();

        List<Term> termList = new ArrayList<>();
        termList.add(term1);
        termList.add(term2);
        termList.add(term3);

        //when
        when(termRepository.findAllFreeByProviderIdFromDate(providerA.getId(), LocalDate.parse("2022-11-21"))).thenReturn(termList);
        List<Term> terms = service.findAllFutureFreeByProviderIdFromDate(providerA.getId(), LocalDate.parse("2022-11-21"));

        //then
        assertNotNull(termList);
        assertEquals(3, terms.size());
        assertEquals(3l, terms.get(0).getId());
        assertEquals(2l, terms.get(1).getId());
    }

    @Test
    public void findAllFutureAppointedByProviderId() {
        //given
        ServiceProvider providerA = ServiceProvider.builder().id(1l).build();

        AvailableService service1 = AvailableService.builder().id(1l).serviceProvider(providerA).build();
        AvailableService service2 = AvailableService.builder().id(2l).serviceProvider(providerA).build();
        AvailableService service3 = AvailableService.builder().id(3l).serviceProvider(providerA).build();

        Term term1 = Term.builder().id(1l).termDate(LocalDate.parse("2022-11-22")).service(service1).build();
        Term term2 = Term.builder().id(2l).termDate(LocalDate.parse("2021-11-28")).service(service2).build();
        Term term3 = Term.builder().id(3l).termDate(LocalDate.parse("2021-11-21")).service(service3).build();

        List<Term> termList = new ArrayList<>();
        termList.add(term1);
        termList.add(term2);
        termList.add(term3);

        //when
        when(termRepository.findAllAppointedByProviderIdFromDate(providerA.getId(), LocalDate.parse(LocalDate.now().toString()))).thenReturn(termList);
        List<Term> terms = service.findAllFutureAppointedByProviderId(providerA.getId());

        //then
        assertNotNull(termList);
        assertEquals(3, terms.size());
        assertEquals(3l, terms.get(0).getId());
        assertEquals(2l, terms.get(1).getId());
    }

    @Test
    public void findAllFutureAppointedByProviderIdFromDate() {
        //given
        ServiceProvider providerA = ServiceProvider.builder().id(1l).build();

        AvailableService service1 = AvailableService.builder().id(1l).serviceProvider(providerA).build();
        AvailableService service2 = AvailableService.builder().id(2l).serviceProvider(providerA).build();
        AvailableService service3 = AvailableService.builder().id(3l).serviceProvider(providerA).build();

        Term term1 = Term.builder().id(1l).termDate(LocalDate.parse("2022-11-22")).service(service1).build();
        Term term2 = Term.builder().id(2l).termDate(LocalDate.parse("2021-11-28")).service(service2).build();
        Term term3 = Term.builder().id(3l).termDate(LocalDate.parse("2021-11-21")).service(service3).build();

        List<Term> termList = new ArrayList<>();
        termList.add(term1);
        termList.add(term2);
        termList.add(term3);

        //when
        when(termRepository.findAllAppointedByProviderIdFromDate(providerA.getId(), LocalDate.parse("2022-11-21"))).thenReturn(termList);
        List<Term> terms = service.findAllFutureAppointedByProviderIdFromDate(providerA.getId(), LocalDate.parse("2022-11-21"));

        //then
        assertNotNull(termList);
        assertEquals(3, terms.size());
        assertEquals(3l, terms.get(0).getId());
        assertEquals(2l, terms.get(1).getId());
    }

    @Test
    public void findAllPastAppointedByProviderId() {
        //given
        ServiceProvider providerA = ServiceProvider.builder().id(1l).build();

        AvailableService service1 = AvailableService.builder().id(1l).serviceProvider(providerA).build();
        AvailableService service2 = AvailableService.builder().id(2l).serviceProvider(providerA).build();
        AvailableService service3 = AvailableService.builder().id(3l).serviceProvider(providerA).build();

        Term term1 = Term.builder().id(1l).termDate(LocalDate.parse("2020-11-22")).service(service1).build();
        Term term2 = Term.builder().id(2l).termDate(LocalDate.parse("2020-11-28")).service(service2).build();
        Term term3 = Term.builder().id(3l).termDate(LocalDate.parse("2020-11-21")).service(service3).build();

        List<Term> termList = new ArrayList<>();
        termList.add(term1);
        termList.add(term2);
        termList.add(term3);

        //when
        when(termRepository.findAllAppointedByProviderIdBeforeDate(providerA.getId(), LocalDate.parse(LocalDate.now().toString()))).thenReturn(termList);
        List<Term> terms = service.findAllPastAppointedByProviderId(providerA.getId());

        //then
        assertNotNull(termList);
        assertEquals(3, terms.size());
        assertEquals(2l, terms.get(0).getId());
        assertEquals(1l, terms.get(1).getId());
    }

    @Test
    public void findAllPastAppointedByDateAndProviderId() {
        //given
        ServiceProvider providerA = ServiceProvider.builder().id(1l).build();

        AvailableService service1 = AvailableService.builder().id(1l).serviceProvider(providerA).build();
        AvailableService service2 = AvailableService.builder().id(2l).serviceProvider(providerA).build();
        AvailableService service3 = AvailableService.builder().id(3l).serviceProvider(providerA).build();

        Term term1 = Term.builder().id(1l).termDate(LocalDate.parse("2020-11-22")).termTime(LocalTime.parse("11:32:22", DateTimeFormatter.ISO_TIME)).service(service1).build();
        Term term2 = Term.builder().id(2l).termDate(LocalDate.parse("2020-11-22")).termTime(LocalTime.parse("12:32:22", DateTimeFormatter.ISO_TIME)).service(service2).build();
        Term term3 = Term.builder().id(3l).termDate(LocalDate.parse("2020-11-22")).termTime(LocalTime.parse("10:32:22", DateTimeFormatter.ISO_TIME)).service(service3).build();

        List<Term> termList = new ArrayList<>();
        termList.add(term1);
        termList.add(term2);
        termList.add(term3);

        //when
        when(termRepository.findAllAppointedByProviderIdAtDate(providerA.getId(), LocalDate.parse("2020-11-22"))).thenReturn(termList);
        List<Term> terms = service.findAllPastAppointedByDateAndProviderId(providerA.getId(), LocalDate.parse("2020-11-22"));

        //then
        assertNotNull(termList);
        assertEquals(3, terms.size());
        assertEquals(3l, terms.get(0).getId());
        assertEquals(1l, terms.get(1).getId());
    }

    @Test
    public void findAllPastAppointedByProviderIdAndClientId() {
        //given
        Client clientA = Client.builder().id(1l).build();

        ServiceProvider providerA = ServiceProvider.builder().id(1l).build();
        ServiceProvider providerB = ServiceProvider.builder().id(2l).build();

        AvailableService service1 = AvailableService.builder().id(1l).serviceProvider(providerA).build();
        AvailableService service2 = AvailableService.builder().id(2l).serviceProvider(providerB).build();
        AvailableService service3 = AvailableService.builder().id(3l).serviceProvider(providerA).build();
        AvailableService service4 = AvailableService.builder().id(4l).serviceProvider(providerA).build();

        Term term1 = Term.builder().id(1l).termDate(LocalDate.parse("2021-11-18")).service(service1).client(clientA).build();
        Term term2 = Term.builder().id(2l).termDate(LocalDate.parse("2021-11-19")).service(service2).client(clientA).build();
        Term term3 = Term.builder().id(3l).termDate(LocalDate.parse("2021-11-20")).service(service3).client(clientA).build();
        Term term4 = Term.builder().id(4l).termDate(LocalDate.parse("2021-11-19")).service(service4).client(clientA).build();

        List<Term> termList = new ArrayList<>();
        termList.add(term1);
        termList.add(term2);
        termList.add(term3);
        termList.add(term4);

        //when
        when(termRepository.findAllByClientId(anyLong())).thenReturn(termList);
        List<Term> terms = service.findAllPastAppointedByProviderIdAndClientId(providerA.getId(), clientA.getId());

        //then
        assertNotNull(termList);
        assertEquals(3, terms.size());
        assertEquals(term3, terms.get(0));
        assertEquals(term1, terms.get(2));
    }

    @Test
    public void findAllPastAppointedByDateAndProviderIdAndClientId() {
        //given
        Client clientA = Client.builder().id(1l).build();

        ServiceProvider providerA = ServiceProvider.builder().id(1l).build();
        ServiceProvider providerB = ServiceProvider.builder().id(2l).build();

        AvailableService service1 = AvailableService.builder().id(1l).serviceProvider(providerA).build();
        AvailableService service2 = AvailableService.builder().id(2l).serviceProvider(providerB).build();
        AvailableService service3 = AvailableService.builder().id(3l).serviceProvider(providerA).build();
        AvailableService service4 = AvailableService.builder().id(4l).serviceProvider(providerA).build();

        Term term1 = Term.builder().id(1l).termDate(LocalDate.parse("2021-11-19")).termTime(LocalTime.parse("12:32:22", DateTimeFormatter.ISO_TIME)).service(service1).client(clientA).build();
        Term term2 = Term.builder().id(2l).termDate(LocalDate.parse("2021-11-19")).termTime(LocalTime.parse("13:32:22", DateTimeFormatter.ISO_TIME)).service(service2).client(clientA).build();
        Term term3 = Term.builder().id(3l).termDate(LocalDate.parse("2021-11-20")).termTime(LocalTime.parse("14:32:22", DateTimeFormatter.ISO_TIME)).service(service3).client(clientA).build();
        Term term4 = Term.builder().id(4l).termDate(LocalDate.parse("2021-11-19")).termTime(LocalTime.parse("09:32:22", DateTimeFormatter.ISO_TIME)).service(service4).client(clientA).build();

        List<Term> termList = new ArrayList<>();
        termList.add(term1);
        termList.add(term2);
        termList.add(term3);
        termList.add(term4);

        //when
        when(termRepository.findAllByClientId(anyLong())).thenReturn(termList);
        List<Term> terms = service.findAllPastAppointedByDateAndProviderIdAndClientId(providerA.getId(), clientA.getId(), LocalDate.parse("2021-11-19"));

        //then
        assertNotNull(termList);
        assertEquals(2, terms.size());
        assertEquals(term4, terms.get(0));
        assertEquals(term1, terms.get(1));
    }
}