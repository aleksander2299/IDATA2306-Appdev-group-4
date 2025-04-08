package group4.backend.repository;

import group4.backend.entities.ExtraFeatures;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ExtraFeaturesRepository extends CrudRepository<ExtraFeatures, Integer> {

    @Override
    Iterable<ExtraFeatures> findAll();

    @Override
    Iterable<ExtraFeatures> findAllById(Iterable<Integer> integers);

    @Override
    Optional<ExtraFeatures> findById(Integer integer);

    @Override
    void delete(ExtraFeatures entity);

    @Override
    void deleteAll();

    @Override
    void deleteAll(Iterable<? extends ExtraFeatures> entities);

    @Override
    void deleteAllById(Iterable<? extends Integer> integers);

    @Override
    void deleteById(Integer integer);
}
