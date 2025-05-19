package group4.backend.repository;

import group4.backend.entities.Provider;
import group4.backend.entities.Room;
import group4.backend.entities.RoomProvider;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface RoomProviderRepository extends CrudRepository<RoomProvider,Integer> {

    @Override
    Iterable<RoomProvider> findAllById(Iterable iterable);

    @Override
    Optional<RoomProvider> findById(Integer integer);

    @Override
    Iterable<RoomProvider> findAll();

    @Override
    void delete(RoomProvider entity);

    @Override
    void deleteById(Integer integer);

    @Override
    void deleteAll();

    @Override
    void deleteAll(Iterable entities);

    @Override
    void deleteAllById(Iterable iterable);


    Optional<RoomProvider> findByRoomAndProvider(Room room, Provider provider);

    Iterable<RoomProvider> findByProvider(Provider provider);
    Iterable<RoomProvider> findByRoom(Room room);

}
