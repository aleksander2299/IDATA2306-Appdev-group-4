package group4.backend.repository;

import group4.backend.entities.Favourite;
import group4.backend.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavouriteRepository extends CrudRepository<Favourite, Integer> {


    @Override
    Iterable<Favourite> findAll();

    @Override
    Optional<Favourite> findById(Integer integer);

    @Override
    void delete(Favourite entity);

    @Override
    void deleteById(Integer integer);

    @Override
    void deleteAll();

    @Override
    Iterable<Favourite> findAllById(Iterable<Integer> integers);

    @Override
    void deleteAllById(Iterable<? extends Integer> integers);

    /**
     * Find all favourites by user
     * @param username
     * @return List of favourites
     */
    List<Favourite> findAllByUser(User username);
}