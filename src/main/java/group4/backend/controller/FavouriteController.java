package group4.backend.controller;

import group4.backend.service.FavouriteService;
import group4.backend.entities.Favourite;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/user/{username}")
    public List<Favourite> getFavouritesByUsername(@PathVariable String username) {
        return favouriteService.findAllByUsername(username);
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
