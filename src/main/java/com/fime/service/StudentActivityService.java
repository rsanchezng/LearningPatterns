package com.fime.service;

import com.fime.domain.StudentActivity;
import com.fime.repository.StudentActivityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link StudentActivity}.
 */
@Service
@Transactional
public class StudentActivityService {

    private final Logger log = LoggerFactory.getLogger(StudentActivityService.class);

    private final StudentActivityRepository studentActivityRepository;

    public StudentActivityService(StudentActivityRepository studentActivityRepository) {
        this.studentActivityRepository = studentActivityRepository;
    }

    /**
     * Save a studentActivity.
     *
     * @param studentActivity the entity to save.
     * @return the persisted entity.
     */
    public StudentActivity save(StudentActivity studentActivity) {
        log.debug("Request to save StudentActivity : {}", studentActivity);
        return studentActivityRepository.save(studentActivity);
    }

    /**
     * Get all the studentActivities.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<StudentActivity> findAll() {
        log.debug("Request to get all StudentActivities");
        return studentActivityRepository.findAll();
    }


    /**
     * Get one studentActivity by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StudentActivity> findOne(Long id) {
        log.debug("Request to get StudentActivity : {}", id);
        return studentActivityRepository.findById(id);
    }

    /**
     * Delete the studentActivity by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete StudentActivity : {}", id);
        studentActivityRepository.deleteById(id);
    }
}
