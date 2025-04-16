package group4.backend.repository;

import group4.backend.entities.*;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SourceExtraFeaturesRepository extends CrudRepository<SourceExtraFeatures, SourceExtraFeaturesId> {

    @Override
    Iterable<SourceExtraFeatures> findAll();

    @Override
    Iterable<SourceExtraFeatures> findAllById(Iterable<SourceExtraFeaturesId> sourceExtraFeaturesIds);

    @Override
    Optional<SourceExtraFeatures> findById(SourceExtraFeaturesId sourceExtraFeaturesId);

    @Override
    void delete(SourceExtraFeatures entity);

    @Override
    void deleteAllById(Iterable<? extends SourceExtraFeaturesId> sourceExtraFeaturesIds);

    @Override
    void deleteById(SourceExtraFeaturesId sourceExtraFeaturesId);

    @Override
    void deleteAll(Iterable<? extends SourceExtraFeatures> entities);

    @Override
    void deleteAll();

    Optional<SourceExtraFeatures> findBySourceAndFeature(Source source, ExtraFeatures feature);
}
