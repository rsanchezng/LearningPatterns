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

import com.fime.domain.Subject;
import com.fime.domain.*; // for static metamodels
import com.fime.repository.SubjectRepository;
import com.fime.service.dto.SubjectCriteria;

/**
 * Service for executing complex queries for {@link Subject} entities in the database.
 * The main input is a {@link SubjectCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Subject} or a {@link Page} of {@link Subject} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SubjectQueryService extends QueryService<Subject> {

    private final Logger log = LoggerFactory.getLogger(SubjectQueryService.class);

    private final SubjectRepository subjectRepository;

    public SubjectQueryService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    /**
     * Return a {@link List} of {@link Subject} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Subject> findByCriteria(SubjectCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Subject> specification = createSpecification(criteria);
        return subjectRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Subject} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Subject> findByCriteria(SubjectCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Subject> specification = createSpecification(criteria);
        return subjectRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SubjectCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Subject> specification = createSpecification(criteria);
        return subjectRepository.count(specification);
    }

    /**
     * Function to convert {@link SubjectCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Subject> createSpecification(SubjectCriteria criteria) {
        Specification<Subject> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Subject_.id));
            }
            if (criteria.getSubjectName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubjectName(), Subject_.subjectName));
            }
            if (criteria.getSubjectDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubjectDescription(), Subject_.subjectDescription));
            }
            if (criteria.getSubjectCredits() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSubjectCredits(), Subject_.subjectCredits));
            }
            if (criteria.getSubjectCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubjectCreatedBy(), Subject_.subjectCreatedBy));
            }
            if (criteria.getSubjectCreationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSubjectCreationDate(), Subject_.subjectCreationDate));
            }
            if (criteria.getSubjectModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubjectModifiedBy(), Subject_.subjectModifiedBy));
            }
            if (criteria.getSubjectModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSubjectModifiedDate(), Subject_.subjectModifiedDate));
            }
            if (criteria.getTeacherId() != null) {
                specification = specification.and(buildSpecification(criteria.getTeacherId(),
                    root -> root.join(Subject_.teacher, JoinType.LEFT).get(Teacher_.id)));
            }
        }
        return specification;
    }
}
