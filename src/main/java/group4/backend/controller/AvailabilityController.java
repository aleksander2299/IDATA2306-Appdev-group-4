package group4.backend.controller;

import Service.AvailabilityService;
import group4.backend.entities.Availability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Controller class for Availability endpoints.
 * AI was used to help create getByDateRange method.
 */
@RestController
@RequestMapping("/availability")
public class AvailabilityController {

    @Autowired
    private AvailabilityService availabilityService;

    @GetMapping
    public List<Availability> getAll() {
        return availabilityService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Availability> getById(@PathVariable Integer id) {
        return availabilityService.findById(id);
    }

    /**
     * Get all availabilities within a specified date range.
     * The expected date format is YYYY-MM-DD.
     */
    @GetMapping("/search")
    public List<Availability> getByDateRange(@RequestParam int roomId, @RequestParam String from, @RequestParam String to) {
        LocalDate fromDate = LocalDate.parse(from);
        LocalDate toDate = LocalDate.parse(to);
        return availabilityService.findAvailabilitiesForRoomInDateRange(roomId, fromDate, toDate);
    }

    @PostMapping
    public Availability createAvailability(@RequestBody Availability availability) {
        return availabilityService.save(availability);
    }

    @DeleteMapping("/{id}")
    public void deleteAvailability(@PathVariable Integer id) {
        availabilityService.deleteById(id);
    }
}
