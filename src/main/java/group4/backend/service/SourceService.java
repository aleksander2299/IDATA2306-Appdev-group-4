package group4.backend.service;

import group4.backend.entities.Source;
import group4.backend.repository.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service class for managing Source entities.
 * This class handles all source-related operations and data access through the SourceRepository.
 * <p>
 * Note: This documentation was generated with the assistance of AI.
 */
@Service
public class SourceService {

    @Autowired
    private SourceRepository sourceRepository;

    /**
     * Retrieves a sorted set of Source entities by their IDs.
     *
     * @param sourceIds Iterable of Integer IDs to search for
     * @return SortedSet of Source entities matching the provided IDs
     * @throws IllegalArgumentException       if sourceIds is null
     * @throws EmptyResultDataAccessException if no sources are found
     */
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

    /**
     * Finds a Source entity by its ID.
     *
     * @param sourceId The ID of the Source to find
     * @return Optional containing the found Source entity
     * @throws IllegalArgumentException if sourceId is null
     */
    public Optional<Source> findById(Integer sourceId) {
        if(sourceId == null){
            throw new IllegalArgumentException("no source ID found ");
        }
        return sourceRepository.findById(sourceId);
    }

    /**
     * Retrieves all Source entities as a sorted set.
     *
     * @return SortedSet containing all Source entities
     * @throws IllegalArgumentException if no sources are found
     */
    public SortedSet<Source> findAll() {
        SortedSet<Source> sourceList = new TreeSet<>();
        sourceRepository.findAll().forEach(sourceList::add);
        if(sourceList.isEmpty())
        {
            throw new IllegalArgumentException("Source ID not found.");
        }
        return sourceList;
    }

    /**
     * Deletes the specified Source entity.
     *
     * @param source The Source entity to delete
     * @throws IllegalArgumentException if source is null
     */
    public void delete(Source source) {
        if(source == null){
            throw new IllegalArgumentException("No source found ");
        }
        sourceRepository.delete(source);
    }

    /**
     * Deletes a Source entity by its ID.
     *
     * @param integer The ID of the Source to delete
     * @throws IllegalArgumentException if integer is null
     */
    public void deleteById(Integer integer){
        if(integer == null){
            throw new IllegalArgumentException("No integer found ");
        }
        sourceRepository.deleteById(integer);
    }

    /**
     * Deletes all Source entities from the database.
     */
    public void deleteAll(){
        sourceRepository.deleteAll();
    }

    /**
     * Deletes all Source entities with the specified IDs.
     *
     * @param integers Iterable of Integer IDs to delete
     * @throws IllegalArgumentException if integers is null
     */
    public void deleteAllById(Iterable<Integer> integers){
        if(integers == null){
            throw new IllegalArgumentException("No integer found ");
        }
        sourceRepository.deleteAllById(integers);
    }

    /**
     * Deletes all specified Source entities.
     *
     * @param source Iterable of Source entities to delete
     * @throws IllegalArgumentException if source is null or empty
     */
    public void deleteAll(Iterable<Source> source){
        if (source == null || !source.iterator().hasNext()) {
            throw new IllegalArgumentException("No Source objects provided to delete.");
        }
        sourceRepository.deleteAll(source);
    }

    /**
     * Adds a new Source entity to the database.
     *
     * @param source The Source entity to add
     */
    public void addSource(Source source) {
        this.sourceRepository.save(source);
    }

    /**
     * Retrieves all Source entities as a list.
     *
     * @return List of all Source entities
     */
    public List<Source> getAllSources() {
        List<Source> sources = new ArrayList<>();
        sourceRepository.findAll().forEach(source -> sources.add((Source) source));
        return sources;
    }

    /**
     * Retrieves a Source entity by its ID.
     *
     * @param id The ID of the Source to retrieve
     * @return Optional containing the found Source entity
     */
    public Optional<Source> getSourceById(int id) {
        return sourceRepository.findById(id);
    }

    /**
     * Saves a Source entity to the database.
     *
     * @param source The Source entity to save
     * @return The saved Source entity
     */
    public Source saveSource(Source source) {
        return sourceRepository.save(source);
    }

    /**
     * Deletes a Source entity by its ID.
     *
     * @param id The ID of the Source to delete
     */
    public void deleteSource(int id) {
        sourceRepository.deleteById(id);
    }
}
