package group4.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="user")
public class User {

    @Id
    @Column(name="username", length=255, nullable=false, unique=true)
    private String username;
    @Column(name="password", length=255, nullable=false, unique=false)
    private String password;
    @Column(name="role", length=255, nullable=false, unique=false)
    private String role;

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns true if the user has a username, password, and a role.
     *
     * @return True if user has valid fields.
     */
    public boolean isValid() {
        return (!this.username.isBlank() &&
            !this.password.isBlank() &&
            !this.role.isBlank());
    }
}