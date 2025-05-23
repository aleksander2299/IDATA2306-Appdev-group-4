package group4.backend.repository;

import group4.backend.entities.Provider;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing Provider entities in the database.
 * <p>
 * Note: This documentation was generated with the assistance of AI.
 */
@Repository
public interface ProviderRepository extends CrudRepository<Provider,Integer> {

    /**
     * Retrieves all Provider entities with the given IDs.
     *
     * @param integers Iterable of Integer IDs to search for
     * @return An Iterable of Provider entities matching the provided IDs
     */
    @Override
    Iterable<Provider> findAllById(Iterable<Integer> integers);

    /**
     * Finds a Provider entity by its ID.
     *
     * @param id The ID of the Provider to find
     * @return An Optional containing the found Provider entity, or empty if not found
     */
    @Override
    Optional<Provider> findById(Integer id);

    /**
     * Retrieves all Provider entities from the database.
     *
     * @return An Iterable of all Provider entities
     */
    @Override
    Iterable<Provider> findAll();

    /**
     * Deletes all Provider entities from the database.
     */
    @Override
    void deleteAll();

    /**
     * Deletes all Provider entities specified in the provided Iterable.
     *
     * @param entities Iterable of Provider entities to delete
     */
    @Override
    void deleteAll(Iterable entities);

    /**
     * Deletes all Provider entities with IDs specified in the provided Iterable.
     *
     * @param iterable Iterable of IDs of entities to delete
     */
    @Override
    void deleteAllById(Iterable iterable);


    /**
     * Finds a Provider entity by its provider name.
     *
     * @param name The name of the provider to search for
     * @return An Optional containing the found Provider entity, or empty if not found
     */
    Optional<Provider> findByProviderName(String name);
}
