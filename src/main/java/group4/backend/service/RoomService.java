package group4.backend.service;


import group4.backend.entities.Room;
import group4.backend.entities.RoomProvider;
import group4.backend.entities.Source;
import group4.backend.repository.BookingRepository;
import group4.backend.repository.RoomProviderRepository;
import group4.backend.repository.RoomRepository;
import group4.backend.repository.SourceRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for managing room-related operations including CRUD operations,
 * room provider management, and room availability checks.
 * NOTE: Java documentation was generated with help from AI to make sure it follows Java documentation guidelines.
 */
@Service
public class RoomService {


  private final BookingRepository bookingRepository;
  private final RoomRepository roomRepository;
  private final RoomProviderRepository roomProviderRepository;
  private final SourceRepository sourceRepository;

  /**
   * Constructs a RoomService with required repositories.
   *
   * @param bookingRepository      Repository for managing booking operations
   * @param roomRepository         Repository for managing room operations
   * @param roomProviderRepository Repository for managing room provider operations
   * @param sourceRepository       Repository for managing source operations
   */
  @Autowired
  public RoomService(BookingRepository bookingRepository, RoomRepository roomRepository,
                     RoomProviderRepository roomProviderRepository, SourceRepository sourceRepository) {
    this.bookingRepository = bookingRepository;
    this.roomRepository = roomRepository;
    this.roomProviderRepository = roomProviderRepository;
    this.sourceRepository = sourceRepository;
  }

  /**
   * Retrieves all rooms from the database.
   *
   * @return A list containing all available rooms
   */
  public List<Room> getAllRooms() {
    List<Room> rooms = new ArrayList<>();
    roomRepository.findAll().forEach(rooms::add);
    return rooms;
  }

  /**
   * Retrieves all room providers associated with a specific room.
   *
   * @param id The ID of the room
   * @return An Iterable of RoomProvider objects associated with the room
   * @throws IllegalArgumentException if the room ID doesn't exist
   * @throws NoSuchElementException   if no providers are found for the room
   */
  public Iterable<RoomProvider> getRoomProviders(int id) {
    Optional<Room> room = this.roomRepository.findById(id);
    Iterable<RoomProvider> roomProviders = null;
    if (room.isPresent()) {
      roomProviders = this.roomProviderRepository.findByRoom(room.get());
    } else {
      throw new IllegalArgumentException("Id does not belong to any saved rooms");
    }
    if (!roomProviders.iterator().hasNext()) {
      throw new NoSuchElementException("There are no room provider for the given room");
    }
    return roomProviders;
  }

  /**
   * Retrieves a room by its ID.
   *
   * @param id The ID of the room to retrieve
   * @return An Optional containing the room if found, empty otherwise
   */
  public Optional<Room> getRoomById(int id) {

    return roomRepository.findById(id);
  }

  /**
   * Retrieves the occupied dates for a specific room.
   *
   * @param roomId The ID of the room to check
   * @return A list of date ranges (start and end dates) when the room is occupied
   * @throws IllegalArgumentException if roomId is null
   * @throws NoSuchElementException   if the room has no bookings
   */
  public List<LocalDate[]> getOccupiedRoomDates(Integer roomId) {
    if (roomId == null) {
      throw new IllegalArgumentException(
          "A number id must be given to find occupied dates belonging to a room");
    }

    List<LocalDate[]> rows = bookingRepository.findRoomBookingDatesByRoomId(roomId);

    if (rows.isEmpty()) {
      throw new NoSuchElementException("Room has no booked dates");
    }
    return rows;
  }

  /**
   * Saves a new room or updates an existing one.
   *
   * @param room The room entity to save
   * @return The saved room entity
   * @throws IllegalArgumentException if the specified source doesn't exist
   */
  public Room saveRoom(Room room) {

    if (room.getSource().getSourceId() != null) {
      Optional<Source> sourceOptional = sourceRepository.findById(room.getSource().getSourceId());
      if (sourceOptional.isEmpty()) {
        throw new IllegalArgumentException("Source does not exist");
      }
      room.setSource(sourceOptional.get());
    }
    return roomRepository.save(room);
  }

  /**
   * Saves a room with a specific source ID.
   *
   * @param sourceId The ID of the source to associate with the room
   * @param room     The room entity to save
   * @return The saved room entity
   * @throws NoSuchElementException if the source ID doesn't exist
   */
  public Room saveRoomWithSourceId(Integer sourceId, Room room) {

    Optional<Source> source = this.sourceRepository.findById(sourceId);


    source.ifPresentOrElse(room::setSource, () -> {
      throw new NoSuchElementException();
    });

    return roomRepository.save(room);
  }

  /**
   * Deletes a room by its ID.
   *
   * @param id The ID of the room to delete
   */
  public void deleteRoom(int id) {
    roomRepository.deleteById(id);
  }

  public void updateRoomImageUrl(Integer roomId, String url) {
    Room room = this.roomRepository.findById(roomId)
        .orElseThrow(() -> new IllegalArgumentException("Room not found"));
    room.setImageurl(url);
    this.roomRepository.save(room);
  }


  public void clearImageUrl(int roomId) {
    Room room = this.roomRepository.findById(roomId)
        .orElseThrow(() -> new IllegalArgumentException("Room not found"));
    room.setImageurl("");
    roomRepository.save(room);
  }


  /**
   * Updates an existing room's properties.
   *
   * @param roomId      The ID of the room to update
   * @param roomName    The new name for the room (optional)
   * @param sourceId    The new source ID for the room (optional)
   * @param description The new description for the room (optional)
   * @param visibility  The new visibility status for the room (optional)
   * @param roomType    The new room type (optional)
   * @param imageUrl    The new image URL for the room (optional)
   * @return The updated room entity
   * @throws NullPointerException     if the room doesn't exist
   * @throws IllegalArgumentException if the specified source doesn't exist
   */
  public Room updateRoom(int roomId, String roomName, Integer sourceId, String description,
                         Boolean visibility, String roomType, String imageUrl) {

    Optional<Room> roomOptional = roomRepository.findById(roomId);
    if (roomOptional.isEmpty()) {
      throw new NullPointerException("room does not exist");
    }
    Room room = roomOptional.get();

    if (sourceId != null) {
      Optional<Source> sourceOptional = sourceRepository.findById(sourceId);
      if (sourceOptional.isEmpty()) {
        throw new IllegalArgumentException("Source does not exist");
      }
      room.setSource(sourceOptional.get());
    }

    if (roomName != null) {
      room.setRoomName(roomName);
    }
    if (description != null) {
      room.setDescription(description);
    }
    if (visibility != null) {
      room.setVisibility(visibility);
    }
    if (roomType != null) {
      room.setRoomType(roomType);
    }
    if (imageUrl != null) {
      room.setImageurl(imageUrl);
    }

    return roomRepository.save(room);
  }

}
