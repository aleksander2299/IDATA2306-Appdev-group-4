package Service;

import Repository.ProviderRepository;
import Repository.RoomProviderRepository;
import Repository.RoomRepository;
import group4.backend.entities.RoomProvider;
import org.springframework.beans.factory.annotation.Autowired;
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
     * finds all roomProvider objects with the IDs specified in Iterable<> aka list
     * @param ids the list of ids to find by
     * @return List of roomProviderObjects
     */
    public List<RoomProvider> findAllById(Iterable<Integer> ids){
        List<RoomProvider> providerList = new ArrayList<>();
        for(RoomProvider roomProvider : findAllById(ids)){
            providerList.add(roomProvider);
        }
        return providerList;
    }


    /**
     * finds roomProvider object by ID
     * @param id the roomProviders Id
     * @return optional of roomProvider
     */
    public Optional<RoomProvider> findById(Integer id){
           return roomProviderRepository.findById(id);
    }


    /**
     * finds all roomProvider objects
     * @return list of all roomProvider objects
     */
    public List<RoomProvider> findAll(){
           List<RoomProvider> providerList = new ArrayList<>();
           for(RoomProvider roomProvider : roomProviderRepository.findAll()){
               providerList.add(roomProvider);
           }
           return providerList;
    }


    /**
     * deletes a roomProvider
     * @param provider the RoomProvider to be deleted.
     */
    public void delete(RoomProvider provider){
           roomProviderRepository.delete(provider);
    }


    /**
     * delete roomProvider object by Id
     * @param id the id of the roomProvider object to be deleted
     */
    public void deleteById(Integer id){
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
        roomProviderRepository.deleteAllById(ids);
    }


    /**
     * delets all roomProvider objects in a list
     * @param roomProviders the list of roomProvider objects to be deleted.
     */
    public void deleteAll(Iterable<RoomProvider> roomProviders){
           roomProviderRepository.deleteAll(roomProviders);
    }










}
