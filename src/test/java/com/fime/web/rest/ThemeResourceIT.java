package com.fime.web.rest;

import com.fime.LearningPatternsApp;
import com.fime.domain.Theme;
import com.fime.domain.Subject;
import com.fime.repository.ThemeRepository;
import com.fime.service.ThemeService;
import com.fime.web.rest.errors.ExceptionTranslator;
import com.fime.service.dto.ThemeCriteria;
import com.fime.service.ThemeQueryService;

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
    private static final LocalDate SMALLER_THEME_CREATION_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_THEME_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_THEME_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_THEME_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_THEME_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_THEME_MODIFIED_DATE = LocalDate.ofEpochDay(-1L);

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private ThemeService themeService;

    @Autowired
    private ThemeQueryService themeQueryService;

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
        final ThemeResource themeResource = new ThemeResource(themeService, themeQueryService);
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
    public void getThemesByIdFiltering() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        Long id = theme.getId();

        defaultThemeShouldBeFound("id.equals=" + id);
        defaultThemeShouldNotBeFound("id.notEquals=" + id);

        defaultThemeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultThemeShouldNotBeFound("id.greaterThan=" + id);

        defaultThemeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultThemeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllThemesByThemeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeName equals to DEFAULT_THEME_NAME
        defaultThemeShouldBeFound("themeName.equals=" + DEFAULT_THEME_NAME);

        // Get all the themeList where themeName equals to UPDATED_THEME_NAME
        defaultThemeShouldNotBeFound("themeName.equals=" + UPDATED_THEME_NAME);
    }

    @Test
    @Transactional
    public void getAllThemesByThemeNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeName not equals to DEFAULT_THEME_NAME
        defaultThemeShouldNotBeFound("themeName.notEquals=" + DEFAULT_THEME_NAME);

        // Get all the themeList where themeName not equals to UPDATED_THEME_NAME
        defaultThemeShouldBeFound("themeName.notEquals=" + UPDATED_THEME_NAME);
    }

    @Test
    @Transactional
    public void getAllThemesByThemeNameIsInShouldWork() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeName in DEFAULT_THEME_NAME or UPDATED_THEME_NAME
        defaultThemeShouldBeFound("themeName.in=" + DEFAULT_THEME_NAME + "," + UPDATED_THEME_NAME);

        // Get all the themeList where themeName equals to UPDATED_THEME_NAME
        defaultThemeShouldNotBeFound("themeName.in=" + UPDATED_THEME_NAME);
    }

    @Test
    @Transactional
    public void getAllThemesByThemeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeName is not null
        defaultThemeShouldBeFound("themeName.specified=true");

        // Get all the themeList where themeName is null
        defaultThemeShouldNotBeFound("themeName.specified=false");
    }
                @Test
    @Transactional
    public void getAllThemesByThemeNameContainsSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeName contains DEFAULT_THEME_NAME
        defaultThemeShouldBeFound("themeName.contains=" + DEFAULT_THEME_NAME);

        // Get all the themeList where themeName contains UPDATED_THEME_NAME
        defaultThemeShouldNotBeFound("themeName.contains=" + UPDATED_THEME_NAME);
    }

    @Test
    @Transactional
    public void getAllThemesByThemeNameNotContainsSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeName does not contain DEFAULT_THEME_NAME
        defaultThemeShouldNotBeFound("themeName.doesNotContain=" + DEFAULT_THEME_NAME);

        // Get all the themeList where themeName does not contain UPDATED_THEME_NAME
        defaultThemeShouldBeFound("themeName.doesNotContain=" + UPDATED_THEME_NAME);
    }


    @Test
    @Transactional
    public void getAllThemesByThemeDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeDescription equals to DEFAULT_THEME_DESCRIPTION
        defaultThemeShouldBeFound("themeDescription.equals=" + DEFAULT_THEME_DESCRIPTION);

        // Get all the themeList where themeDescription equals to UPDATED_THEME_DESCRIPTION
        defaultThemeShouldNotBeFound("themeDescription.equals=" + UPDATED_THEME_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllThemesByThemeDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeDescription not equals to DEFAULT_THEME_DESCRIPTION
        defaultThemeShouldNotBeFound("themeDescription.notEquals=" + DEFAULT_THEME_DESCRIPTION);

        // Get all the themeList where themeDescription not equals to UPDATED_THEME_DESCRIPTION
        defaultThemeShouldBeFound("themeDescription.notEquals=" + UPDATED_THEME_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllThemesByThemeDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeDescription in DEFAULT_THEME_DESCRIPTION or UPDATED_THEME_DESCRIPTION
        defaultThemeShouldBeFound("themeDescription.in=" + DEFAULT_THEME_DESCRIPTION + "," + UPDATED_THEME_DESCRIPTION);

        // Get all the themeList where themeDescription equals to UPDATED_THEME_DESCRIPTION
        defaultThemeShouldNotBeFound("themeDescription.in=" + UPDATED_THEME_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllThemesByThemeDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeDescription is not null
        defaultThemeShouldBeFound("themeDescription.specified=true");

        // Get all the themeList where themeDescription is null
        defaultThemeShouldNotBeFound("themeDescription.specified=false");
    }
                @Test
    @Transactional
    public void getAllThemesByThemeDescriptionContainsSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeDescription contains DEFAULT_THEME_DESCRIPTION
        defaultThemeShouldBeFound("themeDescription.contains=" + DEFAULT_THEME_DESCRIPTION);

        // Get all the themeList where themeDescription contains UPDATED_THEME_DESCRIPTION
        defaultThemeShouldNotBeFound("themeDescription.contains=" + UPDATED_THEME_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllThemesByThemeDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeDescription does not contain DEFAULT_THEME_DESCRIPTION
        defaultThemeShouldNotBeFound("themeDescription.doesNotContain=" + DEFAULT_THEME_DESCRIPTION);

        // Get all the themeList where themeDescription does not contain UPDATED_THEME_DESCRIPTION
        defaultThemeShouldBeFound("themeDescription.doesNotContain=" + UPDATED_THEME_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllThemesByThemeCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeCreatedBy equals to DEFAULT_THEME_CREATED_BY
        defaultThemeShouldBeFound("themeCreatedBy.equals=" + DEFAULT_THEME_CREATED_BY);

        // Get all the themeList where themeCreatedBy equals to UPDATED_THEME_CREATED_BY
        defaultThemeShouldNotBeFound("themeCreatedBy.equals=" + UPDATED_THEME_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllThemesByThemeCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeCreatedBy not equals to DEFAULT_THEME_CREATED_BY
        defaultThemeShouldNotBeFound("themeCreatedBy.notEquals=" + DEFAULT_THEME_CREATED_BY);

        // Get all the themeList where themeCreatedBy not equals to UPDATED_THEME_CREATED_BY
        defaultThemeShouldBeFound("themeCreatedBy.notEquals=" + UPDATED_THEME_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllThemesByThemeCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeCreatedBy in DEFAULT_THEME_CREATED_BY or UPDATED_THEME_CREATED_BY
        defaultThemeShouldBeFound("themeCreatedBy.in=" + DEFAULT_THEME_CREATED_BY + "," + UPDATED_THEME_CREATED_BY);

        // Get all the themeList where themeCreatedBy equals to UPDATED_THEME_CREATED_BY
        defaultThemeShouldNotBeFound("themeCreatedBy.in=" + UPDATED_THEME_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllThemesByThemeCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeCreatedBy is not null
        defaultThemeShouldBeFound("themeCreatedBy.specified=true");

        // Get all the themeList where themeCreatedBy is null
        defaultThemeShouldNotBeFound("themeCreatedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllThemesByThemeCreatedByContainsSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeCreatedBy contains DEFAULT_THEME_CREATED_BY
        defaultThemeShouldBeFound("themeCreatedBy.contains=" + DEFAULT_THEME_CREATED_BY);

        // Get all the themeList where themeCreatedBy contains UPDATED_THEME_CREATED_BY
        defaultThemeShouldNotBeFound("themeCreatedBy.contains=" + UPDATED_THEME_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllThemesByThemeCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeCreatedBy does not contain DEFAULT_THEME_CREATED_BY
        defaultThemeShouldNotBeFound("themeCreatedBy.doesNotContain=" + DEFAULT_THEME_CREATED_BY);

        // Get all the themeList where themeCreatedBy does not contain UPDATED_THEME_CREATED_BY
        defaultThemeShouldBeFound("themeCreatedBy.doesNotContain=" + UPDATED_THEME_CREATED_BY);
    }


    @Test
    @Transactional
    public void getAllThemesByThemeCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeCreationDate equals to DEFAULT_THEME_CREATION_DATE
        defaultThemeShouldBeFound("themeCreationDate.equals=" + DEFAULT_THEME_CREATION_DATE);

        // Get all the themeList where themeCreationDate equals to UPDATED_THEME_CREATION_DATE
        defaultThemeShouldNotBeFound("themeCreationDate.equals=" + UPDATED_THEME_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllThemesByThemeCreationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeCreationDate not equals to DEFAULT_THEME_CREATION_DATE
        defaultThemeShouldNotBeFound("themeCreationDate.notEquals=" + DEFAULT_THEME_CREATION_DATE);

        // Get all the themeList where themeCreationDate not equals to UPDATED_THEME_CREATION_DATE
        defaultThemeShouldBeFound("themeCreationDate.notEquals=" + UPDATED_THEME_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllThemesByThemeCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeCreationDate in DEFAULT_THEME_CREATION_DATE or UPDATED_THEME_CREATION_DATE
        defaultThemeShouldBeFound("themeCreationDate.in=" + DEFAULT_THEME_CREATION_DATE + "," + UPDATED_THEME_CREATION_DATE);

        // Get all the themeList where themeCreationDate equals to UPDATED_THEME_CREATION_DATE
        defaultThemeShouldNotBeFound("themeCreationDate.in=" + UPDATED_THEME_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllThemesByThemeCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeCreationDate is not null
        defaultThemeShouldBeFound("themeCreationDate.specified=true");

        // Get all the themeList where themeCreationDate is null
        defaultThemeShouldNotBeFound("themeCreationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllThemesByThemeCreationDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeCreationDate is greater than or equal to DEFAULT_THEME_CREATION_DATE
        defaultThemeShouldBeFound("themeCreationDate.greaterThanOrEqual=" + DEFAULT_THEME_CREATION_DATE);

        // Get all the themeList where themeCreationDate is greater than or equal to UPDATED_THEME_CREATION_DATE
        defaultThemeShouldNotBeFound("themeCreationDate.greaterThanOrEqual=" + UPDATED_THEME_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllThemesByThemeCreationDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeCreationDate is less than or equal to DEFAULT_THEME_CREATION_DATE
        defaultThemeShouldBeFound("themeCreationDate.lessThanOrEqual=" + DEFAULT_THEME_CREATION_DATE);

        // Get all the themeList where themeCreationDate is less than or equal to SMALLER_THEME_CREATION_DATE
        defaultThemeShouldNotBeFound("themeCreationDate.lessThanOrEqual=" + SMALLER_THEME_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllThemesByThemeCreationDateIsLessThanSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeCreationDate is less than DEFAULT_THEME_CREATION_DATE
        defaultThemeShouldNotBeFound("themeCreationDate.lessThan=" + DEFAULT_THEME_CREATION_DATE);

        // Get all the themeList where themeCreationDate is less than UPDATED_THEME_CREATION_DATE
        defaultThemeShouldBeFound("themeCreationDate.lessThan=" + UPDATED_THEME_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllThemesByThemeCreationDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeCreationDate is greater than DEFAULT_THEME_CREATION_DATE
        defaultThemeShouldNotBeFound("themeCreationDate.greaterThan=" + DEFAULT_THEME_CREATION_DATE);

        // Get all the themeList where themeCreationDate is greater than SMALLER_THEME_CREATION_DATE
        defaultThemeShouldBeFound("themeCreationDate.greaterThan=" + SMALLER_THEME_CREATION_DATE);
    }


    @Test
    @Transactional
    public void getAllThemesByThemeModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeModifiedBy equals to DEFAULT_THEME_MODIFIED_BY
        defaultThemeShouldBeFound("themeModifiedBy.equals=" + DEFAULT_THEME_MODIFIED_BY);

        // Get all the themeList where themeModifiedBy equals to UPDATED_THEME_MODIFIED_BY
        defaultThemeShouldNotBeFound("themeModifiedBy.equals=" + UPDATED_THEME_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllThemesByThemeModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeModifiedBy not equals to DEFAULT_THEME_MODIFIED_BY
        defaultThemeShouldNotBeFound("themeModifiedBy.notEquals=" + DEFAULT_THEME_MODIFIED_BY);

        // Get all the themeList where themeModifiedBy not equals to UPDATED_THEME_MODIFIED_BY
        defaultThemeShouldBeFound("themeModifiedBy.notEquals=" + UPDATED_THEME_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllThemesByThemeModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeModifiedBy in DEFAULT_THEME_MODIFIED_BY or UPDATED_THEME_MODIFIED_BY
        defaultThemeShouldBeFound("themeModifiedBy.in=" + DEFAULT_THEME_MODIFIED_BY + "," + UPDATED_THEME_MODIFIED_BY);

        // Get all the themeList where themeModifiedBy equals to UPDATED_THEME_MODIFIED_BY
        defaultThemeShouldNotBeFound("themeModifiedBy.in=" + UPDATED_THEME_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllThemesByThemeModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeModifiedBy is not null
        defaultThemeShouldBeFound("themeModifiedBy.specified=true");

        // Get all the themeList where themeModifiedBy is null
        defaultThemeShouldNotBeFound("themeModifiedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllThemesByThemeModifiedByContainsSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeModifiedBy contains DEFAULT_THEME_MODIFIED_BY
        defaultThemeShouldBeFound("themeModifiedBy.contains=" + DEFAULT_THEME_MODIFIED_BY);

        // Get all the themeList where themeModifiedBy contains UPDATED_THEME_MODIFIED_BY
        defaultThemeShouldNotBeFound("themeModifiedBy.contains=" + UPDATED_THEME_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllThemesByThemeModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeModifiedBy does not contain DEFAULT_THEME_MODIFIED_BY
        defaultThemeShouldNotBeFound("themeModifiedBy.doesNotContain=" + DEFAULT_THEME_MODIFIED_BY);

        // Get all the themeList where themeModifiedBy does not contain UPDATED_THEME_MODIFIED_BY
        defaultThemeShouldBeFound("themeModifiedBy.doesNotContain=" + UPDATED_THEME_MODIFIED_BY);
    }


    @Test
    @Transactional
    public void getAllThemesByThemeModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeModifiedDate equals to DEFAULT_THEME_MODIFIED_DATE
        defaultThemeShouldBeFound("themeModifiedDate.equals=" + DEFAULT_THEME_MODIFIED_DATE);

        // Get all the themeList where themeModifiedDate equals to UPDATED_THEME_MODIFIED_DATE
        defaultThemeShouldNotBeFound("themeModifiedDate.equals=" + UPDATED_THEME_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllThemesByThemeModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeModifiedDate not equals to DEFAULT_THEME_MODIFIED_DATE
        defaultThemeShouldNotBeFound("themeModifiedDate.notEquals=" + DEFAULT_THEME_MODIFIED_DATE);

        // Get all the themeList where themeModifiedDate not equals to UPDATED_THEME_MODIFIED_DATE
        defaultThemeShouldBeFound("themeModifiedDate.notEquals=" + UPDATED_THEME_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllThemesByThemeModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeModifiedDate in DEFAULT_THEME_MODIFIED_DATE or UPDATED_THEME_MODIFIED_DATE
        defaultThemeShouldBeFound("themeModifiedDate.in=" + DEFAULT_THEME_MODIFIED_DATE + "," + UPDATED_THEME_MODIFIED_DATE);

        // Get all the themeList where themeModifiedDate equals to UPDATED_THEME_MODIFIED_DATE
        defaultThemeShouldNotBeFound("themeModifiedDate.in=" + UPDATED_THEME_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllThemesByThemeModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeModifiedDate is not null
        defaultThemeShouldBeFound("themeModifiedDate.specified=true");

        // Get all the themeList where themeModifiedDate is null
        defaultThemeShouldNotBeFound("themeModifiedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllThemesByThemeModifiedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeModifiedDate is greater than or equal to DEFAULT_THEME_MODIFIED_DATE
        defaultThemeShouldBeFound("themeModifiedDate.greaterThanOrEqual=" + DEFAULT_THEME_MODIFIED_DATE);

        // Get all the themeList where themeModifiedDate is greater than or equal to UPDATED_THEME_MODIFIED_DATE
        defaultThemeShouldNotBeFound("themeModifiedDate.greaterThanOrEqual=" + UPDATED_THEME_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllThemesByThemeModifiedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeModifiedDate is less than or equal to DEFAULT_THEME_MODIFIED_DATE
        defaultThemeShouldBeFound("themeModifiedDate.lessThanOrEqual=" + DEFAULT_THEME_MODIFIED_DATE);

        // Get all the themeList where themeModifiedDate is less than or equal to SMALLER_THEME_MODIFIED_DATE
        defaultThemeShouldNotBeFound("themeModifiedDate.lessThanOrEqual=" + SMALLER_THEME_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllThemesByThemeModifiedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeModifiedDate is less than DEFAULT_THEME_MODIFIED_DATE
        defaultThemeShouldNotBeFound("themeModifiedDate.lessThan=" + DEFAULT_THEME_MODIFIED_DATE);

        // Get all the themeList where themeModifiedDate is less than UPDATED_THEME_MODIFIED_DATE
        defaultThemeShouldBeFound("themeModifiedDate.lessThan=" + UPDATED_THEME_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllThemesByThemeModifiedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);

        // Get all the themeList where themeModifiedDate is greater than DEFAULT_THEME_MODIFIED_DATE
        defaultThemeShouldNotBeFound("themeModifiedDate.greaterThan=" + DEFAULT_THEME_MODIFIED_DATE);

        // Get all the themeList where themeModifiedDate is greater than SMALLER_THEME_MODIFIED_DATE
        defaultThemeShouldBeFound("themeModifiedDate.greaterThan=" + SMALLER_THEME_MODIFIED_DATE);
    }


    @Test
    @Transactional
    public void getAllThemesBySubjectIsEqualToSomething() throws Exception {
        // Initialize the database
        themeRepository.saveAndFlush(theme);
        Subject subject = SubjectResourceIT.createEntity(em);
        em.persist(subject);
        em.flush();
        theme.setSubject(subject);
        themeRepository.saveAndFlush(theme);
        Long subjectId = subject.getId();

        // Get all the themeList where subject equals to subjectId
        defaultThemeShouldBeFound("subjectId.equals=" + subjectId);

        // Get all the themeList where subject equals to subjectId + 1
        defaultThemeShouldNotBeFound("subjectId.equals=" + (subjectId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultThemeShouldBeFound(String filter) throws Exception {
        restThemeMockMvc.perform(get("/api/themes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(theme.getId().intValue())))
            .andExpect(jsonPath("$.[*].themeName").value(hasItem(DEFAULT_THEME_NAME)))
            .andExpect(jsonPath("$.[*].themeDescription").value(hasItem(DEFAULT_THEME_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].themeCreatedBy").value(hasItem(DEFAULT_THEME_CREATED_BY)))
            .andExpect(jsonPath("$.[*].themeCreationDate").value(hasItem(DEFAULT_THEME_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].themeModifiedBy").value(hasItem(DEFAULT_THEME_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].themeModifiedDate").value(hasItem(DEFAULT_THEME_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restThemeMockMvc.perform(get("/api/themes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultThemeShouldNotBeFound(String filter) throws Exception {
        restThemeMockMvc.perform(get("/api/themes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restThemeMockMvc.perform(get("/api/themes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
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
        themeService.save(theme);

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
        themeService.save(theme);

        int databaseSizeBeforeDelete = themeRepository.findAll().size();

        // Delete the theme
        restThemeMockMvc.perform(delete("/api/themes/{id}", theme.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Theme> themeList = themeRepository.findAll();
        assertThat(themeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
