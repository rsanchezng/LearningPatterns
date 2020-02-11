package com.fime.web.rest;

import com.fime.domain.Subtheme;
import com.fime.service.SubthemeService;
import com.fime.web.rest.errors.BadRequestAlertException;
import com.fime.service.dto.SubthemeCriteria;
import com.fime.service.SubthemeQueryService;

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

    private final SubthemeService subthemeService;

    private final SubthemeQueryService subthemeQueryService;

    public SubthemeResource(SubthemeService subthemeService, SubthemeQueryService subthemeQueryService) {
        this.subthemeService = subthemeService;
        this.subthemeQueryService = subthemeQueryService;
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
        Subtheme result = subthemeService.save(subtheme);
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
        Subtheme result = subthemeService.save(subtheme);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, subtheme.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /subthemes} : get all the subthemes.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subthemes in body.
     */
    @GetMapping("/subthemes")
    public ResponseEntity<List<Subtheme>> getAllSubthemes(SubthemeCriteria criteria) {
        log.debug("REST request to get Subthemes by criteria: {}", criteria);
        List<Subtheme> entityList = subthemeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /subthemes/count} : count all the subthemes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/subthemes/count")
    public ResponseEntity<Long> countSubthemes(SubthemeCriteria criteria) {
        log.debug("REST request to count Subthemes by criteria: {}", criteria);
        return ResponseEntity.ok().body(subthemeQueryService.countByCriteria(criteria));
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
        Optional<Subtheme> subtheme = subthemeService.findOne(id);
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
        subthemeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
