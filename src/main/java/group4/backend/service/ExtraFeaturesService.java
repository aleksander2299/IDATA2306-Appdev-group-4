package group4.backend.service;

import group4.backend.entities.ExtraFeatures;
import group4.backend.repository.ExtraFeaturesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ExtraFeaturesService {

    @Autowired
    private ExtraFeaturesRepository extraFeaturesRepository;

    public SortedSet<ExtraFeatures> findAllbyId(Iterable<String> featureIds) {

        if(featureIds == null) {
            throw new IllegalArgumentException("Feature ID not found.");
        }
        SortedSet<ExtraFeatures> featureList = new TreeSet<>();
        for(ExtraFeatures extraFeatures : extraFeaturesRepository.findAllById(featureIds)) {
            featureList.add(extraFeatures);
        }
        if(featureList.isEmpty()) {
            throw new EmptyResultDataAccessException("No ExtraFeatures found", 1);
        }
        return featureList;
    }

    public Optional<ExtraFeatures> findById(String featureId) {
        if(featureId == null){
            throw new IllegalArgumentException("no feature ID found ");
        }
        return extraFeaturesRepository.findById(featureId);
    }

    public SortedSet<ExtraFeatures> findAll() {
        SortedSet<ExtraFeatures> featureList = new TreeSet<>();
        extraFeaturesRepository.findAll().forEach(featureList::add);
        if(featureList.isEmpty())
        {
            throw new IllegalArgumentException("Feature ID not found.");
        }
        return featureList;
    }

    public void delete(ExtraFeatures feature) {
        if(feature == null){
            throw new IllegalArgumentException("no feature found ");
        }
        extraFeaturesRepository.delete(feature);
    }

    public void deleteById(String string){
        if(string == null){
            throw new IllegalArgumentException("no string found ");
        }
        extraFeaturesRepository.deleteById(string);
    }

    public void deleteAll(){
        extraFeaturesRepository.deleteAll();
    }

    public void deletAllById(Iterable<String> strings){
        if(strings == null){
            throw new IllegalArgumentException("no strings found ");
        }
        extraFeaturesRepository.deleteAllById(strings);
    }

    public void deleteAll(Iterable<ExtraFeatures> extraFeatures){
        if (extraFeatures == null || !extraFeatures.iterator().hasNext()) {
            throw new IllegalArgumentException("No ExtraFeatures objects provided to delete.");
        }
        extraFeaturesRepository.deleteAll(extraFeatures);
    }

    public void addFeature(ExtraFeatures extraFeature) {
        this.extraFeaturesRepository.save(extraFeature);
    }

}
