package io.akhil.bookyourmovie.service.impl;

import io.akhil.bookyourmovie.service.MovieService;
import io.akhil.bookyourmovie.domain.Movie;
import io.akhil.bookyourmovie.repository.MovieRepository;
import io.akhil.bookyourmovie.service.dto.MovieDTO;
import io.akhil.bookyourmovie.service.mapper.MovieMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Movie.
 */
@Service
@Transactional
public class MovieServiceImpl implements MovieService {

    private final Logger log = LoggerFactory.getLogger(MovieServiceImpl.class);

    private MovieRepository movieRepository;

    private MovieMapper movieMapper;

    public MovieServiceImpl(MovieRepository movieRepository, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }

    /**
     * Save a movie.
     *
     * @param movieDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MovieDTO save(MovieDTO movieDTO) {
        log.debug("Request to save Movie : {}", movieDTO);

        Movie movie = movieMapper.toEntity(movieDTO);
        movie = movieRepository.save(movie);
        return movieMapper.toDto(movie);
    }

    /**
     * Get all the movies.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MovieDTO> findAll() {
        log.debug("Request to get all Movies");
        return movieRepository.findAll().stream()
            .map(movieMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one movie by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MovieDTO> findOne(Long id) {
        log.debug("Request to get Movie : {}", id);
        return movieRepository.findById(id)
            .map(movieMapper::toDto);
    }

    /**
     * Delete the movie by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Movie : {}", id);
        movieRepository.deleteById(id);
    }
}
