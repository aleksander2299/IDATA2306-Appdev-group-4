package group4.backend.controller;

import group4.backend.entities.Room;
import group4.backend.entities.RoomProvider;
import group4.backend.service.RoomProviderService;
import org.springframework.beans.factory.annotation.Autowired;
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


    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<RoomProvider> deleteAll(){
        if(roomProviderService.findAll().isEmpty()){
            return ResponseEntity.noContent().build();
        }
        roomProviderService.deleteAll();
        return ResponseEntity.ok().build();
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'PROVIDER')")
    @PostMapping()
    public ResponseEntity<Void> linkRoomToProvider(@RequestBody RoomProvider roomProvider){
        roomProviderService.linkRoomToProvider(roomProvider.getRoomId(),roomProvider.getProviderId(),
                roomProvider.getRoomPrice());
        return ResponseEntity.ok().build();
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'PROVIDER')")
    @DeleteMapping()
    public ResponseEntity<RoomProvider> unlinkRoomToProvider(@RequestBody RoomProvider roomProvider){
        roomProviderService.unlinkRoomToProvider(roomProvider.getRoomId(),roomProvider.getRoomProviderId());
        return ResponseEntity.ok().build();
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'PROVIDER')")
    @PutMapping("/{id}")
    public ResponseEntity<RoomProvider> updateRoomProvider(@PathVariable("id") int roomProviderId,
                                                           @RequestBody RoomProvider roomProvider){
        RoomProvider updatedProvider = roomProviderService.updateRoomProvider(
                roomProviderId,roomProvider.getRoomPrice(),roomProvider.getRoomId(),
                roomProvider.getProviderId());

        return ResponseEntity.ok(updatedProvider);

    }





}
