package group4.backend.controller;

import group4.backend.entities.Source;
import group4.backend.service.SourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/source")
public class SourceController {

    @Autowired
    SourceService sourceService;

    @GetMapping("/{id}")
    public ResponseEntity<Source> getSource(@PathVariable("id") int id) {
        Optional<Source> sourceOptional = sourceService.getSourceById(id);
        return sourceOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping()
    public ResponseEntity<List<Source>> getAllSources(){
        List<Source> sources = sourceService.getAllSources();
        if(sources.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sources);
    }

    @PostMapping()
    public ResponseEntity<Source> createSource(@RequestBody Source source) {
        Source newSource = sourceService.saveSource(source);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSource);
    }

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
}
