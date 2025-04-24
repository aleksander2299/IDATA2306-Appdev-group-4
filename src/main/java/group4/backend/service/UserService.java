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

  public boolean deleteUserWithUsername(String username) {
    this.userRepository.deleteById(username);
    return !this.userRepository.existsById(username);
  }

  public boolean updateUser(String nameOfUserToChange, String newName, String password, Role role) {
    boolean updated = false;
    User user = userRepository.findById(nameOfUserToChange)
        .orElseThrow(() -> new EntityNotFoundException("User not found"));
    if (!newName.isBlank()) {
      user.setUsername(newName);
      updated = true;
    }
    if (!password.isBlank()) {
      user.setPassword(password);
      updated = true;
    }
    if (role != null) {
      user.setRole(role);
      updated = true;
    }
    return updated;
  }
}
