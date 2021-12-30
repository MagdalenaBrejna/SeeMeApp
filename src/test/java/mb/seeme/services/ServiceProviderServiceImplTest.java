package mb.seeme.services;

import mb.seeme.model.services.AvailableService;
import mb.seeme.model.terms.Term;
import mb.seeme.model.users.ServiceProvider;
import mb.seeme.repositories.ServiceProviderRepository;
import mb.seeme.repositories.TermRepository;
import mb.seeme.services.users.ServiceProviderServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceProviderServiceImplTest {

    @Mock
    ServiceProviderRepository providerRepository;
    @Mock
    TermRepository termRepository;

    @InjectMocks
    ServiceProviderServiceImpl service;

    private ServiceProvider testServiceProvider;
    private static List<Term> termList;

    @BeforeEach
    void setUp() {
        testServiceProvider = ServiceProvider.builder().id(2l).build();
    }

    @BeforeAll
    static void setTestEnvironment(){
        //given
        ServiceProvider providerA = ServiceProvider.builder().id(1l).name("A").providerField("mechanic").build();
        ServiceProvider providerB = ServiceProvider.builder().id(2l).name("A").providerField("barber").build();
        ServiceProvider providerC = ServiceProvider.builder().id(3l).name("B").providerField("mechanic").build();

        AvailableService service1 = AvailableService.builder().id(1l).serviceProvider(providerB).build();
        AvailableService service2 = AvailableService.builder().id(2l).serviceProvider(providerA).build();
        AvailableService service3 = AvailableService.builder().id(4l).serviceProvider(providerC).build();

        Term term1 = Term.builder().id(1l).termDate(LocalDate.parse("2021-11-21")).service(service1).build();
        Term term2 = Term.builder().id(2l).termDate(LocalDate.parse("2021-11-22")).service(service2).build();
        Term term3 = Term.builder().id(3l).termDate(LocalDate.parse("2021-11-23")).service(service1).build();
        Term term4 = Term.builder().id(4l).termDate(LocalDate.parse("2021-11-24")).service(service3).build();

        termList = new ArrayList<>();
        termList.add(term1);
        termList.add(term2);
        termList.add(term3);
        termList.add(term4);
    }

    @DisplayName("Test getting all service providers")
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
        verify(providerRepository).findAll();
        verify(providerRepository, never()).findById(anyLong());
        assertNotNull(providerSet);
        assertEquals(2, providerSet.size());
    }

    @DisplayName("Test getting service provider by id")
    @Test
    void findById() {
        //when
        when(providerRepository.findById(anyLong())).thenReturn(Optional.of(testServiceProvider));
        ServiceProvider testProvider = service.findById(2l);

        //then
        verify(providerRepository).findById(anyLong());
        verify(providerRepository, never()).findAll();
        assertNotNull(testProvider);
    }

    @DisplayName("Test getting service provider by id which doesn't exist")
    @Test
    void findByIdNotFound() {
        //when
        when(providerRepository.findById(anyLong())).thenReturn(Optional.empty());
        ServiceProvider testProvider = service.findById(2l);

        //then
        verify(providerRepository).findById(anyLong());
        verify(providerRepository, never()).findAll();
        assertNull(testProvider);
    }

    @DisplayName("Test saving service provider")
    @Test
    void save() {
        //when
        when(providerRepository.save(any())).thenReturn(testServiceProvider);
        ServiceProvider savedProvider = service.save(testServiceProvider);

        //then
        assertNotNull(savedProvider);
        verify(providerRepository).save(any());
    }

    @DisplayName("Test deleting service provider")
    @Test
    void delete() {
        //when
        service.delete(testServiceProvider);

        //then
        verify(providerRepository).delete(any());
    }

    @DisplayName("Test deleting service provider by id")
    @Test
    void deleteById() {
        //when
        service.deleteById(2l);

        //then
        verify(providerRepository).deleteById(anyLong());
    }
}