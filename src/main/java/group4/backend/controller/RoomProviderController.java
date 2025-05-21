package group4.backend.controller;

import group4.backend.entities.Provider;
import group4.backend.entities.Room;
import group4.backend.entities.RoomProvider;
import group4.backend.service.RoomProviderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Controller that manages RoomProvider-related APIs and operations.
 * Provides endpoints to perform CRUD operations and linking/unlinking
 * operations between Room and Provider entities.
 * NOTE: Java documentation was generated with help from ai to make sure it follows java documentation guidelines.
 */
@RestController
@RequestMapping("/api/roomProvider")
public class RoomProviderController {


    @Autowired
    private RoomProviderService roomProviderService;


    /**
     * Retrieves a RoomProvider entity by its ID.
     * If the specified RoomProvider exists, it returns a ResponseEntity containing the RoomProvider with status 200 (OK).
     * If the RoomProvider is not found, it returns a ResponseEntity with status 404 (Not Found).
     *
     * @param id the ID of the RoomProvider to be retrieved
     * @return ResponseEntity containing the RoomProvider if found, or a 404 (Not Found) response if not
     */
    @Operation(
            summary = "finds roomProvider by id",
            description = "pathVariable id is used to find a roomProvider",
            responses = {
                    @ApiResponse(responseCode = "200", description = "found roomProvider"),
                    @ApiResponse(responseCode = "404", description = "not found roomProvider")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<RoomProvider> findById(@PathVariable("id") int id){
        Optional<RoomProvider> roomProvider = roomProviderService.findById(id);
        return roomProvider.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves all RoomProvider entities.
     * If no RoomProvider entities are found, responds with a 204 (No Content) status.
     * If RoomProvider entities are present, responds with a 200 (OK) status along with the list.
     *
     * @return ResponseEntity containing a list of RoomProvider objects if present,
     * or a 204 (No Content) response if the list is empty
     */
    @Operation(
            summary = "return all roomProviders",
            description = "returns a list of all RoomProviders",
            responses = {
                    @ApiResponse(responseCode = "200", description = "all roomProviders found"),
                    @ApiResponse(responseCode = "403", description = "expectations not met"),
                    @ApiResponse(responseCode = "204", description = "no roomProviders found")
            }
    )
    @GetMapping()
    public ResponseEntity<List<RoomProvider>> findAll(){
        List<RoomProvider> roomProviderList = roomProviderService.findAll();
        if(roomProviderList.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(roomProviderList);
    }

    /**
     * Deletes a RoomProvider entity by its ID.
     * If the specified RoomProvider exists, it deletes the entity and returns a 200 (OK) response.
     * If the RoomProvider is not found, it returns a 404 (Not Found) response.
     *
     * @param id the ID of the RoomProvider to be deleted
     * @return ResponseEntity with 200 (OK) status if deletion is successful, or 404 (Not Found) if the entity is not found
     */
    @Operation(
            summary = "delete roomProvider by ID",
            description = "finds roomProvider from its id and deletes it",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successfully deleted"),
                    @ApiResponse(responseCode = "403", description = "expectations not met"),
                    @ApiResponse(responseCode = "404", description = "roomProvider not found")
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'PROVIDER')")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteByiD(@PathVariable("id") int id ){
        if(roomProviderService.findById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        roomProviderService.deleteById(id);
        return ResponseEntity.ok().build();

    }

    /**
     * Deletes all RoomProvider entities.
     * If no RoomProvider entities are found, it responds with a 204 (No Content) status.
     * If RoomProvider entities are present, they are deleted, and the method responds with a 200 (OK) status.
     *
     * @return ResponseEntity with status 200 (OK) if deletion is successful,
     * or 204 (No Content) if no entities are found to delete
     */
    @Operation(
            summary = "delete all roomProviders",
            description = "deletes all roomProviders in database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "all roomProviders deleted"),
                    @ApiResponse(responseCode = "403", description = "expectations not met"),
                    @ApiResponse(responseCode = "404", description = "roomProviders not found")
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'PROVIDER')")
    public ResponseEntity<RoomProvider> deleteAll(){
        if(roomProviderService.findAll().isEmpty()){
            return ResponseEntity.noContent().build();
        }
        roomProviderService.deleteAll();
        return ResponseEntity.ok().build();
    }

    /**
     * Links a Room entity identified by roomId to a Provider entity identified by providerId with a specified roomPrice.
     * If the linking is successful, it returns the created RoomProvider entity with a 201 (Created) status.
     * If the linking fails, it returns a 400 (Bad Request) status.
     *
     * @param roomId the ID of the Room to be linked
     * @param providerId the ID of the Provider to be linked
     * @param roomPrice the price of the Room
     * @return ResponseEntity containing the linked RoomProvider entity if successful, or a 400 (Bad Request) status if linking fails
     */
    @Operation(
            summary = "links a room an a provider together with a price",
            description = "takes roomId, providerID and a room price and link them together",
            responses = {
                    @ApiResponse(responseCode = "201", description = "successfully linked"),
                    @ApiResponse(responseCode = "403", description = "expectations not met")
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'PROVIDER')")
    @PostMapping("/link/{roomId}/{providerId}/{roomPrice}")
    public ResponseEntity<RoomProvider> linkRoomIdAndProviderId(@PathVariable Integer roomId,
                                                           @PathVariable Integer providerId,@PathVariable Integer roomPrice){
        ResponseEntity<RoomProvider> response;
        Optional<RoomProvider> linkedRoomProvider = roomProviderService.linkRoomToProvider(roomId, providerId,roomPrice);

        response = linkedRoomProvider.map(
              roomProvider -> ResponseEntity.status(HttpStatus.CREATED).body(roomProvider))
          .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
        return response;
    }

    /**
     * Unlinks a Room entity identified by roomId from a Provider entity identified by providerId.
     * This operation removes the association between the specified Room and Provider.
     *
     * @param roomId the ID of the Room to be unlinked
     * @param providerId the ID of the Provider to be unlinked
     * @return ResponseEntity with a 200 (OK) status if the unlinking is successful
     */
    @Operation(
            summary = "unlink room and provider",
            description = "roomId and providerID used to find a roomProvider and unlink them",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successfully unlinked"),
                    @ApiResponse(responseCode = "403", description = "expectations not met"),
                    @ApiResponse(responseCode = "404", description = "roomProvider not found")
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'PROVIDER')")
    @DeleteMapping("/unlink/{roomId}/{providerId}")
    public ResponseEntity<RoomProvider> unlinkRoomIdAndProviderId(@PathVariable Integer roomId, @PathVariable Integer providerId){
        try {
            roomProviderService.unlinkRoomToProvider(roomId, providerId);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Updates an existing RoomProvider entity using its ID.
     * The method expects a valid RoomProvider object in the request body,
     * which contains updated details such as room price, room ID, and provider ID.
     * The updated RoomProvider is returned in the response.
     * Requires the user to have either the 'ADMIN' or 'PROVIDER' role.
     *
     * @param roomProviderId the ID of the RoomProvider to be updated
     * @param roomProvider the RoomProvider object containing the updated details
     * @return ResponseEntity containing the updated RoomProvider object with a 200 (OK) status
     */
    @Operation(
            summary = "update a roomProvider",
            description = "update a roomProvider found from id based on data from requestBody of roomProvider",
            responses = {
                    @ApiResponse(responseCode = "200", description = "all roomProviders found"),
                    @ApiResponse(responseCode = "403", description = "expectations not met"),
                    @ApiResponse(responseCode = "404", description = "roomProvider not found")
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'PROVIDER')")
    @PutMapping("/{id}")
    public ResponseEntity<RoomProvider> updateRoomProvider(@PathVariable("id") int roomProviderId,
                                                           @RequestBody RoomProvider roomProvider){
        RoomProvider updatedProvider = roomProviderService.updateRoomProvider(
                roomProviderId,roomProvider.getRoomPrice(),roomProvider.getRoom().getRoomId(),
                roomProvider.getProvider().getProviderId());

        return ResponseEntity.ok(updatedProvider);

    }

}
