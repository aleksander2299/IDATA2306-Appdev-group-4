package Repository;

import group4.backend.entities.RoomProvider;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoomProviderRepository extends CrudRepository<RoomProvider,Integer> {

    @Override
    Iterable findAllById(Iterable iterable);

    @Override
    Optional<RoomProvider> findById(Integer integer);

    @Override
    Iterable findAll();

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

}
