package group4.backend.service;

import group4.backend.entities.Role;
import group4.backend.entities.User;
import group4.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Iterable<User> getAllUsers() {
    return this.userRepository.findAll();
  }

  public Optional<User> getUser(String username) {
    return this.userRepository.findById(username);
  }

  public boolean addUser(User user) {
    boolean added = false;
    if (canBeAdded(user)) {
      this.userRepository.save(user);
      added = true;
    }
    return added;
  }

  public boolean canBeAdded(User user) {
    return user != null && user.isValid()
        && (this.userRepository.findById(user.getUsername()).isEmpty());
  }

  /**
   * returns true if deletion attempt goes through.
   *
   * @param user the user to delete
   * @return True if user can be deleted.
   */
  public boolean deleteUser(User user) {
    boolean delete = false;
    Optional<User> userInDb = getUser(user.getUsername());
    if (userInDb.isPresent() && user.getPassword().equals(userInDb.get().getPassword())) {
        this.userRepository.deleteById(user.getUsername());
        delete = true;
    }
    return delete;
  }

  public boolean updateUser(String nameOfUserToChange, User userToPut) {
    boolean updated = false;
    if (this.userRepository.findById(nameOfUserToChange).isPresent()) {
      this.userRepository.deleteById(nameOfUserToChange);
      this.userRepository.save(userToPut);
      updated = true;
    }
    return updated;
  }
}
