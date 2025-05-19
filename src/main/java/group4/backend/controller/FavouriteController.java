package group4.backend.controller;

import group4.backend.customExceptions.ExpectedDeletedEntityException;
import group4.backend.dtos.FavouriteWithOnlyIds;
import group4.backend.entities.Room;
import group4.backend.service.FavouriteService;
import group4.backend.entities.Favourite;
import jakarta.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * This controller handles operations related to "Favourite" entities.
 * It provides endpoints for retrieving, creating, and deleting Favourites.
 * NOTE: Java documentation was generated with help from ai to make sure it follows java documentation guidelines.
 */
@RestController
@RequestMapping("/api/favourite")
public class FavouriteController {

    private final FavouriteService favouriteService;
    Logger logger = LoggerFactory.getLogger(FavouriteController.class);

    @Autowired
    public FavouriteController(FavouriteService favouriteService) {
        this.favouriteService = favouriteService;
    }

    /**
     * Retrieves all Favourite entities from the database.
     *
     * @return an Iterable containing all Favourite entities
     */
    @GetMapping
    public Iterable<Favourite> getAllFavourites() {
        return favouriteService.findAll();
    }

    /**
     * Retrieves a Favourite entity by its ID.
     *
     * @param id the ID of the Favourite to be retrieved
     * @return an Optional containing the Favourite entity if found, or an empty Optional if no entity is found
     */
    @GetMapping("/{id}")
    public Optional<Favourite> getFavouriteById(@PathVariable Integer id) {
        return favouriteService.findById(id);
    }

    /**
     * Retrieves the list of favourite rooms associated with a specific username.
     * Handles potential exceptions for invalid input, entity not found, or server errors.
     *
     * @param username the username of the user whose favourite rooms are to be retrieved
     * @return a ResponseEntity containing an Iterable of Favourite entities if found,
     *         or an appropriate HTTP status in case of errors
     */
    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/user/{username}")
    public ResponseEntity<Iterable<Favourite>> getFavouriteRoomsByUsername(@PathVariable String username) {
        ResponseEntity<Iterable<Favourite>> response = ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
        Iterable<Favourite> favouriteRooms = null;
        try {
            favouriteRooms = favouriteService.findAllByUsername(username);
        } catch (IllegalArgumentException iAe){
            logger.error(iAe.getMessage());
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (EntityNotFoundException | NoSuchElementException nFe) {
            logger.error(nFe.getMessage());
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
      if (favouriteRooms != null) {
            response = ResponseEntity.status(HttpStatus.OK).body(favouriteRooms);
        }
        return response;
    }


    /**
     * Creates a new Favourite entity.
     *
     * @param favourite the Favourite entity to be created
     * @return the created Favourite entity
     */
    @PostMapping
    public Favourite createFavourite(@RequestBody Favourite favourite) {
        return favouriteService.saveFavourite(favourite);
    }

    /**
     * Creates a new Favourite entity based on provided IDs.
     * Handles exceptions related to invalid input or missing required entities.
     *
     * @param basicFavourite the DTO containing only IDs required to create a Favourite entity
     * @return a ResponseEntity containing the created Favourite entity with HTTP status CREATED upon success,
     *         BAD_REQUEST in case of invalid input, NOT_FOUND if an entity is missing, or INTERNAL_SERVER_ERROR for other errors
     */
    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/withIds")
    public ResponseEntity<Favourite> createFavouriteWithIds(@RequestBody FavouriteWithOnlyIds basicFavourite) {
        ResponseEntity<Favourite> response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        Favourite favourite = null;
        try {
            favourite = this.favouriteService.saveFavouriteWithOnlyIds(basicFavourite);
            response = ResponseEntity.status(HttpStatus.CREATED).body(favourite);
        } catch (IllegalArgumentException iAe) {
            logger.error(iAe.getMessage());
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (NoSuchElementException nSeE) {
            logger.error(nSeE.getMessage());
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

    /**
     * Deletes a Favourite entity by its ID.
     * Handles exceptions related to invalid input or expected issues during the deletion process.
     *
     * @param id the ID of the Favourite entity to be deleted
     * @return a ResponseEntity containing the ID of the deleted Favourite entity with HTTP status NO_CONTENT upon success,
     *         BAD_REQUEST if the input is invalid, EXPECTATION_FAILED for expected issues during deletion,
     *         or other appropriate HTTP status codes in case of errors
     */
    @PreAuthorize("hasAnyRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteFavouriteById(@PathVariable Integer id) {
        ResponseEntity<Integer> response = ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
        Integer idOfDeletedFavourite =  null;
        try {
            favouriteService.deleteById(id);
            idOfDeletedFavourite = id;
        } catch (IllegalArgumentException iAe) {
            logger.error(iAe.getMessage());
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (ExpectedDeletedEntityException eDeE) {
            logger.error(eDeE.getMessage());
            response = ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
        if (idOfDeletedFavourite != null) {
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body(idOfDeletedFavourite);
        }
        return response;
    }

    /**
     * Deletes a Favourite entity based on the given IDs.
     * Handles exceptions related to invalid input or expected issues during the deletion process.
     *
     * @param basicFavourite the DTO containing only the IDs required to delete a Favourite entity
     * @return a ResponseEntity containing a confirmation message "Deleted" with HTTP status NO_CONTENT upon success,
     *         BAD_REQUEST if the input is invalid, EXPECTATION_FAILED for expected issues during deletion,
     *         or other appropriate HTTP status codes in case of errors
     */
    @PreAuthorize("hasAnyRole('USER')")
    @DeleteMapping("/withIds")
    public ResponseEntity<String> deleteFavouriteWithIds(@RequestBody FavouriteWithOnlyIds basicFavourite) {
        ResponseEntity<String> response = ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
        try {
            favouriteService.deleteFavouriteWithOnlyIds(basicFavourite);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deleted");
        } catch (IllegalArgumentException iAe) {
            logger.error(iAe.getMessage());
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (ExpectedDeletedEntityException eDeE) {
            logger.error(eDeE.getMessage());
            response = ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
        return response;
    }
}
