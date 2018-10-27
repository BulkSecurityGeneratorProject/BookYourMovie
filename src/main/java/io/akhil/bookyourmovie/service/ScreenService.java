package io.akhil.bookyourmovie.service;

import io.akhil.bookyourmovie.service.dto.ScreenDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Screen.
 */
public interface ScreenService {

    /**
     * Save a screen.
     *
     * @param screenDTO the entity to save
     * @return the persisted entity
     */
    ScreenDTO save(ScreenDTO screenDTO);

    /**
     * Get all the screens.
     *
     * @return the list of entities
     */
    List<ScreenDTO> findAll();


    /**
     * Get the "id" screen.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ScreenDTO> findOne(Long id);

    /**
     * Delete the "id" screen.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
