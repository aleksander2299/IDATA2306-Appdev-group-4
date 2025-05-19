package group4.backend.controller;

import group4.backend.entities.Room;
import group4.backend.entities.RoomProvider;
import group4.backend.entities.Source;
import group4.backend.service.RoomService;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


/**
 * The RoomController is a REST controller responsible for handling
 * HTTP requests related to Room entities. This includes functionality for
 * retrieving, creating, updating, and deleting rooms. It communicates with
 * the {@link RoomService} to perform underlying operations.
 * NOTE: Java documentation was generated with help from ai to make sure it follows java documentation guidelines.
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

    /**
     * Retrieves the source associated with a specific room identified by its ID.
     *
     * @param id the ID of the room whose source is to be fetched
     * @return a ResponseEntity containing the Source object if found, or
     *         a 404 NOT FOUND response if the source is not available
     */
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
     * Retrieves the occupied date ranges for a room identified by its ID.
     *
     * @param roomId The ID of the room whose occupied dates are to be fetched.
     * @return A ResponseEntity containing a list of date ranges,
     *         where each date range is represented by an array of two LocalDate objects indicating
     *         the start and end dates (inclusive). Returns a 200 OK response with the list,
     *         400 BAD REQUEST if the roomId is invalid, 404 NOT FOUND if no such room exists,
     *         or other HTTP status codes as required.
     */
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
     * method for posting list of room to database
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

    /**
     * Updates the details of an existing room identified by its ID.
     *
     * @param roomId The ID of the room to be updated.
     * @param room   The room object containing updated information such as
     *               room name, source ID, description, visibility, room type,
     *               and image URL.
     * @return A ResponseEntity containing the updated Room object with a status
     *         of 200 OK upon successful update.
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable("id") int roomId,@RequestBody Room room){
        Room updatedRoom = roomService.updateRoom(roomId, room.getRoomName(),room.getSource().getSourceId(), room.getDescription(),
                room.isVisible(), room.getRoomType(), room.getImageUrl());

        return ResponseEntity.ok(updatedRoom);

    }

}
