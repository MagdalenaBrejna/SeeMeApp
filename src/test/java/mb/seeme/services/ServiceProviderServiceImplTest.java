package mb.seeme.services;

import mb.seeme.model.users.ServiceProvider;
import mb.seeme.repositories.ServiceProviderRepository;
import mb.seeme.services.users.ServiceProviderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceProviderServiceImplTest {

    @Mock
    ServiceProviderRepository providerRepository;

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
        Set<ServiceProvider> testServiceSet = new HashSet<>();
        testServiceSet.add(ServiceProvider.builder().id(1l).build());
        testServiceSet.add(ServiceProvider.builder().id(2l).build());
        //when
        when(providerRepository.findAll()).thenReturn(testServiceSet);
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
}