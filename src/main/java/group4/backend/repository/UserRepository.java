package group4.backend.repository;

import group4.backend.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {


    Optional<User> findByUsername(String userName);
}
