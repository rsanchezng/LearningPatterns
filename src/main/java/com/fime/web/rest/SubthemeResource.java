package com.fime.web.rest;

import com.fime.domain.Subtheme;
import com.fime.repository.SubthemeRepository;
import com.fime.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.fime.domain.Subtheme}.
 */
@RestController
@RequestMapping("/api")
public class SubthemeResource {

    private final Logger log = LoggerFactory.getLogger(SubthemeResource.class);

    private static final String ENTITY_NAME = "subtheme";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubthemeRepository subthemeRepository;

    public SubthemeResource(SubthemeRepository subthemeRepository) {
        this.subthemeRepository = subthemeRepository;
    }

    /**
     * {@code POST  /subthemes} : Create a new subtheme.
     *
     * @param subtheme the subtheme to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subtheme, or with status {@code 400 (Bad Request)} if the subtheme has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/subthemes")
    public ResponseEntity<Subtheme> createSubtheme(@RequestBody Subtheme subtheme) throws URISyntaxException {
        log.debug("REST request to save Subtheme : {}", subtheme);
        if (subtheme.getId() != null) {
            throw new BadRequestAlertException("A new subtheme cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Subtheme result = subthemeRepository.save(subtheme);
        return ResponseEntity.created(new URI("/api/subthemes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /subthemes} : Updates an existing subtheme.
     *
     * @param subtheme the subtheme to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subtheme,
     * or with status {@code 400 (Bad Request)} if the subtheme is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subtheme couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/subthemes")
    public ResponseEntity<Subtheme> updateSubtheme(@RequestBody Subtheme subtheme) throws URISyntaxException {
        log.debug("REST request to update Subtheme : {}", subtheme);
        if (subtheme.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Subtheme result = subthemeRepository.save(subtheme);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, subtheme.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /subthemes} : get all the subthemes.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subthemes in body.
     */
    @GetMapping("/subthemes")
    public List<Subtheme> getAllSubthemes() {
        log.debug("REST request to get all Subthemes");
        return subthemeRepository.findAll();
    }

    /**
     * {@code GET  /subthemes/:id} : get the "id" subtheme.
     *
     * @param id the id of the subtheme to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subtheme, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/subthemes/{id}")
    public ResponseEntity<Subtheme> getSubtheme(@PathVariable Long id) {
        log.debug("REST request to get Subtheme : {}", id);
        Optional<Subtheme> subtheme = subthemeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(subtheme);
    }

    /**
     * {@code DELETE  /subthemes/:id} : delete the "id" subtheme.
     *
     * @param id the id of the subtheme to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/subthemes/{id}")
    public ResponseEntity<Void> deleteSubtheme(@PathVariable Long id) {
        log.debug("REST request to delete Subtheme : {}", id);
        subthemeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
