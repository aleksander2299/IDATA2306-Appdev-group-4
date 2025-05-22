package group4.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

/**
 * Entity class representing a booking in the system.
 * This class manages the relationship between users and room providers,
 * handling check-in and check-out dates for room reservations.
 * <p>
 * Note: This documentation was generated with the assistance of AI.
 */
@Entity
@Table(name="booking")
public class Booking {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer bookingId;

  /**
   * The room provider associated with this booking.
   */
  @ManyToOne
  @JoinColumn(name = "room_provider", referencedColumnName = "room_provider_id", foreignKey = @ForeignKey(name = "FK_room_provider_id"))
  private RoomProvider roomProvider;

  /**
   * The user who made the booking.
   */
  @ManyToOne
  @JoinColumn(name = "username", referencedColumnName = "username", foreignKey = @ForeignKey(name = "FK_username"))
  private User user;

  /**
   * The check-in date for the booking.
   */
  @Column(name = "check_in_date")
  private LocalDate checkInDate;

  /**
   * The check-out date for the booking.
   */
  @Column(name = "check_out_date")
  private LocalDate checkOutDate;

  /**
   * Gets the booking ID.
   *
   * @return the booking ID
   */
  public Integer getBookingId() {
    return this.bookingId;
  }

  /**
   * Gets the room provider associated with this booking.
   *
   * @return the room provider
   */
  public RoomProvider getRoomProvider() {
    return this.roomProvider;
  }

  /**
   * Gets the user who made the booking.
   *
   * @return the user
   */
  public User getUser() {
    return this.user;
  }

  /**
   * Gets the check-in date.
   *
   * @return the check-in date
   */
  public LocalDate getCheckInDate() {
    return this.checkInDate;
  }

  /**
   * Gets the check-out date.
   *
   * @return the check-out date
   */
  public LocalDate getCheckOutDate() {
    return this.checkOutDate;
  }

  /**
   * Sets the booking ID.
   *
   * @param bookingId the booking ID to set
   */
  public void setBookingId(Integer bookingId) {
    this.bookingId = bookingId;
  }

  /**
   * Sets the room provider.
   *
   * @param roomProvider the room provider to set
   */
  public void setRoomProvider(RoomProvider roomProvider) {
    this.roomProvider = roomProvider;
  }

  /**
   * Sets the user.
   *
   * @param user the user to set
   */
  public void setUser(User user) {
    this.user = user;
  }

  /**
   * Sets the check-in date.
   *
   * @param checkInDate the check-in date to set
   */
  public void setCheckInDate(LocalDate checkInDate) {
    this.checkInDate = checkInDate;
  }

  /**
   * Sets the check-out date.
   *
   * @param checkOutDate the check-out date to set
   */
  public void setCheckOutDate(LocalDate checkOutDate) {
    this.checkOutDate = checkOutDate;
  }

  /**
   * Validates if the booking is valid.
   * A valid booking must have no ID (new booking), a user, a room provider,
   * check-in and check-out dates, and the check-in date must be before the check-out date.
   *
   * @return true if the booking is valid, false otherwise
   */
  public boolean isValid() {
    return (
        this.bookingId == null
        && this.user != null
        && this.roomProvider != null
        && this.checkInDate != null
        && this.checkOutDate != null
        && this.checkInDate.isBefore(this.checkOutDate)
    );
  }
}
