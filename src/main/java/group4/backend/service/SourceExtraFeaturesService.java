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

    /**
     * Retrieves all SourceExtraFeatures entities from the database and returns them as a list.
     * Throws an exception if no entities are found.
     *
     * @return a list of SourceExtraFeatures entities
     * @throws EmptyResultDataAccessException if no SourceExtraFeatures entities are found
     */
    public List<SourceExtraFeatures> findAll(){

        List<SourceExtraFeatures> sourceExtraFeaturesList = new ArrayList<>();
        sourceExtraFeaturesRepository.findAll().forEach(sourceExtraFeaturesList::add);
        if (sourceExtraFeaturesList.isEmpty()) {
            throw new EmptyResultDataAccessException("No Source extra feature found", 1);
        }
        return sourceExtraFeaturesList;
    }

    /**
     * Links a Source entity identified by its ID to an ExtraFeatures entity identified by its ID.
     * Creates a relationship between the Source and the ExtraFeatures if both exist in the respective repositories.
     *
     * @param sourceId the ID of the Source to be linked
     * @param featureId the ID of the ExtraFeatures to be linked
     * @throws IllegalArgumentException if the Source or ExtraFeatures entity does not exist
     */
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

    /**
     * Unlinks a Source entity identified by its ID from an ExtraFeatures entity identified by its ID.
     * Removes the association between the Source and the ExtraFeatures if the link exists in the repository.
     *
     * @param sourceId the ID of the Source to be unlinked
     * @param featureId the ID of the ExtraFeatures to be unlinked
     * @throws IllegalArgumentException if the link between the Source and ExtraFeatures does not exist
     */
    public void unlinkSourceToFeature(int sourceId, String featureId){
        Source source = sourceRepository.findById(sourceId).get();
        ExtraFeatures feature = extraFeaturesRepository.findById(featureId).get();

        if(sourceExtraFeaturesRepository.findBySourceIdAndFeature(source, feature).isEmpty()){
            throw new IllegalArgumentException("The link does not exist.");
        }

        SourceExtraFeatures sourceExtraFeatures = sourceExtraFeaturesRepository.findBySourceIdAndFeature(source,feature).get();
        sourceExtraFeaturesRepository.delete(sourceExtraFeatures);
    }

    /**
     * Retrieves all SourceExtraFeatures entities from the database and returns them as a list.
     *
     * @return a list of all sourceExtraFeatures
     */
    public List<SourceExtraFeatures> getAllSourceExtraFeatures() {
        List<SourceExtraFeatures> sourceExtraFeatures = new ArrayList<>();
        sourceExtraFeaturesRepository.findAll().forEach(sourceExtraFeatures::add);
        return sourceExtraFeatures;
    }

    /**
     * Retrieves a list of extra features associated with a given source.
     *
     * @param source the source entity for which to retrieve associated extra features
     * @return a list of ExtraFeatures objects associated with the given source
     */
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
