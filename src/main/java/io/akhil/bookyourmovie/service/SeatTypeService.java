package io.akhil.bookyourmovie.service;

import io.akhil.bookyourmovie.service.dto.SeatTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing SeatType.
 */
public interface SeatTypeService {

    /**
     * Save a seatType.
     *
     * @param seatTypeDTO the entity to save
     * @return the persisted entity
     */
    SeatTypeDTO save(SeatTypeDTO seatTypeDTO);

    /**
     * Get all the seatTypes.
     *
     * @return the list of entities
     */
    List<SeatTypeDTO> findAll();


    /**
     * Get the "id" seatType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SeatTypeDTO> findOne(Long id);

    /**
     * Delete the "id" seatType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
