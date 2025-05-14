package group4.backend.controller;

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
 * Controller class for handling Favourite endpoints.
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
     * Find all Favourites
     *
     * @return an iterable of favourites.
     */
    @GetMapping
    public Iterable<Favourite> getAllFavourites() {
        return favouriteService.findAll();
    }

    /**
     * Find a Favourite by ID
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Optional<Favourite> getFavouriteById(@PathVariable Integer id) {
        return favouriteService.findById(id);
    }

    /**
     * Find all favourites by username
     * @param username
     * @return
     */
    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/user/{username}")
    public ResponseEntity<Iterable<Room>> getFavouriteRoomsByUsername(@PathVariable String username) {
        ResponseEntity<Iterable<Room>> response = ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
        Iterable<Room> favouriteRooms = null;
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
            response = ResponseEntity.status(HttpStatus.FOUND).body(favouriteRooms);
        }
        return response;
    }


    /**
     * Create a new Favourite
     * @param favourite
     * @return
     */
    @PostMapping
    public Favourite createFavourite(@RequestBody Favourite favourite) {
        return favouriteService.saveFavourite(favourite);
    }

    /**
     * Delete a Favourite by ID
     * @param id
     */
    @DeleteMapping("/{id}")
    public void deleteFavouriteById(@PathVariable Integer id) {
        favouriteService.deleteById(id);
    }
}
