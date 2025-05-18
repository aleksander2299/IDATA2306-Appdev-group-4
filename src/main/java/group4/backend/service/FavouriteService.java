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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for handling Favourite entity operations.
 */
@Service
public class FavouriteService {

    private final FavouriteRepository favouriteRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public FavouriteService(FavouriteRepository favouriteRepository, UserRepository userRepository, RoomRepository roomRepository) {
        this.favouriteRepository = favouriteRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
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
