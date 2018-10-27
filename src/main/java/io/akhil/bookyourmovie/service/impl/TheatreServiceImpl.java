package io.akhil.bookyourmovie.service.impl;

import io.akhil.bookyourmovie.service.TheatreService;
import io.akhil.bookyourmovie.domain.Theatre;
import io.akhil.bookyourmovie.repository.TheatreRepository;
import io.akhil.bookyourmovie.service.dto.TheatreDTO;
import io.akhil.bookyourmovie.service.mapper.TheatreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Theatre.
 */
@Service
@Transactional
public class TheatreServiceImpl implements TheatreService {

    private final Logger log = LoggerFactory.getLogger(TheatreServiceImpl.class);

    private TheatreRepository theatreRepository;

    private TheatreMapper theatreMapper;

    public TheatreServiceImpl(TheatreRepository theatreRepository, TheatreMapper theatreMapper) {
        this.theatreRepository = theatreRepository;
        this.theatreMapper = theatreMapper;
    }

    /**
     * Save a theatre.
     *
     * @param theatreDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TheatreDTO save(TheatreDTO theatreDTO) {
        log.debug("Request to save Theatre : {}", theatreDTO);

        Theatre theatre = theatreMapper.toEntity(theatreDTO);
        theatre = theatreRepository.save(theatre);
        return theatreMapper.toDto(theatre);
    }

    /**
     * Get all the theatres.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TheatreDTO> findAll() {
        log.debug("Request to get all Theatres");
        return theatreRepository.findAll().stream()
            .map(theatreMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one theatre by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TheatreDTO> findOne(Long id) {
        log.debug("Request to get Theatre : {}", id);
        return theatreRepository.findById(id)
            .map(theatreMapper::toDto);
    }

    /**
     * Delete the theatre by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Theatre : {}", id);
        theatreRepository.deleteById(id);
    }
}
