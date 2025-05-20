package group4.backend.security;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller handling authentication-related endpoints for user registration and login.
 * Provides endpoints for user registration and authentication operations.
 * NOTE: Java documentation was generated with help from AI to make sure it follows Java documentation guidelines.
 */
@RestController
@RequestMapping("/api/authenticate")
@RequiredArgsConstructor
public class AuthenticationController {


    private final AuthenticationService authenticationService;

    /**
     * Handles user registration requests.
     *
     * @param registerRequest The registration details containing user information
     * @return A {@link ResponseEntity} containing an {@link AuthenticationResponse} with the registration result
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody
                                                           RegisterRequest registerRequest){
        return ResponseEntity.ok(authenticationService.register(registerRequest));

    }


    /**
     * Authenticates user login requests.
     *
     * @param loginRequest The login credentials containing username and password
     * @return A {@link ResponseEntity} containing an {@link AuthenticationResponse} with the authentication result
     */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody
                                                               AuthenticationRequest loginRequest){
        return ResponseEntity.ok(authenticationService.login(loginRequest));

    }





}
