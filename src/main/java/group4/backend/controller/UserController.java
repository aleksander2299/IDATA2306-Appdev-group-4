package group4.backend.controller;

import group4.backend.entities.Booking;
import group4.backend.entities.User;
import group4.backend.service.UserService;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/user/{username}")
  public ResponseEntity<User> getUser(@PathVariable String username) {
    Optional<User> user = this.userService.getUser(username);
    ResponseEntity<User> response;
    response = user.map(value -> ResponseEntity.status(HttpStatus.OK).body(value))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    return response;
  }

  @GetMapping("/user")
  public ResponseEntity<Iterable<User>> getAll() {
    return ResponseEntity.status(HttpStatus.OK).body(this.userService.getAllUsers());
  }

  @PostMapping("/user")
  public ResponseEntity<User> postUser(@RequestBody User user) {
    ResponseEntity<User> response;
    if (this.userService.addUser(user)) {
      response = ResponseEntity.status(HttpStatus.OK).body(user);
    } else {
      response = ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(user);
    }
    return response;
  }

  @DeleteMapping("/user/{username}")
  public ResponseEntity<String> deleteUser(@PathVariable String username) {
    ResponseEntity<String> response;
    if (this.userService.getUser(username).isPresent()) {
      if (this.userService.deleteUserWithUsername(username)) {
        response = ResponseEntity.status(HttpStatus.OK).body("");
      } else {
        response = ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("");
      }
    } else {
      response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
    }

    return response;
  }

  @PutMapping
  public ResponseEntity<User> updateUser(User user) {
    ResponseEntity<User> response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    if (this.userService.updateUser(user)) {
      response = ResponseEntity.status(HttpStatus.OK).body(user);
    }
    return response;
  }
}
