package io.akhil.bookyourmovie.service.impl;

import io.akhil.bookyourmovie.service.SeatTypeService;
import io.akhil.bookyourmovie.domain.SeatType;
import io.akhil.bookyourmovie.repository.SeatTypeRepository;
import io.akhil.bookyourmovie.service.dto.SeatTypeDTO;
import io.akhil.bookyourmovie.service.mapper.SeatTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing SeatType.
 */
@Service
@Transactional
public class SeatTypeServiceImpl implements SeatTypeService {

    private final Logger log = LoggerFactory.getLogger(SeatTypeServiceImpl.class);

    private SeatTypeRepository seatTypeRepository;

    private SeatTypeMapper seatTypeMapper;

    public SeatTypeServiceImpl(SeatTypeRepository seatTypeRepository, SeatTypeMapper seatTypeMapper) {
        this.seatTypeRepository = seatTypeRepository;
        this.seatTypeMapper = seatTypeMapper;
    }

    /**
     * Save a seatType.
     *
     * @param seatTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SeatTypeDTO save(SeatTypeDTO seatTypeDTO) {
        log.debug("Request to save SeatType : {}", seatTypeDTO);

        SeatType seatType = seatTypeMapper.toEntity(seatTypeDTO);
        seatType = seatTypeRepository.save(seatType);
        return seatTypeMapper.toDto(seatType);
    }

    /**
     * Get all the seatTypes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SeatTypeDTO> findAll() {
        log.debug("Request to get all SeatTypes");
        return seatTypeRepository.findAll().stream()
            .map(seatTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one seatType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SeatTypeDTO> findOne(Long id) {
        log.debug("Request to get SeatType : {}", id);
        return seatTypeRepository.findById(id)
            .map(seatTypeMapper::toDto);
    }

    /**
     * Delete the seatType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SeatType : {}", id);
        seatTypeRepository.deleteById(id);
    }
}
