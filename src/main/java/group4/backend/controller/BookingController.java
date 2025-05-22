package group4.backend.controller;

import group4.backend.entities.Booking;
import group4.backend.entities.Room;
import group4.backend.entities.RoomProvider;
import group4.backend.entities.User;
import group4.backend.service.BookingService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
 * NOTE: Java documentation was generated with help from ai to make sure it follows java documentation guidelines.
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
  @Operation(
          summary = "Get all bookings",
          description = "Fetches all bookings from bookingservice",
          responses = {
                  @ApiResponse(responseCode = "200", description = "List of bookings (empty of no bookings found)")
          }
  )
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
  @Operation(
          summary = "Get booking by booking ID",
          description = "Fetches a booking based on booking id from parameter",
          responses = {
                  @ApiResponse(responseCode = "200", description = "A booking instance"),
                  @ApiResponse(responseCode = "404", description = "No booking instance found")
          }
  )
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
  @Operation(
          summary = "Get all bookings for a specific room",
          description = "Fetches all bookings for a room and return an iterable of said bookings",
          responses = {
                  @ApiResponse(responseCode = "200", description = "An iterable of the bookings for the room" +
                          " an empty iterable in the case there is no bookings for that room")
          }
  )
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
  @Operation(
          summary = "Get all bookings a specific user has ",
          description = "Fetches all bookings for a single user and returns it as an iterable",
          responses = {
                  @ApiResponse(responseCode = "200", description = "An iterable of the bookings for the user" +
                          " an empty iterable in the case there is no bookings for that room")
          }
  )
  @GetMapping("/user/{username}")
  @PreAuthorize("hasAnyRole('ADMIN') or #username == authentication.name")
  public ResponseEntity<Iterable<Booking>> getWithUserId(@PathVariable String username) {
    logger.info("Getting all the bookings with room id: {}", username);
    ResponseEntity<Iterable<Booking>> response;
    Iterable<Booking> bookings;
    try {
      bookings = this.bookingService.getAllBookingsBelongingToUsername(username);
      response = ResponseEntity.ok(bookings);
    } catch (IllegalArgumentException iAe) {
      logger.error(iAe.getMessage());
      response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.emptyList());
    } catch (NoSuchElementException nSeE) {
      logger.error(nSeE.getMessage());
      response = ResponseEntity.ok(Collections.emptyList());
    } catch (Exception e) {
      response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
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
  @Operation(
          summary = "Post a booking ",
          description = "Send a requestbody of booking as parameter and it will then post the booking to the repository",
          responses = {
                  @ApiResponse(responseCode = "201", description = "The booking has been successfully posted"),
                  @ApiResponse(responseCode = "403", description = "The booking wasnt created and saved for various reasons I.E(security, wrong request body")
          }
  )
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
  @Operation(
          summary = "Post a booking ",
          description = "This method unlike the other post method posts a booking based on pathvariables of roomproviderid and username, and requestbody of booking",
          responses = {
                  @ApiResponse(responseCode = "201", description = "The booking has been successfully posted"),
                  @ApiResponse(responseCode = "403", description = "The booking wasnt created and saved for various reasons I.E(security, wrong request body")
          }
  )
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
  @Operation(
          summary = "Delete a booking by ID",
          description = "Deletes a booking with the given booking ID if it exists.",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Booking successfully deleted"),
                  @ApiResponse(responseCode = "400", description = "Booking was not found or could not be deleted")
          }
  )
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
  @Operation(
          summary = "Update an existing booking",
          description = "Updates the booking with the provided booking ID. Optional parameters (roomProvider, user, check-in and check-out dates) can be supplied to update specific fields. Only provided fields will be updated.",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Booking was successfully found and updated"),
                  @ApiResponse(responseCode = "404", description = "Booking with the given ID was not found")
          }
  )

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
