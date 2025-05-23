package group4.backend.dtos;

import group4.backend.entities.Room;
import group4.backend.entities.RoomProvider;

/**
 * Data Transfer Object (DTO) that combines a single Room with its associated RoomProvider.
 * <p>
 * Note: This documentation was generated with the assistance of AI.
 */
public class SingleRoomWithMultipleRoomProviderDTO {

  private Room room;
  private RoomProvider roomProvider;

  /**
   * Constructs a new SingleRoomWithMultipleRoomProviderDTO with the specified room and provider.
   *
   * @param room         The Room entity to be associated with this DTO
   * @param roomProvider The RoomProvider entity to be associated with this DTO
   */
  public SingleRoomWithMultipleRoomProviderDTO(Room room, RoomProvider roomProvider) {
    this.room = room;
    this.roomProvider = roomProvider;
  }

  /**
   * Gets the RoomProvider associated with this DTO.
   *
   * @return the RoomProvider entity
   */
  public RoomProvider getRoomProvider() {
    return roomProvider;
  }

  /**
   * Gets the Room associated with this DTO.
   *
   * @return the Room entity
   */
  public Room getRoom() {
    return room;
  }

  /**
   * Sets the RoomProvider for this DTO.
   *
   * @param roomProvider the RoomProvider entity to set
   */
  public void setRoomProvider(RoomProvider roomProvider) {
    this.roomProvider = roomProvider;
  }

  /**
   * Sets the Room for this DTO.
   *
   * @param room the Room entity to set
   */
  public void setRoom(Room room) {
    this.room = room;
  }
}
