package com.fime.web.rest;

import com.fime.domain.Teacher;
import com.fime.service.TeacherService;
import com.fime.web.rest.errors.BadRequestAlertException;
import com.fime.service.dto.TeacherCriteria;
import com.fime.service.TeacherQueryService;

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
 * REST controller for managing {@link com.fime.domain.Teacher}.
 */
@RestController
@RequestMapping("/api")
public class TeacherResource {

    private final Logger log = LoggerFactory.getLogger(TeacherResource.class);

    private static final String ENTITY_NAME = "teacher";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TeacherService teacherService;

    private final TeacherQueryService teacherQueryService;

    public TeacherResource(TeacherService teacherService, TeacherQueryService teacherQueryService) {
        this.teacherService = teacherService;
        this.teacherQueryService = teacherQueryService;
    }

    /**
     * {@code POST  /teachers} : Create a new teacher.
     *
     * @param teacher the teacher to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new teacher, or with status {@code 400 (Bad Request)} if the teacher has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/teachers")
    public ResponseEntity<Teacher> createTeacher(@RequestBody Teacher teacher) throws URISyntaxException {
        log.debug("REST request to save Teacher : {}", teacher);
        if (teacher.getId() != null) {
            throw new BadRequestAlertException("A new teacher cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Teacher result = teacherService.save(teacher);
        return ResponseEntity.created(new URI("/api/teachers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /teachers} : Updates an existing teacher.
     *
     * @param teacher the teacher to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teacher,
     * or with status {@code 400 (Bad Request)} if the teacher is not valid,
     * or with status {@code 500 (Internal Server Error)} if the teacher couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/teachers")
    public ResponseEntity<Teacher> updateTeacher(@RequestBody Teacher teacher) throws URISyntaxException {
        log.debug("REST request to update Teacher : {}", teacher);
        if (teacher.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Teacher result = teacherService.save(teacher);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, teacher.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /teachers} : get all the teachers.
     *

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of teachers in body.
     */
    @GetMapping("/teachers")
    public ResponseEntity<List<Teacher>> getAllTeachers(TeacherCriteria criteria) {
        log.debug("REST request to get Teachers by criteria: {}", criteria);
        List<Teacher> entityList = teacherQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /teachers/count} : count all the teachers.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/teachers/count")
    public ResponseEntity<Long> countTeachers(TeacherCriteria criteria) {
        log.debug("REST request to count Teachers by criteria: {}", criteria);
        return ResponseEntity.ok().body(teacherQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /teachers/:id} : get the "id" teacher.
     *
     * @param id the id of the teacher to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the teacher, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/teachers/{id}")
    public ResponseEntity<Teacher> getTeacher(@PathVariable Long id) {
        log.debug("REST request to get Teacher : {}", id);
        Optional<Teacher> teacher = teacherService.findOne(id);
        return ResponseUtil.wrapOrNotFound(teacher);
    }

    /**
     * {@code DELETE  /teachers/:id} : delete the "id" teacher.
     *
     * @param id the id of the teacher to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/teachers/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        log.debug("REST request to delete Teacher : {}", id);
        teacherService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
