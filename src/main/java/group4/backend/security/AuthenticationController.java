package group4.backend.security;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(
            summary = "registers a user in database with security ",
            description = "Registers a user with authenticationService",
            responses = {
                    @ApiResponse(responseCode = "200", description = "registered user"),
                    @ApiResponse(responseCode = "400", description = "Invalid registration details")
            }
    )
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
    @Operation(
            summary = "logs in a user ",
            description = "logs in a user based on authenticationRequest body",
            responses = {
                    @ApiResponse(responseCode = "200", description = "extra features found"),
                    @ApiResponse(responseCode = "401", description = "Invalid username or password")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody
                                                               AuthenticationRequest loginRequest){
        return ResponseEntity.ok(authenticationService.login(loginRequest));

    }





}
