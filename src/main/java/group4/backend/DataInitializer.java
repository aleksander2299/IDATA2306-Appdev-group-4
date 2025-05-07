package group4.backend;

import group4.backend.entities.*;
import group4.backend.service.*;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;

@Component
@Profile("dev")
public class DataInitializer() implements CommandLineRunner {

    // Trying to figure out best use case so ai was used to help make this
    @Autowired
    private DataInitializer(UserService userService,
                            ProviderService providerService,
                            RoomService roomService,
                            BookingService bookingService,
                            ExtraFeaturesService extraFeaturesService,
                            SourceService sourceService,
                            FavouriteService favouriteService,
                            RoomProviderService roomProviderService,
                            SourceExtraFeaturesService sourceExtraFeaturesService) {

    }

    public void run(String... args) throws Exception {

    }
}
