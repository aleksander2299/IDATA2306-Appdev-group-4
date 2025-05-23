package group4.backend.repository;

import group4.backend.entities.Favourite;
import group4.backend.entities.Room;
import group4.backend.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing Favourite entities in the database.
 * This interface provides methods for querying and managing favorite records.
 * <p>
 * Note: This documentation was generated with the assistance of AI.
 */
@Repository
public interface FavouriteRepository extends CrudRepository<Favourite, Integer> {


    /**
     * Retrieves all Favourite entities from the database.
     *
     * @return An Iterable of all Favourite entities
     */
    @Override
    Iterable<Favourite> findAll();

    /**
     * Finds a Favourite entity by its ID.
     *
     * @param integer The ID of the Favourite to find
     * @return An Optional containing the found Favourite entity, or empty if not found
     */
    @Override
    Optional<Favourite> findById(Integer integer);

    /**
     * Deletes the specified Favourite entity from the database.
     *
     * @param entity The Favourite entity to delete
     */
    @Override
    void delete(Favourite entity);

    /**
     * Deletes a Favourite entity with the specified ID.
     *
     * @param integer The ID of the Favourite entity to delete
     */
    @Override
    void deleteById(Integer integer);

    /**
     * Deletes all Favourite entities from the database.
     */
    @Override
    void deleteAll();

    /**
     * Retrieves all Favourite entities with the given IDs.
     *
     * @param integers Iterable of Integer IDs to search for
     * @return An Iterable of Favourite entities matching the provided IDs
     */
    @Override
    Iterable<Favourite> findAllById(Iterable<Integer> integers);

    /**
     * Deletes all Favourite entities with IDs specified in the provided Iterable.
     *
     * @param integers Iterable of Integer IDs of entities to delete
     */
    @Override
    void deleteAllById(Iterable<? extends Integer> integers);

    /**
     * Find all favourites by user
     * @param username the username of the user
     * @return List of favourites
     */
    List<Favourite> findAllByUser(User username);

    /**
     * Finds all favourites for a specific user and room combination.
     *
     * @param user The User entity to search favourites for
     * @param room The Room entity to search favourites for
     * @return A List of Favourite entities matching both the user and room
     */
    List<Favourite> findAllByUserAndRoom(User user, Room room);
}