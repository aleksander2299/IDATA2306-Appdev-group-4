package group4.backend.service;

import group4.backend.entities.Role;
import group4.backend.entities.User;
import group4.backend.repository.UserRepository;
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

  public boolean deleteUserWithUsername(String username) {
    this.userRepository.deleteById(username);
    return !this.userRepository.existsById(username);
  }

  public boolean updateUser(User user) {
    boolean updated = false;
    if (user != null && this.userRepository.existsById(user.getUsername())) {
      this.userRepository.save(user);
      updated = true;
    }
    return updated;
  }
}
