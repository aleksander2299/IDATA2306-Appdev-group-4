package group4.backend.repository;

import group4.backend.entities.Booking;
import group4.backend.entities.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Integer> {

  public Iterable<Booking> findByRoom(Room room);
}
