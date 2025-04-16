package group4.backend.service;


import group4.backend.entities.*;
import group4.backend.repository.ExtraFeaturesRepository;
import group4.backend.repository.SourceExtraFeaturesRepository;
import group4.backend.repository.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SourceExtraFeaturesService {

    @Autowired
    private SourceExtraFeaturesRepository sourceExtraFeaturesRepository;

    @Autowired
    private SourceRepository sourceRepository;

    @Autowired
    private ExtraFeaturesRepository extraFeaturesRepository;


    public void linkSourceToFeature(int sourceId, String featureId){

        if(sourceRepository.findById(sourceId).isEmpty() || extraFeaturesRepository.findById(featureId).isEmpty()){
            throw new IllegalArgumentException("Source or feature does not exist.");
        }
        Source source = sourceRepository.findById(sourceId).get();
        ExtraFeatures feature = extraFeaturesRepository.findById(featureId).get();


        SourceExtraFeatures sourceExtraFeatures = new SourceExtraFeatures();
        sourceExtraFeatures.setSourceID(source);
        sourceExtraFeatures.setFeature(feature);
        sourceExtraFeaturesRepository.save(sourceExtraFeatures);
    }

    public void unlinkSourceToFeature(int sourceId, String featureId){
        Source sourceID = sourceRepository.findById(sourceId).get();
        ExtraFeatures feature = extraFeaturesRepository.findById(featureId).get();

        if(sourceExtraFeaturesRepository.findBySourceIDAndFeature(sourceID, feature).isEmpty()){
            throw new IllegalArgumentException("The link does not exist.");
        }

        SourceExtraFeatures sourceExtraFeatures = sourceExtraFeaturesRepository.findBySourceIDAndFeature(sourceID,feature).get();
        sourceExtraFeaturesRepository.delete(sourceExtraFeatures);
    }
}
