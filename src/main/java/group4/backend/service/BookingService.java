package group4.backend.service;

import group4.backend.entities.Booking;
import group4.backend.entities.Room;
import group4.backend.entities.User;
import group4.backend.repository.BookingRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

  private final BookingRepository bookingRepository;

  @Autowired
  public BookingService(BookingRepository bookingRepository) {
    this.bookingRepository = bookingRepository;
  }

  public Iterable<Booking> getAllBookings() {
    return this.bookingRepository.findAll();
  }

  public Optional<Booking> getBooking(Integer id) {
    return this.bookingRepository.findById(id);
  }

  public Iterable<Booking> getAllBookingsByRoom(Room room) {
    return this.bookingRepository.findByRoomProvider_Room(room);
  }

  public boolean addBooking(Booking booking) {
    boolean added = false;
    if (canBeAdded(booking)) {
      this.bookingRepository.save(booking);
      added = true;
    }
    return added;
  }

  private boolean canBeAdded(Booking booking) {
    return (
        booking != null
        && booking.isValid()
    );
  }

  public boolean deleteBookingById(Integer id) {
    boolean deleted = false;
    Optional<Booking> bookingInDb = getBooking(id);
    if (bookingInDb.isPresent()) {
      this.bookingRepository.deleteById(id);
      deleted = true;
    }
    return deleted;
  }

  public boolean updateUser(Booking bookingToPut) {
    boolean updated = false;
    Optional<Booking> existingBookingOpt = this.bookingRepository.findById(
        bookingToPut.getBookingId());
    if (existingBookingOpt.isPresent()) {
      Booking existingBooking = existingBookingOpt.get();

      // Update the fields you want to change
      existingBooking.setRoomProvider(bookingToPut.getRoomProvider());
      existingBooking.setUser(bookingToPut.getUser());
      existingBooking.setCheckInDate(bookingToPut.getCheckInDate());
      existingBooking.setCheckOutDate(bookingToPut.getCheckOutDate());

      // Save the updated entity
      this.bookingRepository.save(existingBooking);
      updated = true;
    }
    return updated;
  }
}
