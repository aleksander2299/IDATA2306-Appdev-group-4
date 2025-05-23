package group4.backend.security;


import group4.backend.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A data transfer object (DTO) class representing a user registration request.
 * This class encapsulates the necessary information required for registering a new user in the system.
 * <p>
 * Note: This documentation was generated with the assistance of AI.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    /**
     * The desired username for the new user account.
     * This field must be unique within the system.
     */
    private String username;

    /**
     * The password for the new user account.
     * This should be provided in plain text and will be encrypted during processing.
     */
    private String password;

    /**
     * The role to be assigned to the new user account.
     * This determines the user's permissions and access levels within the system.
     */
    private Role role;

}
