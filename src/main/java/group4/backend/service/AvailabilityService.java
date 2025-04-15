package group4.backend.service;


import group4.backend.entities.Availability;
import group4.backend.repository.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for handling Availability entity operations.
 *
 * AI was used to help create the findAvailabilitiesForRoomInDateRange method.
 */
@Service
public class AvailabilityService {

    @Autowired
    private AvailabilityRepository availabilityRepository;

    /**
     * Find all Availabilities in the table.
     * @return List of Availabilities
     */
    public List<Availability> findAll() {
        List<Availability> list = new ArrayList<>();
        for (Availability availability : availabilityRepository.findAll()) {
            list.add(availability);
        }
        if (list.isEmpty()) {
            throw new EmptyResultDataAccessException("No Availabilities found", 1);
        }
        return list;
    }

    /**
     * Find all Availabilities in the table with a given ID.
     * @param id
     * @return List of Availabilities
     */
    public Optional<Availability> findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("No Availability ID provided.");
        }
        return availabilityRepository.findById(id);
    }

    /**
     * Saves the given Availability object to the database.
     * @param availability
     * @return the saved Availability object
     */
    public Availability save(Availability availability) {
        if (availability == null) {
            throw new IllegalArgumentException("Cannot save null Availability.");
        }
        return availabilityRepository.save(availability);
    }

    /**
     * Deletes the Availability object with the given ID from the database.
     * @param id
     */
    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("No Availability ID provided.");
        }
        availabilityRepository.deleteById(id);
    }

    /**
     * Deletes all Availability objects from the database.
     */
    public void deleteAll() {
        availabilityRepository.deleteAll();
    }

    /**
     * Finds availabilities that are available within the specified date range.
     * For a specific room, it will return all available dates.
     * @param roomId   the ID of the room to check availabilities for
     * @param fromDate the start date of the range (inclusive)
     * @param toDate   the end date of the range (inclusive)
     * @return a list of Availability objects that meet the criteria.
     */
    public List<Availability> findAvailabilitiesForRoomInDateRange(int roomId, LocalDate fromDate, LocalDate toDate) {
        if (fromDate == null || toDate == null) {
            throw new IllegalArgumentException("Both fromDate and toDate must be provided.");
        }
        List<Availability> results = availabilityRepository.findRoomByAvailability(roomId, fromDate, toDate);
        if (results.isEmpty()) {
            throw new EmptyResultDataAccessException("No Availabilities found for the given date range.", 1);
        }
        return results;
    }
}
