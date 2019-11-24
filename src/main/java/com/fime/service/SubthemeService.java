package com.fime.service;

import com.fime.domain.Subtheme;
import com.fime.repository.SubthemeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Subtheme}.
 */
@Service
@Transactional
public class SubthemeService {

    private final Logger log = LoggerFactory.getLogger(SubthemeService.class);

    private final SubthemeRepository subthemeRepository;

    public SubthemeService(SubthemeRepository subthemeRepository) {
        this.subthemeRepository = subthemeRepository;
    }

    /**
     * Save a subtheme.
     *
     * @param subtheme the entity to save.
     * @return the persisted entity.
     */
    public Subtheme save(Subtheme subtheme) {
        log.debug("Request to save Subtheme : {}", subtheme);
        return subthemeRepository.save(subtheme);
    }

    /**
     * Get all the subthemes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Subtheme> findAll() {
        log.debug("Request to get all Subthemes");
        return subthemeRepository.findAll();
    }


    /**
     * Get one subtheme by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Subtheme> findOne(Long id) {
        log.debug("Request to get Subtheme : {}", id);
        return subthemeRepository.findById(id);
    }

    /**
     * Delete the subtheme by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Subtheme : {}", id);
        subthemeRepository.deleteById(id);
    }
}
