package io.akhil.bookyourmovie.service;

import io.akhil.bookyourmovie.service.dto.SeatDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Seat.
 */
public interface SeatService {

    /**
     * Save a seat.
     *
     * @param seatDTO the entity to save
     * @return the persisted entity
     */
    SeatDTO save(SeatDTO seatDTO);

    /**
     * Get all the seats.
     *
     * @return the list of entities
     */
    List<SeatDTO> findAll();


    /**
     * Get the "id" seat.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SeatDTO> findOne(Long id);

    /**
     * Delete the "id" seat.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
