package io.akhil.bookyourmovie.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.akhil.bookyourmovie.service.RowService;
import io.akhil.bookyourmovie.web.rest.errors.BadRequestAlertException;
import io.akhil.bookyourmovie.web.rest.util.HeaderUtil;
import io.akhil.bookyourmovie.service.dto.RowDTO;
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
 * REST controller for managing Row.
 */
@RestController
@RequestMapping("/api")
public class RowResource {

    private final Logger log = LoggerFactory.getLogger(RowResource.class);

    private static final String ENTITY_NAME = "row";

    private RowService rowService;

    public RowResource(RowService rowService) {
        this.rowService = rowService;
    }

    /**
     * POST  /rows : Create a new row.
     *
     * @param rowDTO the rowDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rowDTO, or with status 400 (Bad Request) if the row has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rows")
    @Timed
    public ResponseEntity<RowDTO> createRow(@Valid @RequestBody RowDTO rowDTO) throws URISyntaxException {
        log.debug("REST request to save Row : {}", rowDTO);
        if (rowDTO.getId() != null) {
            throw new BadRequestAlertException("A new row cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RowDTO result = rowService.save(rowDTO);
        return ResponseEntity.created(new URI("/api/rows/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rows : Updates an existing row.
     *
     * @param rowDTO the rowDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rowDTO,
     * or with status 400 (Bad Request) if the rowDTO is not valid,
     * or with status 500 (Internal Server Error) if the rowDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rows")
    @Timed
    public ResponseEntity<RowDTO> updateRow(@Valid @RequestBody RowDTO rowDTO) throws URISyntaxException {
        log.debug("REST request to update Row : {}", rowDTO);
        if (rowDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RowDTO result = rowService.save(rowDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rowDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rows : get all the rows.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rows in body
     */
    @GetMapping("/rows")
    @Timed
    public List<RowDTO> getAllRows() {
        log.debug("REST request to get all Rows");
        return rowService.findAll();
    }

    /**
     * GET  /rows/:id : get the "id" row.
     *
     * @param id the id of the rowDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rowDTO, or with status 404 (Not Found)
     */
    @GetMapping("/rows/{id}")
    @Timed
    public ResponseEntity<RowDTO> getRow(@PathVariable Long id) {
        log.debug("REST request to get Row : {}", id);
        Optional<RowDTO> rowDTO = rowService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rowDTO);
    }

    /**
     * DELETE  /rows/:id : delete the "id" row.
     *
     * @param id the id of the rowDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rows/{id}")
    @Timed
    public ResponseEntity<Void> deleteRow(@PathVariable Long id) {
        log.debug("REST request to delete Row : {}", id);
        rowService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
