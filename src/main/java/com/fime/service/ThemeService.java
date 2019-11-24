package com.fime.service;

import com.fime.domain.Theme;
import com.fime.repository.ThemeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Theme}.
 */
@Service
@Transactional
public class ThemeService {

    private final Logger log = LoggerFactory.getLogger(ThemeService.class);

    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    /**
     * Save a theme.
     *
     * @param theme the entity to save.
     * @return the persisted entity.
     */
    public Theme save(Theme theme) {
        log.debug("Request to save Theme : {}", theme);
        return themeRepository.save(theme);
    }

    /**
     * Get all the themes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Theme> findAll() {
        log.debug("Request to get all Themes");
        return themeRepository.findAll();
    }


    /**
     * Get one theme by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Theme> findOne(Long id) {
        log.debug("Request to get Theme : {}", id);
        return themeRepository.findById(id);
    }

    /**
     * Delete the theme by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Theme : {}", id);
        themeRepository.deleteById(id);
    }
}
