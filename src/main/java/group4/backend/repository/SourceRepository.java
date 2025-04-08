package group4.backend.repository;

import group4.backend.entities.Source;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SourceRepository extends CrudRepository<Source, Integer> {

    @Override
    Iterable<Source> findAll();

    @Override
    Iterable<Source> findAllById(Iterable<Integer> integers);

    @Override
    Optional<Source> findById(Integer integer);

    @Override
    void delete(Source entity);

    @Override
    void deleteAll();

    @Override
    void deleteAll(Iterable<? extends Source> entities);

    @Override
    void deleteAllById(Iterable<? extends Integer> integers);

    @Override
    void deleteById(Integer integer);
}
