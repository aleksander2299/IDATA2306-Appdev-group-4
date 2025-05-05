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

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final jwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        var user = group4.backend.entities.User.builder().username(registerRequest.getUserName())
                        .password(passwordEncoder.encode(registerRequest.getPassWord()))
                                .role(registerRequest.getRole()).build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(new CustomUserDetails(user));
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

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
