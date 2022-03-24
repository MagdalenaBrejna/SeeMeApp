package mb.seeme.security;

import mb.seeme.jwt.JwtTokenVerifier;
import mb.seeme.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import mb.seeme.services.users.UserAuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.crypto.SecretKey;

//@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserAuthenticationService userAuthenticationService;
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;

    public ApplicationSecurityConfig(UserAuthenticationService userAuthenticationService, PasswordEncoder passwordEncoder, SecretKey secretKey, JwtConfig jwtConfig) {
        this.passwordEncoder = passwordEncoder;
        this.userAuthenticationService = userAuthenticationService;
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and().csrf().disable()
                    .addFilterBefore(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey), UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(new JwtTokenVerifier(secretKey, jwtConfig), BasicAuthenticationFilter.class)
                //.addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
                    //.addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
                    //.addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
                    //.addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                    //.addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
                    .authorizeRequests()
                    .antMatchers("/client/**").hasRole("CLIENT")
                    .antMatchers("/providers/**").hasRole("PROVIDER")
                    .antMatchers("/fragments/**").hasAnyRole("CLIENT","PROVIDER")
                    .antMatchers("/home").permitAll()
                    .and().formLogin().loginPage("/login").permitAll().successHandler(appAuthenticationSuccessHandler())
                    .and().httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler appAuthenticationSuccessHandler(){
        return new AppAuthenticationSuccessHandler();
    }
/*
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userAuthenticationService).passwordEncoder(passwordEncoder());
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "webjars/**", "/static/**", "/css/**", "/js/**", "/images/**","/vendor/**","/fonts/**");
    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userAuthenticationService);
        return provider;
    }

 */
}
