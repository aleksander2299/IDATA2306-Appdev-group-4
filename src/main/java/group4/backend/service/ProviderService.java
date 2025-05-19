package group4.backend.service;


import group4.backend.entities.Provider;
import group4.backend.entities.RoomProvider;
import group4.backend.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProviderService {

    @Autowired
    private ProviderRepository providerRepository;



    public List<Provider> getAllProviders() {
        List<Provider> providers = new ArrayList<>();
        providerRepository.findAll().forEach(providers::add);
        return providers;
    }

    public List<RoomProvider> getRoomProviders(int id) {
        return providerRepository.findById(id).get().getRoomProviders();
    }
    public Optional<Provider> getProviderById(int id) {
        return providerRepository.findById(id);
    }

    public Optional<Provider> getProvicerByName(String name)
    {
        return providerRepository.findByProviderName(name);
    }

    public Provider saveProvider(Provider provider) {
        return providerRepository.save(provider);
    }

    public void deleteProvider(int id) {
        providerRepository.deleteById(id);
    }

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
