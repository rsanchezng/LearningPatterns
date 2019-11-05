package com.fime.web.rest;

import com.fime.LearningPatternsApp;
import com.fime.domain.Theme;
import com.fime.repository.ThemeRepository;
import com.fime.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.fime.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ThemeResource} REST controller.
 */
@SpringBootTest(classes = LearningPatternsApp.class)
public class ThemeResourceIT {

    private static final String DEFAULT_THEME_NAME = "AAAAAAAAAA";
    private static final String UPDATED_THEME_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_THEME_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_THEME_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_THEME_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_THEME_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_THEME_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_THEME_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_THEME_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_THEME_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_THEME_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_THEME_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restThemeMockMvc;

    private Theme theme;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ThemeResource themeResource = new ThemeResource(themeRepository);
        this.restThemeMockMvc = MockMvcBuilders.standaloneSetup(themeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Theme createEntity(EntityManager em) {
        Theme theme = new Theme()
            .themeName(DEFAULT_THEME_NAME)
            .themeDescription(DEFAULT_THEME_DESCRIPTION)
            .themeCreatedBy(DEFAULT_THEME_CREATED_BY)
            .themeCreationDate(DEFAULT_THEME_CREATION_DATE)
            .themeModifiedBy(DEFAULT_THEME_MODIFIED_BY)
            .themeModifiedDate(DEFAULT_THEME_MODIFIED_DATE);
        return theme;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Theme createUpdatedEntity(EntityManager em) {
        Theme theme = new Theme()
            .themeName(UPDATED_THEME_NAME)
            .themeDescription(UPDATED_THEME_DESCRIPTION)
            .themeCreatedBy(UPDATED_THEME_CREATED_BY)
            .themeCreationDate(UPDATED_THEME_CREATION_DATE)
            .themeModifiedBy(UPDATED_THEME_MODIFIED_BY)
            .themeModifiedDate(UPDATED_THEME_MODIFIED_DATE);
        return theme;
    }

    @BeforeEach
    public void initTest() {
        theme = createEntity(em);
    }

    @Test
    @Transactional
    public void createTheme() throws Exception {
        int databaseSizeBeforeCreate = themeRepository.findAll().size();

        // Create the Theme
        restThemeMockMvc.perform(post("/api/themes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(theme)))
            .andExpect(status().isCreated());

        // Validate the Theme in the database
        List<Theme> themeList = themeRepository.findAll();
        assertThat(themeList).hasSize(databaseSizeBeforeCreate + 1);
        Theme testTheme = themeList.get(themeList.size() - 1);
        assertThat(testTheme.getThemeName()).isEqualTo(DEFAULT_THEME_NAME);
        assertThat(testTheme.getThemeDescription()).isEqualTo(DEFAULT_THEME_DESCRIPTION);
        assertThat(testTheme.getThemeCreatedBy()).isEqualTo(DEFAULT_THEME_CREATED_BY);
        assertThat(testTheme.getThemeCreationDate()).isEqualTo(DEFAULT_THEME_CREATION_DATE);
        assertThat(testTheme.getThemeModifiedBy()).isEqualTo(DEFAULT_THEME_MODIFIED_BY);
        assertThat(testTheme.getThemeModifiedDate()).isEqualTo(DEFAULT_THEME_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createThemeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = themeRepository.findAll().size();

        // Create the Theme with an existing ID
        theme.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restThemeMockMvc.perform(post("/api/themes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(theme)))
            .andExpect(status().isBadRequest());

        // Validate the Theme in the database
        List<Theme> themeList = themeRepository.findAll();
        assertThat(themeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllThemes() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList
        restThemeMockMvc.perform(get("/api/themes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(theme.getId().intValue())))
            .andExpect(jsonPath("$.[*].themeName").value(hasItem(DEFAULT_THEME_NAME)))
            .andExpect(jsonPath("$.[*].themeDescription").value(hasItem(DEFAULT_THEME_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].themeCreatedBy").value(hasItem(DEFAULT_THEME_CREATED_BY)))
            .andExpect(jsonPath("$.[*].themeCreationDate").value(hasItem(DEFAULT_THEME_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].themeModifiedBy").value(hasItem(DEFAULT_THEME_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].themeModifiedDate").value(hasItem(DEFAULT_THEME_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getTheme() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get the theme
        restThemeMockMvc.perform(get("/api/themes/{id}", theme.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(theme.getId().intValue()))
            .andExpect(jsonPath("$.themeName").value(DEFAULT_THEME_NAME))
            .andExpect(jsonPath("$.themeDescription").value(DEFAULT_THEME_DESCRIPTION))
            .andExpect(jsonPath("$.themeCreatedBy").value(DEFAULT_THEME_CREATED_BY))
            .andExpect(jsonPath("$.themeCreationDate").value(DEFAULT_THEME_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.themeModifiedBy").value(DEFAULT_THEME_MODIFIED_BY))
            .andExpect(jsonPath("$.themeModifiedDate").value(DEFAULT_THEME_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTheme() throws Exception {
        // Get the theme
        restThemeMockMvc.perform(get("/api/themes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTheme() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        int databaseSizeBeforeUpdate = themeRepository.findAll().size();

        // Update the theme
        Theme updatedTheme = themeRepository.findById(theme.getId()).get();
        // Disconnect from session so that the updates on updatedTheme are not directly saved in db
        em.detach(updatedTheme);
        updatedTheme
            .themeName(UPDATED_THEME_NAME)
            .themeDescription(UPDATED_THEME_DESCRIPTION)
            .themeCreatedBy(UPDATED_THEME_CREATED_BY)
            .themeCreationDate(UPDATED_THEME_CREATION_DATE)
            .themeModifiedBy(UPDATED_THEME_MODIFIED_BY)
            .themeModifiedDate(UPDATED_THEME_MODIFIED_DATE);

        restThemeMockMvc.perform(put("/api/themes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTheme)))
            .andExpect(status().isOk());

        // Validate the Theme in the database
        List<Theme> themeList = themeRepository.findAll();
        assertThat(themeList).hasSize(databaseSizeBeforeUpdate);
        Theme testTheme = themeList.get(themeList.size() - 1);
        assertThat(testTheme.getThemeName()).isEqualTo(UPDATED_THEME_NAME);
        assertThat(testTheme.getThemeDescription()).isEqualTo(UPDATED_THEME_DESCRIPTION);
        assertThat(testTheme.getThemeCreatedBy()).isEqualTo(UPDATED_THEME_CREATED_BY);
        assertThat(testTheme.getThemeCreationDate()).isEqualTo(UPDATED_THEME_CREATION_DATE);
        assertThat(testTheme.getThemeModifiedBy()).isEqualTo(UPDATED_THEME_MODIFIED_BY);
        assertThat(testTheme.getThemeModifiedDate()).isEqualTo(UPDATED_THEME_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTheme() throws Exception {
        int databaseSizeBeforeUpdate = themeRepository.findAll().size();

        // Create the Theme

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThemeMockMvc.perform(put("/api/themes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(theme)))
            .andExpect(status().isBadRequest());

        // Validate the Theme in the database
        List<Theme> themeList = themeRepository.findAll();
        assertThat(themeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTheme() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        int databaseSizeBeforeDelete = themeRepository.findAll().size();

        // Delete the theme
        restThemeMockMvc.perform(delete("/api/themes/{id}", theme.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Theme> themeList = themeRepository.findAll();
        assertThat(themeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Theme.class);
        Theme theme1 = new Theme();
        theme1.setId(1L);
        Theme theme2 = new Theme();
        theme2.setId(theme1.getId());
        assertThat(theme1).isEqualTo(theme2);
        theme2.setId(2L);
        assertThat(theme1).isNotEqualTo(theme2);
        theme1.setId(null);
        assertThat(theme1).isNotEqualTo(theme2);
    }
}
