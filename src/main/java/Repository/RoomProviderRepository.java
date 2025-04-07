package Repository;

import group4.backend.entities.RoomProvider;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

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



}
