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

import com.fime.domain.Theme;
import com.fime.domain.*; // for static metamodels
import com.fime.repository.ThemeRepository;
import com.fime.service.dto.ThemeCriteria;

/**
 * Service for executing complex queries for {@link Theme} entities in the database.
 * The main input is a {@link ThemeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Theme} or a {@link Page} of {@link Theme} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ThemeQueryService extends QueryService<Theme> {

    private final Logger log = LoggerFactory.getLogger(ThemeQueryService.class);

    private final ThemeRepository themeRepository;

    public ThemeQueryService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    /**
     * Return a {@link List} of {@link Theme} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Theme> findByCriteria(ThemeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Theme> specification = createSpecification(criteria);
        return themeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Theme} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Theme> findByCriteria(ThemeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Theme> specification = createSpecification(criteria);
        return themeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ThemeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Theme> specification = createSpecification(criteria);
        return themeRepository.count(specification);
    }

    /**
     * Function to convert {@link ThemeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Theme> createSpecification(ThemeCriteria criteria) {
        Specification<Theme> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Theme_.id));
            }
            if (criteria.getThemeName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getThemeName(), Theme_.themeName));
            }
            if (criteria.getThemeDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getThemeDescription(), Theme_.themeDescription));
            }
            if (criteria.getThemeCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getThemeCreatedBy(), Theme_.themeCreatedBy));
            }
            if (criteria.getThemeCreationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getThemeCreationDate(), Theme_.themeCreationDate));
            }
            if (criteria.getThemeModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getThemeModifiedBy(), Theme_.themeModifiedBy));
            }
            if (criteria.getThemeModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getThemeModifiedDate(), Theme_.themeModifiedDate));
            }
            if (criteria.getSubjectId() != null) {
                specification = specification.and(buildSpecification(criteria.getSubjectId(),
                    root -> root.join(Theme_.subject, JoinType.LEFT).get(Subject_.id)));
            }
        }
        return specification;
    }
}
