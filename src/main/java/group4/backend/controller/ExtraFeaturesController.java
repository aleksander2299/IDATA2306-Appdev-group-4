package group4.backend.controller;

import group4.backend.entities.ExtraFeatures;
import group4.backend.service.ExtraFeaturesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/extra_features")
public class ExtraFeaturesController {

    @Autowired
    ExtraFeaturesService extraFeaturesService;

    @GetMapping("/{id}")
    public ResponseEntity<ExtraFeatures> getFeature(@PathVariable("id") String id) {
        Optional<ExtraFeatures> extraFeaturesOptional = extraFeaturesService.getFeatureById(id);
        return extraFeaturesOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping()
    public ResponseEntity<List<ExtraFeatures>> getAllFeatures() {
        List<ExtraFeatures> features = extraFeaturesService.getAllFeatures();
        if(features.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.ok(features);
        }
    }

    @PostMapping
    public ResponseEntity<ExtraFeatures> createFeature(@RequestBody ExtraFeatures feature) {
         ExtraFeatures newFeature = extraFeaturesService.saveFeature(feature);
        return ResponseEntity.status(HttpStatus.CREATED).body(newFeature);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<ExtraFeatures>> createFeatures(@RequestBody List<ExtraFeatures> features) {
        if(features.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(features);
        }
        for(ExtraFeatures extraFeatures : features) {
            extraFeaturesService.saveFeature(extraFeatures);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(features);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ExtraFeatures> deleteFeature(@PathVariable("id") String id) {
        if(extraFeaturesService.getFeatureById(id).isPresent()) {
            extraFeaturesService.deleteFeature(id);
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteAllFeatures() {
        List<ExtraFeatures> features = extraFeaturesService.getAllFeatures();
        for(ExtraFeatures extraFeatures : features) {
            extraFeaturesService.deleteFeature(extraFeatures.getFeature());
        }
        return ResponseEntity.ok().build();

    }
}

