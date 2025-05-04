package group4.backend.dto;


/**
 * DTO class for user omitting password for security.
 */
public class UserDTO {


    private String username;

    private String role;


    public UserDTO(String username, String role){
        this.username = username;
        this.role = role;
    }

}
