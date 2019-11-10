package com.fime.web.rest;

import com.fime.domain.StudentSchedule;
import com.fime.repository.StudentScheduleRepository;
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
 * REST controller for managing {@link com.fime.domain.StudentSchedule}.
 */
@RestController
@RequestMapping("/api")
public class StudentScheduleResource {

    private final Logger log = LoggerFactory.getLogger(StudentScheduleResource.class);

    private static final String ENTITY_NAME = "studentSchedule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StudentScheduleRepository studentScheduleRepository;

    public StudentScheduleResource(StudentScheduleRepository studentScheduleRepository) {
        this.studentScheduleRepository = studentScheduleRepository;
    }

    /**
     * {@code POST  /student-schedules} : Create a new studentSchedule.
     *
     * @param studentSchedule the studentSchedule to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new studentSchedule, or with status {@code 400 (Bad Request)} if the studentSchedule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/student-schedules")
    public ResponseEntity<StudentSchedule> createStudentSchedule(@RequestBody StudentSchedule studentSchedule) throws URISyntaxException {
        log.debug("REST request to save StudentSchedule : {}", studentSchedule);
        if (studentSchedule.getId() != null) {
            throw new BadRequestAlertException("A new studentSchedule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StudentSchedule result = studentScheduleRepository.save(studentSchedule);
        return ResponseEntity.created(new URI("/api/student-schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /student-schedules} : Updates an existing studentSchedule.
     *
     * @param studentSchedule the studentSchedule to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated studentSchedule,
     * or with status {@code 400 (Bad Request)} if the studentSchedule is not valid,
     * or with status {@code 500 (Internal Server Error)} if the studentSchedule couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/student-schedules")
    public ResponseEntity<StudentSchedule> updateStudentSchedule(@RequestBody StudentSchedule studentSchedule) throws URISyntaxException {
        log.debug("REST request to update StudentSchedule : {}", studentSchedule);
        if (studentSchedule.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StudentSchedule result = studentScheduleRepository.save(studentSchedule);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, studentSchedule.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /student-schedules} : get all the studentSchedules.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of studentSchedules in body.
     */
    @GetMapping("/student-schedules")
    public List<StudentSchedule> getAllStudentSchedules() {
        log.debug("REST request to get all StudentSchedules");
        return studentScheduleRepository.findAll();
    }

    /**
     * {@code GET  /student-schedules/:id} : get the "id" studentSchedule.
     *
     * @param id the id of the studentSchedule to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the studentSchedule, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/student-schedules/{id}")
    public ResponseEntity<StudentSchedule> getStudentSchedule(@PathVariable Long id) {
        log.debug("REST request to get StudentSchedule : {}", id);
        Optional<StudentSchedule> studentSchedule = studentScheduleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(studentSchedule);
    }

    /**
     * {@code DELETE  /student-schedules/:id} : delete the "id" studentSchedule.
     *
     * @param id the id of the studentSchedule to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/student-schedules/{id}")
    public ResponseEntity<Void> deleteStudentSchedule(@PathVariable Long id) {
        log.debug("REST request to delete StudentSchedule : {}", id);
        studentScheduleRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
