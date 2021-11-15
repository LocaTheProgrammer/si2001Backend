package it.si2001.configuration;

import it.si2001.converter.EntityDTOConverter;
import it.si2001.dao.CarRepository;
import it.si2001.dao.ReservationRepository;
import it.si2001.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class AppConfiguration extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private UserDetailsService jwtUserDetailsService;

    private JwtRequestFilter jwtRequestFilter;


    public AppConfiguration(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, UserDetailsService jwtUserDetailsService, JwtRequestFilter jwtRequestFilter){
        this.jwtUserDetailsService=jwtUserDetailsService;
        this.jwtAuthenticationEntryPoint=jwtAuthenticationEntryPoint;
        this.jwtRequestFilter=jwtRequestFilter;
    }




    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                // dont authenticate this particular request
                .authorizeRequests()
                .antMatchers("/rentalCar/rest/car/deleteCarById/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/authenticate").permitAll().antMatchers("/rest/user/logIn").permitAll().
                // all other requests need to be authenticated
                        anyRequest().authenticated().and().
                // make sure we use stateless session; session won't be used to
                // store user's state.
                        exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            // Add a filter to validate the tokens with every request
            http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/rest/car/**").allowedMethods("GET", "POST", "PUT", "DELETE");
        registry.addMapping("/rest/**").allowedMethods("GET", "POST", "PUT", "DELETE");
        registry.addMapping("/rest/user/**").allowedMethods("GET", "POST", "PUT", "DELETE");
        registry.addMapping("/rest/reservation/**").allowedMethods("GET", "POST", "PUT", "DELETE");
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }



}