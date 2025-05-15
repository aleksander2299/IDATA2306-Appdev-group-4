package group4.backend.service;

import group4.backend.entities.*;
import group4.backend.repository.ExtraFeaturesRepository;
import group4.backend.repository.SourceExtraFeaturesRepository;
import group4.backend.repository.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SourceExtraFeaturesService {

    @Autowired
    private SourceExtraFeaturesRepository sourceExtraFeaturesRepository;

    @Autowired
    private SourceRepository sourceRepository;

    @Autowired
    private ExtraFeaturesRepository extraFeaturesRepository;

    public List<SourceExtraFeatures> findAll(){

        List<SourceExtraFeatures> sourceExtraFeaturesList = new ArrayList<>();
        sourceExtraFeaturesRepository.findAll().forEach(sourceExtraFeaturesList::add);
        if (sourceExtraFeaturesList.isEmpty()) {
            throw new EmptyResultDataAccessException("No Source extra feature found", 1);
        }
        return sourceExtraFeaturesList;
    }

    // Got help from chatGPT to make this work.
    public void linkSourceToFeature(int sourceId, String featureId) {
        // Check if the Source and Feature exists.
        Optional<Source> sourceOptional = sourceRepository.findById(sourceId);
        Optional<ExtraFeatures> extraFeaturesOptional = extraFeaturesRepository.findById(featureId);

        if (sourceOptional.isEmpty() || extraFeaturesOptional.isEmpty()) {
            throw new IllegalArgumentException("Source or Feature does not exist.");
        }

        Source source = sourceOptional.get();
        ExtraFeatures feature = extraFeaturesOptional.get();

        // Creates the Id for SourceExtraFeatures.
        SourceExtraFeaturesId id = new SourceExtraFeaturesId(feature.getFeature(), source.getSourceId());

        // Creates the SourceExtraFeatures object and sets the Id.
        SourceExtraFeatures sourceExtraFeatures = new SourceExtraFeatures();
        sourceExtraFeatures.setId(id);
        sourceExtraFeatures.setSourceId(source);
        sourceExtraFeatures.setFeature(feature);

        // Saves the SourceExtraFeatures entity.
        sourceExtraFeaturesRepository.save(sourceExtraFeatures);
    }

    public void unlinkSourceToFeature(int sourceId, String featureId){
        Source source = sourceRepository.findById(sourceId).get();
        ExtraFeatures feature = extraFeaturesRepository.findById(featureId).get();

        if(sourceExtraFeaturesRepository.findBySourceIdAndFeature(source, feature).isEmpty()){
            throw new IllegalArgumentException("The link does not exist.");
        }

        SourceExtraFeatures sourceExtraFeatures = sourceExtraFeaturesRepository.findBySourceIdAndFeature(source,feature).get();
        sourceExtraFeaturesRepository.delete(sourceExtraFeatures);
    }

    public List<SourceExtraFeatures> getAllSourceExtraFeatures() {
        List<SourceExtraFeatures> sourceExtraFeatures = new ArrayList<>();
        sourceExtraFeaturesRepository.findAll().forEach(sourceExtraFeatures::add);
        return sourceExtraFeatures;
    }

    public List<ExtraFeatures> getFeaturesForSource(Source source) {
        List<ExtraFeatures> features = new ArrayList<>();

        for (SourceExtraFeatures sourceExtraFeatures : sourceExtraFeaturesRepository.findAll()) {
            if (sourceExtraFeatures.getSourceId().equals(source)) {
                features.add(sourceExtraFeatures.getFeature());
            }
        }
        return features;
    }

}
