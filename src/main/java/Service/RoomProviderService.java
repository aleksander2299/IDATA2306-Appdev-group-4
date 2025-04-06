package Service;

import Repository.ProviderRepository;
import Repository.RoomProviderRepository;
import Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomProviderService {

    @Autowired
    private RoomProviderRepository roomProviderRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ProviderRepository providerRepository;





}
