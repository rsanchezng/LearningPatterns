package com.fime.service;

import com.fime.domain.StudentSchedule;
import com.fime.repository.StudentScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link StudentSchedule}.
 */
@Service
@Transactional
public class StudentScheduleService {

    private final Logger log = LoggerFactory.getLogger(StudentScheduleService.class);

    private final StudentScheduleRepository studentScheduleRepository;

    public StudentScheduleService(StudentScheduleRepository studentScheduleRepository) {
        this.studentScheduleRepository = studentScheduleRepository;
    }

    /**
     * Save a studentSchedule.
     *
     * @param studentSchedule the entity to save.
     * @return the persisted entity.
     */
    public StudentSchedule save(StudentSchedule studentSchedule) {
        log.debug("Request to save StudentSchedule : {}", studentSchedule);
        return studentScheduleRepository.save(studentSchedule);
    }

    /**
     * Get all the studentSchedules.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<StudentSchedule> findAll() {
        log.debug("Request to get all StudentSchedules");
        return studentScheduleRepository.findAll();
    }


    /**
     * Get one studentSchedule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StudentSchedule> findOne(Long id) {
        log.debug("Request to get StudentSchedule : {}", id);
        return studentScheduleRepository.findById(id);
    }

    /**
     * Delete the studentSchedule by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete StudentSchedule : {}", id);
        studentScheduleRepository.deleteById(id);
    }
}
