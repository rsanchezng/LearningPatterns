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

import com.fime.domain.StudentActivity;
import com.fime.domain.*; // for static metamodels
import com.fime.repository.StudentActivityRepository;
import com.fime.service.dto.StudentActivityCriteria;

/**
 * Service for executing complex queries for {@link StudentActivity} entities in the database.
 * The main input is a {@link StudentActivityCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StudentActivity} or a {@link Page} of {@link StudentActivity} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StudentActivityQueryService extends QueryService<StudentActivity> {

    private final Logger log = LoggerFactory.getLogger(StudentActivityQueryService.class);

    private final StudentActivityRepository studentActivityRepository;

    public StudentActivityQueryService(StudentActivityRepository studentActivityRepository) {
        this.studentActivityRepository = studentActivityRepository;
    }

    /**
     * Return a {@link List} of {@link StudentActivity} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StudentActivity> findByCriteria(StudentActivityCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StudentActivity> specification = createSpecification(criteria);
        return studentActivityRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link StudentActivity} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StudentActivity> findByCriteria(StudentActivityCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StudentActivity> specification = createSpecification(criteria);
        return studentActivityRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StudentActivityCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StudentActivity> specification = createSpecification(criteria);
        return studentActivityRepository.count(specification);
    }

    /**
     * Function to convert {@link StudentActivityCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StudentActivity> createSpecification(StudentActivityCriteria criteria) {
        Specification<StudentActivity> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), StudentActivity_.id));
            }
            if (criteria.getActivityStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActivityStartDate(), StudentActivity_.activityStartDate));
            }
            if (criteria.getActivityEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActivityEndDate(), StudentActivity_.activityEndDate));
            }
            if (criteria.getActivityGrade() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActivityGrade(), StudentActivity_.activityGrade));
            }
            if (criteria.getStudentActivityGradeDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStudentActivityGradeDate(), StudentActivity_.studentActivityGradeDate));
            }
            if (criteria.getStudentActivityCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStudentActivityCreatedDate(), StudentActivity_.studentActivityCreatedDate));
            }
            if (criteria.getStudentActivityCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStudentActivityCreatedBy(), StudentActivity_.studentActivityCreatedBy));
            }
            if (criteria.getStudentActivityModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStudentActivityModifiedDate(), StudentActivity_.studentActivityModifiedDate));
            }
            if (criteria.getStudentActivityModifiedBy() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStudentActivityModifiedBy(), StudentActivity_.studentActivityModifiedBy));
            }
            if (criteria.getActivityId() != null) {
                specification = specification.and(buildSpecification(criteria.getActivityId(),
                    root -> root.join(StudentActivity_.activity, JoinType.LEFT).get(Activity_.id)));
            }
            if (criteria.getStudentscheduleId() != null) {
                specification = specification.and(buildSpecification(criteria.getStudentscheduleId(),
                    root -> root.join(StudentActivity_.studentschedule, JoinType.LEFT).get(StudentSchedule_.id)));
            }
        }
        return specification;
    }
}
