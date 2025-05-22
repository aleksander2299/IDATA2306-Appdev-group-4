package group4.backend.service;

import group4.backend.entities.Booking;
import group4.backend.entities.Room;
import group4.backend.entities.RoomProvider;
import group4.backend.repository.RoomProviderRepository;
import group4.backend.entities.User;
import group4.backend.repository.BookingRepository;
import group4.backend.repository.UserRepository;
import java.time.LocalDate;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

/**
 * Service class for managing booking-related operations in the system.
 * Handles creation, retrieval, update, and deletion of bookings.
 * Provides business logic for booking validation and management.
 * NOTE: Java documentation was generated with help from AI to make sure it follows java documentation guidelines.
 */
@Service
public class BookingService {

  private final BookingRepository bookingRepository;
  private final RoomProviderRepository roomProviderRepository;
  private final UserRepository userRepository;

  /**
   * Constructs a new BookingService with the required repositories.
   *
   * @param bookingRepository      Repository for booking operations
   * @param roomProviderRepository Repository for room provider operations
   * @param userRepository         Repository for user operations
   */
  @Autowired
  public BookingService(BookingRepository bookingRepository, RoomProviderRepository roomProviderRepository, UserRepository userRepository) {
    this.bookingRepository = bookingRepository;
    this.roomProviderRepository = roomProviderRepository;
    this.userRepository = userRepository;
  }

  /**
   * Retrieves all bookings from the system.
   *
   * @return An Iterable containing all booking records
   */
  public Iterable<Booking> getAllBookings() {
    return this.bookingRepository.findAll();
  }

  /**
   * Retrieves a specific booking by its ID.
   *
   * @param id The ID of the booking to retrieve
   * @return An Optional containing the booking if found, empty otherwise
   */
  public Optional<Booking> getBooking(Integer id) {
    return this.bookingRepository.findById(id);
  }

  /**
   * Retrieves all bookings for a specific room.
   *
   * @param room The room to find bookings for
   * @return An Iterable containing all bookings for the specified room
   */
  public Iterable<Booking> getAllBookingsByRoom(Room room) {
    return this.bookingRepository.findByRoomProvider_Room(room);
  }

  /**
   * Retrieves all bookings associated with a specific username.
   *
   * @param username The username to find bookings for
   * @return An Iterable containing all bookings for the specified username
   * @throws IllegalArgumentException if username is null
   * @throws NoSuchElementException   if no bookings are found for the username
   */
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

  /**
   * Adds a new booking to the system if it meets all validity criteria.
   *
   * @param booking The booking to be added
   * @return true if the booking was successfully added, false otherwise
   */
  public boolean addBooking(Booking booking) {
    boolean added = false;
    if (canBeAdded(booking)) {
      this.bookingRepository.save(booking);
      added = true;
    }
    return added;
  }

  /**
   * Creates and adds a new booking using provided IDs and booking details.
   *
   * @param roomProviderId The ID of the room provider
   * @param username       The username of the user making the booking
   * @param booking        The booking details to be added
   * @return An Optional containing the created booking if successful
   * @throws IllegalArgumentException if the booking parameters are invalid
   */
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

  /**
   * Deletes a booking by its ID.
   *
   * @param id The ID of the booking to delete
   * @return true if the booking was successfully deleted, false if not found
   */
  public boolean deleteBookingById(Integer id) {
    boolean deleted = false;
    Optional<Booking> bookingInDb = getBooking(id);
    if (bookingInDb.isPresent()) {
      this.bookingRepository.deleteById(id);
      deleted = true;
    }
    return deleted;
  }

  /**
   * Updates an existing booking with new information.
   *
   * @param bookingId    The ID of the booking to update
   * @param roomProvider The new room provider (optional)
   * @param user         The new user (optional)
   * @param checkInDate  The new check-in date (optional)
   * @param checkOutDate The new check-out date (optional)
   * @return true if any changes were made and saved, false otherwise
   */
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

  /**
   * Checks if the currently authenticated user is the owner of the booking.
   * Simplified with explicit if statements.
   *
   * @param authentication The current authentication object.
   * @param bookingId      The ID of the booking to check.
   * @return true if the user is the owner, false otherwise.
   * NOTE: AI was used to help fully generate this authentication
   */
  public boolean isBookingOwner(Authentication authentication, Integer bookingId) {
    if (authentication == null || bookingId == null ||
            !authentication.isAuthenticated()) {
      return false;
    }

    Optional<Booking> booking = this.bookingRepository.findById(bookingId);
    if (booking.isEmpty()) {
      return false;
    }
    Booking bookingObject = booking.get();
    if (bookingObject.getUser() != null &&
            bookingObject.getUser().getUsername() != null) {
      return bookingObject.getUser().getUsername().equals(authentication.getName());
    }
    return false;
  }
}
