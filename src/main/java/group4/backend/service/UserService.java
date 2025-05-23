package group4.backend.service;

import group4.backend.entities.User;
import group4.backend.repository.UserRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service layer for managing user-related operations. Provides functionality to perform
 * CRUD operations on User entities. Encodes user passwords before storing them
 * and ensures validation of user details before performing operations.
 *
 * This class interacts with UserRepository for database access and PasswordEncoder
 * for encoding passwords.
 * Note: This documentation was generated with the assistance of AI.
 */
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

  /**
   * Adds a new user to the repository if the user meets the necessary requirements and does not
   * already exist in the system. The user's password is encoded before being saved.
   *
   * @param user the user to be added to the repository
   * @return true if the user was successfully added, false otherwise
   */
  public boolean addUser(User user) {
    boolean added = false;
    if (canBeAdded(user)) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      this.userRepository.save(user);
      added = true;
    }
    return added;
  }

  /**
   * Determines if a user can be added to the repository by validating the user's properties
   * and ensuring the username does not already exist in the repository.
   *
   * @param user the user to check for addition
   * @return true if the user is valid, not null, and does not already exist in the repository; false otherwise
   */
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

  /**
   * Updates the details of an existing user in the repository.
   * Ensures that the username of the user cannot be modified and that the new password is encoded before saving.
   *
   * @param nameOfUserToChange the username of the user whose details are to be updated
   * @param userToPut the updated user object containing the new details
   * @return the updated user object after successful alteration
   * @throws NoSuchElementException if the user with the given username does not exist
   * @throws IllegalArgumentException if attempting to change the username to an existing username
   */
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
