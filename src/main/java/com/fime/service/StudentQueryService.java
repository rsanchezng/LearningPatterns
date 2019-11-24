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

import com.fime.domain.Student;
import com.fime.domain.*; // for static metamodels
import com.fime.repository.StudentRepository;
import com.fime.service.dto.StudentCriteria;

/**
 * Service for executing complex queries for {@link Student} entities in the database.
 * The main input is a {@link StudentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Student} or a {@link Page} of {@link Student} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StudentQueryService extends QueryService<Student> {

    private final Logger log = LoggerFactory.getLogger(StudentQueryService.class);

    private final StudentRepository studentRepository;

    public StudentQueryService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * Return a {@link List} of {@link Student} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Student> findByCriteria(StudentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Student> specification = createSpecification(criteria);
        return studentRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Student} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Student> findByCriteria(StudentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Student> specification = createSpecification(criteria);
        return studentRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StudentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Student> specification = createSpecification(criteria);
        return studentRepository.count(specification);
    }

    /**
     * Function to convert {@link StudentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Student> createSpecification(StudentCriteria criteria) {
        Specification<Student> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Student_.id));
            }
            if (criteria.getStudentFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStudentFirstName(), Student_.studentFirstName));
            }
            if (criteria.getStudentLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStudentLastName(), Student_.studentLastName));
            }
            if (criteria.getStudentEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStudentEmail(), Student_.studentEmail));
            }
            if (criteria.getStudentPassword() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStudentPassword(), Student_.studentPassword));
            }
            if (criteria.getStudentCredits() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStudentCredits(), Student_.studentCredits));
            }
            if (criteria.getStudentCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStudentCreatedBy(), Student_.studentCreatedBy));
            }
            if (criteria.getStudentCreationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStudentCreationDate(), Student_.studentCreationDate));
            }
            if (criteria.getStudentModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStudentModifiedBy(), Student_.studentModifiedBy));
            }
            if (criteria.getStudentModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStudentModifiedDate(), Student_.studentModifiedDate));
            }
        }
        return specification;
    }
}
