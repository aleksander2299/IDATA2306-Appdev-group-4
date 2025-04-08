package group4.backend.service;

import Repository.ProviderRepository;
import group4.backend.entities.Provider;
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

    public Optional<Provider> getProviderById(int id) {
        return providerRepository.findById(id);
    }

    public Provider saveProvider(Provider provider) {
        return providerRepository.save(provider);
    }

    public void deleteProvider(int id) {
        providerRepository.deleteById(id);
    }


}
