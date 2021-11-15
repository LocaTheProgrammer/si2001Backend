package it.si2001.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("user".equals(username)) {
            return new User("user", "$2a$12$Vvqh0KO7K5ZqFydpMa0w0ec8nCS7cu11B6/uQIGw/WJrI2fNMkVKm",//password is "user"
                    new ArrayList<>());
        }
        if ("admin".equals(username)) {
            return new User("admin", "$2a$12$3g4zJNiReRDnmYJiOQ58QeCAeee1YdFQkBZWgHP614p0v1lKA.PIq",//password is "admin"
                    new ArrayList<>());
        }
        if ("sarah".equals(username)) {
            return new User("admin", "$2a$12$z9XRjFo5TbEyS9IWOInehOECIh3UFtJnM0VMsyiLACd6GQ4HPm6m6",//password is "password"
                    new ArrayList<>());
        }
        else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}