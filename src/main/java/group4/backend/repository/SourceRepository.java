package group4.backend.repository;

import group4.backend.entities.Source;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Repository interface for managing Source entities in the database.
 * <p>
 * Note: This documentation was generated with the assistance of AI.
 */
public interface SourceRepository extends CrudRepository<Source, Integer> {

    /**
     * Retrieves all Source entities from the database.
     *
     * @return An Iterable of all Source entities
     */
    @Override
    Iterable<Source> findAll();

    /**
     * Retrieves all Source entities with the given IDs.
     *
     * @param integers Iterable of Integer IDs to search for
     * @return An Iterable of Source entities matching the provided IDs
     */
    @Override
    Iterable<Source> findAllById(Iterable<Integer> integers);

    /**
     * Finds a Source entity by its ID.
     *
     * @param integer The ID of the Source to find
     * @return An Optional containing the found Source entity, or empty if not found
     */
    @Override
    Optional<Source> findById(Integer integer);

    /**
     * Deletes the specified Source entity from the database.
     *
     * @param entity The Source entity to delete
     */
    @Override
    void delete(Source entity);

    /**
     * Deletes all Source entities from the database.
     */
    @Override
    void deleteAll();

    /**
     * Deletes all Source entities specified in the provided Iterable.
     *
     * @param entities Iterable of Source entities to delete
     */
    @Override
    void deleteAll(Iterable<? extends Source> entities);

    /**
     * Deletes all Source entities with IDs specified in the provided Iterable.
     *
     * @param integers Iterable of Integer IDs of entities to delete
     */
    @Override
    void deleteAllById(Iterable<? extends Integer> integers);

    /**
     * Deletes a Source entity with the specified ID.
     *
     * @param integer The ID of the Source entity to delete
     */
    @Override
    void deleteById(Integer integer);
}
