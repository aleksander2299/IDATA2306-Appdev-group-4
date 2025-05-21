package group4.backend.security;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A response object that encapsulates the authentication token returned after successful authentication.
 * This class is used in the authentication flow to transfer the JWT token from the server to the client.
 * NOTE: Java documentation was generated with help from AI to ensure it follows Java documentation guidelines.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    /**
     * The JWT (JSON Web Token) authentication token.
     * This token is used for subsequent authenticated requests to the API.
     */
    private String token;

}
