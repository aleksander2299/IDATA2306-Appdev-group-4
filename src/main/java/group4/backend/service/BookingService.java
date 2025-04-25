package group4.backend.service;

import group4.backend.entities.Booking;
import group4.backend.entities.Room;
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

  public Iterable<Booking> getBookings() {
    return bookingRepository.findAll();
  }


  public Iterable<Booking> getBookingsWithRoom(Room room) {
    return bookingRepository.findByRoomProvider_Room(room);
  }
}
