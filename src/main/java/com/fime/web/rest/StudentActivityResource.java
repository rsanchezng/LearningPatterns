package com.fime.web.rest;

import com.fime.domain.StudentActivity;
import com.fime.service.StudentActivityService;
import com.fime.web.rest.errors.BadRequestAlertException;
import com.fime.service.dto.StudentActivityCriteria;
import com.fime.service.StudentActivityQueryService;

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
 * REST controller for managing {@link com.fime.domain.StudentActivity}.
 */
@RestController
@RequestMapping("/api")
public class StudentActivityResource {

    private final Logger log = LoggerFactory.getLogger(StudentActivityResource.class);

    private static final String ENTITY_NAME = "studentActivity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StudentActivityService studentActivityService;

    private final StudentActivityQueryService studentActivityQueryService;

    public StudentActivityResource(StudentActivityService studentActivityService, StudentActivityQueryService studentActivityQueryService) {
        this.studentActivityService = studentActivityService;
        this.studentActivityQueryService = studentActivityQueryService;
    }

    /**
     * {@code POST  /student-activities} : Create a new studentActivity.
     *
     * @param studentActivity the studentActivity to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new studentActivity, or with status {@code 400 (Bad Request)} if the studentActivity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/student-activities")
    public ResponseEntity<StudentActivity> createStudentActivity(@RequestBody StudentActivity studentActivity) throws URISyntaxException {
        log.debug("REST request to save StudentActivity : {}", studentActivity);
        if (studentActivity.getId() != null) {
            throw new BadRequestAlertException("A new studentActivity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StudentActivity result = studentActivityService.save(studentActivity);
        return ResponseEntity.created(new URI("/api/student-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /student-activities} : Updates an existing studentActivity.
     *
     * @param studentActivity the studentActivity to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated studentActivity,
     * or with status {@code 400 (Bad Request)} if the studentActivity is not valid,
     * or with status {@code 500 (Internal Server Error)} if the studentActivity couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/student-activities")
    public ResponseEntity<StudentActivity> updateStudentActivity(@RequestBody StudentActivity studentActivity) throws URISyntaxException {
        log.debug("REST request to update StudentActivity : {}", studentActivity);
        if (studentActivity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StudentActivity result = studentActivityService.save(studentActivity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, studentActivity.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /student-activities} : get all the studentActivities.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of studentActivities in body.
     */
    @GetMapping("/student-activities")
    public ResponseEntity<List<StudentActivity>> getAllStudentActivities(StudentActivityCriteria criteria) {
        log.debug("REST request to get StudentActivities by criteria: {}", criteria);
        List<StudentActivity> entityList = studentActivityQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /student-activities/count} : count all the studentActivities.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/student-activities/count")
    public ResponseEntity<Long> countStudentActivities(StudentActivityCriteria criteria) {
        log.debug("REST request to count StudentActivities by criteria: {}", criteria);
        return ResponseEntity.ok().body(studentActivityQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /student-activities/:id} : get the "id" studentActivity.
     *
     * @param id the id of the studentActivity to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the studentActivity, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/student-activities/{id}")
    public ResponseEntity<StudentActivity> getStudentActivity(@PathVariable Long id) {
        log.debug("REST request to get StudentActivity : {}", id);
        Optional<StudentActivity> studentActivity = studentActivityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(studentActivity);
    }

    /**
     * {@code DELETE  /student-activities/:id} : delete the "id" studentActivity.
     *
     * @param id the id of the studentActivity to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/student-activities/{id}")
    public ResponseEntity<Void> deleteStudentActivity(@PathVariable Long id) {
        log.debug("REST request to delete StudentActivity : {}", id);
        studentActivityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
