package group4.backend.service;

import group4.backend.entities.Booking;
import group4.backend.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

  private final BookingRepository bookingRepository;

  @Autowired
  public BookingService(BookingRepository bookingRepository) {
    this.bookingRepository = bookingRepository;
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
        && this.bookingRepository.existsById(booking.getBookingId())
    );
  }

  public boolean deleteBookingById(Integer id) {
    boolean cannotBeFound = false;
    this.bookingRepository.deleteById(id);
    if (this.bookingRepository.existsById(id)) {
      cannotBeFound = true;
    }
    return cannotBeFound;
  }
}
