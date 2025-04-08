package group4.backend.repository;

import group4.backend.entities.ExtraFeatures;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ExtraFeaturesRepository extends CrudRepository<ExtraFeatures, String> {

    @Override
    Iterable<ExtraFeatures> findAll();

    @Override
    Iterable<ExtraFeatures> findAllById(Iterable<String> strings);

    @Override
    Optional<ExtraFeatures> findById(String string);

    @Override
    void delete(ExtraFeatures entity);

    @Override
    void deleteAll();

    @Override
    void deleteAll(Iterable<? extends ExtraFeatures> entities);

    @Override
    void deleteAllById(Iterable<? extends String> strings);

    @Override
    void deleteById(String string);

}
