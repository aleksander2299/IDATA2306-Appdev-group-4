package group4.backend.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * class representing availability table in our database.
 */
@Entity
@Table(name = "availability")
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "availability_id", nullable = false)
    private int availabilityId;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false, foreignKey = @ForeignKey(name = "room_id_fk"))
    private Room roomId;

    @Column(name = "availability_from", nullable = false)
    private LocalDate availabilityFrom;

    @Column(name = "availability_to", nullable = false)
    private LocalDate availabilityTo;

    /**
     * gets availability
     * @return the int availability_id
     */
    public int getAvailabilityId() {
        return availabilityId;
    }

    /**
     * sets availability_id to new availability_id
     * @param availabilityId the new availabilityId
     */
    public void setAvailabilityId(int availabilityId) {
        this.availabilityId = availabilityId;
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
     * gets availability_from
     * @return the string availability_from
     */
    public LocalDate getAvailabilityFrom() {
        return availabilityFrom;
    }

    /**
     * sets availability_from to new availability_from
     * @param availabilityFrom the new availabilityFrom
     */
    public void setAvailabilityFrom(LocalDate availabilityFrom) {
        this.availabilityFrom = availabilityFrom;
    }

    /**
     * gets availability_to
     * @return the string availability_to
     */
    public LocalDate getAvailabilityTo() {
        return availabilityTo;
    }

    /**
     * sets availability_to to new availability_to
     * @param availabilityTo the new availabilityTo
     */
    public void setAvailabilityTo(LocalDate availabilityTo) {
        this.availabilityTo = availabilityTo;
    }



}
