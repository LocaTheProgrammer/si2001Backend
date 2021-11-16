package it.si2001.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import it.si2001.dao.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<it.si2001.entity.User> uOPT = this.userRepository.findByEmail(username);
        UserDetails user = null;
        if (uOPT.isPresent()) {
            it.si2001.entity.User u = uOPT.get();
            if (u.getEmail().equals(username)) {
                if(u.getRole().equals("user")){
                    user = User.withUsername(u.getEmail()).password(u.getPassword()).authorities("USER").build();
                }
                if(u.getRole().equals("admin")){
                    user = User.withUsername(u.getEmail()).password(u.getPassword()).authorities("ADMIN").build();
                }
                return user;
            }else{
                throw new UsernameNotFoundException("User not found with username: " + username);
            }
        }
        else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}