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

@Entity
@Table(name="booking")
public class Booking {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer bookingId;

  @ManyToOne
  @JoinColumn(name = "room_provider", referencedColumnName = "room_provider_id", foreignKey = @ForeignKey(name = "FK_room_provider_id"))
  private RoomProvider roomProvider;

  @ManyToOne
  @JoinColumn(name = "username", referencedColumnName = "username", foreignKey = @ForeignKey(name = "FK_username"))
  private User user;

  @Column(name = "check_in_date")
  private LocalDate checkInDate;

  @Column(name = "check_out_date")
  private LocalDate checkOutDate;

  public Integer getBookingId() {
    return this.bookingId;
  }

  public RoomProvider getRoomProvider() {
    return this.roomProvider;
  }

  public User getUser() {
    return this.user;
  }

  public LocalDate getCheckInDate() {
    return this.checkInDate;
  }

  public LocalDate getCheckOutDate() {
    return this.checkOutDate;
  }

  public void setBookingId(Integer bookingId) {
    this.bookingId = bookingId;
  }

  public void setRoomProvider(RoomProvider roomProvider) {
    this.roomProvider = roomProvider;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void setCheckInDate(LocalDate checkInDate) {
    this.checkInDate = checkInDate;
  }

  public void setCheckOutDate(LocalDate checkOutDate) {
    this.checkOutDate = checkOutDate;
  }

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
