package group4.backend.repository;

import group4.backend.entities.SourceExtraFeatures;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SourceExtraFeaturesRepository extends CrudRepository<SourceExtraFeatures, Integer> {

    @Override
    Iterable<SourceExtraFeatures> findAll();

    @Override
    Iterable<SourceExtraFeatures> findAllById(Iterable<Integer> integers);

    @Override
    Optional<SourceExtraFeatures> findById(Integer integer);

    @Override
    void delete(SourceExtraFeatures entity);

    @Override
    void deleteById(Integer integer);

    @Override
    void deleteAll(Iterable<? extends SourceExtraFeatures> entities);

    @Override
    void deleteAll();

    @Override
    void deleteAllById(Iterable<? extends Integer> integers);
}
