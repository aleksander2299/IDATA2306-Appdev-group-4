package group4.backend.repository;

import group4.backend.entities.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface RoomRepository extends CrudRepository<Room,Integer> {}
