package group4.backend.service;


import group4.backend.entities.Room;
import group4.backend.entities.Source;
import group4.backend.repository.RoomRepository;
import group4.backend.repository.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {


    private RoomRepository roomRepository;


    @Autowired
    private SourceRepository sourceRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms() {
            List<Room> rooms = new ArrayList<>();
        roomRepository.findAll().forEach(room -> rooms.add((Room) room));
        return rooms;
    }

    public Optional<Room> getRoomById(int id) {
        return roomRepository.findById(id);
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

    public void deleteRoom(int id) {
        roomRepository.deleteById(id);
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

        if (roomName != null) room.setRoomName(roomName);
        if (description != null) room.setDescription(description);
        if (visibility != null) room.setVisibility(visibility);
        if (roomType != null) room.setRoomType(roomType);
        if (imageUrl != null) room.setImageurl(imageUrl);

        return roomRepository.save(room);
    }

}
