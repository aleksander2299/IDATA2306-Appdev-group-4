package group4.backend.dtos;

import group4.backend.entities.Room;
import group4.backend.entities.RoomProvider;

public class RoomWithRoomProviderDTO {

  private Room room;
  private RoomProvider roomProvider;

  public RoomWithRoomProviderDTO(Room room, RoomProvider roomProvider) {
    this.room = room;
    this.roomProvider = roomProvider;
  }

  public RoomProvider getRoomProvider() {
    return roomProvider;
  }

  public Room getRoom() {
    return room;
  }

  public void setRoomProvider(RoomProvider roomProvider) {
    this.roomProvider = roomProvider;
  }

  public void setRoom(Room room) {
    this.room = room;
  }
}
