package group4.backend.controller;

import group4.backend.entities.Booking;
import group4.backend.entities.Room;
import group4.backend.service.BookingService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
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

  @GetMapping("s")
  public Iterable<Booking> getAll() {
    logger.info("Getting all bookings");
    return bookingService.getBookings();
  }

  @GetMapping
  public Iterable<Booking> getWithRoom(@RequestBody Room room) {
    logger.info("Getting all the bookings with room {}", room.getRoomName());
    return bookingService.getBookingsWithRoom(room);
  }
}
