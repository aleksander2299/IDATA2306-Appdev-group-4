package group4.backend.service;


import group4.backend.entities.Provider;
import group4.backend.entities.Room;
import group4.backend.entities.RoomProvider;
import group4.backend.repository.ProviderRepository;
import group4.backend.repository.RoomProviderRepository;
import group4.backend.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * class representing roomProviderService, a service class for roomProvider Table
 */
@Service
public class RoomProviderService {

    @Autowired
    private RoomProviderRepository roomProviderRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ProviderRepository providerRepository;


    /**
     * finds all roomProvider objects with the Ids specified in Iterable<> aka list
     * @param ids the list of ids to find by
     * @return List of roomProviderObjects
     */
    public List<RoomProvider> findAllById(Iterable<Integer> ids){
        if(ids == null){
            throw new IllegalArgumentException("no ids found ");
        }
        List<RoomProvider> providerList = new ArrayList<>();
        for(RoomProvider roomProvider : roomProviderRepository.findAllById(ids)){
            providerList.add(roomProvider);
        }
        if (providerList.isEmpty()) {
            throw new EmptyResultDataAccessException("No RoomProviders found", 1);
        }
        return providerList;
    }


    /**
     * finds roomProvider object by ID
     * @param id the roomProviders ID
     * @return optional of roomProvider
     */
    public Optional<RoomProvider> findById(Integer id){
        if(id == null){
            throw new IllegalArgumentException("no id found ");
        }
           return roomProviderRepository.findById(id);
    }


    /**
     * finds all roomProvider objects
     * @return list of all roomProvider objects
     */
    public List<RoomProvider> findAll(){

            List<RoomProvider> providerList = new ArrayList<>();
            roomProviderRepository.findAll().forEach(providerList::add);
            if (providerList.isEmpty()) {
            throw new EmptyResultDataAccessException("No RoomProviders found", 1);
            }
            return providerList;
    }


    /**
     * deletes a roomProvider
     * @param provider the RoomProvider to be deleted.
     */
    public void delete(RoomProvider provider){
        if(provider == null){
            throw new IllegalArgumentException("no id found ");
        }
           roomProviderRepository.delete(provider);
    }


    /**
     * delete roomProvider object by ID
     * @param id the id of the roomProvider object to be deleted
     */
    public void deleteById(Integer id){
        if(id == null){
            throw new IllegalArgumentException("no ids found ");
        }
         roomProviderRepository.deleteById(id);
    }

    /**
     * deletes all roomProvider objects.
     */
    public void deleteAll(){
           roomProviderRepository.deleteAll();
    }

    /**
     * deletes all room provider objects by their ids in param list
     * @param ids the list of ids which roomProvider objects will be dleeted
     */
    public void deletAllById(Iterable<Integer> ids){
        if(ids == null){
            throw new IllegalArgumentException("no ids found ");
        }
        roomProviderRepository.deleteAllById(ids);
    }


    /**
     * delets all roomProvider objects in a list
     * @param roomProviders the list of roomProvider objects to be deleted.
     */
    public void deleteAll(Iterable<RoomProvider> roomProviders){
        if (roomProviders == null || !roomProviders.iterator().hasNext()) {
            throw new IllegalArgumentException("No RoomProvider objects provided to delete.");
        }
            roomProviderRepository.deleteAll(roomProviders);

    }


    /**
     * method that links an existing room to a provider
     * @param roomId id of the room to be linked to a given provider
     * @param providerId if of the provider to be linked to a given room
     */
    public Optional<RoomProvider> linkRoomToProvider(Integer roomId, Integer providerId, Integer roomPrice){
        if (roomId == null) {
            throw new IllegalArgumentException("There is no room with id null");
        }
        if (providerId == null) {
            throw new IllegalArgumentException(("There is no provider with id null"));
        }
        Optional<Room> room = this.roomRepository.findById(roomId);
        Optional<Provider> provider = this.providerRepository.findById(providerId);
        Optional<RoomProvider> roomProvider = Optional.empty();

        if (room.isPresent() && provider.isPresent()) {
            roomProviderRepository.save(new RoomProvider(null, provider.get(), room.get(), roomPrice));
            roomProvider = this.roomProviderRepository.findByRoomAndProvider(room.get(), provider.get());
        }

        return roomProvider;
    }


    /**
     * method for unlinking a room and a provider effectively removing an entyry from the table
     * @param roomId the room to unlink from provider
     * @param providerId the provider to unlink from room
     */
    public void unlinkRoomToProvider(Integer roomId, Integer providerId){

        Optional<Room> room = roomRepository.findById(roomId);
        Optional<Provider> provider = providerRepository.findById(providerId);
        Optional<RoomProvider> roomProvider = Optional.empty();

        if (room.isPresent() && provider.isPresent()) {
            roomProvider = roomProviderRepository.findByRoomAndProvider(room.get(),provider.get());
        }
        roomProvider.ifPresent(value -> roomProviderRepository.delete(value));

    }



    public RoomProvider updateRoomProvider(int roomProviderId, Integer roomPrice,
                                                           Integer roomId, Integer providerId){

        Optional<RoomProvider> roomProviderOptional = roomProviderRepository.findById(roomProviderId);
        if(roomProviderOptional.isEmpty()){
            throw new NullPointerException("no roomProvider instance found");
        }
        RoomProvider roomProvider = roomProviderOptional.get();

        if(roomPrice != null){roomProvider.setRoomPrice(roomPrice);}
        if(roomId != null){
            Optional<Room> roomOptional = roomRepository.findById(roomId);
            roomOptional.ifPresent(roomProvider::setRoom);
        }
        if(providerId != null){
            Optional<Provider> providerOptional = providerRepository.findById(providerId);
            providerOptional.ifPresent(roomProvider::setProvider);
        }

        return roomProviderRepository.save(roomProvider);
    }


}
