package group4.backend.security;

import group4.backend.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * A custom implementation of Spring Security's UserDetails interface.
 * This class serves as a bridge between the application's User entity and Spring Security's user representation.
 * It provides core user information required by Spring Security framework.
 * <p>
 * NOTE: This documentation was generated with assistance from AI to ensure compliance with Java documentation standards.
 */
public class CustomUserDetails implements UserDetails {


    private final String username;

    private final String password;

    private final String role;
    private final User user;

    /**
     * Constructs a CustomUserDetails instance from a User entity.
     * Maps the user's properties to Spring Security's required fields and prepends "ROLE_" to the user's role.
     *
     * @param user The User entity to create UserDetails from
     */
    public CustomUserDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = "ROLE_" + user.getRole();
        this.user = user;
    }

    /**
     * Returns the authorities granted to the user.
     * Creates a single SimpleGrantedAuthority from the user's role.
     *
     * @return A collection containing the user's granted authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    /**
     * Returns the user's password.
     *
     * @return The password
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     * Returns the user's username.
     *
     * @return The username
     */
    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * Indicates whether the user's account has expired.
     * In this implementation, accounts never expire.
     *
     * @return true always, indicating the account is not expired
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user's account is locked.
     * In this implementation, accounts are never locked.
     *
     * @return true always, indicating the account is not locked
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials have expired.
     * In this implementation, credentials never expire.
     *
     * @return true always, indicating the credentials are not expired
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled.
     * In this implementation, users are always enabled.
     *
     * @return true always, indicating the user is enabled
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
