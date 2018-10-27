package io.akhil.bookyourmovie.service.impl;

import io.akhil.bookyourmovie.service.ScreenService;
import io.akhil.bookyourmovie.domain.Screen;
import io.akhil.bookyourmovie.repository.ScreenRepository;
import io.akhil.bookyourmovie.service.dto.ScreenDTO;
import io.akhil.bookyourmovie.service.mapper.ScreenMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Screen.
 */
@Service
@Transactional
public class ScreenServiceImpl implements ScreenService {

    private final Logger log = LoggerFactory.getLogger(ScreenServiceImpl.class);

    private ScreenRepository screenRepository;

    private ScreenMapper screenMapper;

    public ScreenServiceImpl(ScreenRepository screenRepository, ScreenMapper screenMapper) {
        this.screenRepository = screenRepository;
        this.screenMapper = screenMapper;
    }

    /**
     * Save a screen.
     *
     * @param screenDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ScreenDTO save(ScreenDTO screenDTO) {
        log.debug("Request to save Screen : {}", screenDTO);

        Screen screen = screenMapper.toEntity(screenDTO);
        screen = screenRepository.save(screen);
        return screenMapper.toDto(screen);
    }

    /**
     * Get all the screens.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ScreenDTO> findAll() {
        log.debug("Request to get all Screens");
        return screenRepository.findAll().stream()
            .map(screenMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one screen by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ScreenDTO> findOne(Long id) {
        log.debug("Request to get Screen : {}", id);
        return screenRepository.findById(id)
            .map(screenMapper::toDto);
    }

    /**
     * Delete the screen by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Screen : {}", id);
        screenRepository.deleteById(id);
    }
}
