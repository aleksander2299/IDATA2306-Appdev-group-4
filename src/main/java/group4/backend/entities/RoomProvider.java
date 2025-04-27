package group4.backend.entities;

import jakarta.persistence.*;

/**
 * class representing a room and provider relationship.
 * IE a room and a provider listing the room with a price
 */
@Entity
@Table(name = "room_provider")
public class RoomProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_provider_id", nullable = false)
    private Integer roomProviderId;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false, foreignKey = @ForeignKey(name = "FK_provider_id"))
    private Provider provider;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false, foreignKey = @ForeignKey(name = "FK_room_id"))
    private Room room;

    @Column(name = "room_price")
    private Integer roomPrice;


    /**
     * returns RoomProviderId
     * @return the current RoomProviderId
     */
    public Integer getRoomProviderId() {
        return roomProviderId;
    }

    /**
     * sets RoomProviderId to a new RoomProviderId from parameter
     * @param roomProviderId the new RoomProviderId
     */
    public void setRoomProviderId(Integer roomProviderId) {
        this.roomProviderId = roomProviderId;
    }

    /**
     * returns current RoomPrice
     * @return the currentRoomPrice int
     */
    public int getRoomPrice() {
        return roomPrice;
    }

    /**
     * sets roomPrice to new roomPrice in parameter
     * @param roomPrice the new roomPrice
     */
    public void setRoomPrice(int roomPrice) {
        this.roomPrice = roomPrice;
    }


    /**
     * gets the provider
     * @return the provider
     */
    public Provider getProvider() {
        return provider;
    }

    /**
     * sets the provider to a new provider
     * @param provider the new provider
     */
    public void setProvider(Provider provider) {
        this.provider = provider;
    }


    /**
     * gets the room
     * @return the room.
     */
    public Room getRoom() {
        return room;
    }

    /**
     * sets the room to new room in parameter
     * @param room the new room.
     */
    public void setRoom(Room room) {
        this.room = room;
    }



}
