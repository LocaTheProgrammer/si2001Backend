package it.si2001.configuration;

import it.si2001.converter.EntityDTOConverter;
import it.si2001.dao.CarRepository;
import it.si2001.dao.ReservationRepository;
import it.si2001.dao.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)


public class AppConfiguration extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {




    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers().permitAll()//end point senza controllo di nessun tipo, quindi accessibile ad ogni client
                .anyRequest().permitAll()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/rest/car/**").allowedMethods("GET", "POST", "PUT", "DELETE");
        registry.addMapping("/rest/user/**").allowedMethods("GET", "POST", "PUT", "DELETE");
        registry.addMapping("/rest/reservation/**").allowedMethods("GET", "POST", "PUT", "DELETE");
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }


    @Bean
    public EntityDTOConverter entityDTOConverter(UserRepository userRepository, CarRepository carRepository, ReservationRepository reservationRepository){
        return new EntityDTOConverter(userRepository, carRepository, reservationRepository);
    }
}