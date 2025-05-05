package group4.backend.repository;

import group4.backend.entities.Booking;
import group4.backend.entities.Room;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Integer> {

  public Iterable<Booking> findByRoomProvider_Room(Room room);

  @Query("SELECT COUNT(b) FROM Booking b " +
      "WHERE b.room = :room " +
      "AND b.dateFrom <= :newTo " +
      "AND b.dateTo >= :newFrom")
  long countOverlappingBookings(
      @Param("room") Room room,
      @Param("newFrom") LocalDate newFrom,
      @Param("newTo") LocalDate newTo
  );
}
