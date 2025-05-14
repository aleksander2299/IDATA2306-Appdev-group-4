package group4.backend.service;

import group4.backend.entities.Booking;
import group4.backend.entities.Room;
import group4.backend.entities.RoomProvider;
import group4.backend.repository.RoomProviderRepository;
import group4.backend.entities.User;
import group4.backend.repository.BookingRepository;
import group4.backend.repository.UserRepository;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

  private final BookingRepository bookingRepository;
  private final RoomProviderRepository roomProviderRepository;
  private final UserRepository userRepository;

  @Autowired
  public BookingService(BookingRepository bookingRepository, RoomProviderRepository roomProviderRepository, UserRepository userRepository) {
    this.bookingRepository = bookingRepository;
    this.roomProviderRepository = roomProviderRepository;
    this.userRepository = userRepository;
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

  public Iterable<Booking> getAllBookingsBelongingToUsername(String username) {
    if (username == null) {
      throw new IllegalArgumentException("No username provided.");
    }
    Iterable<Booking> bookings = this.bookingRepository.findByUser_Username(username);
    if (!bookings.iterator().hasNext()) {
      throw new NoSuchElementException("Could not find any bookings belonging to that username");
    }
    return bookings;
  }

  public boolean addBooking(Booking booking) {
    boolean added = false;
    if (canBeAdded(booking)) {
      this.bookingRepository.save(booking);
      added = true;
    }
    return added;
  }

  public Optional<Booking> addBookingUsingIds(Integer roomProviderId, String username, Booking booking) {
    Optional<Booking> optionalBooking = Optional.of(prepareBookingWithIds(roomProviderId, username, booking));

    addBooking(optionalBooking.get());

    return optionalBooking;
  }

  private boolean canBeAdded(Booking booking) {
    return (
        booking != null
        && booking.isValid()
        &&
            (this.bookingRepository.countOverlappingBookings(booking.getRoomProvider().getRoom(),
                booking.getCheckInDate(),
                booking.getCheckOutDate()
            ) == 0)
    );
  }

  private Booking prepareBookingWithIds(Integer roomProviderId, String username, Booking booking) {
    Optional<RoomProvider> roomProvider = this.roomProviderRepository.findById(roomProviderId);
    Optional<User> user = this.userRepository.findById(username);
    Booking preparedBooking = null;

    if (booking.getBookingId() == null &&
        booking.getCheckInDate() != null &&
        booking.getCheckOutDate() != null &&
        booking.getCheckInDate().isBefore(booking.getCheckOutDate())
    ) {
      if (roomProvider.isPresent() && user.isPresent()) {
        booking.setRoomProvider(roomProvider.get());
        booking.setUser(user.get());
        preparedBooking = booking;
      }
    } else {
      throw new IllegalArgumentException("A booking with a given id, no check-in date, no check-out date, or greater check-in than check-out date can be fixed and saved");
    }
    return preparedBooking;
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

  public boolean updateUser(Integer bookingId, RoomProvider roomProvider, User user, LocalDate checkInDate, LocalDate checkOutDate) {
    boolean altered = false;
    Optional<Booking> existingBookingOpt = this.bookingRepository.findById(bookingId);
    if (existingBookingOpt.isPresent()) {
      Booking existingBooking = existingBookingOpt.get();

      // Update the fields you want to change
      if (roomProvider != null) {
        existingBooking.setRoomProvider(roomProvider);
        altered = true;
      }
      if (user != null) {
        existingBooking.setUser(user);
        altered = true;
      }
      if (checkInDate != null) {
        existingBooking.setCheckInDate(checkInDate);
        altered = true;
      }
      if (checkOutDate != null) {
        existingBooking.setCheckOutDate(checkOutDate);
        altered = true;
      }
      if (altered) {
        this.bookingRepository.save(existingBooking);
      }
      // Save the updated entity
    }
    return altered;
  }
}
