package io.akhil.bookyourmovie.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.akhil.bookyourmovie.service.ScreenService;
import io.akhil.bookyourmovie.web.rest.errors.BadRequestAlertException;
import io.akhil.bookyourmovie.web.rest.util.HeaderUtil;
import io.akhil.bookyourmovie.service.dto.ScreenDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Screen.
 */
@RestController
@RequestMapping("/api")
public class ScreenResource {

    private final Logger log = LoggerFactory.getLogger(ScreenResource.class);

    private static final String ENTITY_NAME = "screen";

    private ScreenService screenService;

    public ScreenResource(ScreenService screenService) {
        this.screenService = screenService;
    }

    /**
     * POST  /screens : Create a new screen.
     *
     * @param screenDTO the screenDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new screenDTO, or with status 400 (Bad Request) if the screen has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/screens")
    @Timed
    public ResponseEntity<ScreenDTO> createScreen(@RequestBody ScreenDTO screenDTO) throws URISyntaxException {
        log.debug("REST request to save Screen : {}", screenDTO);
        if (screenDTO.getId() != null) {
            throw new BadRequestAlertException("A new screen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ScreenDTO result = screenService.save(screenDTO);
        return ResponseEntity.created(new URI("/api/screens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /screens : Updates an existing screen.
     *
     * @param screenDTO the screenDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated screenDTO,
     * or with status 400 (Bad Request) if the screenDTO is not valid,
     * or with status 500 (Internal Server Error) if the screenDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/screens")
    @Timed
    public ResponseEntity<ScreenDTO> updateScreen(@RequestBody ScreenDTO screenDTO) throws URISyntaxException {
        log.debug("REST request to update Screen : {}", screenDTO);
        if (screenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ScreenDTO result = screenService.save(screenDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, screenDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /screens : get all the screens.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of screens in body
     */
    @GetMapping("/screens")
    @Timed
    public List<ScreenDTO> getAllScreens() {
        log.debug("REST request to get all Screens");
        return screenService.findAll();
    }

    /**
     * GET  /screens/:id : get the "id" screen.
     *
     * @param id the id of the screenDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the screenDTO, or with status 404 (Not Found)
     */
    @GetMapping("/screens/{id}")
    @Timed
    public ResponseEntity<ScreenDTO> getScreen(@PathVariable Long id) {
        log.debug("REST request to get Screen : {}", id);
        Optional<ScreenDTO> screenDTO = screenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(screenDTO);
    }

    /**
     * DELETE  /screens/:id : delete the "id" screen.
     *
     * @param id the id of the screenDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/screens/{id}")
    @Timed
    public ResponseEntity<Void> deleteScreen(@PathVariable Long id) {
        log.debug("REST request to delete Screen : {}", id);
        screenService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
