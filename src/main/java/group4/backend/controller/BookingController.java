package group4.backend.controller;

import group4.backend.entities.Booking;
import group4.backend.entities.Room;
import group4.backend.entities.RoomProvider;
import group4.backend.entities.User;
import group4.backend.service.BookingService;

import java.time.LocalDate;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The BookingController class is a REST controller for handling API requests related to booking operations,
 * such as retrieving, creating, updating, and deleting bookings. It communicates with the BookingService
 * for the business logic.
 * NOTE: Java documentation was generated with help from ai to make sure it follows guidelines.
 */
@RestController
@RequestMapping("/api/booking")
public class BookingController {

  private static final Logger logger = LoggerFactory.getLogger(BookingController.class);
  BookingService bookingService;


  public BookingController(BookingService bookingService) {
    this.bookingService = bookingService;
  }

  /**
   * Returns all bookings.
   * @return A ResponseEntity containing an iterable of all bookings and HTTP status OK.
   */
  @GetMapping("/all")
  public ResponseEntity<Iterable<Booking>> getAll() {
    logger.info("Getting all bookings");
    return ResponseEntity.status(HttpStatus.OK).body(bookingService.getAllBookings());
  }

  /**
   * Returns a specific booking by its ID.
   * @param bookingId The ID of the booking to be retrived.
   * @return A ResponseEntity containing the booking with a (HTTP status OK)
   *         or a (HTTP status NOT_FOUND) if it is not found.
   */
  @GetMapping("/{bookingId}")
  public ResponseEntity<Booking> getBooking(@PathVariable Integer bookingId) {
    ResponseEntity<Booking> response;
    Optional<Booking> booking = this.bookingService.getBooking(bookingId);
    response = booking.map(value -> ResponseEntity.status(HttpStatus.OK).body(value))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    return response;
  }

  /**
   * Retrives all booking connected with a specific room
   * @param room The room entity to find bookings
   * @return A ResponseEntity containing an iterable array of bookings for the room and a (HTTP status OK)
   */
  @GetMapping("/room")
  public ResponseEntity<Iterable<Booking>> getWithRoom(@RequestBody Room room) {
    logger.info("Getting all the bookings with room {}", room.getRoomName());
    return ResponseEntity.status(HttpStatus.OK).body(bookingService.getAllBookingsByRoom(room));
  }

  /**
   * Retrives all booking connected with a specific username
   * @param username The username connected to the bookings
   * @return A ResponseEntity containing an iterable array of bookings for the user and a (HTTP status OK)
   */
  @GetMapping("/user/{username}")
  public ResponseEntity<Iterable<Booking>> getWithUserId(@PathVariable String username) {
    logger.info("Getting all the bookings with room id: {}", username);
    ResponseEntity<Iterable<Booking>> response = null;
    Iterable<Booking> bookings = null;
    try {
      bookings = this.bookingService.getAllBookingsBelongingToUsername(username);
    } catch (IllegalArgumentException iAe) {
      logger.error(iAe.getMessage());
      response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    } catch (NoSuchElementException nSeE) {
      logger.error(nSeE.getMessage());
      response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    if (bookings != null) {
      response = ResponseEntity.status(HttpStatus.OK).body(bookings);
    }
    return response;
  }

  /**
   * Creates a new booking.
   *
   * @param booking The booking entity containing the details of the booking to be created.
   * @return A ResponseEntity containing the created booking with HTTP status CREATED if successful,
   *         or a ResponseEntity with HTTP status FORBIDDEN if the booking could not be added.
   */
  @PostMapping
  public ResponseEntity<Booking> postBooking(@RequestBody Booking booking) {
    ResponseEntity<Booking> response;
    if (this.bookingService.addBooking(booking)) {
      response = ResponseEntity.status(HttpStatus.CREATED).body(booking);
    } else {
      response = ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }
    return response;
  }

  /**
   * Creates a new booking using the specified room provider ID, username, and booking details.
   * This method requires the user to have 'ADMIN' or 'USER' roles.
   *
   * @param roomProviderId The ID of the room provider associated with the booking.
   * @param username The username of the user making the booking.
   * @param booking The booking entity containing the details of the booking to be created.
   * @return A ResponseEntity containing the created booking with HTTP status CREATED if successful,
   *         or a ResponseEntity with HTTP status FORBIDDEN if the booking could not be added.
   */
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  @PostMapping("/withIds/{roomProviderId}/{username}")
  public ResponseEntity<Booking> postBooking(@PathVariable Integer roomProviderId, @PathVariable String username, @RequestBody Booking booking) {
    ResponseEntity<Booking> response;
    Optional<Booking> optionalBooking = this.bookingService.addBookingUsingIds(roomProviderId, username, booking);
    response = optionalBooking.map(value -> ResponseEntity.status(HttpStatus.CREATED).body(value))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    return response;
  }

  /**
   * Deletes a booking identified by its ID.
   *
   * @param bookingId The ID of the booking to be deleted.
   * @return A ResponseEntity containing:
   *         - An empty body with HTTP status OK if the booking is successfully deleted.
   *         - A string message with HTTP status I_AM_A_TEAPOT if the booking is still unexpectedly present after attempted deletion.
   *         - A string message with HTTP status BAD_REQUEST if the booking was not found or could not be deleted.
   */
  @DeleteMapping("/{bookingId}")
  public ResponseEntity<String> deleteBooking(@PathVariable Integer bookingId) {
    ResponseEntity<String> response;

    if (this.bookingService.deleteBookingById(bookingId)) {
      if (this.bookingService.getBooking(bookingId).isPresent()) {
        response = ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("Booking, unexpectedly, is still present in database after deletion");
      } else {
        response = ResponseEntity.status(HttpStatus.OK).body("");
      }
    } else {
      response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Either booking was not found or user could not be deleted");
    }
    return response;
  }

  /**
   * Updates an existing booking with the provided information. Only the fields provided will be updated.
   * If the booking is found and successfully updated, an HTTP 200 (OK) response is returned.
   * Otherwise, an HTTP 404 (NOT FOUND) response is returned if the booking does not exist.
   *
   * @param bookingId The ID of the booking to be updated. This parameter is required.
   * @param roomProvider The new RoomProvider entity to associate with the booking. This parameter is optional.
   * @param user The User entity to associate with the booking. This parameter is optional.
   * @param checkInDate The new check-in date for the booking. This parameter is optional.
   * @param checkOutDate The new check-out date for the booking. This parameter is optional.
   * @return A ResponseEntity containing a success message with HTTP status OK
   *         if the booking was updated, or a failure message with HTTP status NOT FOUND if no booking
   *         with the specified ID exists.
   */
  @PutMapping()
  public ResponseEntity<String> updateBooking(
      @RequestParam(required = true) Integer bookingId,
      @RequestParam(required = false) RoomProvider roomProvider,
      @RequestParam(required = false) User user,
      @RequestParam(required = false) LocalDate checkInDate,
      @RequestParam(required = false) LocalDate checkOutDate) {

    ResponseEntity<String> response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("A booking with that name was not found");
    if (this.bookingService.updateUser(bookingId, roomProvider, user, checkInDate, checkOutDate)) {
      response = ResponseEntity.status(HttpStatus.OK).body("Found and updated booking: " + bookingId);
    }
    return response;
  }
}
