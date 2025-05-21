package group4.backend.security;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A data transfer object (DTO) class used for authentication requests.
 * This class encapsulates the credentials required for user authentication.
 * NOTE: Java documentation was generated with help from AI to ensure it follows Java documentation guidelines.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    /**
     * The username credential used for authentication.
     * This field stores the unique identifier of the user attempting to authenticate.
     */
    private String username;

    /**
     * The password credential used for authentication.
     * This field stores the secret password associated with the username.
     */
    private String password;
}
