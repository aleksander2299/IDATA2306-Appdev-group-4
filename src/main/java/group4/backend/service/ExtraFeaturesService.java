package group4.backend.service;

import group4.backend.entities.ExtraFeatures;
import group4.backend.repository.ExtraFeaturesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * Service class for managing extra features functionality.
 * Provides methods for CRUD operations on ExtraFeatures entities.
 * NOTE: Java documentation was generated with help from AI to ensure it follows Java documentation guidelines.
 */
@Service
public class ExtraFeaturesService {

    @Autowired
    private ExtraFeaturesRepository extraFeaturesRepository;

    /**
     * Retrieves a sorted set of ExtraFeatures by their IDs.
     *
     * @param featureIds An Iterable collection of feature IDs to search for
     * @return A SortedSet containing the found ExtraFeatures objects
     * @throws IllegalArgumentException       if featureIds is null
     * @throws EmptyResultDataAccessException if no features are found
     */
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

    /**
     * Finds an ExtraFeatures entity by its ID.
     *
     * @param featureId The ID of the feature to find
     * @return An Optional containing the found ExtraFeatures object, or empty if not found
     * @throws IllegalArgumentException if featureId is null
     */
    public Optional<ExtraFeatures> findById(String featureId) {
        if(featureId == null){
            throw new IllegalArgumentException("no feature ID found ");
        }
        return extraFeaturesRepository.findById(featureId);
    }

    /**
     * Retrieves all ExtraFeatures entities as a sorted set.
     *
     * @return A SortedSet containing all ExtraFeatures objects
     * @throws IllegalArgumentException if no features are found
     */
    public SortedSet<ExtraFeatures> findAll() {
        SortedSet<ExtraFeatures> featureList = new TreeSet<>();
        extraFeaturesRepository.findAll().forEach(featureList::add);
        if(featureList.isEmpty())
        {
            throw new IllegalArgumentException("Feature ID not found.");
        }
        return featureList;
    }

    /**
     * Deletes the specified ExtraFeatures entity.
     *
     * @param feature The ExtraFeatures object to delete
     * @throws IllegalArgumentException if feature is null
     */
    public void delete(ExtraFeatures feature) {
        if(feature == null){
            throw new IllegalArgumentException("no feature found ");
        }
        extraFeaturesRepository.delete(feature);
    }

    /**
     * Deletes an ExtraFeatures entity by its ID.
     *
     * @param string The ID of the feature to delete
     * @throws IllegalArgumentException if string is null
     */
    public void deleteById(String string){
        if(string == null){
            throw new IllegalArgumentException("no string found ");
        }
        extraFeaturesRepository.deleteById(string);
    }

    /**
     * Deletes all ExtraFeatures entities from the repository.
     */
    public void deleteAll(){
        extraFeaturesRepository.deleteAll();
    }

    /**
     * Deletes multiple ExtraFeatures entities by their IDs.
     *
     * @param strings An Iterable collection of feature IDs to delete
     * @throws IllegalArgumentException if strings is null
     */
    public void deletAllById(Iterable<String> strings){
        if(strings == null){
            throw new IllegalArgumentException("no strings found ");
        }
        extraFeaturesRepository.deleteAllById(strings);
    }

    /**
     * Deletes multiple ExtraFeatures entities.
     *
     * @param extraFeatures An Iterable collection of ExtraFeatures objects to delete
     * @throws IllegalArgumentException if extraFeatures is null or empty
     */
    public void deleteAll(Iterable<ExtraFeatures> extraFeatures){
        if (extraFeatures == null || !extraFeatures.iterator().hasNext()) {
            throw new IllegalArgumentException("No ExtraFeatures objects provided to delete.");
        }
        extraFeaturesRepository.deleteAll(extraFeatures);
    }

    /**
     * Adds a new ExtraFeatures entity to the repository.
     *
     * @param extraFeature The ExtraFeatures object to add
     */
    public void addFeature(ExtraFeatures extraFeature) {
        this.extraFeaturesRepository.save(extraFeature);
    }

    /**
     * Retrieves an ExtraFeatures entity by its ID.
     *
     * @param id The ID of the feature to retrieve
     * @return An Optional containing the found ExtraFeatures object, or empty if not found
     */
    public Optional<ExtraFeatures> getFeatureById(String id) {
        return extraFeaturesRepository.findById(id);
    }

    /**
     * Retrieves all ExtraFeatures entities as a list.
     *
     * @return A List containing all ExtraFeatures objects
     */
    public List<ExtraFeatures> getAllFeatures() {
        List<ExtraFeatures> features = new ArrayList<>();
        extraFeaturesRepository.findAll().forEach(extraFeature -> features.add((ExtraFeatures) extraFeature));
        return features;
    }

    /**
     * Saves an ExtraFeatures entity to the repository.
     *
     * @param feature The ExtraFeatures object to save
     * @return The saved ExtraFeatures object
     */
    public ExtraFeatures saveFeature(ExtraFeatures feature) {
        return extraFeaturesRepository.save(feature);
    }

    public void deleteFeature(String id) { extraFeaturesRepository.deleteById(id);
    }

}
