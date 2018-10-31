package io.akhil.bookyourmovie.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.akhil.bookyourmovie.service.ShowService;
import io.akhil.bookyourmovie.web.rest.errors.BadRequestAlertException;
import io.akhil.bookyourmovie.web.rest.util.HeaderUtil;
import io.akhil.bookyourmovie.service.dto.ShowDTO;
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
 * REST controller for managing Show.
 */
@RestController
@RequestMapping("/api")
public class ShowResource {

    private final Logger log = LoggerFactory.getLogger(ShowResource.class);

    private static final String ENTITY_NAME = "show";

    private ShowService showService;

    public ShowResource(ShowService showService) {
        this.showService = showService;
    }

    /**
     * POST  /shows : Create a new show.
     *
     * @param showDTO the showDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new showDTO, or with status 400 (Bad Request) if the show has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shows")
    @Timed
    public ResponseEntity<ShowDTO> createShow(@Valid @RequestBody ShowDTO showDTO) throws URISyntaxException {
        log.debug("REST request to save Show : {}", showDTO);
        if (showDTO.getId() != null) {
            throw new BadRequestAlertException("A new show cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShowDTO result = showService.save(showDTO);
        return ResponseEntity.created(new URI("/api/shows/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shows : Updates an existing show.
     *
     * @param showDTO the showDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated showDTO,
     * or with status 400 (Bad Request) if the showDTO is not valid,
     * or with status 500 (Internal Server Error) if the showDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shows")
    @Timed
    public ResponseEntity<ShowDTO> updateShow(@Valid @RequestBody ShowDTO showDTO) throws URISyntaxException {
        log.debug("REST request to update Show : {}", showDTO);
        if (showDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ShowDTO result = showService.save(showDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, showDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shows : get all the shows.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of shows in body
     */
    @GetMapping("/shows")
    @Timed
    public List<ShowDTO> getAllShows() {
        log.debug("REST request to get all Shows");
        return showService.findAll();
    }

    /**
     * GET  /shows/:id : get the "id" show.
     *
     * @param id the id of the showDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the showDTO, or with status 404 (Not Found)
     */
    @GetMapping("/shows/{id}")
    @Timed
    public ResponseEntity<ShowDTO> getShow(@PathVariable Long id) {
        log.debug("REST request to get Show : {}", id);
        Optional<ShowDTO> showDTO = showService.findOne(id);
        return ResponseUtil.wrapOrNotFound(showDTO);
    }

    /**
     * DELETE  /shows/:id : delete the "id" show.
     *
     * @param id the id of the showDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shows/{id}")
    @Timed
    public ResponseEntity<Void> deleteShow(@PathVariable Long id) {
        log.debug("REST request to delete Show : {}", id);
        showService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
