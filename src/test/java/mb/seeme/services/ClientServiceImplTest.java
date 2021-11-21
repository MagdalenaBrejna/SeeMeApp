package mb.seeme.services;

import mb.seeme.model.users.Client;
import mb.seeme.repositories.ClientRepository;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        assertNotNull(clientSet);
        assertEquals(2, clientSet.size());
    }

    @Test
    void findById() {
        //when
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));
        Client testClient = service.findById(2l);
        //then
        assertNotNull(testClient);
    }

    @Test
    void findByIdNotFound() {
        //when
        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());
        Client testClient = service.findById(2l);
        //then
        assertNull(testClient);
    }

    @Test
    void save() {
        //given
        Client clientToSave = Client.builder().id(1l).build();
        //when
        when(clientRepository.save(any())).thenReturn(clientToSave);
        Client savedClient = service.save(clientToSave);
        //then
        assertNotNull(savedClient);
        verify(clientRepository).save(any());
    }

    @Test
    void delete() {
        //when
        service.delete(client);
        //then
        verify(clientRepository).delete(any());
    }

    @Test
    void deleteById() {
        //when
        service.deleteById(2l);
        //then
        verify(clientRepository).deleteById(anyLong());
    }
}