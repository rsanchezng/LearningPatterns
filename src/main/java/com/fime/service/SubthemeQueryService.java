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

import com.fime.domain.Subtheme;
import com.fime.domain.*; // for static metamodels
import com.fime.repository.SubthemeRepository;
import com.fime.service.dto.SubthemeCriteria;

/**
 * Service for executing complex queries for {@link Subtheme} entities in the database.
 * The main input is a {@link SubthemeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Subtheme} or a {@link Page} of {@link Subtheme} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SubthemeQueryService extends QueryService<Subtheme> {

    private final Logger log = LoggerFactory.getLogger(SubthemeQueryService.class);

    private final SubthemeRepository subthemeRepository;

    public SubthemeQueryService(SubthemeRepository subthemeRepository) {
        this.subthemeRepository = subthemeRepository;
    }

    /**
     * Return a {@link List} of {@link Subtheme} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Subtheme> findByCriteria(SubthemeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Subtheme> specification = createSpecification(criteria);
        return subthemeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Subtheme} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Subtheme> findByCriteria(SubthemeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Subtheme> specification = createSpecification(criteria);
        return subthemeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SubthemeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Subtheme> specification = createSpecification(criteria);
        return subthemeRepository.count(specification);
    }

    /**
     * Function to convert {@link SubthemeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Subtheme> createSpecification(SubthemeCriteria criteria) {
        Specification<Subtheme> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Subtheme_.id));
            }
            if (criteria.getSubthemeName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubthemeName(), Subtheme_.subthemeName));
            }
            if (criteria.getSubthemeDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubthemeDescription(), Subtheme_.subthemeDescription));
            }
            if (criteria.getSubthemeCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubthemeCreatedBy(), Subtheme_.subthemeCreatedBy));
            }
            if (criteria.getSubthemeCreationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSubthemeCreationDate(), Subtheme_.subthemeCreationDate));
            }
            if (criteria.getSubthemeModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubthemeModifiedBy(), Subtheme_.subthemeModifiedBy));
            }
            if (criteria.getSubthemeModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSubthemeModifiedDate(), Subtheme_.subthemeModifiedDate));
            }
            if (criteria.getSubthemeMaxGrade() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSubthemeMaxGrade(), Subtheme_.subthemeMaxGrade));
            }
            if (criteria.getThemeId() != null) {
                specification = specification.and(buildSpecification(criteria.getThemeId(),
                    root -> root.join(Subtheme_.theme, JoinType.LEFT).get(Theme_.id)));
            }
        }
        return specification;
    }
}
