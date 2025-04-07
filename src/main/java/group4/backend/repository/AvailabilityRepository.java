package Repository;

import group4.backend.entities.Availability;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for Availability entity.
 * This interface extends CrudRepository to provide CRUD operations.
 * AI was used to help generate the Query.
 */
@Repository
public interface AvailabilityRepository extends CrudRepository<Availability, Integer> {

    @Override
    Iterable<Availability> findAll();

    /**
     * Finds all available rooms within a given date range.
     * For a specific room, it will return all available dates.
     * @param fromDate
     * @param toDate
     * @return List of available rooms
     * TODO: Figure out why roomId is set up as a.roomId.roomId
     */
    @Query("SELECT a FROM Availability a " +
            "WHERE a.roomId.roomId = :roomId " +
            "AND a.availabilityFrom >= :fromDate " +
            "AND a.availabilityTo <= :toDate")
    List<Availability> findRoomByAvailability(@Param("roomId") int roomId, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);
}
