package io.akhil.bookyourmovie.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.akhil.bookyourmovie.service.TheatreService;
import io.akhil.bookyourmovie.web.rest.errors.BadRequestAlertException;
import io.akhil.bookyourmovie.web.rest.util.HeaderUtil;
import io.akhil.bookyourmovie.service.dto.TheatreDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Theatre.
 */
@RestController
@RequestMapping("/api")
public class TheatreResource {

    private final Logger log = LoggerFactory.getLogger(TheatreResource.class);

    private static final String ENTITY_NAME = "theatre";

    private TheatreService theatreService;

    public TheatreResource(TheatreService theatreService) {
        this.theatreService = theatreService;
    }

    /**
     * POST  /theatres : Create a new theatre.
     *
     * @param theatreDTO the theatreDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new theatreDTO, or with status 400 (Bad Request) if the theatre has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/theatres")
    @Timed
    public ResponseEntity<TheatreDTO> createTheatre(@Valid @RequestBody TheatreDTO theatreDTO) throws URISyntaxException {
        log.debug("REST request to save Theatre : {}", theatreDTO);
        if (theatreDTO.getId() != null) {
            throw new BadRequestAlertException("A new theatre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TheatreDTO result = theatreService.save(theatreDTO);
        return ResponseEntity.created(new URI("/api/theatres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /theatres : Updates an existing theatre.
     *
     * @param theatreDTO the theatreDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated theatreDTO,
     * or with status 400 (Bad Request) if the theatreDTO is not valid,
     * or with status 500 (Internal Server Error) if the theatreDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/theatres")
    @Timed
    public ResponseEntity<TheatreDTO> updateTheatre(@Valid @RequestBody TheatreDTO theatreDTO) throws URISyntaxException {
        log.debug("REST request to update Theatre : {}", theatreDTO);
        if (theatreDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TheatreDTO result = theatreService.save(theatreDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, theatreDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /theatres : get all the theatres.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of theatres in body
     */
    @GetMapping("/theatres")
    @Timed
    public List<TheatreDTO> getAllTheatres() {
        log.debug("REST request to get all Theatres");
        return theatreService.findAll();
    }

    /**
     * GET  /theatres/:id : get the "id" theatre.
     *
     * @param id the id of the theatreDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the theatreDTO, or with status 404 (Not Found)
     */
    @GetMapping("/theatres/{id}")
    @Timed
    public ResponseEntity<TheatreDTO> getTheatre(@PathVariable Long id) {
        log.debug("REST request to get Theatre : {}", id);
        Optional<TheatreDTO> theatreDTO = theatreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(theatreDTO);
    }

    /**
     * DELETE  /theatres/:id : delete the "id" theatre.
     *
     * @param id the id of the theatreDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/theatres/{id}")
    @Timed
    public ResponseEntity<Void> deleteTheatre(@PathVariable Long id) {
        log.debug("REST request to delete Theatre : {}", id);
        theatreService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
