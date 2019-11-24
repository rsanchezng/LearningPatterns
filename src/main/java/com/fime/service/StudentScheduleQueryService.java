package com.fime.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.fime.domain.StudentSchedule;
import com.fime.domain.*; // for static metamodels
import com.fime.repository.StudentScheduleRepository;
import com.fime.service.dto.StudentScheduleCriteria;

/**
 * Service for executing complex queries for {@link StudentSchedule} entities in the database.
 * The main input is a {@link StudentScheduleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StudentSchedule} or a {@link Page} of {@link StudentSchedule} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StudentScheduleQueryService extends QueryService<StudentSchedule> {

    private final Logger log = LoggerFactory.getLogger(StudentScheduleQueryService.class);

    private final StudentScheduleRepository studentScheduleRepository;

    public StudentScheduleQueryService(StudentScheduleRepository studentScheduleRepository) {
        this.studentScheduleRepository = studentScheduleRepository;
    }

    /**
     * Return a {@link List} of {@link StudentSchedule} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StudentSchedule> findByCriteria(StudentScheduleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StudentSchedule> specification = createSpecification(criteria);
        return studentScheduleRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link StudentSchedule} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StudentSchedule> findByCriteria(StudentScheduleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StudentSchedule> specification = createSpecification(criteria);
        return studentScheduleRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StudentScheduleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StudentSchedule> specification = createSpecification(criteria);
        return studentScheduleRepository.count(specification);
    }

    /**
     * Function to convert {@link StudentScheduleCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StudentSchedule> createSpecification(StudentScheduleCriteria criteria) {
        Specification<StudentSchedule> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), StudentSchedule_.id));
            }
            if (criteria.getStudentScheduleCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStudentScheduleCreatedBy(), StudentSchedule_.studentScheduleCreatedBy));
            }
            if (criteria.getStudentScheduleCreationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStudentScheduleCreationDate(), StudentSchedule_.studentScheduleCreationDate));
            }
            if (criteria.getStudentScheduleModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStudentScheduleModifiedBy(), StudentSchedule_.studentScheduleModifiedBy));
            }
            if (criteria.getStudentScheduleModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStudentScheduleModifiedDate(), StudentSchedule_.studentScheduleModifiedDate));
            }
            if (criteria.getGroupId() != null) {
                specification = specification.and(buildSpecification(criteria.getGroupId(),
                    root -> root.join(StudentSchedule_.group, JoinType.LEFT).get(Group_.id)));
            }
            if (criteria.getStudentId() != null) {
                specification = specification.and(buildSpecification(criteria.getStudentId(),
                    root -> root.join(StudentSchedule_.student, JoinType.LEFT).get(Student_.id)));
            }
        }
        return specification;
    }
}
