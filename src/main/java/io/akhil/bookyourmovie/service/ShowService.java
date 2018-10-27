package io.akhil.bookyourmovie.service;

import io.akhil.bookyourmovie.service.dto.ShowDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Show.
 */
public interface ShowService {

    /**
     * Save a show.
     *
     * @param showDTO the entity to save
     * @return the persisted entity
     */
    ShowDTO save(ShowDTO showDTO);

    /**
     * Get all the shows.
     *
     * @return the list of entities
     */
    List<ShowDTO> findAll();


    /**
     * Get the "id" show.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ShowDTO> findOne(Long id);

    /**
     * Delete the "id" show.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
