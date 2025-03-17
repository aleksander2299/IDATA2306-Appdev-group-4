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
    private int roomProviderId;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false, foreignKey = @ForeignKey(name = "FK_provider_id"))
    private Provider provider;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false, foreignKey = @ForeignKey(name = "FK_room_id"))
    private Room room;

    @Column(name = "room_price")
    private int roomPrice;


    /**
     * returns RoomProviderId
     * @return the current RoomProviderId
     */
    public int getRoomProviderId() {
        return roomProviderId;
    }

    /**
     * sets RoomProviderId to a new RoomProviderId from parameter
     * @param roomProviderId the new RoomProviderId
     */
    public void setRoomProviderId(int roomProviderId) {
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
}
