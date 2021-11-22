package mb.seeme.services;

import mb.seeme.model.services.AvailableService;
import mb.seeme.repositories.AvailableServiceRepository;
import mb.seeme.services.services.AvailableServiceServiceImpl;
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
class AvailableServiceServiceImplTest {

    @Mock
    AvailableServiceRepository serviceRepository;

    @InjectMocks
    AvailableServiceServiceImpl service;

    AvailableService availableService;

    @BeforeEach
    void setUp() {
        availableService = AvailableService.builder().id(1l).build();
    }

    @Test
    void findAll() {
        //given
        Set<AvailableService> testServiceSet = new HashSet<>();
        testServiceSet.add(AvailableService.builder().id(1l).build());
        testServiceSet.add(AvailableService.builder().id(2l).build());
        //when
        when(serviceRepository.findAll()).thenReturn(testServiceSet);
        Set<AvailableService> serviceSet = service.findAll();
        //then
        assertNotNull(serviceSet);
        assertEquals(2, serviceSet.size());
    }

    @Test
    void findById() {
        //when
        when(serviceRepository.findById(anyLong())).thenReturn(Optional.of(availableService));
        AvailableService testService = service.findById(1l);
        //then
        assertNotNull(testService);
    }

    @Test
    void findByIdNotFound() {
        //when
        when(serviceRepository.findById(anyLong())).thenReturn(Optional.empty());
        AvailableService testService = service.findById(1l);
        //then
        assertNull(testService);
    }

    @Test
    void save() {
        //given
        AvailableService serviceToSave = AvailableService.builder().id(1l).build();
        //when
        when(serviceRepository.save(any())).thenReturn(serviceToSave);
        AvailableService savedService = service.save(serviceToSave);
        //then
        assertNotNull(savedService);
        verify(serviceRepository).save(any());
    }

    @Test
    void delete() {
        //when
        service.delete(availableService);
        //then
        verify(serviceRepository).delete(any());
    }

    @Test
    void deleteById() {
        //when
        service.deleteById(2l);
        //then
        verify(serviceRepository).deleteById(anyLong());
    }
}