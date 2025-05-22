package group4.backend.config;


import group4.backend.repository.UserRepository;
import group4.backend.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * This class serves as the configuration for setting up authentication and security-related beans.
 * It integrates with Spring Security to provide the necessary components such as
 * authentication providers, password encoders, and user detail services.
 *
 * Annotations:
 * - {@code @Configuration}: Marks this class as a configuration class.
 * - {@code @RequiredArgsConstructor}: Generates a constructor for the class with required fields.
 * NOTE: Documentation was made with help from AI
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {


    @Autowired
    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return  username ->  new CustomUserDetails(
                userRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found")));

        };


    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}




