package group4.backend.controller;

import group4.backend.entities.Provider;
import group4.backend.entities.RoomProvider;
import group4.backend.service.ProviderService;
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
 * The ProviderController class handles HTTP requests related to operations on providers.
 * This includes retrieving, creating, updating, and deleting providers or their associated data.
 * It provides REST endpoints for interacting with the Provider entity.
 * NOTE: Java documentation was generated with help from ai to make sure it follows java documentation guidelines.
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
    @Operation(
            summary = "Get all providers",
            description = "Retrieves a list of all providers in the system.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Providers retrieved successfully"),
                    @ApiResponse(responseCode = "204", description = "No providers found")
            }
    )
    @GetMapping()
    public ResponseEntity<List<Provider>> getProviders(){
        List<Provider> providerList = providerService.getAllProviders();
        if(providerList.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(providerList);
    }

    @Operation(
            summary = "Get a provider by ID",
            description = "Retrieves a provider by its numeric ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Provider retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Provider not found")
            }
    )
    @GetMapping("/withId/{numericId}/roomProviders)")
    public ResponseEntity<Optional<Provider>> getRoomProvidersId(@PathVariable("numericId") int numericId) {
        Optional<Provider> rooms= providerService.getProviderById(numericId);
        if (rooms.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (rooms == null || rooms.isEmpty()) {
            return ResponseEntity.noContent().build(); // Optional: return 204 if no rooms
        }

        return ResponseEntity.ok(rooms);
    }

    /**
     * gets the roomProviders for a provider
     * @param providerName the name of the provider to get the rooms from
     * @return Responseentity of room
     */
    @Operation(
            summary = "Get room providers for a given provider name",
            description = "Returns the room providers associated with the specified provider.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Room providers retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "No room providers found for the given provider")
            }
    )
    @PreAuthorize("hasAnyRole('PROVIDER')")
    @GetMapping("/{provider_name}/roomProviders")
    public ResponseEntity<Iterable<RoomProvider>> getRoomProviders(@PathVariable("provider_name") String providerName) {
        Iterable<RoomProvider> rooms= providerService.getRoomProviders(providerName);
        if (!rooms.iterator().hasNext()) {
            return ResponseEntity.notFound().build();
        }

        if (rooms == null) {
            return ResponseEntity.noContent().build(); // Optional: return 204 if no rooms
        }

        return ResponseEntity.ok(rooms);
    }



    /**
     * gets provider at /api/providers/id
     * @param id the id of the provider to get
     * @return repsonseEntity if the provider was found or not
     */
    @Operation(
            summary = "Get a provider by ID",
            description = "Retrieves a single provider by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Provider retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Provider not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Provider> getProvider(@PathVariable("id") int id){
        Optional<Provider> providerOptional = providerService.getProviderById(id);
        return providerOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }


    @Operation(
            summary = "Get a provider by name",
            description = "Retrieves a provider using its name.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Provider retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Provider not found")
            }
    )
    @GetMapping("/byName/{providerName}")
    public ResponseEntity<Provider> getProviderByName(@PathVariable("providerName") String providerName){
        Optional<Provider> providerOptional = providerService.getProviderByName(providerName);
        return providerOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }


    /**
     * posts a provider to the database
     * @param provider the provider to post
     * @return the responseentity of provider if it worked or not.
     */
    @Operation(
            summary = "Create a new provider",
            description = "Creates and saves a new provider based on the request body.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Provider created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid provider data")
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'PROVIDER')")
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
    @Operation(
            summary = "Create providers in bulk",
            description = "Accepts a list of providers and saves them all to the database.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Providers created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid provider list")
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'PROVIDER')")
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

    /**
     * Deletes a provider with the specified ID if it exists.
     *
     * @param id the ID of the provider to be deleted
     * @return ResponseEntity with status 200 if the provider was successfully deleted,
     *         204 if no provider with the specified ID is found
     */
    @Operation(
            summary = "Delete a provider by ID",
            description = "Deletes the provider identified by the given ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Provider deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Provider not found")
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'PROVIDER')")
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
    @Operation(
            summary = "Delete all providers",
            description = "Deletes all providers currently stored in the database.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All providers deleted successfully"),
                    @ApiResponse(responseCode = "204", description = "No providers to delete")
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'PROVIDER')")
    @DeleteMapping()
    public ResponseEntity<Void> deleteAllRoom() {
        List<Provider> providers = providerService.getAllProviders();
        for(Provider provider : providers){
            providerService.deleteProvider(provider.getProviderId());
        }
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Update a provider",
            description = "Updates the name of a provider identified by ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Provider updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Provider not found")
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'PROVIDER')")
    @PutMapping("/{id}")
    public ResponseEntity<Provider> updateProvider(@PathVariable("id") int providerId,@RequestParam String newName){
        Provider updatedProvider = providerService.updateProvider(providerId, newName);
        return ResponseEntity.ok(updatedProvider);
    }




}
