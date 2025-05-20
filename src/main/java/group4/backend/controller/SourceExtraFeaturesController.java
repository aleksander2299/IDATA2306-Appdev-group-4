package group4.backend.controller;

import group4.backend.entities.ExtraFeatures;
import group4.backend.entities.Source;
import group4.backend.entities.SourceExtraFeatures;
import group4.backend.service.ExtraFeaturesService;
import group4.backend.service.SourceExtraFeaturesService;
import group4.backend.service.SourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;



/**
 * Controller class for managing the interactions between sources and their extra features.
 * This class provides APIs to retrieve, link, and manage sources and their associated extra features.
 * NOTE: Java documentation was generated with help from ai to make sure it follows java documentation guidelines.
 */
@RestController
@RequestMapping("/api/source_extra_features")
public class SourceExtraFeaturesController {

    @Autowired
    SourceExtraFeaturesService sourceExtraFeaturesService;

    @Autowired
    ExtraFeaturesService extraFeaturesService;

    @Autowired
    SourceService sourceService;

    /**
     * Retrieves a Source entity by its identifier.
     * If the Source is found, it returns a ResponseEntity containing the Source.
     * Otherwise, it returns a ResponseEntity with a not found status.
     *
     * @param id the unique identifier of the Source to retrieve
     * @return ResponseEntity containing the Source if found, or a ResponseEntity with a not found status
     */
    @GetMapping("/source/{id}")
    public ResponseEntity<Source> getSource(@PathVariable("id") int id) {
        Optional<Source> sourceOptional = sourceService.getSourceById(id);
        return sourceOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves a list of all Source entities available.
     * If no sources are found, returns a ResponseEntity with no content status.
     *
     * @return ResponseEntity containing a list of Source objects if any exist,
     *         or a ResponseEntity with no content status if the list is empty.
     */
    @GetMapping("/source")
    public ResponseEntity<List<Source>> getAllSources(){
        List<Source> sources = sourceService.getAllSources();
        if(sources.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sources);
    }

    /**
     * Retrieves an ExtraFeatures entity by its unique identifier.
     * If the ExtraFeatures entity is found, it returns a ResponseEntity containing the entity.
     * Otherwise, it returns a ResponseEntity with a not found status.
     *
     * @param id the unique identifier of the ExtraFeatures entity to retrieve
     * @return ResponseEntity containing the ExtraFeatures entity if found, or a ResponseEntity with a not found status
     */
    @GetMapping("/extra_features/{id}")
    public ResponseEntity<ExtraFeatures> getFeatures(@PathVariable("id") String id) {
        Optional<ExtraFeatures> extraFeaturesOptional = extraFeaturesService.getFeatureById(id);
        return extraFeaturesOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves the list of extra features associated with a given source.
     * If the source is not found, it returns a ResponseEntity with a not found status.
     *
     * @param SourceId the unique identifier of the source for which to retrieve extra features
     * @return ResponseEntity containing a list of ExtraFeatures if the source is found,
     *         otherwise a ResponseEntity with a not found status
     */
    @GetMapping("/extra_features/sourceFeatures/{SourceId}")
    public ResponseEntity<List<ExtraFeatures>> getFeaturesForSource(@PathVariable("SourceId") int SourceId) {
        Optional<Source> sourceOptional = sourceService.getSourceById(SourceId);
        if (sourceOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Source source = sourceOptional.get();

        List<ExtraFeatures> extraFeatures = sourceExtraFeaturesService.getFeaturesForSource(source);

        return ResponseEntity.ok(extraFeatures);
    }

    /**
     * Retrieves a list of all extra features available.
     * If no features are found, it returns a ResponseEntity with no content status.
     *
     * @return ResponseEntity containing a list of ExtraFeatures if any exist,
     *         or a ResponseEntity with no content status if none are available.
     */
    @GetMapping("/extra_features")
    public ResponseEntity<List<ExtraFeatures>> getAllFeatures() {
        List<ExtraFeatures> features = extraFeaturesService.getAllFeatures();
        if(features.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.ok(features);
        }
    }

    /**
     * Links a specified source to a specified feature.
     * This method requires the user to have the 'ADMIN' role for authorization.
     *
     * @param sourceId the unique identifier of the source to be linked
     * @param featureId the unique identifier of the feature to be linked to the source
     * @return ResponseEntity containing a success message if the source and feature are linked successfully,
     *         or a bad request status with an error message if the linking process fails
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/{sourceId}/{featureId}")
    public ResponseEntity<String> linkSourceToFeature(@PathVariable int sourceId, @PathVariable String featureId) {
        try {
            sourceExtraFeaturesService.linkSourceToFeature(sourceId, featureId);
            return ResponseEntity.ok("Source and Feature linked successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error linking Source and Feature: " + e.getMessage());
        }
    }

    /**
     * Retrieves a list of all SourceExtraFeatures entities available.
     * If no SourceExtraFeatures are found, returns a ResponseEntity with no content status.
     *
     * @return ResponseEntity containing a list of SourceExtraFeatures objects if any exist,
     *         or a ResponseEntity with no content status if the list is empty.
     */
    @GetMapping()
    public ResponseEntity<List<SourceExtraFeatures>> getAllSourceExtraFeatures() {
        List<SourceExtraFeatures> sourceExtraFeatures = sourceExtraFeaturesService.getAllSourceExtraFeatures();
        if(sourceExtraFeatures.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.ok(sourceExtraFeatures);
        }
    }

}
