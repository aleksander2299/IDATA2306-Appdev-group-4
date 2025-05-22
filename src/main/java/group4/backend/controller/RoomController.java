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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
 * Controller for managing room-related operations. Provides endpoints for retrieving, creating, updating,
 * and deleting rooms, as well as handling associated data like room providers, sources, occupied dates,
 * and images.
 */
@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;
    private final String imageURL;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
        //This path matches the directory used on the server for images.
        //In development, it must be changed to fit an existing directory.
        this.imageURL = "/home/local/shared/WebDeployer4001/website/uploads";
    }


    /**
     * gets a room at /api/rooms/id
     * @param id the id of the room to get
     * @return ResponseEntity of room
     */
    @Operation(
            summary = "Get room by ID",
            description = "Retrieves a room by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Room found and returned"),
                    @ApiResponse(responseCode = "404", description = "Room not found")
            }
    )
    @PreAuthorize("permitAll")
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
    @Operation(
            summary = "Get a rooms source",
            description = "Retrieves a rooms source by roomId",
            responses = {
                    @ApiResponse(responseCode = "200", description = "source found and returned"),
                    @ApiResponse(responseCode = "404", description = "source not found")
            }
    )
    @PreAuthorize("permitAll")
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
    @Operation(
            summary = "Gets roomProviders for a room",
            description = "Retrieves a Iterable of roomProviders from a rooms id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "list of roomProviders found and returned"),
                    @ApiResponse(responseCode = "403",description = "expectations not met")
            }
    )
    @PreAuthorize("permitAll")
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
    @Operation(
            summary = "Get all rooms in database",
            description = "gets all rooms in the database and returns a list of them",
            responses = {
                    @ApiResponse(responseCode = "200", description = "rooms found and returned"),
                    @ApiResponse(responseCode = "204", description = "no rooms in list")
            }
    )
    @PreAuthorize("permitAll")
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
     * @param roomId The ID of the room whose occupied dates are to be fetched.
     * @return A ResponseEntity containing a list of date ranges,
     *         where each date range is represented by an array of two LocalDate objects indicating
     *         the start and end dates (inclusive). Returns a 200 OK response with the list,
     *         400 BAD REQUEST if the roomId is invalid, 404 NOT FOUND if no such room exists,
     *         or other HTTP status codes as required.
     */
    @Operation(
            summary = "Get all the dates the room is booked, i.e its occupied dates",
            description = "returns a list of localDates, the dates the room is occupied",
            responses = {
                    @ApiResponse(responseCode = "200", description = "list of dates returned"),
            }
    )
    @PreAuthorize("permitAll")
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
    @Operation(
            summary = "creates a room",
            description = "Creates and saves room to database based on requestBody of room data",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Room created and saved"),
                    @ApiResponse(responseCode = "403", description = "expectations not med")
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        Room newRoom = roomService.saveRoom(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRoom);
    }

    @Operation(
            summary = "creates a room with a source linked by sourceId ",
            description = "sourceID is used to find a source and if found creates a room from requestBody " +
                    "and saves it with source linked",
            responses = {
                    @ApiResponse(responseCode = "200", description = "created and saved room with source"),
                    @ApiResponse(responseCode = "403", description = "expectations not met")
            }
    )
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
    @Operation(
            summary = "Creates rooms in bulk from list",
            description = "requestBody of list of room is used to create rooms ",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successfully created rooms from list"),
                    @ApiResponse(responseCode = "403", description = "expectations not met")
            }
    )
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
    @Operation(
            summary = "uploads image to room",
            description = "Uploads image to room from roomID with multipartfile",
            responses = {
                    @ApiResponse(responseCode = "201", description = "successfully uploaded image"),
                    @ApiResponse(responseCode = "403", description = "Expectations not met")
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'PROVIDER')")
    @PostMapping("/{roomId}/images")
    public ResponseEntity<String> uploadImageToRoom(@PathVariable Integer roomId, @RequestParam MultipartFile file) {
        ResponseEntity<String> response = null;
        String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
        Path uploadPath = Paths.get(this.imageURL, filename);

        try(InputStream input = file.getInputStream()) {
            Files.copy(input, uploadPath, StandardCopyOption.REPLACE_EXISTING);
            this.roomService.updateRoomImageUrl(roomId, "/images/" + filename);
            response = ResponseEntity.status(HttpStatus.CREATED).body(filename);
        } catch (IOException ioE) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return response;
    }


    /**
     * This method was created with AI assistance
     * @param roomId
     * @param file
     * @return
     */
    @Operation(
        summary = "uploads image",
        description = "Uploads image with multipartfile",
        responses = {
            @ApiResponse(responseCode = "201", description = "successfully uploaded image"),
            @ApiResponse(responseCode = "403", description = "Expectations not met")
        }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'PROVIDER')")
    @PostMapping("/images")
    public ResponseEntity<String> uploadImage(@RequestParam MultipartFile file) {
        ResponseEntity<String> response = null;
        String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
        Path uploadPath = Paths.get(this.imageURL, filename);

        try(InputStream input = file.getInputStream()) {
            Files.copy(input, uploadPath, StandardCopyOption.REPLACE_EXISTING);
            response = ResponseEntity.status(HttpStatus.CREATED).body(filename);
        } catch (IOException ioE) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return response;
    }


    @Operation(
            summary = "delete image from /images",
            description = "deletes image from images",
            responses = {
                    @ApiResponse(responseCode = "200", description = "deleted image"),
                    @ApiResponse(responseCode = "403", description = "expectations not met")
            }
    )


    @PreAuthorize("hasAnyRole('ADMIN', 'PROVIDER')")
    @DeleteMapping("/images/{filename}")
    public ResponseEntity<String> deleteImage(@PathVariable String filename) {
        ResponseEntity<String> response = null;
        Path path = Paths.get(this.imageURL, filename);
        try {
            Files.deleteIfExists(path);
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Deleted");
        } catch (IOException e) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deletion failed");
        }
        return response;
    }

    @Operation(
            summary = "delete image from room",
            description = "deletes image from room found from roomId",
            responses = {
                    @ApiResponse(responseCode = "200", description = "deleted image"),
                    @ApiResponse(responseCode = "403", description = "expectations not met")
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'PROVIDER')")
    @DeleteMapping("/images/room/{roomId}")
    public ResponseEntity<String> deleteImage(@PathVariable Integer roomId) {
        ResponseEntity<String> response = null;
        Optional<Room> room = this.roomService.getRoomById(roomId);
        if (room.isPresent()) {
            String filename = room.get().getImageUrl().replaceFirst("/images/", "");
            this.roomService.clearImageUrl(roomId);
            Path path = Paths.get(this.imageURL, filename);
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
    @Operation(
            summary = "Delete room from database",
            description = "finds room from roomid in database and deletes it",
            responses = {
                    @ApiResponse(responseCode = "200", description = "deleted room"),
                    @ApiResponse(responseCode = "403", description = "expectations not met"),
                    @ApiResponse(responseCode = "204", description = "room not found")

            }
    )
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


    @Operation(
            summary = "delete all rooms",
            description = "deletes all rooms",
            responses = {
                    @ApiResponse(responseCode = "200", description = "all rooms deleted"),
                    @ApiResponse(responseCode = "403", description = "expectations not met")
            }
    )
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
    @Operation(
            summary = "update room",
            description = "update room by finding from roomid and updates to requestbody room",
            responses = {
                    @ApiResponse(responseCode = "200", description = "updated room"),
                    @ApiResponse(responseCode = "403", description = "expectations not met")
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable("id") int roomId,@RequestBody Room room){
        Room updatedRoom = roomService.updateRoom(roomId, room.getRoomName(),room.getSource().getSourceId(), room.getDescription(),
                room.getVisibility(), room.getRoomType(), room.getImageUrl());

        return ResponseEntity.ok(updatedRoom);

    }

    /**
     * Searches for rooms that match the given query.
     *
     * @param query The search query to filter the rooms. It should not be null or empty.
     * @return A {@link ResponseEntity} containing a list of rooms that match the query, or a response with no content if no rooms are found.
     * NOTE: This class was made with AI assistance
     */
    @Operation(
            summary = "search rooms",
            description = "search rooms by query",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Matching rooms found and returned"),
                    @ApiResponse(responseCode = "204", description = "No rooms match the query")
            }
    )
    @PreAuthorize("permitAll")
    @GetMapping("/search")
    public ResponseEntity<List<Room>> searchRooms(@RequestParam("query") String query) {
        if (query == null || query.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<Room> rooms = roomService.searchRoomsByQuery(query);
        if (rooms.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(rooms);
    }

}
