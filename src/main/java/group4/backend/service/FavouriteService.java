package Service;

import Repository.FavouriteRepository;
//TODO: Uncomment the following import statement when UserRepository is available
//import Repository.UserRepository;
import group4.backend.entities.Favourite;
import group4.backend.entities.Room;
import group4.backend.entities.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for handling Favourite entity operations.
 */
@Service
public class FavouriteService {

    @Autowired
    private FavouriteRepository favouriteRepository;

    //TODO: Uncomment the following line when UserRepository is available
    //@Autowired
    //private UserRepository userRepository;

    /**
     * Find all Favourites in the table.
     * @return List of Favourites
     */
    public List<Favourite> findAll() {
        List<Favourite> favourites = new ArrayList<>();
        favouriteRepository.findAll().forEach(favourites::add);

        if (favourites.isEmpty()) {
            throw new EmptyResultDataAccessException("No Favourites found", 1);
        }
        return favourites;
    }

    // Example: find a Favourite by ID
    public Optional<Favourite> findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("No Favourite ID provided.");
        }
        return favouriteRepository.findById(id);
    }

    // Example: find all Favourites by a list of IDs
    public List<Favourite> findAllById(Iterable<Integer> ids) {
        List<Favourite> favourites = new ArrayList<>();
        favouriteRepository.findAllById(ids).forEach(favourites::add);

        if (favourites.isEmpty()) {
            throw new EmptyResultDataAccessException("No Favourites found for given IDs", 1);
        }
        return favourites;
    }

    /**
     * TODO: Uncomment the following method when UserRepository is available
     * Find all Favourites by username.
     * @param username
     * @return
     */
    /**
    public List<Favourite> findAllByUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("No username provided.");
        }

        // fetch the User object by username
        User user = userRepository.findById(username);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User not found with username: " + username);
        }



        List<Favourite> userFavourites = favouriteRepository.findAllByUsername(user);
        if (userFavourites.isEmpty()) {
            throw new EmptyResultDataAccessException("No Favourites found for user: " + username);
        }
        return userFavourites;
    }
     */

    /**
     * Save/Create a Favourite.
     * @param favourite
     * @return
     */
    public Favourite saveFavourite(Favourite favourite) {
        if (favourite == null) {
            throw new IllegalArgumentException("Cannot save null Favourite.");
        }
        return favouriteRepository.save(favourite);
    }

    /**
     * Delete a Favourite.
     * @param favourite
     */
    public void delete(Favourite favourite) {
        if (favourite == null) {
            throw new IllegalArgumentException("Favourite is null.");
        }
        favouriteRepository.delete(favourite);
    }

    /**
     * Delete a Favourite by ID.
     * @param id
     */
    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("No Favourite ID provided.");
        }
        favouriteRepository.deleteById(id);
    }

    /**
     * Delete all Favourites in the table.
     */
    public void deleteAll() {
        favouriteRepository.deleteAll();
    }
}
