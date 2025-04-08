package group4.backend.repository;

import group4.backend.entities.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.Optional;

@Repository
public interface RoomRepository extends CrudRepository<Room,Integer> {

    @Override
    Iterable findAllById(Iterable iterable);

    @Override
    Optional<Room> findById(Integer integer);

    @Override
    Iterable findAll();

    @Override
    void deleteById(Integer integer);

    @Override
    void delete(Room entity);

    @Override
    void deleteAll();

    @Override
    void deleteAll(Iterable entities);

    @Override
    void deleteAllById(Iterable iterable);
}
