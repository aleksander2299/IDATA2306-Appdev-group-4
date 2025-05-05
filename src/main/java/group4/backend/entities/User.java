package group4.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name="user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @Column(name="username", length=255, nullable=false, unique=true)
    private String username;
    @Column(name="password", length=255, nullable=false, unique=false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name="role", length=255, nullable=false, unique=false)
    private Role role;

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
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
            this.role != null);
    }
}