package group4.backend.controller;

import group4.backend.entities.User;
import group4.backend.service.UserService;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A REST controller for managing user-related operations.
 * Provides endpoints to create, retrieve, update, and delete user data.
 * Supports role-based authorization for certain operations.
 * NOTE: Java documentation was generated with help from ai to make sure it follows java documentation guidelines.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

  UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Retrieves a user by their username.
   *
   * @param username The username of the user to be retrieved.
   * @return A {@link ResponseEntity} containing the user with an HTTP status of OK if found,
   * or a {@link ResponseEntity} with an HTTP status of NOT FOUND if the user does not exist.
   */
  @Operation(
          summary = "returns a user by username ",
          description = "gets a user from username ",
          responses = {
                  @ApiResponse(responseCode = "200", description = "user found and returned"),
                  @ApiResponse(responseCode = "404", description = "not found")
          }
  )
  @GetMapping("/{username}")
  public ResponseEntity<User> getUser(@PathVariable String username) {
    Optional<User> user = this.userService.getUser(username);
    ResponseEntity<User> response;
    response = user.map(value -> ResponseEntity.status(HttpStatus.OK).body(value))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    return response;
  }

  /**
   * Retrieves all users.
   *
   * @return A {@link ResponseEntity} containing an {@link Iterable} of {@link User} objects
   * with an HTTP status of OK.
   */

  @Operation(
          summary = "Get all users ",
          description = "gets all the users in the database and returns them as " +
                  "iterable of user",
          responses = {
                  @ApiResponse(responseCode = "200", description = "returned users"),
                  @ApiResponse(responseCode = "204", description = "no content")
          }
  )
  @GetMapping("/all")
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<Iterable<User>> getAll() {
    return ResponseEntity.status(HttpStatus.OK).body(this.userService.getAllUsers());
  }

  /**
   * Adds a new user to the system. Only users with the 'ADMIN' role are authorized to perform this operation.
   *
   * @param user The {@link User} object to be added. The user object should contain necessary details such as username, password, and role.
   * @return A {@link ResponseEntity} containing the added {@link User} object with an HTTP status of CREATED if the operation is successful;
   * otherwise, it returns the same {@link User} object with an HTTP status of FORBIDDEN if the user could not be added.
   */
  @Operation(
          summary = "creates a user ",
          description = "gets user from requestbody and saves it to database",
          responses = {
                  @ApiResponse(responseCode = "201", description = "user created"),
                  @ApiResponse(responseCode = "403", description = "expectations not met")
          }
  )
  @PreAuthorize("hasAnyRole('ADMIN')")
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

  /**
   * Deletes a user from the system. This operation is accessible to users with 'ADMIN' or 'USER' roles.
   * The deletion process verifies if the user exists and removes the corresponding record if valid.
   *
   * @param user The {@link User} object to be deleted. The user object must include
   * the username and a valid password for authentication.
   * @return A {@link ResponseEntity} containing:
   * - An HTTP status of OK with an empty body if the user is successfully deleted.
   * - An HTTP status of BAD_REQUEST if the user does not exist or could not be deleted.
   * - An HTTP status of I_AM_A_TEAPOT if the user still appears in the database after deletion.
   */
  @Operation(
          summary = "delete a user ",
          description = "finds user from requestbody data and deletes it",
          responses = {
                  @ApiResponse(responseCode = "200", description = "user deleted"),
                  @ApiResponse(responseCode = "403", description = "expectations not met")
          }
  )
  @PreAuthorize("hasAnyRole('ADMIN','USER')")
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

  /**
   * Updates the information of an existing user in the system.
   * This operation is accessible to users with 'ADMIN' or 'USER' roles.
   *
   * @param username The username of the user to be updated.
   * @param user The {@link User} object containing the updated details of the user.
   * @return A {@link ResponseEntity} containing:
   * - A message with an HTTP status of OK if the user is successfully found and updated.
   * - A message with an HTTP status of NOT FOUND if the user does not exist.
   */
  @Operation(
          summary = "update a user ",
          description = "finds user by username and updates with requestbody data",
          responses = {
                  @ApiResponse(responseCode = "200", description = "user updated"),
                  @ApiResponse(responseCode = "403", description = "expectations not met"),
                  @ApiResponse(responseCode = "404", description = "not found user ?")
          }
  )
  @PreAuthorize("hasAnyRole('ADMIN','USER')")
  @PutMapping("{username}")
  public ResponseEntity<String> updateUser(@PathVariable String username, @RequestBody User user) {
    ResponseEntity<String> response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("A user with that name was not found");
    if (this.userService.updateUser(username, user)) {
      response = ResponseEntity.status(HttpStatus.OK).body("Found and updated user: " + username);
    }
    return response;
  }
}
