package group4.backend.entities;

import jakarta.persistence.*;
/**
 * class representing favourite table in our database.
 */
@Entity
@Table(name = "favourite")
public class Favourite {
//Temp
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favourite_id", nullable = false)
    private int favouriteId;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false, foreignKey = @ForeignKey(name = "user_id_fk"))
    private Room roomId;

    @ManyToOne
    @JoinColumn(name = "username", nullable = false, foreignKey = @ForeignKey(name = "username"))
    private User username;

    /**
     * gets favourite
     * @return the int favourite_id
     */
    public int getFavouriteId() {
        return favouriteId;
    }

    /**
     * sets favourite_id to new favourite_id
     * @param favouriteId the new favouriteId
     */
    public void setFavouriteId(int favouriteId) {
        this.favouriteId = favouriteId;
    }

    /**
     * gets room_id
     * @return the int room_id primary key
     */
    public Room getRoomId() {
        return roomId;
    }

    /**
     * sets room_id to new room_id
     * @param roomId the new roomId
     */
    public void setRoomId(Room roomId) {
        this.roomId = roomId;
    }

    /**
     * gets username
     * @return the string username
     */
    public User getUsername() {
        return username;
    }

    /**
     * sets username to new username
     * @param username the new username
     */
    public void setUsername(User username) {
        this.username = username;
    }
}
