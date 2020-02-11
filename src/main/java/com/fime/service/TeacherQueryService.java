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

import com.fime.domain.Teacher;
import com.fime.domain.*; // for static metamodels
import com.fime.repository.TeacherRepository;
import com.fime.service.dto.TeacherCriteria;

/**
 * Service for executing complex queries for {@link Teacher} entities in the database.
 * The main input is a {@link TeacherCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Teacher} or a {@link Page} of {@link Teacher} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TeacherQueryService extends QueryService<Teacher> {

    private final Logger log = LoggerFactory.getLogger(TeacherQueryService.class);

    private final TeacherRepository teacherRepository;

    public TeacherQueryService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    /**
     * Return a {@link List} of {@link Teacher} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Teacher> findByCriteria(TeacherCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Teacher> specification = createSpecification(criteria);
        return teacherRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Teacher} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Teacher> findByCriteria(TeacherCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Teacher> specification = createSpecification(criteria);
        return teacherRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TeacherCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Teacher> specification = createSpecification(criteria);
        return teacherRepository.count(specification);
    }

    /**
     * Function to convert {@link TeacherCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Teacher> createSpecification(TeacherCriteria criteria) {
        Specification<Teacher> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Teacher_.id));
            }
            if (criteria.getTeacherFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTeacherFirstName(), Teacher_.teacherFirstName));
            }
            if (criteria.getTeacherLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTeacherLastName(), Teacher_.teacherLastName));
            }
            if (criteria.getTeacherEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTeacherEmail(), Teacher_.teacherEmail));
            }
            if (criteria.getTeacherPassword() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTeacherPassword(), Teacher_.teacherPassword));
            }
            if (criteria.getTeacherCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTeacherCreatedBy(), Teacher_.teacherCreatedBy));
            }
            if (criteria.getTeacherCreationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTeacherCreationDate(), Teacher_.teacherCreationDate));
            }
            if (criteria.getTeacherModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTeacherModifiedBy(), Teacher_.teacherModifiedBy));
            }
            if (criteria.getTeacherModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTeacherModifiedDate(), Teacher_.teacherModifiedDate));
            }
        }
        return specification;
    }
}
