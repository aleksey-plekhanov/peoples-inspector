package traffic_id.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import traffic_id.demo.model.UserData;
import traffic_id.demo.repository.UserDataRepository;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserData userData = userDataRepository.findByLogin(name);

        if (userData == null || !bCryptPasswordEncoder.matches(password, userData.getPassword())) { 
            throw new BadCredentialsException("Неверный логин или пароль"); 
        } 

        return new UsernamePasswordAuthenticationToken(name, password, getAuthorities(userData.getRoles()));
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private static List<GrantedAuthority> getAuthorities (String roles) {
        String[] rolesMas = roles.split(",");
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : rolesMas) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}
