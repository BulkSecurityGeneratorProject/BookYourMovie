package io.akhil.bookyourmovie.service;

import io.akhil.bookyourmovie.service.dto.BookingDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Booking.
 */
public interface BookingService {

    /**
     * Save a booking.
     *
     * @param bookingDTO the entity to save
     * @return the persisted entity
     */
    BookingDTO save(BookingDTO bookingDTO);

    /**
     * Get all the bookings.
     *
     * @return the list of entities
     */
    List<BookingDTO> findAll();


    /**
     * Get the "id" booking.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<BookingDTO> findOne(Long id);

    /**
     * Delete the "id" booking.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
