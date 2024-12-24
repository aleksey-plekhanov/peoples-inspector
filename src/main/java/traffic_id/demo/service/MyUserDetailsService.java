package traffic_id.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import traffic_id.demo.model.UserData;
import traffic_id.demo.repository.UserDataRepository;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDataRepository userDataRepository;
    
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserData user = userDataRepository.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("Неверный логин или пароль");
        }
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        return new org.springframework.security.core.userdetails.User(
          user.getLogin(), user.getPassword(), enabled, accountNonExpired,
          credentialsNonExpired, accountNonLocked, getAuthorities(user.getRoles()));
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
