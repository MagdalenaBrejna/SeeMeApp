package mb.seeme.services;

import mb.seeme.model.users.Client;
import mb.seeme.repositories.ClientRepository;
import mb.seeme.services.users.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    ClientRepository clientRepository;

    @InjectMocks
    ClientServiceImpl service;

    Client client;

    @BeforeEach
    void setUp() {
        client = Client.builder().id(2l).build();
    }

    @DisplayName("Test getting all clients")
    @Test
    void findAll() {
        //given
        Set<Client> testClientSet = new HashSet<>();
        testClientSet.add(Client.builder().id(1l).build());
        testClientSet.add(Client.builder().id(2l).build());

        //when
        when(clientRepository.findAll()).thenReturn(testClientSet);
        Set<Client> clientSet = service.findAll();

        //then
        verify(clientRepository).findAll();
        verify(clientRepository, never()).findById(anyLong());
        assertNotNull(clientSet);
        assertEquals(2, clientSet.size());
    }

    @DisplayName("Test getting client by id")
    @Test
    void findById() {
        //when
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));
        Client testClient = service.findById(2l);

        //then
        verify(clientRepository).findById(anyLong());
        verify(clientRepository, never()).findAll();
        assertNotNull(testClient);
    }

    @DisplayName("Test getting client by id which doesn't exist")
    @Test
    void findByIdNotFound() {
        //when
        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());
        Client testClient = service.findById(2l);

        //then
        verify(clientRepository).findById(anyLong());
        verify(clientRepository, never()).findAll();
        assertNull(testClient);
    }

    @DisplayName("Test saving client")
    @Test
    void save() {
        //when
        when(clientRepository.save(any())).thenReturn(client);
        Client savedClient = service.save(client);

        //then
        assertNotNull(savedClient);
        verify(clientRepository).save(any());
    }

    @DisplayName("Test deleting client")
    @Test
    void delete() {
        //when
        service.delete(client);

        //then
        verify(clientRepository).delete(any());
    }

    @DisplayName("Test deleting client by id")
    @Test
    void deleteById() {
        //when
        service.deleteById(2l);

        //then
        verify(clientRepository).deleteById(anyLong());
    }
}