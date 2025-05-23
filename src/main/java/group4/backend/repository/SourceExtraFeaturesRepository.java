package group4.backend.repository;

import group4.backend.entities.*;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing SourceExtraFeatures entities in the database.
 * This interface provides methods for querying and managing source extra features records.
 * <p>
 * Note: This documentation was generated with the assistance of AI.
 */
public interface SourceExtraFeaturesRepository extends CrudRepository<SourceExtraFeatures, SourceExtraFeaturesId> {

    /**
     * Retrieves all SourceExtraFeatures entities from the database.
     *
     * @return An Iterable of all SourceExtraFeatures entities
     */
    @Override
    Iterable<SourceExtraFeatures> findAll();

    /**
     * Retrieves all SourceExtraFeatures entities with the given IDs.
     *
     * @param sourceExtraFeaturesIds Iterable of SourceExtraFeaturesId to search for
     * @return An Iterable of SourceExtraFeatures entities matching the provided IDs
     */
    @Override
    Iterable<SourceExtraFeatures> findAllById(Iterable<SourceExtraFeaturesId> sourceExtraFeaturesIds);

    /**
     * Finds a SourceExtraFeatures entity by its ID.
     *
     * @param sourceExtraFeaturesId The ID of the SourceExtraFeatures to find
     * @return An Optional containing the found SourceExtraFeatures entity, or empty if not found
     */
    @Override
    Optional<SourceExtraFeatures> findById(SourceExtraFeaturesId sourceExtraFeaturesId);

    /**
     * Deletes the specified SourceExtraFeatures entity from the database.
     *
     * @param entity The SourceExtraFeatures entity to delete
     */
    @Override
    void delete(SourceExtraFeatures entity);

    /**
     * Deletes all SourceExtraFeatures entities with IDs specified in the provided Iterable.
     *
     * @param sourceExtraFeaturesIds Iterable of SourceExtraFeaturesId of entities to delete
     */
    @Override
    void deleteAllById(Iterable<? extends SourceExtraFeaturesId> sourceExtraFeaturesIds);

    /**
     * Deletes a SourceExtraFeatures entity with the specified ID.
     *
     * @param sourceExtraFeaturesId The ID of the SourceExtraFeatures entity to delete
     */
    @Override
    void deleteById(SourceExtraFeaturesId sourceExtraFeaturesId);

    /**
     * Deletes all SourceExtraFeatures entities specified in the provided Iterable.
     *
     * @param entities Iterable of SourceExtraFeatures entities to delete
     */
    @Override
    void deleteAll(Iterable<? extends SourceExtraFeatures> entities);

    /**
     * Deletes all SourceExtraFeatures entities from the database.
     */
    @Override
    void deleteAll();

    /**
     * Finds a SourceExtraFeatures entity by its source ID and feature.
     *
     * @param sourceId The Source entity to search for
     * @param feature  The ExtraFeatures entity to search for
     * @return An Optional containing the found SourceExtraFeatures entity, or empty if not found
     */
    Optional<SourceExtraFeatures> findBySourceIdAndFeature(Source sourceId, ExtraFeatures feature);
}
