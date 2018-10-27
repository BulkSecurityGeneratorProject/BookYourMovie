package io.akhil.bookyourmovie.service;

import io.akhil.bookyourmovie.service.dto.TheatreDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Theatre.
 */
public interface TheatreService {

    /**
     * Save a theatre.
     *
     * @param theatreDTO the entity to save
     * @return the persisted entity
     */
    TheatreDTO save(TheatreDTO theatreDTO);

    /**
     * Get all the theatres.
     *
     * @return the list of entities
     */
    List<TheatreDTO> findAll();


    /**
     * Get the "id" theatre.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TheatreDTO> findOne(Long id);

    /**
     * Delete the "id" theatre.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
