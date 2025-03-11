package group4.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="user")
public class User {

    @Column(name="username", length=255, nullable=false, unique=true)
    private String username;
    @Column(name="password", length=255, nullable=false, unique=false)
    private String password;
    @Column(name="role", length=255, nullable=false, unique=false)
    private String role;
}