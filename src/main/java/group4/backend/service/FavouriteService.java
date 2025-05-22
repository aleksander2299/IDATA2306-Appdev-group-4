package group4.backend.service;


import group4.backend.customExceptions.ExpectedDeletedEntityException;
import group4.backend.dtos.FavouriteWithOnlyIds;
import group4.backend.entities.Favourite;
import group4.backend.entities.Room;
import group4.backend.entities.User;
import group4.backend.repository.FavouriteRepository;
import group4.backend.repository.RoomRepository;
import group4.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing Favourite entity operations in the application.
 * Provides methods for creating, reading, updating, and deleting favourites,
 * as well as managing relationships between users and their favourite rooms.
 *
 * NOTE: Java documentation was generated with help from AI to ensure it follows
 * Java documentation guidelines.
 */
@Service
public class FavouriteService {

    private final FavouriteRepository favouriteRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;


    /**
     * Constructs a new FavouriteService with required repositories.
     *
     * @param favouriteRepository Repository for Favourite entity operations
     * @param userRepository      Repository for User entity operations
     * @param roomRepository      Repository for Room entity operations
     */
    @Autowired
    public FavouriteService(FavouriteRepository favouriteRepository, UserRepository userRepository, RoomRepository roomRepository) {
        this.favouriteRepository = favouriteRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    /**
     * Checks if the currently authenticated user is the owner of the favourite.
     * (This is the method called from @PreAuthorize)
     * @param authentication The current authentication object.
     * @param favouriteId    The ID of the favourite to check.
     * @return true if the user is the owner, false otherwise.
     * NOTE: Made with help from AI
     */
    public boolean isOwner(Authentication authentication, Integer favouriteId) {
        if (authentication == null || !authentication.isAuthenticated() || favouriteId == null) {
            return false;
        }
        String currentUsername = authentication.getName();
        if (currentUsername == null || currentUsername.isEmpty()) { return false; }

        Optional<Favourite> favouriteOptional = this.favouriteRepository.findById(favouriteId);
        if (favouriteOptional.isEmpty()) {
            return false;
        }
        Favourite favouriteObject = favouriteOptional.get();
        if (favouriteObject.getUser() != null && favouriteObject.getUser().getUsername() != null) {
            return currentUsername.equals(favouriteObject.getUser().getUsername());
        }
        return false;
    }

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


    /**
     * Retrieves a Favourite by its ID.
     *
     * @param id The ID of the Favourite to retrieve
     * @return An Optional containing the found Favourite, or empty if not found
     * @throws IllegalArgumentException if the provided ID is null
     */
    public Optional<Favourite> findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("No Favourite ID provided.");
        }
        return favouriteRepository.findById(id);
    }



    /**
     * Retrieves all Favourites matching the provided IDs.
     *
     * @param ids Collection of Favourite IDs to retrieve
     * @return List of found Favourites
     * @throws EmptyResultDataAccessException if no Favourites are found for the given IDs
     */
    public List<Favourite> findAllById(Iterable<Integer> ids) {
        List<Favourite> favourites = new ArrayList<>();
        favouriteRepository.findAllById(ids).forEach(favourites::add);

        if (favourites.isEmpty()) {
            throw new EmptyResultDataAccessException("No Favourites found for given IDs", 1);
        }
        return favourites;
    }

    /**
     * Retrieves all Favourites associated with a specific username.
     *
     * @param username The username whose favourites are to be retrieved
     * @return Iterable collection of Favourites belonging to the user
     * @throws IllegalArgumentException if the username is null
     * @throws EntityNotFoundException  if the user is not found
     * @throws NoSuchElementException   if no favourites are found for the user
     */
    public Iterable<Favourite> findAllByUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("No username provided.");
        }

        // fetch the User object by username
        Optional<User> user = userRepository.findById(username);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User not found with username: " + username);
        }

        List<Favourite> userFavourites = favouriteRepository.findAllByUser(user.get());
        if (userFavourites.isEmpty()) {
            throw new NoSuchElementException("No Favourites found for user: " + username);
        }
        return userFavourites;
    }

    /**
     * Creates a new Favourite using only IDs from a DTO object.
     * This method validates the input, retrieves the associated User and Room entities,
     * and creates a new Favourite entity.
     *
     * @param basicFavourite DTO containing the favourite's ID, room ID, and username
     * @return The saved Favourite entity
     * @throws IllegalArgumentException if the DTO is null or contains invalid data
     * @throws NoSuchElementException   if the referenced room or user is not found
     */
    public Favourite saveFavouriteWithOnlyIds(FavouriteWithOnlyIds basicFavourite) {
        if (basicFavourite == null) {
            throw new IllegalArgumentException("Null was passed as favourite DTO when trying to post favourite");
        }
        if (basicFavourite.getRoomId() == null) {
            throw new IllegalArgumentException("DTO has room id: null");
        }
        if (basicFavourite.getUsername().isBlank()) {
            throw new IllegalArgumentException("DTO has username: null or blank");
        }
        Optional<Room> room = this.roomRepository.findById(basicFavourite.getRoomId());
        if (room.isEmpty()) {
            throw new NoSuchElementException("Passed room id was not found in database");
        }
        Optional<User> user = this.userRepository.findByUsername(basicFavourite.getUsername());
        if (user.isEmpty()) {
            throw new NoSuchElementException("Passed username was not found in database");
        }
        Favourite favourite = new Favourite(basicFavourite.getFavouriteId(), room.get(), user.get());
        this.favouriteRepository.save(favourite);

        return favourite;
    }

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
        if (this.favouriteRepository.findById(id).isPresent()) {
            throw new ExpectedDeletedEntityException("The favourite object meant to be deleted was found after repository deletion method");
        }
    }

    public void deleteFavouriteWithOnlyIds(FavouriteWithOnlyIds basicFavourite) {
        if (basicFavourite == null) {
            throw new IllegalArgumentException("Null was passed as favourite DTO when trying to post favourite");
        }
        if (basicFavourite.getRoomId() == null) {
            throw new IllegalArgumentException("DTO has room id: null");
        }
        if (basicFavourite.getUsername().isBlank()) {
            throw new IllegalArgumentException("DTO has username: null or blank");
        }
        Optional<Room> room = this.roomRepository.findById(basicFavourite.getRoomId());
        if (room.isEmpty()) {
            throw new NoSuchElementException("Passed room id was not found in database");
        }
        Optional<User> user = this.userRepository.findByUsername(basicFavourite.getUsername());
        if (user.isEmpty()) {
            throw new NoSuchElementException("Passed username was not found in database");
        }


        List<Favourite> favourites = this.favouriteRepository.findAllByUserAndRoom(user.get(), room.get());
        if (favourites.isEmpty()) {
            throw new NoSuchElementException("Found no favourites of that room with that user");
        }
        for (Favourite favourite : favourites) {
            this.favouriteRepository.delete(favourite);
        }
        if (!this.favouriteRepository.findAllByUserAndRoom(user.get(), room.get()).isEmpty()) {
            throw new ExpectedDeletedEntityException("Supposedly deleted favourite is found after deletion");
        }
    }

    /**
     * Delete all Favourites in the table.
     */
    public void deleteAll() {
        favouriteRepository.deleteAll();
    }
}
