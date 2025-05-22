package group4.backend.service;

import group4.backend.entities.User;
import group4.backend.repository.UserRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
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
      user.setPassword(passwordEncoder.encode(user.getPassword()));
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
    if (userInDb.isPresent() && passwordEncoder.matches(user.getPassword(), userInDb.get().getPassword())) {
        this.userRepository.deleteById(user.getUsername());
        delete = true;
    }
    return delete;
  }

  public User updateUser(String nameOfUserToChange, User userToPut) {
    if (this.userRepository.findById(nameOfUserToChange).isEmpty()) {
      throw new NoSuchElementException("There are no user with username" + nameOfUserToChange);
    }
    if (this.userRepository.findById(userToPut.getUsername()).isPresent()) {
      throw new IllegalArgumentException("Cannot change username");
    }

    userToPut.setPassword(passwordEncoder.encode(userToPut.getPassword()));
    this.userRepository.deleteById(nameOfUserToChange);
    this.userRepository.save(userToPut);

    return userToPut;
  }
}
