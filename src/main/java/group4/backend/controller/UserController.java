package group4.backend.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

  UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/{username}")
  public ResponseEntity<User> getUser(@PathVariable String username) {
    Optional<User> user = this.userService.getUser(username);
    ResponseEntity<User> response;
    response = user.map(value -> ResponseEntity.status(HttpStatus.OK).body(value))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    return response;
  }

  @GetMapping("/all")
  public ResponseEntity<Iterable<User>> getAll() {
    return ResponseEntity.status(HttpStatus.OK).body(this.userService.getAllUsers());
  }

  @PostMapping
  public ResponseEntity<User> postUser(@RequestBody User user) {
    ResponseEntity<User> response;
    if (this.userService.addUser(user)) {
      response = ResponseEntity.status(HttpStatus.CREATED).body(user);
    } else {
      response = ResponseEntity.status(HttpStatus.FORBIDDEN).body(user);
    }
    return response;
  }

  @DeleteMapping
  public ResponseEntity<String> deleteUser(@RequestBody User user) {
    ResponseEntity<String> response;

    if (this.userService.deleteUser(user)) {
      if (this.userService.getUser(user.getUsername()).isPresent()) {
        response = ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("User, unexpectedly, is still present in database after deletion");
      } else {
        response = ResponseEntity.status(HttpStatus.OK).body("");
      }
    } else {
      response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Either user was not found or user could not be deleted");
    }

    return response;
  }

  @PutMapping("{username}")
  public ResponseEntity<String> updateUser(@PathVariable String username, @RequestBody User user) {
    ResponseEntity<String> response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("A user with that name was not found");
    if (this.userService.updateUser(username, user)) {
      response = ResponseEntity.status(HttpStatus.OK).body("Found and updated user: " + username);
    }
    return response;
  }
}
