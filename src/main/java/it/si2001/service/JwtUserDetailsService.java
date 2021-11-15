package it.si2001.service;

import java.util.ArrayList;
import java.util.Optional;

import it.si2001.dao.UserRepository;
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
        Optional<it.si2001.entity.User> u = this.userRepository.findByEmail(username);

        if (u.isPresent()) {
            if (u.get().getEmail().equals(username)) {
                return new User(u.get().getEmail(), u.get().getPassword(),
                        new ArrayList<>());
            }else{
                throw new UsernameNotFoundException("User not found with username: " + username);
            }
        }

        else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}