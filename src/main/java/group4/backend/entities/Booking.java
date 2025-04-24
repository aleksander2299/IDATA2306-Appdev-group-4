package group4.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name="booking")
public class Booking {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int bookingId;

  @JoinColumn(name = "room_provider", referencedColumnName = "room_provider_id", foreignKey = @ForeignKey(name = "FK_room_provider_id"))
  private int roomProviderId;

  @JoinColumn(name = "room", referencedColumnName = "username", foreignKey = @ForeignKey(name = "FK_username"))
  private String username;

  @Column(name = "check_in_date")
  private LocalDate checkInDate;

  @Column(name = "check_out_date")
  private LocalDate checkOutDate;

  public int getBookingId() {
    return this.bookingId;
  }

  public int getRoomProviderId() {
    return this.roomProviderId;
  }

  public String getUsername() {
    return this.username;
  }

  public LocalDate getCheckInDate() {
    return this.checkInDate;
  }

  public LocalDate getCheckOutDate() {
    return this.checkOutDate;
  }

  public void setBookingId(int bookingId) {
    this.bookingId = bookingId;
  }

  public void setRoomProviderId(int roomProviderId) {
    this.roomProviderId = roomProviderId;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setCheckInDate(LocalDate checkInDate) {
    this.checkInDate = checkInDate;
  }

  public void setCheckOutDate(LocalDate checkOutDate) {
    this.checkOutDate = checkOutDate;
  }
}
