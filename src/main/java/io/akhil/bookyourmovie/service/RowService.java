package io.akhil.bookyourmovie.service;

import io.akhil.bookyourmovie.service.dto.RowDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Row.
 */
public interface RowService {

    /**
     * Save a row.
     *
     * @param rowDTO the entity to save
     * @return the persisted entity
     */
    RowDTO save(RowDTO rowDTO);

    /**
     * Get all the rows.
     *
     * @return the list of entities
     */
    List<RowDTO> findAll();


    /**
     * Get the "id" row.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RowDTO> findOne(Long id);

    /**
     * Delete the "id" row.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
