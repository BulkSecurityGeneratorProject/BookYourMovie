package io.akhil.bookyourmovie.service.impl;

import io.akhil.bookyourmovie.service.SeatService;
import io.akhil.bookyourmovie.domain.Seat;
import io.akhil.bookyourmovie.repository.SeatRepository;
import io.akhil.bookyourmovie.service.dto.SeatDTO;
import io.akhil.bookyourmovie.service.mapper.SeatMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Seat.
 */
@Service
@Transactional
public class SeatServiceImpl implements SeatService {

    private final Logger log = LoggerFactory.getLogger(SeatServiceImpl.class);

    private SeatRepository seatRepository;

    private SeatMapper seatMapper;

    public SeatServiceImpl(SeatRepository seatRepository, SeatMapper seatMapper) {
        this.seatRepository = seatRepository;
        this.seatMapper = seatMapper;
    }

    /**
     * Save a seat.
     *
     * @param seatDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SeatDTO save(SeatDTO seatDTO) {
        log.debug("Request to save Seat : {}", seatDTO);

        Seat seat = seatMapper.toEntity(seatDTO);
        seat = seatRepository.save(seat);
        return seatMapper.toDto(seat);
    }

    /**
     * Get all the seats.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SeatDTO> findAll() {
        log.debug("Request to get all Seats");
        return seatRepository.findAll().stream()
            .map(seatMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one seat by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SeatDTO> findOne(Long id) {
        log.debug("Request to get Seat : {}", id);
        return seatRepository.findById(id)
            .map(seatMapper::toDto);
    }

    /**
     * Delete the seat by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Seat : {}", id);
        seatRepository.deleteById(id);
    }
}
