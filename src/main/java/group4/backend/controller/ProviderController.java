package group4.backend.controller;

import group4.backend.entities.Provider;
import group4.backend.entities.Room;
import group4.backend.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Restcontroller for provider table.
 */
@RestController
@RequestMapping("/api/providers")
public class ProviderController {

    @Autowired
    ProviderService providerService;


    /**
     * gets all the providers
     * @return responseentity if there are providers or not.
     */
    @GetMapping()
    public ResponseEntity<List<Provider>> getProviders(){
        List<Provider> providerList = providerService.getAllProviders();
        if(providerList.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(providerList);
    }


    /**
     * gets provider at /api/providers/id
     * @param id the id of the provider to get
     * @return repsonseEntity if the provider was found or not
     */
    @GetMapping("/{id}")
    public ResponseEntity<Provider> getProvider(@PathVariable("id") int id){
        Optional<Provider> providerOptional = providerService.getProviderById(id);
        return providerOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }


    /**
     * posts a provider to the database
     * @param provider the provider to post
     * @return the responseentity of provider if it worked or not.
     */
    @PostMapping()
    public ResponseEntity<Provider> postProvider(@RequestBody Provider provider) {
        if(provider != null) {
           providerService.saveProvider(provider);
        }
        else{
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(provider);

    }



    /**
     *method for posting list of providers to database
     * @param providers the list of providers to post
     * @return responsentity if it worked or not.
     */
    @PostMapping("/bulk")
    public ResponseEntity<List<Provider>> createRooms(@RequestBody List<Provider> providers){
        if(providers.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(providers);
        }
        for(Provider provider : providers){
            providerService.saveProvider(provider);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(providers);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProvider(@PathVariable("id") int id) {
        if (providerService.getProviderById(id).isPresent()) {
            providerService.deleteProvider(id);
        } else {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().build();

    }



    /**
     * deletes all providers in the database
     * @return ResponseEntity if the list of rooms is empty or if it did delete all.
     */
    @DeleteMapping()
    public ResponseEntity<Void> deleteAllRoom() {
        List<Provider> providers = providerService.getAllProviders();
        for(Provider provider : providers){
            providerService.deleteProvider(provider.getProviderId());
        }
        return ResponseEntity.ok().build();
    }





}
