package group4.backend.security;


import group4.backend.config.jwtService;
import group4.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class handling user authentication operations including registration and login.
 * This class manages JWT token generation and user authentication processes.
 * NOTE: Java documentation was generated with help from AI to ensure it follows Java documentation guidelines.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final jwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Registers a new user in the system with the provided credentials.
     *
     * @param registerRequest Contains the registration details including username, password, and role
     * @return AuthenticationResponse containing the JWT token for the newly registered user
     */
    public AuthenticationResponse register(RegisterRequest registerRequest) {
        var user = group4.backend.entities.User.builder().username(registerRequest.getUsername())
                        .password(passwordEncoder.encode(registerRequest.getPassword()))
                                .role(registerRequest.getRole()).build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(new CustomUserDetails(user));
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    /**
     * Authenticates a user with their credentials and generates a JWT token.
     *
     * @param loginRequest Contains the login credentials (username and password)
     * @return AuthenticationResponse containing the JWT token for the authenticated user
     * @throws RuntimeException if authentication fails or user is not found
     */
    public AuthenticationResponse login(AuthenticationRequest loginRequest) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            )
    );
    var user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(new CustomUserDetails(user));
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
