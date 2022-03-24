package mb.seeme.security;

import mb.seeme.model.users.Person;
import mb.seeme.services.users.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ApplicationAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserAuthenticationService customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) {
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        Person customer = customerRepository.loadUserByUsername(username);

            if (passwordEncoder.matches(pwd, customer.getPassword())) {
                return new UsernamePasswordAuthenticationToken(username, pwd, customer.getAuthorities());
            } else {
                throw new BadCredentialsException("Invalid password!");
            }

    }
/*
    private List<SimpleGrantedAuthority> getGrantedAuthorities(Set<SimpleGrantedAuthority> authorities) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (SimpleGrantedAuthority authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
        }
        return grantedAuthorities;
    }

 */

    @Override
    public boolean supports(Class<?> authenticationType) {
        return authenticationType.equals(UsernamePasswordAuthenticationToken.class);
    }
}
