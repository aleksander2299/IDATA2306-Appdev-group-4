package group4.backend.service;


import group4.backend.entities.Provider;
import group4.backend.entities.RoomProvider;
import group4.backend.repository.ProviderRepository;
import group4.backend.repository.RoomProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class responsible for managing Provider-related business logic and database operations.
 * This class handles CRUD operations for Provider entities and their relationships.
 * NOTE: Java documentation was generated with help from AI to ensure it follows Java documentation guidelines.
 */
@Service
public class ProviderService {

    /**
     * Repository for Provider entity operations
     */
    @Autowired
    private ProviderRepository providerRepository;

    /**
     * Repository for RoomProvider entity operations
     */
    @Autowired
    private RoomProviderRepository roomProviderRepository;


    /**
     * Retrieves all providers from the database
     *
     * @return List of all providers
     */
    public List<Provider> getAllProviders() {
        List<Provider> providers = new ArrayList<>();
        providerRepository.findAll().forEach(providers::add);
        return providers;
    }

    /**
     * Retrieves all room providers associated with a specific provider name
     *
     * @param name The name of the provider to search for
     * @return Iterable of RoomProvider entities
     * @throws IllegalArgumentException if no provider is found with the given name
     */
    public Iterable<RoomProvider> getRoomProviders(String name) {
        Optional<Provider> providerOptional = this.providerRepository.findByProviderName(name);
        Iterable<RoomProvider> roomProviders = null;
        if(providerOptional.isPresent()){
            roomProviders = this.roomProviderRepository.findByProvider(providerOptional.get());
        }
        else{
            throw new IllegalArgumentException("no roomprovider found");
        }
        return roomProviders;
    }

    /**
     * Retrieves a provider by their ID
     *
     * @param id The ID of the provider to retrieve
     * @return Optional containing the provider if found, empty otherwise
     */
    public Optional<Provider> getProviderById(int id) {
        return providerRepository.findById(id);
    }

    /**
     * Retrieves a provider by their name
     *
     * @param name The name of the provider to retrieve
     * @return Optional containing the provider if found, empty otherwise
     */
    public Optional<Provider> getProviderByName(String name)
    {
        return providerRepository.findByProviderName(name);
    }

    /**
     * Saves a provider to the database
     *
     * @param provider The provider entity to save
     * @return The saved provider entity
     */
    public Provider saveProvider(Provider provider) {
        return providerRepository.save(provider);
    }

    /**
     * Deletes a provider by their ID
     *
     * @param id The ID of the provider to delete
     */
    public void deleteProvider(int id) {
        providerRepository.deleteById(id);
    }

    /**
     * Updates a provider's name
     *
     * @param providerId   The ID of the provider to update
     * @param providerName The new name for the provider
     * @return The updated provider entity
     * @throws NullPointerException if no provider is found with the given ID
     */
    public Provider updateProvider(int providerId, String providerName) {

        Optional<Provider> providerOptional = providerRepository.findById(providerId);
        if(providerOptional.isEmpty()){
            throw new NullPointerException("no provider found");
        }
        Provider provider = providerOptional.get();


        if (providerName != null) provider.setProviderName(providerName);

        return providerRepository.save(provider);
    }


}
