package group4.backend.service;

import group4.backend.entities.Availability;
import group4.backend.entities.Room;
import group4.backend.repository.AvailabilityRepository;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;

public class AvailabilityService {

  AvailabilityRepository availabilityRepository;

  @Autowired
  public AvailabilityService(AvailabilityRepository availabilityRepository) {
    this.availabilityRepository = availabilityRepository;
  }

  public void addAvailableTime(Room room, LocalDate dateStart, LocalDate dateEnd) {
    if (dateStart.isAfter(dateEnd)) {
      throw new IllegalArgumentException("The start date cannot be greater than the end date");
    }
    Iterable<Availability> availableDates = this.availabilityRepository
        .findOverlappingAvailability(room, dateStart, dateEnd);
    Availability workingAvailability = new Availability(null, room, dateStart, dateEnd);
    for (Availability storedAvailability : availableDates) {
      int count = 0;

      int workingEndComparedToStoredStart = workingAvailability.getAvailabilityTo()
          .compareTo(storedAvailability.getAvailabilityFrom());
      if (workingEndComparedToStoredStart < 0) {
        count += 1;
      } else {
        int workingStartComparedToStoredEnd = workingAvailability.getAvailabilityFrom()
            .compareTo(storedAvailability.getAvailabilityTo());
        if (workingStartComparedToStoredEnd > 0) {
          count += 10;
        }
      }
      switch (count) {
        case 0 -> {}
        case 1 -> {}
        case 10 -> {}
        case 11 -> {}
        default -> {}
      }
    }
  }

}
