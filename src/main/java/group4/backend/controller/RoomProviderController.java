package group4.backend.controller;

import group4.backend.entities.RoomProvider;
import group4.backend.service.RoomProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roomProvider")
public class RoomProviderController {


    @Autowired
    private RoomProviderService roomProviderService;




    public ResponseEntity<RoomProvider> findById(){

    }


    public ResponseEntity<RoomProvider> findAll(){

    }

    public ResponseEntity<RoomProvider> findAllById(){

    }


    public ResponseEntity<RoomProvider> delete(){

    }

    public ResponseEntity<RoomProvider> DeleteById(){

    }

    public ResponseEntity<RoomProvider> deleteAllById(){

    }


    public ResponseEntity<RoomProvider> deleteAll(){

    }


    public ResponseEntity<RoomProvider> linkRoomToProvider(){

    }

    public ResponseEntity<RoomProvider> unlinkRoomToProvider(){

    }




}
