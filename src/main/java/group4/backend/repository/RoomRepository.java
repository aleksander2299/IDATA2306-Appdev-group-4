package group4.backend.repository;

import group4.backend.entities.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface RoomRepository extends CrudRepository<Room,Integer> {
    /**
     * Searches for rooms where the keyword matches (case-insensitive) against
     * room name, source name (hotel name), source city, or source country.
     *
     * @param keyword The search keyword (should be passed in lowercase by the service).
     * @return A list of matching rooms.
     * NOTE: This class was made almost entirely by AI for the query logic.
     */
    @Query("SELECT r FROM Room r LEFT JOIN r.source s " +
            "WHERE LOWER(r.roomName) LIKE %:keyword% " +
            "OR LOWER(s.sourceName) LIKE %:keyword% " +   // Hotel/Property Name
            "OR LOWER(s.city) LIKE %:keyword% " +         // City
            "OR LOWER(s.country) LIKE %:keyword%")        // Country
    List<Room> searchByKeyword(@Param("keyword") String keyword);
}
