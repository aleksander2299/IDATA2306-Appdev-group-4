package group4.backend.controller;

import group4.backend.entities.ExtraFeatures;
import group4.backend.service.ExtraFeaturesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller class for managing operations related to ExtraFeatures.
 * This controller provides endpoints to retrieve, create, and delete ExtraFeatures resources.
 * NOTE: Java documentation was generated with help from ai to make sure it follows java documentation guidelines.
 */

@RestController
@RequestMapping("/api/extra_features")
public class ExtraFeaturesController {

    @Autowired
    ExtraFeaturesService extraFeaturesService;

    /**
     * Retrieves an ExtraFeatures object by its ID.
     *
     * @param id the ID of the ExtraFeatures to retrieve
     * @return a ResponseEntity containing the ExtraFeatures object if found, or a 404 Not Found status if not found
     */
    @Operation(
            summary = "Gets an extra feature based on its id ",
            description = "Pathvariable id will be used to get an extra feature object",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The extra feature was found"),
                    @ApiResponse(responseCode = "404", description = "extra feature object was not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ExtraFeatures> getFeature(@PathVariable("id") String id) {
        Optional<ExtraFeatures> extraFeaturesOptional = extraFeaturesService.getFeatureById(id);
        return extraFeaturesOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves all the ExtraFeatures objects from the database.
     *
     * @return a ResponseEntity containing a list of ExtraFeatures objects with HTTP status 200 if the list is not empty,
     *         or an HTTP status 204 (No Content) if the list is empty.
     */
    @Operation(
            summary = "get all extra features",
            description = "gets all the extra features that exists and put them in a list ",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successfully found all features and put them in list"),
                    @ApiResponse(responseCode = "204", description = "list is empty")
            }
    )
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

    /**
     * Creates a new ExtraFeatures entity and saves it to the database.
     *
     * @param feature the ExtraFeatures object to be created and saved
     * @return a ResponseEntity containing the created ExtraFeatures object with HTTP status 201 (Created)
     */
    @Operation(
            summary = "creates an extra feature a post operation",
            description = "uses a request body of extra feature to create an object and save it to database ",
            responses = {
                    @ApiResponse(responseCode = "201", description = "successfully created and saved feature"),
                    @ApiResponse(responseCode = "403", description = "something went wrong like request body might be wrong or authorization")
            }
    )
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ExtraFeatures> createFeature(@RequestBody ExtraFeatures feature) {
         ExtraFeatures newFeature = extraFeaturesService.saveFeature(feature);
        return ResponseEntity.status(HttpStatus.CREATED).body(newFeature);
    }

    /**
     * Creates multiple ExtraFeatures entities and saves them to the database in bulk.
     * If the provided list is empty, it returns a response with HTTP status 204 (No Content).
     * Otherwise, it saves each ExtraFeatures object in the list and returns the saved list with HTTP status 201 (Created).
     *
     * @param features a list of ExtraFeatures objects to be created and saved
     * @return a ResponseEntity containing the list of created ExtraFeatures objects with HTTP status 201 (Created) if successful,
     *         or a ResponseEntity with HTTP status 204 (No Content) if the provided list is empty
     */
    @Operation(
            summary = "creates extra features in bulk, i.e multiple at a time",
            description = "A request body of a list of extra features can be sent to create multiple extra features",
            responses = {
                    @ApiResponse(responseCode = "201", description = "successfully created and saved features"),
                    @ApiResponse(responseCode = "204", description = "input list empty")
            }
    )
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

    /**
     * Deletes an ExtraFeatures resource based on the specified ID.
     * If the resource with the given ID is found, it will be removed from the database,
     * and a no-content response will be returned. If the resource is not found,
     * a not-found response will be returned.
     *
     * @param id the ID of the ExtraFeatures resource to be deleted
     * @return a Response*/
    @Operation(
            summary = "deletes extra feature based on its id",
            description = "deletes a feature based on its path variable id, if its found in database then its deleted ",
            responses = {
                    @ApiResponse(responseCode = "204", description = "successfully deleted feature"),
                    @ApiResponse(responseCode = "404", description = "feature not found")
            }
    )
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

    /**
     * Deletes all ExtraFeatures resources from the database.
     * This method retrieves all the existing ExtraFeatures objects
     * and deletes them one by one.
     *
     * @return a ResponseEntity with HTTP status 200 (OK) after all features are successfully deleted
     */
    @Operation(
            summary = "deletes all existing features",
            description = "all existing features in database are deleted, no parameters needed ",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successfully deleted all features"),
                    @ApiResponse(responseCode = "403", description = "something went wrong like request body might be wrong or authorization")
            }
    )
    @DeleteMapping()
    public ResponseEntity<Void> deleteAllFeatures() {
        List<ExtraFeatures> features = extraFeaturesService.getAllFeatures();
        for(ExtraFeatures extraFeatures : features) {
            extraFeaturesService.deleteFeature(extraFeatures.getFeature());
        }
        return ResponseEntity.ok().build();

    }
}

