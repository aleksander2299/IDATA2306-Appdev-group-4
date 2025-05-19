package group4.backend.controller;

import group4.backend.entities.Room;
import group4.backend.entities.RoomProvider;
import group4.backend.entities.Source;
import group4.backend.service.RoomService;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;


/**
 * Restcontroller for rooms, can add and remove rooms.
 */
@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }


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
    public ResponseEntity<Iterable<RoomProvider>> getRoomProviders(@PathVariable("id") int id) {
        ResponseEntity<Iterable<RoomProvider>> response = null;
        try {
            Iterable<RoomProvider> providers = roomService.getRoomProviders(id);
            response = ResponseEntity.status(HttpStatus.OK).body(providers);
        } catch (IllegalArgumentException iAe) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (NoSuchElementException nSeE) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return response;
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

    @GetMapping("/{roomId}/dates")
    public ResponseEntity<List<LocalDate[]>> getOccupiedDates(@PathVariable Integer roomId) {
        ResponseEntity<List<LocalDate[]>> response = ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
        List<LocalDate[]> dates = null;
        try {
            dates = this.roomService.getOccupiedRoomDates(roomId);
        } catch (IllegalArgumentException iAe) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (NoSuchElementException nSeE) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (dates != null) {
            response = ResponseEntity.status(HttpStatus.OK).body(dates);
        }

        return response;
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
     * This method was created with AI assistance
     * @param roomId
     * @param file
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'PROVIDER')")
    @PostMapping("/{roomId}/images")
    public ResponseEntity<String> uploadImage(@PathVariable Integer roomId, @RequestParam MultipartFile file) {
        ResponseEntity<String> response = null;
        String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
        Path uploadPath = Paths.get("C:/webAttempt/epicProject/uploads", filename);

        try(InputStream input = file.getInputStream()) {
            Files.copy(input, uploadPath, StandardCopyOption.REPLACE_EXISTING);
            this.roomService.updateRoom(roomId, null, null, null, null, null, "/images/" + filename);
            response = ResponseEntity.status(HttpStatus.CREATED).body(filename);
        } catch (IOException ioE) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return response;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROVIDER')")
    @DeleteMapping("/images/{filename}")
    public ResponseEntity<String> deleteImage(@PathVariable String filename) {
        ResponseEntity<String> response = null;
        Path path = Paths.get("C:/webAttempt/epicProject/uploads", filename);
        try {
            Files.deleteIfExists(path);
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Deleted");
        } catch (IOException e) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deletion failed");
        }
        return response;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROVIDER')")
    @DeleteMapping("/images/room/{roomId}")
    public ResponseEntity<String> deleteImage(@PathVariable Integer roomId) {
        ResponseEntity<String> response = null;
        Optional<Room> room = this.roomService.getRoomById(roomId);
        if (room.isPresent()) {
            String filename = room.get().getImageUrl().replaceFirst("/images/", "");
            this.roomService.updateRoom(roomId, null, null, null, null, null, "");
            Path path = Paths.get("C:/webAttempt/epicProject/uploads", filename);
            try {
                Files.deleteIfExists(path);
                response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deleted");
            } catch (IOException e) {
                response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deletion failed");
            }
        }
        return response;
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
