package group4.backend.controller;

import group4.backend.entities.Room;
import group4.backend.entities.RoomProvider;
import group4.backend.entities.Source;
import group4.backend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


/**
 * Restcontroller for rooms, can add and remove rooms.
 */
@RestController
@RequestMapping("/api/rooms")
public class RoomController {


    @Autowired
    RoomService roomService;


    /**
     * gets a room at /api/rooms/id
     * @param id the id of the room to get
     * @return Responseentity of room
     */
    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoom(@PathVariable("id") int id) {
        Optional<Room> roomOptional = roomService.getRoomById(id);
        return roomOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/{id}/source")
    public ResponseEntity<Source> getSource(@PathVariable("id") int id){
        Optional<Source> source = Optional.ofNullable(roomService.getRoomById(id).get().getSource());
        return source.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * gets the roomProviders for a room
     * @param id the id of the room to get
     * @return Responseentity of room
     */
    @GetMapping("/{id}/roomProviders")
    public ResponseEntity<List<RoomProvider>> getRoomProviders(@PathVariable("id") int id) {
        List<RoomProvider> providers= roomService.getRoomById(id).get().getRoomProviders();
        if (providers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (providers == null || providers.isEmpty()) {
            return ResponseEntity.noContent().build(); // Optional: return 204 if no providers
        }

        return ResponseEntity.ok(providers);
    }


    /**
     * returns all rooms at /api/rooms.
     * @return Responsentity message depending on if there are rooms or not.
     */
    @GetMapping()
    public ResponseEntity<List<Room>> getAllRooms(){
        List<Room> rooms = roomService.getAllRooms();
        if (rooms.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(rooms);
    }


    /**
     * post a room to the database
     * @param room the room to be posted
     * @return Responsentity if it was created.
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        Room newRoom = roomService.saveRoom(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRoom);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/withSource/{sourceId}")
    public ResponseEntity<Room> createRoomWithSourceId(@PathVariable Integer sourceId, @RequestBody Room room) {
        Room newRoom = roomService.saveRoomWithSourceId(sourceId, room);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRoom);
    }

    /**
     *method for posting list of room to database
     * @param rooms the list of rooms to post
     * @return responsentity if it worked or not.
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/bulk")
    public ResponseEntity<List<Room>> createRooms(@RequestBody List<Room> rooms){
        if(rooms.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(rooms);
        }
        for(Room room : rooms){
            roomService.saveRoom(room);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(rooms);
    }

    /**
     * deletes room at /api/rooms/id
     * @param id the id of the room to delete
     * @return ResponseEntity if room was deleted or not.
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable("id") int id) {
       if(roomService.getRoomById(id).isEmpty()){
           return ResponseEntity.noContent().build();
       }
       else
       {
           roomService.deleteRoom(id);
       }
       return ResponseEntity.ok().build();
    }


    /**
     * deletes all room in the database
     * @return ResponseEntity if the list of rooms is empty or if it did delete all. 
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping()
    public ResponseEntity<Void> deleteAllRoom() {
        List<Room> rooms = roomService.getAllRooms();
        for(Room room : rooms){
            roomService.deleteRoom(room.getRoomId());
        }
        return ResponseEntity.ok().build();
    }



    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable("id") int roomId,@RequestBody Room room){
        Room updatedRoom = roomService.updateRoom(roomId, room.getRoomName(),room.getSource().getSourceId(), room.getDescription(),
                room.isVisible(), room.getRoomType(), room.getImageUrl());

        return ResponseEntity.ok(updatedRoom);

    }

}
