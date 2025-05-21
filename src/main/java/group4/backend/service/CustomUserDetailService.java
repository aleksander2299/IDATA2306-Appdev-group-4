package group4.backend.service;

import group4.backend.entities.User;
import group4.backend.repository.UserRepository;
import group4.backend.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.List;

/**
 * A custom implementation of Spring Security's UserDetailsService that handles user authentication.
 * This service retrieves user details from the database and converts them into Spring Security's UserDetails format.
 * NOTE: Java documentation was generated with help from AI to make sure it follows Java documentation guidelines.
 */
public class CustomUserDetailService implements UserDetailsService {



    private final UserRepository userRepository;

    /**
     * Constructs a new CustomUserDetailService with the specified UserRepository.
     *
     * @param userRepository the repository used to access user data from the database
     */
    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * Loads a user's details by their username.
     *
     * @param username the username to search for
     * @return UserDetails object containing the user's authentication and authorization information
     * @throws UsernameNotFoundException if no user is found with the given username
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new CustomUserDetails(user);
    }



}
