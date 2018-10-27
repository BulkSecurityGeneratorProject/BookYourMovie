package io.akhil.bookyourmovie.service;

import io.akhil.bookyourmovie.service.dto.MovieDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Movie.
 */
public interface MovieService {

    /**
     * Save a movie.
     *
     * @param movieDTO the entity to save
     * @return the persisted entity
     */
    MovieDTO save(MovieDTO movieDTO);

    /**
     * Get all the movies.
     *
     * @return the list of entities
     */
    List<MovieDTO> findAll();


    /**
     * Get the "id" movie.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MovieDTO> findOne(Long id);

    /**
     * Delete the "id" movie.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
