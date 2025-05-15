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

@RestController
@RequestMapping("/api/source_extra_features")
public class SourceExtraFeaturesController {

    @Autowired
    SourceExtraFeaturesService sourceExtraFeaturesService;

    @Autowired
    ExtraFeaturesService extraFeaturesService;

    @Autowired
    SourceService sourceService;

    @GetMapping("/source/{id}")
    public ResponseEntity<Source> getSource(@PathVariable("id") int id) {
        Optional<Source> sourceOptional = sourceService.getSourceById(id);
        return sourceOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/source")
    public ResponseEntity<List<Source>> getAllSources(){
        List<Source> sources = sourceService.getAllSources();
        if(sources.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sources);
    }

    @GetMapping("/extra_features/{id}")
    public ResponseEntity<ExtraFeatures> getFeatures(@PathVariable("id") String id) {
        Optional<ExtraFeatures> extraFeaturesOptional = extraFeaturesService.getFeatureById(id);
        return extraFeaturesOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

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
