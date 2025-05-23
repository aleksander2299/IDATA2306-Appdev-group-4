package group4.backend.repository;

import group4.backend.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing User entities in the database.
 * This interface provides methods for querying and managing user records.
 * <p>
 * Note: This documentation was generated with the assistance of AI.
 */
@Repository
public interface UserRepository extends CrudRepository<User, String> {


    /**
     * Finds a User entity by their username.
     *
     * @param userName The username to search for
     * @return An Optional containing the found User entity, or empty if not found
     */
    Optional<User> findByUsername(String userName);
}
