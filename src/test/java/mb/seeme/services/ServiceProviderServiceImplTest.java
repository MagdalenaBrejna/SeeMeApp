package mb.seeme.services;

import mb.seeme.model.services.AvailableService;
import mb.seeme.model.terms.Term;
import mb.seeme.model.users.ServiceProvider;
import mb.seeme.repositories.ServiceProviderRepository;
import mb.seeme.repositories.TermRepository;
import mb.seeme.services.users.ServiceProviderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceProviderServiceImplTest {

    @Mock
    ServiceProviderRepository providerRepository;
    @Mock
    TermRepository termRepository;

    @InjectMocks
    ServiceProviderServiceImpl service;

    ServiceProvider provider;

    @BeforeEach
    void setUp() {
        provider = ServiceProvider.builder().id(2l).build();
    }

    @Test
    void findAll() {
        //given
        Set<ServiceProvider> testServiceProviderSet = new HashSet<>();
        testServiceProviderSet.add(ServiceProvider.builder().id(1l).build());
        testServiceProviderSet.add(ServiceProvider.builder().id(2l).build());
        //when
        when(providerRepository.findAll()).thenReturn(testServiceProviderSet);
        Set<ServiceProvider> providerSet = service.findAll();
        //then
        assertNotNull(providerSet);
        assertEquals(2, providerSet.size());
    }

    @Test
    void findById() {
        //when
        when(providerRepository.findById(anyLong())).thenReturn(Optional.of(provider));
        ServiceProvider testProvider = service.findById(2l);
        //then
        assertNotNull(testProvider);
    }

    @Test
    void findByIdNotFound() {
        //when
        when(providerRepository.findById(anyLong())).thenReturn(Optional.empty());
        ServiceProvider testProvider = service.findById(2l);
        //then
        assertNull(testProvider);
    }

    @Test
    void save() {
        //given
        ServiceProvider providerToSave = ServiceProvider.builder().id(1l).build();
        //when
        when(providerRepository.save(any())).thenReturn(providerToSave);
        ServiceProvider savedProvider = service.save(providerToSave);
        //then
        assertNotNull(savedProvider);
        verify(providerRepository).save(any());
    }

    @Test
    void delete() {
        //when
        service.delete(provider);
        //then
        verify(providerRepository).delete(any());
    }

    @Test
    void deleteById() {
        //when
        service.deleteById(2l);
        //then
        verify(providerRepository).deleteById(anyLong());
    }

    @Test
    void findAllByNameLikeInTermOrder() {
        //given
        ServiceProvider providerA = ServiceProvider.builder().id(1l).name("A").build();
        ServiceProvider providerB = ServiceProvider.builder().id(2l).name("A").build();
        ServiceProvider providerC = ServiceProvider.builder().id(3l).name("B").build();

        AvailableService service1 = AvailableService.builder().id(1l).serviceProvider(providerB).build();
        AvailableService service2 = AvailableService.builder().id(2l).serviceProvider(providerA).build();
        AvailableService service3 = AvailableService.builder().id(3l).serviceProvider(providerB).build();
        AvailableService service4 = AvailableService.builder().id(4l).serviceProvider(providerC).build();

        Term term1 = Term.builder().id(1l).termDate(LocalDate.parse("2021-11-21")).service(service1).build();
        Term term2 = Term.builder().id(2l).termDate(LocalDate.parse("2021-11-22")).service(service2).build();
        Term term3 = Term.builder().id(3l).termDate(LocalDate.parse("2021-11-23")).service(service3).build();
        Term term4 = Term.builder().id(4l).termDate(LocalDate.parse("2021-11-24")).service(service4).build();

        List<Term> termList = new ArrayList<>();
        termList.add(term1);
        termList.add(term2);
        termList.add(term3);
        termList.add(term4);

        //when
        when(termRepository.findAllFromDate(LocalDate.parse(LocalDate.now().toString()))).thenReturn(termList);
        List<ServiceProvider> serviceProviderList = service.findAllByNameLikeInTermOrder("A");

        //then
        assertNotNull(termList);
        assertEquals(2, serviceProviderList.size());
        assertEquals(providerB, serviceProviderList.get(0));
        assertEquals(providerA, serviceProviderList.get(1));
    }

    @Test
    void findAllWithTermsFromDate() {
        //given
        ServiceProvider providerA = ServiceProvider.builder().id(1l).build();
        ServiceProvider providerB = ServiceProvider.builder().id(2l).build();
        ServiceProvider providerC = ServiceProvider.builder().id(3l).build();

        AvailableService service1 = AvailableService.builder().id(1l).serviceProvider(providerB).build();
        AvailableService service2 = AvailableService.builder().id(2l).serviceProvider(providerA).build();
        AvailableService service3 = AvailableService.builder().id(3l).serviceProvider(providerB).build();
        AvailableService service4 = AvailableService.builder().id(4l).serviceProvider(providerC).build();

        Term term1 = Term.builder().id(1l).termDate(LocalDate.parse("2021-11-21")).service(service1).build();
        Term term2 = Term.builder().id(2l).termDate(LocalDate.parse("2021-11-22")).service(service2).build();
        Term term3 = Term.builder().id(3l).termDate(LocalDate.parse("2021-11-23")).service(service3).build();
        Term term4 = Term.builder().id(4l).termDate(LocalDate.parse("2021-11-24")).service(service4).build();

        List<Term> termList = new ArrayList<>();
        termList.add(term1);
        termList.add(term2);
        termList.add(term3);
        termList.add(term4);

        //when
        when(termRepository.findAllFromDate(LocalDate.parse("2021-11-21"))).thenReturn(termList);
        List<ServiceProvider> serviceProviderList = service.findAllWithTermsFromDateInTermOrder(LocalDate.parse("2021-11-21"));

        //then
        assertNotNull(termList);
        assertEquals(3, serviceProviderList.size());
        assertEquals(providerB, serviceProviderList.get(0));
        assertEquals(providerC, serviceProviderList.get(2));
    }

    @Test
    void findAllByFieldLike() {
        //given
        ServiceProvider providerA = ServiceProvider.builder().id(1l).providerField("mechanic").build();
        ServiceProvider providerB = ServiceProvider.builder().id(2l).providerField("barber").build();
        ServiceProvider providerC = ServiceProvider.builder().id(3l).providerField("mechanic").build();

        AvailableService service1 = AvailableService.builder().id(1l).serviceProvider(providerB).build();
        AvailableService service2 = AvailableService.builder().id(2l).serviceProvider(providerC).build();
        AvailableService service3 = AvailableService.builder().id(3l).serviceProvider(providerB).build();
        AvailableService service4 = AvailableService.builder().id(4l).serviceProvider(providerA).build();

        Term term1 = Term.builder().id(1l).termDate(LocalDate.parse("2021-11-21")).service(service1).build();
        Term term2 = Term.builder().id(2l).termDate(LocalDate.parse("2021-11-22")).service(service2).build();
        Term term3 = Term.builder().id(3l).termDate(LocalDate.parse("2021-11-23")).service(service3).build();
        Term term4 = Term.builder().id(4l).termDate(LocalDate.parse("2021-11-24")).service(service4).build();

        List<Term> termList = new ArrayList<>();
        termList.add(term1);
        termList.add(term2);
        termList.add(term3);
        termList.add(term4);

        //when
        when(termRepository.findAllFromDate(LocalDate.parse(LocalDate.now().toString()))).thenReturn(termList);
        List<ServiceProvider> serviceProviderList = service.findAllByFieldLikeInTermOrder("mechanic");

        //then
        assertNotNull(termList);
        assertEquals(2, serviceProviderList.size());
        assertEquals(providerC, serviceProviderList.get(0));
        assertEquals(providerA, serviceProviderList.get(1));
    }
}