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

@Service
public class RoomService {


  private final BookingRepository bookingRepository;
  private final RoomRepository roomRepository;
  private final RoomProviderRepository roomProviderRepository;
  private final SourceRepository sourceRepository;

  @Autowired
  public RoomService(BookingRepository bookingRepository, RoomRepository roomRepository,
                     RoomProviderRepository roomProviderRepository, SourceRepository sourceRepository) {
    this.bookingRepository = bookingRepository;
    this.roomRepository = roomRepository;
    this.roomProviderRepository = roomProviderRepository;
    this.sourceRepository = sourceRepository;
  }

  public List<Room> getAllRooms() {
    List<Room> rooms = new ArrayList<>();
    roomRepository.findAll().forEach(rooms::add);
    return rooms;
  }

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

  public Optional<Room> getRoomById(int id) {

    return roomRepository.findById(id);
  }

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

  public Room saveRoomWithSourceId(Integer sourceId, Room room) {

    Optional<Source> source = this.sourceRepository.findById(sourceId);


    source.ifPresentOrElse(room::setSource, () -> {
      throw new NoSuchElementException();
    });

    return roomRepository.save(room);
  }

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
