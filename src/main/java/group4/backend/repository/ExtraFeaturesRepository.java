package group4.backend.repository;

import group4.backend.entities.ExtraFeatures;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Repository interface for managing ExtraFeatures entities in the database.
 * <p>
 * Note: This documentation was generated with the assistance of AI.
 */
public interface ExtraFeaturesRepository extends CrudRepository<ExtraFeatures, String> {

    /**
     * Retrieves all ExtraFeatures entities from the database.
     *
     * @return An Iterable of all ExtraFeatures entities
     */
    @Override
    Iterable<ExtraFeatures> findAll();

    /**
     * Retrieves all ExtraFeatures entities with the given IDs.
     *
     * @param strings Iterable of String IDs to search for
     * @return An Iterable of ExtraFeatures entities matching the provided IDs
     */
    @Override
    Iterable<ExtraFeatures> findAllById(Iterable<String> strings);

    /**
     * Finds an ExtraFeatures entity by its ID.
     *
     * @param string The ID of the ExtraFeatures to find
     * @return An Optional containing the found ExtraFeatures entity, or empty if not found
     */
    @Override
    Optional<ExtraFeatures> findById(String string);

    /**
     * Deletes the specified ExtraFeatures entity from the database.
     *
     * @param entity The ExtraFeatures entity to delete
     */
    @Override
    void delete(ExtraFeatures entity);

    /**
     * Deletes all ExtraFeatures entities from the database.
     */
    @Override
    void deleteAll();

    /**
     * Deletes all ExtraFeatures entities specified in the provided Iterable.
     *
     * @param entities Iterable of ExtraFeatures entities to delete
     */
    @Override
    void deleteAll(Iterable<? extends ExtraFeatures> entities);

    /**
     * Deletes all ExtraFeatures entities with IDs specified in the provided Iterable.
     *
     * @param strings Iterable of String IDs of entities to delete
     */
    @Override
    void deleteAllById(Iterable<? extends String> strings);

    /**
     * Deletes an ExtraFeatures entity with the specified ID.
     *
     * @param string The ID of the ExtraFeatures entity to delete
     */
    @Override
    void deleteById(String string);

}
