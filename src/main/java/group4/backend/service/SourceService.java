package group4.backend.service;

import group4.backend.entities.Source;
import group4.backend.repository.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SourceService {

    @Autowired
    private SourceRepository sourceRepository;

    public SortedSet<Source> findAllbyId(Iterable<Integer> sourceIds) {

        if(sourceIds == null) {
            throw new IllegalArgumentException("Source ID not found.");
        }
        SortedSet<Source> sourceList = new TreeSet<>();
        for(Source source : sourceRepository.findAllById(sourceIds)) {
            sourceList.add(source);
        }
        if(sourceList.isEmpty()) {
            throw new EmptyResultDataAccessException("No source found", 1);
        }
        return sourceList;
    }

    public Optional<Source> findById(Integer sourceId) {
        if(sourceId == null){
            throw new IllegalArgumentException("no source ID found ");
        }
        return sourceRepository.findById(sourceId);
    }

    public SortedSet<Source> findAll() {
        SortedSet<Source> sourceList = new TreeSet<>();
        sourceRepository.findAll().forEach(sourceList::add);
        if(sourceList.isEmpty())
        {
            throw new IllegalArgumentException("Source ID not found.");
        }
        return sourceList;
    }

    public void delete(Source source) {
        if(source == null){
            throw new IllegalArgumentException("No source found ");
        }
        sourceRepository.delete(source);
    }

    public void deleteById(Integer integer){
        if(integer == null){
            throw new IllegalArgumentException("No integer found ");
        }
        sourceRepository.deleteById(integer);
    }

    public void deleteAll(){
        sourceRepository.deleteAll();
    }

    public void deletAllById(Iterable<Integer> integers){
        if(integers == null){
            throw new IllegalArgumentException("No integer found ");
        }
        sourceRepository.deleteAllById(integers);
    }

    public void deleteAll(Iterable<Source> source){
        if (source == null || !source.iterator().hasNext()) {
            throw new IllegalArgumentException("No Source objects provided to delete.");
        }
        sourceRepository.deleteAll(source);
    }

    public void addSource(Source source) {
        this.sourceRepository.save(source);
    }

    public List<Source> getAllSources() {
        List<Source> sources = new ArrayList<>();
        sourceRepository.findAll().forEach(source -> sources.add((Source) source));
        return sources;
    }

    public Optional<Source> getSourceById(int id) {
        return sourceRepository.findById(id);
    }

    public Source saveSource(Source source) {
        return sourceRepository.save(source);
    }

    public void deleteSource(int id) {
        sourceRepository.deleteById(id);
    }
}
