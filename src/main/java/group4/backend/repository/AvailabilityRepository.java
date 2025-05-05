package group4.backend.repository;

import group4.backend.entities.Availability;
import group4.backend.entities.Room;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AvailabilityRepository extends CrudRepository<Availability, Integer> {

  @Query("SELECT a FROM AvailableTime a WHERE a.room = :room " +
      "AND a.availableFrom <= :newTo AND a.availableTo >= :newFrom")
  Iterable<Availability> findOverlappingAvailability(
      @Param("room") Room room,
      @Param("newFrom") LocalDate newFrom,
      @Param("newTo") LocalDate newTo
  );
}
