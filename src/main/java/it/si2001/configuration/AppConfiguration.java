package it.si2001.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class AppConfiguration extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {





    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests().antMatchers("/login").permitAll()
		.antMatchers(ADMIN_CAR_MATCHER).access("hasRole('ADMIN')")
		.and().formLogin().loginProcessingUrl("/login").usernameParameter("userId").passwordParameter("password");
//		.and().exceptionHandling().accessDeniedPage("/login/form?forbidden")
//		.and().logout().logoutUrl("/login/form?logout");

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
    
    @Bean
    public UserDetailsService userDetailService() {
    	
    	UserBuilder users =User.builder();
    	
    	InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    	
    	manager.createUser(users.username("pippo").password(new BCryptPasswordEncoder().encode("qwerty")).roles("USER").build());
    	manager.createUser(users.username("pluto").password(new BCryptPasswordEncoder().encode("qwerty")).roles("ADMIN").build());
    	
    
    	return manager;
    }
    
    @Override
    public void configure(final AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }
    
    private static final String[] ADMIN_CAR_MATCHER= {
    	"/rest/car/delete/**"	
    };


}