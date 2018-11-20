package io.akhil.bookyourmovie.service.impl;

import io.akhil.bookyourmovie.service.RowService;
import io.akhil.bookyourmovie.domain.Row;
import io.akhil.bookyourmovie.repository.RowRepository;
import io.akhil.bookyourmovie.service.dto.RowDTO;
import io.akhil.bookyourmovie.service.mapper.RowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Row.
 */
@Service
@Transactional
public class RowServiceImpl implements RowService {

    private final Logger log = LoggerFactory.getLogger(RowServiceImpl.class);

    private RowRepository rowRepository;

    private RowMapper rowMapper;

    public RowServiceImpl(RowRepository rowRepository, RowMapper rowMapper) {
        this.rowRepository = rowRepository;
        this.rowMapper = rowMapper;
    }

    /**
     * Save a row.
     *
     * @param rowDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RowDTO save(RowDTO rowDTO) {
        log.debug("Request to save Row : {}", rowDTO);

        Row row = rowMapper.toEntity(rowDTO);
        row = rowRepository.save(row);
        return rowMapper.toDto(row);
    }

    /**
     * Get all the rows.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RowDTO> findAll() {
        log.debug("Request to get all Rows");
        return rowRepository.findAll().stream()
            .map(rowMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one row by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RowDTO> findOne(Long id) {
        log.debug("Request to get Row : {}", id);
        return rowRepository.findById(id)
            .map(rowMapper::toDto);
    }

    /**
     * Delete the row by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Row : {}", id);
        rowRepository.deleteById(id);
    }
}
