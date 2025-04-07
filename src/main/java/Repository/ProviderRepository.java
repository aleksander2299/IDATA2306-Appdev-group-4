package Repository;

import group4.backend.entities.Provider;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProviderRepository extends CrudRepository<Provider,Integer> {

    @Override
    Iterable<Provider> findAllById(Iterable<Integer> integers);

    @Override
    Optional<Provider> findById(Integer id);

    @Override
    Iterable<Provider> findAll();

    @Override
    void deleteAll();

    @Override
    void deleteAll(Iterable entities);

    @Override
    void deleteAllById(Iterable iterable);

}
