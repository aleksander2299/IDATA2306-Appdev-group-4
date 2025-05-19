package group4.backend.controller;

import group4.backend.entities.Room;
import group4.backend.entities.Source;
import group4.backend.service.SourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * SourceController is a REST controller that handles HTTP requests associated with
 * the Source entity. This includes retrieving, creating, updating, and deleting
 * Source objects in the application. The operations are exposed under the
 * "/api/source" base path.
 *
 * It leverages SourceService to interact with the data layer for Source entities
 * and provides role-based access control for specific endpoints.
 * NOTE: Java documentation was generated with help from ai to make sure it follows java documentation guidelines.
 */
@RestController
@RequestMapping("/api/source")
public class SourceController {

    @Autowired
    SourceService sourceService;

    /**
     * Retrieves a Source entity based on its unique ID.
     *
     * @param id the unique identifier of the Source to be retrieved
     * @return a ResponseEntity containing the Source if found, or a 404 Not Found response if the Source does not exist
     */
    @GetMapping("/{id}")
    public ResponseEntity<Source> getSource(@PathVariable("id") int id) {
        Optional<Source> sourceOptional = sourceService.getSourceById(id);
        return sourceOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves all Source entities from the database.
     *
     * @return a ResponseEntity containing a list of all Source objects if present;
     *         a 204 No Content response if no sources are found.
     */
    @GetMapping()
    public ResponseEntity<List<Source>> getAllSources(){
        List<Source> sources = sourceService.getAllSources();
        if(sources.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sources);
    }

    /**
     * Creates a new Source entity and saves it to the database.
     * Requires ADMIN role for access.
     *
     * @param source the Source entity to be created
     * @return a ResponseEntity containing the created Source entity with a 201 Created status code
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<Source> createSource(@RequestBody Source source) {
        Source newSource = sourceService.saveSource(source);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSource);
    }

    /**
     * Method for posting list of sources to the database.
     * @param sources the list of sources to post.
     * @return ResponseEntity if it succeeded or not.
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/bulk")
    public ResponseEntity<List<Source>> createRooms(@RequestBody List<Source> sources){
        if(sources.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(sources);
        }
        for(Source source : sources){
            sourceService.saveSource(source);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(sources);
    }

    /**
     * Deletes source at /api/source/id.
     * @param id the id of the source to delete.
     * @return ResponseEntity if source was deleted or not.
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Source> deleteSource(@PathVariable("id") int id) {
        if(sourceService.getSourceById(id).isPresent()) {
            sourceService.deleteSource(id);
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes all sources in the database.
     * @return ResponseEntity if the list of sources is empty or if it did delete all.
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping()
    public ResponseEntity<Void> deleteAllSources() {
        List<Source> sources = sourceService.getAllSources();
        for(Source source : sources){
            sourceService.deleteSource(source.getSourceId());
        }
        return ResponseEntity.ok().build();
    }
}
