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

import com.fime.domain.Activity;
import com.fime.domain.*; // for static metamodels
import com.fime.repository.ActivityRepository;
import com.fime.service.dto.ActivityCriteria;

/**
 * Service for executing complex queries for {@link Activity} entities in the database.
 * The main input is a {@link ActivityCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Activity} or a {@link Page} of {@link Activity} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ActivityQueryService extends QueryService<Activity> {

    private final Logger log = LoggerFactory.getLogger(ActivityQueryService.class);

    private final ActivityRepository activityRepository;

    public ActivityQueryService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    /**
     * Return a {@link List} of {@link Activity} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Activity> findByCriteria(ActivityCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Activity> specification = createSpecification(criteria);
        return activityRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Activity} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Activity> findByCriteria(ActivityCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Activity> specification = createSpecification(criteria);
        return activityRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ActivityCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Activity> specification = createSpecification(criteria);
        return activityRepository.count(specification);
    }

    /**
     * Function to convert {@link ActivityCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Activity> createSpecification(ActivityCriteria criteria) {
        Specification<Activity> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Activity_.id));
            }
            if (criteria.getActivityName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActivityName(), Activity_.activityName));
            }
            if (criteria.getActivityDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActivityDescription(), Activity_.activityDescription));
            }
            if (criteria.getActivityDuration() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActivityDuration(), Activity_.activityDuration));
            }
            if (criteria.getActivityUtility() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActivityUtility(), Activity_.activityUtility));
            }
            if (criteria.getActivityReqsId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActivityReqsId(), Activity_.activityReqsId));
            }
            if (criteria.getActivityCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActivityCreatedBy(), Activity_.activityCreatedBy));
            }
            if (criteria.getActivityCreationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActivityCreationDate(), Activity_.activityCreationDate));
            }
            if (criteria.getActivityModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActivityModifiedBy(), Activity_.activityModifiedBy));
            }
            if (criteria.getActivityModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActivityModifiedDate(), Activity_.activityModifiedDate));
            }
            if (criteria.getSubthemeId() != null) {
                specification = specification.and(buildSpecification(criteria.getSubthemeId(),
                    root -> root.join(Activity_.subtheme, JoinType.LEFT).get(Subtheme_.id)));
            }
        }
        return specification;
    }
}
