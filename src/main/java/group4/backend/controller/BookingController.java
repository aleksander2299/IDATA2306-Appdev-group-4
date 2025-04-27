package group4.backend.controller;

import group4.backend.entities.Booking;
import group4.backend.entities.Room;
import group4.backend.entities.User;
import group4.backend.service.BookingService;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booking")
public class BookingController {

  private static final Logger logger = LoggerFactory.getLogger(BookingController.class);
  BookingService bookingService;


  public BookingController(BookingService bookingService) {
    this.bookingService = bookingService;
  }

  @GetMapping("/all")
  public ResponseEntity<Iterable<Booking>> getAll() {
    logger.info("Getting all bookings");
    return ResponseEntity.status(HttpStatus.OK).body(bookingService.getAllBookings());
  }

  @GetMapping("/{bookingId}")
  public ResponseEntity<Booking> getBooking(@PathVariable Integer bookingId) {
    ResponseEntity<Booking> response;
    Optional<Booking> booking = this.bookingService.getBooking(bookingId);
    response = booking.map(value -> ResponseEntity.status(HttpStatus.OK).body(value))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    return response;
  }

  @GetMapping("/room")
  public ResponseEntity<Iterable<Booking>> getWithRoom(@RequestBody Room room) {
    logger.info("Getting all the bookings with room {}", room.getRoomName());
    return ResponseEntity.status(HttpStatus.OK).body(bookingService.getAllBookingsByRoom(room));
  }

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

  @PutMapping()
  public ResponseEntity<String> updateBooking(@RequestBody Booking booking) {
    ResponseEntity<String> response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("A booking with that name was not found");
    if (this.bookingService.updateUser(booking)) {
      response = ResponseEntity.status(HttpStatus.OK).body("Found and updated booking: " + booking.getBookingId());
    }
    return response;
  }
}
