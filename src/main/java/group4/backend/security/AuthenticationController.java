package group4.backend.security;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authenticate")
@RequiredArgsConstructor
public class AuthenticationController {


    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody
                                                           RegisterRequest registerRequest){
        return ResponseEntity.ok(authenticationService.register(registerRequest));

    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody
                                                               AuthenticationRequest registerRequest){
        return ResponseEntity.ok(authenticationService.login(registerRequest));

    }





}
