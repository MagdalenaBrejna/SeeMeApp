package mb.seeme.model.users;

import mb.seeme.model.terms.Term;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    Client client;

    @BeforeEach
    void setUp() {
        client = new Client();
    }

    @DisplayName("Test setting client id")
    @Test
    void getId() throws Exception {
        //given
        Long idValue = 2L;
        //when
        client.setId(2L);
        //then
        assertEquals(idValue, client.getId());
    }

    @DisplayName("Test setting client name")
    @Test
    void getName() throws Exception {
        //given
        String name = "John";
        //when
        client.setName(name);
        //then
        assertEquals(client.getName(), name);
    }

    @DisplayName("Test setting client telephone number")
    @Test
    void getTelephone() throws Exception {
        //given
        String telNumber = "111222333";
        //when
        client.setTelephone(telNumber);
        //then
        assertEquals(client.getTelephone(), telNumber);
    }

    @DisplayName("Test setting client email")
    @Test
    void getEmail() throws Exception {
        //given
        String email = "123@gmail.com";
        //when
        client.setEmail(email);
        //then
        assertEquals(client.getEmail(), email);
    }

    @DisplayName("Test setting client password")
    @Test
    void getPassword() throws Exception {
        //given
        String password = "password";
        //when
        client.setPassword(password);
        //then
        assertEquals(client.getPassword(), password);
    }

    @DisplayName("Test setting client role")
    @Test
    void getUserRole() throws Exception {
        //given
        UserRole role = UserRole.USER;
        //when
        client.setUserRole(role);
        //then
        assertEquals(client.getUserRole(), role);
    }

    @DisplayName("Test setting terms set")
    @Test
    void getTerms() throws Exception {
        //given
        Set<Term> terms = new HashSet<Term>();
        //when + then
        client.setTerms(terms);
        assertEquals(client.getTerms().size(), 0);

        terms.add(new Term());
        assertEquals(client.getTerms().size(), 1);
    }
}