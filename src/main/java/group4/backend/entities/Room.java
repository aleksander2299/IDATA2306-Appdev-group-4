package group4.backend.entities;


import jakarta.persistence.*;

import java.util.List;

/**
 * class representing room table in our database. responsible for holding room: name, id, description, type etc
 */
@Entity
@Table(name = "room")
public class Room {



    /**
     * set to auto increment for now. primary key roomId
     */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private int roomId;  // Primary Key

    @ManyToOne
    @JoinColumn(name = "source_id", referencedColumnName = "source_id", foreignKey = @ForeignKey(name = "FK_source_id"))
    private Source source;




    @OneToMany(mappedBy = "room")
    private List<RoomProvider> roomProviders;

    @Column(name = "room_name", nullable = false)
    private String roomName;

    @Column(name = "description")
    private String description;

    @Column(name = "visibility", nullable = false)
    private boolean visibility = true;

    @Column(name = "room_type", nullable = false)
    private String roomType;

    @Column(name = "image_url")
    private String imageurl;




    public Room(){}

    /**
     * returns source id
     * @return int source id
     */
    public Source getSource() { return this.source; }


    /**
     * sets the source id to a new source id
     * @param source the source id
     */
    public void setSource(Source source) {this.source = source; }


    /**
     * gets the room id;
     * @return the roomId
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     * sets the roomId to param int
     * @param roomId the roomId to set it to
     */
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    /**
     * gets the room name of this instance
     * @return String of roomname
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * sets roomname to param string
     * @param roomName the new room name
     */
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    /**
     * gets the description and returns as string
     * @return the description returned as string
     */
    public String getDescription() {
        return description;
    }

    /**
     * sets the description to param string
     * @param description the new description of the room
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * returns the visibility status of the room, so if the room should be visible on the website or not
     * @return the visibility boolean of the room(default is true on creation).
     */
    public boolean isVisible() {
        return visibility;
    }

    /**
     * sets visibility to true or false depending on param
     * @param visibility the new status of visibility of room
     */
    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    /**
     * returns room type
     * @return string room type
     */
    public String getRoomType() {
        return roomType;
    }

    /**
     * sets the room type to a new room type
     * @param roomType the new room type
     */
    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }


    public void setImageurl(String imageUrl) {
        this.imageurl = imageUrl;
    }


    public String getImageUrl(){
        return this.imageurl;
    }
}
