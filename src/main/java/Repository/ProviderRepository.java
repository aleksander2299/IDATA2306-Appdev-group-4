package Repository;

import group4.backend.entities.Provider;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProviderRepository extends CrudRepository<Provider,Integer> {

    @Override
    Iterable findAllById(Iterable iterable);

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
