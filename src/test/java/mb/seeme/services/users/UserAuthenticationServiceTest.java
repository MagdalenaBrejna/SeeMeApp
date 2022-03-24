package mb.seeme.services.users;

import mb.seeme.model.users.Person;
import mb.seeme.model.users.ServiceProvider;
import mb.seeme.repositories.ServiceProviderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class UserAuthenticationServiceTest {

    @Autowired
    MockMvc mockMvc;

    //@Autowired
    //private WebApplicationContext context;

    @InjectMocks
    UserAuthenticationService userAuthenticationService;

    @Mock
    ServiceProviderRepository providerRepository;

    ServiceProvider provider;


    @BeforeEach
    void setUp() {
        provider = ServiceProvider.builder().id(1l).build();
        //this.mockMvc = MockMvcBuilders.standaloneSetup().build();
        //mockMvc = MockMvcBuilders
              //  .webAppContextSetup(context)
            //    .build();
    }

    @Test
    void loadUserByUsername() {
        //when
        when(providerRepository.selectProviderByUsername(anyString())).thenReturn(provider);
        Person loadedPerson = userAuthenticationService.loadUserByUsername("providerA");
        //then
        assertNotNull(loadedPerson);
        assertEquals(loadedPerson.getClass(), ServiceProvider.class);
    }

    @Test
    public void existentUserCanGetTokenAndAuthentication() throws Exception {
       // String username = "AC";
        //String password = "passAC";

        //String body = "{\"username\":" + "\"" + username + "\"" + "," + "\"password\":" + "\"" + password +  "\"" + "}";
        //System.out.print(body);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/login").with(csrf()).content("{\"username\": \"AC\", \"password\": \"passAC\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(302))
                .andReturn();

       // MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/login")
       //         .content(body))
          //      .andExpect(status().isOk()).andReturn();

        //MvcResult result =

         //      mockMvc
          //              .perform(post("/login")
          //              .with(user("admin").password("pass").roles("USER","ADMIN")))

        String token = result.getResponse().getHeader("Authorization");
        //String response = result.getResponse().getContentAsString();
        System.out.print("TOKEN " + token + "\n");
        //response = response.replace("{\"access_token\": \"", "");
       // String token = response.replace("\"}", "");
//
        //mockMvc.perform(MockMvcRequestBuilders.get("/test")
        //                .header("Authorization", "Bearer " + token))
         //       .andExpect(status().isOk());


    }

    @Test
    void getAuthenticatedProviderId() {
    }

    @Test
    void getAuthenticatedClientId() {
    }
}