package group4.backend.repository;

import group4.backend.entities.Booking;
import group4.backend.entities.Room;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Booking entities in the database.
 * This interface provides methods for querying and managing booking records.
 * <p>
 * Note: This documentation was generated with the assistance of AI.
 */
@Repository
public interface BookingRepository extends CrudRepository<Booking, Integer> {

  /**
   * Retrieves all bookings associated with a specific username.
   *
   * @param username The username of the user whose bookings are to be retrieved
   * @return An Iterable of Booking entities matching the given username
   */
  public Iterable<Booking> findByUser_Username(String username);

  /**
   * Finds all bookings for a specific room.
   *
   * @param room The Room entity to search bookings for
   * @return An Iterable of Booking entities associated with the given room
   */
  public Iterable<Booking> findByRoomProvider_Room(Room room);

  /**
   * Counts the number of bookings that overlap with a given date range for a specific room.
   *
   * @param room    The Room entity to check for overlapping bookings
   * @param newFrom The start date of the period to check
   * @param newTo   The end date of the period to check
   * @return The number of overlapping bookings found
   */
  @Query("SELECT COUNT(b) FROM Booking b " +
      "WHERE b.roomProvider.room = :room " +
      "AND b.checkInDate <= :newTo " +
      "AND b.checkOutDate >= :newFrom")
  long countOverlappingBookings(
      @Param("room") Room room,
      @Param("newFrom") LocalDate newFrom,
      @Param("newTo") LocalDate newTo
  );

  /**
   * Retrieves all check-in and check-out dates for bookings of a specific room.
   *
   * @param roomId The ID of the room to get booking dates for
   * @return A List of LocalDate arrays where each array contains [checkInDate, checkOutDate]
   */
  @Query("""
    SELECT b.checkInDate, b.checkOutDate
    FROM Booking b
    JOIN b.roomProvider rp
    WHERE rp.room.roomId = :roomId
""")
  List<LocalDate[]> findRoomBookingDatesByRoomId(@Param("roomId") Integer roomId);
}
