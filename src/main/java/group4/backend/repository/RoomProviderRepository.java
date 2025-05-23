package group4.backend.repository;

import group4.backend.entities.Provider;
import group4.backend.entities.Room;
import group4.backend.entities.RoomProvider;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing RoomProvider entities in the database.
 * <p>
 * Note: This documentation was generated with the assistance of AI.
 */
@Repository
public interface RoomProviderRepository extends CrudRepository<RoomProvider,Integer> {

    /**
     * Retrieves all RoomProvider entities with the given IDs.
     *
     * @param iterable Iterable of IDs to search for
     * @return An Iterable of RoomProvider entities matching the provided IDs
     */
    @Override
    Iterable<RoomProvider> findAllById(Iterable iterable);

    /**
     * Finds a RoomProvider entity by its ID.
     *
     * @param integer The ID of the RoomProvider to find
     * @return An Optional containing the found RoomProvider entity, or empty if not found
     */
    @Override
    Optional<RoomProvider> findById(Integer integer);

    /**
     * Retrieves all RoomProvider entities from the database.
     *
     * @return An Iterable of all RoomProvider entities
     */
    @Override
    Iterable<RoomProvider> findAll();

    /**
     * Deletes the specified RoomProvider entity from the database.
     *
     * @param entity The RoomProvider entity to delete
     */
    @Override
    void delete(RoomProvider entity);

    /**
     * Deletes a RoomProvider entity with the specified ID.
     *
     * @param integer The ID of the RoomProvider entity to delete
     */
    @Override
    void deleteById(Integer integer);

    /**
     * Deletes all RoomProvider entities from the database.
     */
    @Override
    void deleteAll();

    /**
     * Deletes all RoomProvider entities specified in the provided Iterable.
     *
     * @param entities Iterable of RoomProvider entities to delete
     */
    @Override
    void deleteAll(Iterable entities);

    /**
     * Deletes all RoomProvider entities with IDs specified in the provided Iterable.
     *
     * @param iterable Iterable of IDs of entities to delete
     */
    @Override
    void deleteAllById(Iterable iterable);

    /**
     * Finds a RoomProvider entity by Room and Provider combination.
     *
     * @param room     The Room entity to search for
     * @param provider The Provider entity to search for
     * @return An Optional containing the found RoomProvider entity, or empty if not found
     */
    Optional<RoomProvider> findByRoomAndProvider(Room room, Provider provider);

    /**
     * Finds all RoomProvider entities for a specific Provider.
     *
     * @param provider The Provider entity to search for
     * @return An Iterable of RoomProvider entities associated with the provider
     */
    Iterable<RoomProvider> findByProvider(Provider provider);

    /**
     * Finds all RoomProvider entities for a specific Room.
     *
     * @param room The Room entity to search for
     * @return An Iterable of RoomProvider entities associated with the room
     */
    Iterable<RoomProvider> findByRoom(Room room);

}
