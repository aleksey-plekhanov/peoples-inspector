package traffic_id.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import traffic_id.demo.service.CustomAuthenticationProvider;
import traffic_id.demo.service.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig{

    @Autowired
    public MyUserDetailsService userDetailsService;
    
    @Autowired
    private CustomAuthenticationProvider authProvider;

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { 
        auth.authenticationProvider(authProvider);
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
    } 

    @Bean
    public RememberMeAuthenticationProvider rememberMeAuthenticationProvider() { 
	    return new RememberMeAuthenticationProvider("traffic"); 
    }
    
    @Bean
    public TokenBasedRememberMeServices rememberMeServices() { 
        TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices("traffic", userDetailsService); 
        rememberMeServices.setTokenValiditySeconds(604800); // one week 
        return rememberMeServices; 
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf((csrf) -> csrf
                .disable())
            .authorizeHttpRequests((authz) -> authz
            .requestMatchers( "/registration/**", "/error", "/css/**", "/images/**", "/scss/**", "/vendor/**", "/js/**", "/img/**").permitAll()
            .requestMatchers("/", "/hello", "/user").hasAnyRole("USER", "MODERATOR", "ADMIN")
            .requestMatchers("/user/**", "/application/**").hasAnyRole("MODERATOR", "ADMIN")
            .requestMatchers("/moderator/**").hasAnyRole("ADMIN")
            .anyRequest().authenticated()
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/", true)
                .loginProcessingUrl("/login")
            )
            .httpBasic(Customizer.withDefaults())
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .permitAll())
            .rememberMe((rememberMeConfigurer) -> rememberMeConfigurer
                .key("traffic") 
                .rememberMeServices(rememberMeServices()) 
            );

        return http.build();
    }
}
