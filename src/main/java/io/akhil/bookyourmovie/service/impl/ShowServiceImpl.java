package io.akhil.bookyourmovie.service.impl;

import io.akhil.bookyourmovie.service.ShowService;
import io.akhil.bookyourmovie.domain.Show;
import io.akhil.bookyourmovie.repository.ShowRepository;
import io.akhil.bookyourmovie.service.dto.ShowDTO;
import io.akhil.bookyourmovie.service.mapper.ShowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Show.
 */
@Service
@Transactional
public class ShowServiceImpl implements ShowService {

    private final Logger log = LoggerFactory.getLogger(ShowServiceImpl.class);

    private ShowRepository showRepository;

    private ShowMapper showMapper;

    public ShowServiceImpl(ShowRepository showRepository, ShowMapper showMapper) {
        this.showRepository = showRepository;
        this.showMapper = showMapper;
    }

    /**
     * Save a show.
     *
     * @param showDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ShowDTO save(ShowDTO showDTO) {
        log.debug("Request to save Show : {}", showDTO);

        Show show = showMapper.toEntity(showDTO);
        show = showRepository.save(show);
        return showMapper.toDto(show);
    }

    /**
     * Get all the shows.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShowDTO> findAll() {
        log.debug("Request to get all Shows");
        return showRepository.findAll().stream()
            .map(showMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one show by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ShowDTO> findOne(Long id) {
        log.debug("Request to get Show : {}", id);
        return showRepository.findById(id)
            .map(showMapper::toDto);
    }

    /**
     * Delete the show by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Show : {}", id);
        showRepository.deleteById(id);
    }
}
