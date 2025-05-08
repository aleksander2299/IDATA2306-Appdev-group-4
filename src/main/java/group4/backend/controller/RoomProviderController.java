package group4.backend.controller;

import group4.backend.entities.Provider;
import group4.backend.entities.Room;
import group4.backend.entities.RoomProvider;
import group4.backend.service.RoomProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roomProvider")
public class RoomProviderController {


    @Autowired
    private RoomProviderService roomProviderService;




    @GetMapping("/{id}")
    public ResponseEntity<RoomProvider> findById(@PathVariable("id") int id){
        Optional<RoomProvider> roomProvider = roomProviderService.findById(id);
        return roomProvider.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }


    @GetMapping()
    public ResponseEntity<List<RoomProvider>> findAll(){
        List<RoomProvider> roomProviderList = roomProviderService.findAll();
        if(roomProviderList.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(roomProviderList);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROVIDER')")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteByiD(@PathVariable("id") int id ){
        if(roomProviderService.findById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        roomProviderService.deleteById(id);
        return ResponseEntity.ok().build();

    }


    @PreAuthorize("hasAnyRole('ADMIN', 'PROVIDER')")
    public ResponseEntity<RoomProvider> deleteAll(){
        if(roomProviderService.findAll().isEmpty()){
            return ResponseEntity.noContent().build();
        }
        roomProviderService.deleteAll();
        return ResponseEntity.ok().build();
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'PROVIDER')")
    @PostMapping("/link/{roomId}/{providerId}/{roomPrice}")
    public ResponseEntity<RoomProvider> linkRoomIdAndProviderId(@PathVariable Integer roomId,
                                                           @PathVariable Integer providerId,@PathVariable Integer roomPrice){
        ResponseEntity<RoomProvider> response;
        Optional<RoomProvider> linkedRoomProvider = roomProviderService.linkRoomToProvider(roomId, providerId);
        linkedRoomProvider.get().setRoomPrice(roomPrice);
        response = linkedRoomProvider.map(
              roomProvider -> ResponseEntity.status(HttpStatus.CREATED).body(roomProvider))
          .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
        return response;
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'PROVIDER')")
    @DeleteMapping("/unlink/{roomId}/{providerId}")
    public ResponseEntity<RoomProvider> unlinkRoomIdAndProviderId(@PathVariable Integer roomId, @PathVariable Integer providerId){
        roomProviderService.unlinkRoomToProvider(roomId, providerId);
        return ResponseEntity.ok().build();
    }


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
