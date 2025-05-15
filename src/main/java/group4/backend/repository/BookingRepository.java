package group4.backend.repository;

import group4.backend.entities.Booking;
import group4.backend.entities.Room;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Integer> {

  public Iterable<Booking> findByUser_Username(String username);

  public Iterable<Booking> findByRoomProvider_Room(Room room);

  @Query("SELECT COUNT(b) FROM Booking b " +
      "WHERE b.roomProvider.room = :room " +
      "AND b.checkInDate <= :newTo " +
      "AND b.checkOutDate >= :newFrom")
  long countOverlappingBookings(
      @Param("room") Room room,
      @Param("newFrom") LocalDate newFrom,
      @Param("newTo") LocalDate newTo
  );

  @Query("""
    SELECT b.checkInDate, b.checkOutDate
    FROM Booking b
    JOIN b.roomProvider rp
    WHERE rp.room.roomId = :roomId
""")
  List<LocalDate[]> findRoomBookingDatesByRoomId(@Param("roomId") Integer roomId);
}
