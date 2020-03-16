package com.fime.web.rest;

import com.fime.LearningPatternsApp;
import com.fime.domain.Subtheme;
import com.fime.domain.Theme;
import com.fime.repository.SubthemeRepository;
import com.fime.service.SubthemeService;
import com.fime.web.rest.errors.ExceptionTranslator;
import com.fime.service.dto.SubthemeCriteria;
import com.fime.service.SubthemeQueryService;

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
 * Integration tests for the {@link SubthemeResource} REST controller.
 */
@SpringBootTest(classes = LearningPatternsApp.class)
public class SubthemeResourceIT {

    private static final String DEFAULT_SUBTHEME_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SUBTHEME_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SUBTHEME_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SUBTHEME_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SUBTHEME_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_SUBTHEME_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_SUBTHEME_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SUBTHEME_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_SUBTHEME_CREATION_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_SUBTHEME_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_SUBTHEME_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_SUBTHEME_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SUBTHEME_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_SUBTHEME_MODIFIED_DATE = LocalDate.ofEpochDay(-1L);

    private static final Integer DEFAULT_SUBTHEME_MAX_GRADE = 1;
    private static final Integer UPDATED_SUBTHEME_MAX_GRADE = 2;
    private static final Integer SMALLER_SUBTHEME_MAX_GRADE = 1 - 1;

    @Autowired
    private SubthemeRepository subthemeRepository;

    @Autowired
    private SubthemeService subthemeService;

    @Autowired
    private SubthemeQueryService subthemeQueryService;

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

    private MockMvc restSubthemeMockMvc;

    private Subtheme subtheme;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SubthemeResource subthemeResource = new SubthemeResource(subthemeService, subthemeQueryService);
        this.restSubthemeMockMvc = MockMvcBuilders.standaloneSetup(subthemeResource)
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
    public static Subtheme createEntity(EntityManager em) {
        Subtheme subtheme = new Subtheme()
            .subthemeName(DEFAULT_SUBTHEME_NAME)
            .subthemeDescription(DEFAULT_SUBTHEME_DESCRIPTION)
            .subthemeCreatedBy(DEFAULT_SUBTHEME_CREATED_BY)
            .subthemeCreationDate(DEFAULT_SUBTHEME_CREATION_DATE)
            .subthemeModifiedBy(DEFAULT_SUBTHEME_MODIFIED_BY)
            .subthemeModifiedDate(DEFAULT_SUBTHEME_MODIFIED_DATE)
            .subthemeMaxGrade(DEFAULT_SUBTHEME_MAX_GRADE);
        return subtheme;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subtheme createUpdatedEntity(EntityManager em) {
        Subtheme subtheme = new Subtheme()
            .subthemeName(UPDATED_SUBTHEME_NAME)
            .subthemeDescription(UPDATED_SUBTHEME_DESCRIPTION)
            .subthemeCreatedBy(UPDATED_SUBTHEME_CREATED_BY)
            .subthemeCreationDate(UPDATED_SUBTHEME_CREATION_DATE)
            .subthemeModifiedBy(UPDATED_SUBTHEME_MODIFIED_BY)
            .subthemeModifiedDate(UPDATED_SUBTHEME_MODIFIED_DATE)
            .subthemeMaxGrade(UPDATED_SUBTHEME_MAX_GRADE);
        return subtheme;
    }

    @BeforeEach
    public void initTest() {
        subtheme = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubtheme() throws Exception {
        int databaseSizeBeforeCreate = subthemeRepository.findAll().size();

        // Create the Subtheme
        restSubthemeMockMvc.perform(post("/api/subthemes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subtheme)))
            .andExpect(status().isCreated());

        // Validate the Subtheme in the database
        List<Subtheme> subthemeList = subthemeRepository.findAll();
        assertThat(subthemeList).hasSize(databaseSizeBeforeCreate + 1);
        Subtheme testSubtheme = subthemeList.get(subthemeList.size() - 1);
        assertThat(testSubtheme.getSubthemeName()).isEqualTo(DEFAULT_SUBTHEME_NAME);
        assertThat(testSubtheme.getSubthemeDescription()).isEqualTo(DEFAULT_SUBTHEME_DESCRIPTION);
        assertThat(testSubtheme.getSubthemeCreatedBy()).isEqualTo(DEFAULT_SUBTHEME_CREATED_BY);
        assertThat(testSubtheme.getSubthemeCreationDate()).isEqualTo(DEFAULT_SUBTHEME_CREATION_DATE);
        assertThat(testSubtheme.getSubthemeModifiedBy()).isEqualTo(DEFAULT_SUBTHEME_MODIFIED_BY);
        assertThat(testSubtheme.getSubthemeModifiedDate()).isEqualTo(DEFAULT_SUBTHEME_MODIFIED_DATE);
        assertThat(testSubtheme.getSubthemeMaxGrade()).isEqualTo(DEFAULT_SUBTHEME_MAX_GRADE);
    }

    @Test
    @Transactional
    public void createSubthemeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subthemeRepository.findAll().size();

        // Create the Subtheme with an existing ID
        subtheme.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubthemeMockMvc.perform(post("/api/subthemes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subtheme)))
            .andExpect(status().isBadRequest());

        // Validate the Subtheme in the database
        List<Subtheme> subthemeList = subthemeRepository.findAll();
        assertThat(subthemeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSubthemes() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList
        restSubthemeMockMvc.perform(get("/api/subthemes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subtheme.getId().intValue())))
            .andExpect(jsonPath("$.[*].subthemeName").value(hasItem(DEFAULT_SUBTHEME_NAME)))
            .andExpect(jsonPath("$.[*].subthemeDescription").value(hasItem(DEFAULT_SUBTHEME_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].subthemeCreatedBy").value(hasItem(DEFAULT_SUBTHEME_CREATED_BY)))
            .andExpect(jsonPath("$.[*].subthemeCreationDate").value(hasItem(DEFAULT_SUBTHEME_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].subthemeModifiedBy").value(hasItem(DEFAULT_SUBTHEME_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].subthemeModifiedDate").value(hasItem(DEFAULT_SUBTHEME_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].subthemeMaxGrade").value(hasItem(DEFAULT_SUBTHEME_MAX_GRADE)));
    }
    
    @Test
    @Transactional
    public void getSubtheme() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get the subtheme
        restSubthemeMockMvc.perform(get("/api/subthemes/{id}", subtheme.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(subtheme.getId().intValue()))
            .andExpect(jsonPath("$.subthemeName").value(DEFAULT_SUBTHEME_NAME))
            .andExpect(jsonPath("$.subthemeDescription").value(DEFAULT_SUBTHEME_DESCRIPTION))
            .andExpect(jsonPath("$.subthemeCreatedBy").value(DEFAULT_SUBTHEME_CREATED_BY))
            .andExpect(jsonPath("$.subthemeCreationDate").value(DEFAULT_SUBTHEME_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.subthemeModifiedBy").value(DEFAULT_SUBTHEME_MODIFIED_BY))
            .andExpect(jsonPath("$.subthemeModifiedDate").value(DEFAULT_SUBTHEME_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.subthemeMaxGrade").value(DEFAULT_SUBTHEME_MAX_GRADE));
    }


    @Test
    @Transactional
    public void getSubthemesByIdFiltering() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        Long id = subtheme.getId();

        defaultSubthemeShouldBeFound("id.equals=" + id);
        defaultSubthemeShouldNotBeFound("id.notEquals=" + id);

        defaultSubthemeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSubthemeShouldNotBeFound("id.greaterThan=" + id);

        defaultSubthemeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSubthemeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSubthemesBySubthemeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeName equals to DEFAULT_SUBTHEME_NAME
        defaultSubthemeShouldBeFound("subthemeName.equals=" + DEFAULT_SUBTHEME_NAME);

        // Get all the subthemeList where subthemeName equals to UPDATED_SUBTHEME_NAME
        defaultSubthemeShouldNotBeFound("subthemeName.equals=" + UPDATED_SUBTHEME_NAME);
    }

    @Test
    @Transactional
    public void getAllSubthemesBySubthemeNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeName not equals to DEFAULT_SUBTHEME_NAME
        defaultSubthemeShouldNotBeFound("subthemeName.notEquals=" + DEFAULT_SUBTHEME_NAME);

        // Get all the subthemeList where subthemeName not equals to UPDATED_SUBTHEME_NAME
        defaultSubthemeShouldBeFound("subthemeName.notEquals=" + UPDATED_SUBTHEME_NAME);
    }

    @Test
    @Transactional
    public void getAllSubthemesBySubthemeNameIsInShouldWork() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeName in DEFAULT_SUBTHEME_NAME or UPDATED_SUBTHEME_NAME
        defaultSubthemeShouldBeFound("subthemeName.in=" + DEFAULT_SUBTHEME_NAME + "," + UPDATED_SUBTHEME_NAME);

        // Get all the subthemeList where subthemeName equals to UPDATED_SUBTHEME_NAME
        defaultSubthemeShouldNotBeFound("subthemeName.in=" + UPDATED_SUBTHEME_NAME);
    }

    @Test
    @Transactional
    public void getAllSubthemesBySubthemeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeName is not null
        defaultSubthemeShouldBeFound("subthemeName.specified=true");

        // Get all the subthemeList where subthemeName is null
        defaultSubthemeShouldNotBeFound("subthemeName.specified=false");
    }
                @Test
    @Transactional
    public void getAllSubthemesBySubthemeNameContainsSomething() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeName contains DEFAULT_SUBTHEME_NAME
        defaultSubthemeShouldBeFound("subthemeName.contains=" + DEFAULT_SUBTHEME_NAME);

        // Get all the subthemeList where subthemeName contains UPDATED_SUBTHEME_NAME
        defaultSubthemeShouldNotBeFound("subthemeName.contains=" + UPDATED_SUBTHEME_NAME);
    }

    @Test
    @Transactional
    public void getAllSubthemesBySubthemeNameNotContainsSomething() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeName does not contain DEFAULT_SUBTHEME_NAME
        defaultSubthemeShouldNotBeFound("subthemeName.doesNotContain=" + DEFAULT_SUBTHEME_NAME);

        // Get all the subthemeList where subthemeName does not contain UPDATED_SUBTHEME_NAME
        defaultSubthemeShouldBeFound("subthemeName.doesNotContain=" + UPDATED_SUBTHEME_NAME);
    }


    @Test
    @Transactional
    public void getAllSubthemesBySubthemeDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeDescription equals to DEFAULT_SUBTHEME_DESCRIPTION
        defaultSubthemeShouldBeFound("subthemeDescription.equals=" + DEFAULT_SUBTHEME_DESCRIPTION);

        // Get all the subthemeList where subthemeDescription equals to UPDATED_SUBTHEME_DESCRIPTION
        defaultSubthemeShouldNotBeFound("subthemeDescription.equals=" + UPDATED_SUBTHEME_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSubthemesBySubthemeDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeDescription not equals to DEFAULT_SUBTHEME_DESCRIPTION
        defaultSubthemeShouldNotBeFound("subthemeDescription.notEquals=" + DEFAULT_SUBTHEME_DESCRIPTION);

        // Get all the subthemeList where subthemeDescription not equals to UPDATED_SUBTHEME_DESCRIPTION
        defaultSubthemeShouldBeFound("subthemeDescription.notEquals=" + UPDATED_SUBTHEME_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSubthemesBySubthemeDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeDescription in DEFAULT_SUBTHEME_DESCRIPTION or UPDATED_SUBTHEME_DESCRIPTION
        defaultSubthemeShouldBeFound("subthemeDescription.in=" + DEFAULT_SUBTHEME_DESCRIPTION + "," + UPDATED_SUBTHEME_DESCRIPTION);

        // Get all the subthemeList where subthemeDescription equals to UPDATED_SUBTHEME_DESCRIPTION
        defaultSubthemeShouldNotBeFound("subthemeDescription.in=" + UPDATED_SUBTHEME_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSubthemesBySubthemeDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeDescription is not null
        defaultSubthemeShouldBeFound("subthemeDescription.specified=true");

        // Get all the subthemeList where subthemeDescription is null
        defaultSubthemeShouldNotBeFound("subthemeDescription.specified=false");
    }
                @Test
    @Transactional
    public void getAllSubthemesBySubthemeDescriptionContainsSomething() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeDescription contains DEFAULT_SUBTHEME_DESCRIPTION
        defaultSubthemeShouldBeFound("subthemeDescription.contains=" + DEFAULT_SUBTHEME_DESCRIPTION);

        // Get all the subthemeList where subthemeDescription contains UPDATED_SUBTHEME_DESCRIPTION
        defaultSubthemeShouldNotBeFound("subthemeDescription.contains=" + UPDATED_SUBTHEME_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSubthemesBySubthemeDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeDescription does not contain DEFAULT_SUBTHEME_DESCRIPTION
        defaultSubthemeShouldNotBeFound("subthemeDescription.doesNotContain=" + DEFAULT_SUBTHEME_DESCRIPTION);

        // Get all the subthemeList where subthemeDescription does not contain UPDATED_SUBTHEME_DESCRIPTION
        defaultSubthemeShouldBeFound("subthemeDescription.doesNotContain=" + UPDATED_SUBTHEME_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllSubthemesBySubthemeCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeCreatedBy equals to DEFAULT_SUBTHEME_CREATED_BY
        defaultSubthemeShouldBeFound("subthemeCreatedBy.equals=" + DEFAULT_SUBTHEME_CREATED_BY);

        // Get all the subthemeList where subthemeCreatedBy equals to UPDATED_SUBTHEME_CREATED_BY
        defaultSubthemeShouldNotBeFound("subthemeCreatedBy.equals=" + UPDATED_SUBTHEME_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSubthemesBySubthemeCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeCreatedBy not equals to DEFAULT_SUBTHEME_CREATED_BY
        defaultSubthemeShouldNotBeFound("subthemeCreatedBy.notEquals=" + DEFAULT_SUBTHEME_CREATED_BY);

        // Get all the subthemeList where subthemeCreatedBy not equals to UPDATED_SUBTHEME_CREATED_BY
        defaultSubthemeShouldBeFound("subthemeCreatedBy.notEquals=" + UPDATED_SUBTHEME_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSubthemesBySubthemeCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeCreatedBy in DEFAULT_SUBTHEME_CREATED_BY or UPDATED_SUBTHEME_CREATED_BY
        defaultSubthemeShouldBeFound("subthemeCreatedBy.in=" + DEFAULT_SUBTHEME_CREATED_BY + "," + UPDATED_SUBTHEME_CREATED_BY);

        // Get all the subthemeList where subthemeCreatedBy equals to UPDATED_SUBTHEME_CREATED_BY
        defaultSubthemeShouldNotBeFound("subthemeCreatedBy.in=" + UPDATED_SUBTHEME_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSubthemesBySubthemeCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeCreatedBy is not null
        defaultSubthemeShouldBeFound("subthemeCreatedBy.specified=true");

        // Get all the subthemeList where subthemeCreatedBy is null
        defaultSubthemeShouldNotBeFound("subthemeCreatedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllSubthemesBySubthemeCreatedByContainsSomething() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeCreatedBy contains DEFAULT_SUBTHEME_CREATED_BY
        defaultSubthemeShouldBeFound("subthemeCreatedBy.contains=" + DEFAULT_SUBTHEME_CREATED_BY);

        // Get all the subthemeList where subthemeCreatedBy contains UPDATED_SUBTHEME_CREATED_BY
        defaultSubthemeShouldNotBeFound("subthemeCreatedBy.contains=" + UPDATED_SUBTHEME_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSubthemesBySubthemeCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeCreatedBy does not contain DEFAULT_SUBTHEME_CREATED_BY
        defaultSubthemeShouldNotBeFound("subthemeCreatedBy.doesNotContain=" + DEFAULT_SUBTHEME_CREATED_BY);

        // Get all the subthemeList where subthemeCreatedBy does not contain UPDATED_SUBTHEME_CREATED_BY
        defaultSubthemeShouldBeFound("subthemeCreatedBy.doesNotContain=" + UPDATED_SUBTHEME_CREATED_BY);
    }


    @Test
    @Transactional
    public void getAllSubthemesBySubthemeCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeCreationDate equals to DEFAULT_SUBTHEME_CREATION_DATE
        defaultSubthemeShouldBeFound("subthemeCreationDate.equals=" + DEFAULT_SUBTHEME_CREATION_DATE);

        // Get all the subthemeList where subthemeCreationDate equals to UPDATED_SUBTHEME_CREATION_DATE
        defaultSubthemeShouldNotBeFound("subthemeCreationDate.equals=" + UPDATED_SUBTHEME_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllSubthemesBySubthemeCreationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeCreationDate not equals to DEFAULT_SUBTHEME_CREATION_DATE
        defaultSubthemeShouldNotBeFound("subthemeCreationDate.notEquals=" + DEFAULT_SUBTHEME_CREATION_DATE);

        // Get all the subthemeList where subthemeCreationDate not equals to UPDATED_SUBTHEME_CREATION_DATE
        defaultSubthemeShouldBeFound("subthemeCreationDate.notEquals=" + UPDATED_SUBTHEME_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllSubthemesBySubthemeCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeCreationDate in DEFAULT_SUBTHEME_CREATION_DATE or UPDATED_SUBTHEME_CREATION_DATE
        defaultSubthemeShouldBeFound("subthemeCreationDate.in=" + DEFAULT_SUBTHEME_CREATION_DATE + "," + UPDATED_SUBTHEME_CREATION_DATE);

        // Get all the subthemeList where subthemeCreationDate equals to UPDATED_SUBTHEME_CREATION_DATE
        defaultSubthemeShouldNotBeFound("subthemeCreationDate.in=" + UPDATED_SUBTHEME_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllSubthemesBySubthemeCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeCreationDate is not null
        defaultSubthemeShouldBeFound("subthemeCreationDate.specified=true");

        // Get all the subthemeList where subthemeCreationDate is null
        defaultSubthemeShouldNotBeFound("subthemeCreationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllSubthemesBySubthemeCreationDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeCreationDate is greater than or equal to DEFAULT_SUBTHEME_CREATION_DATE
        defaultSubthemeShouldBeFound("subthemeCreationDate.greaterThanOrEqual=" + DEFAULT_SUBTHEME_CREATION_DATE);

        // Get all the subthemeList where subthemeCreationDate is greater than or equal to UPDATED_SUBTHEME_CREATION_DATE
        defaultSubthemeShouldNotBeFound("subthemeCreationDate.greaterThanOrEqual=" + UPDATED_SUBTHEME_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllSubthemesBySubthemeCreationDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeCreationDate is less than or equal to DEFAULT_SUBTHEME_CREATION_DATE
        defaultSubthemeShouldBeFound("subthemeCreationDate.lessThanOrEqual=" + DEFAULT_SUBTHEME_CREATION_DATE);

        // Get all the subthemeList where subthemeCreationDate is less than or equal to SMALLER_SUBTHEME_CREATION_DATE
        defaultSubthemeShouldNotBeFound("subthemeCreationDate.lessThanOrEqual=" + SMALLER_SUBTHEME_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllSubthemesBySubthemeCreationDateIsLessThanSomething() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeCreationDate is less than DEFAULT_SUBTHEME_CREATION_DATE
        defaultSubthemeShouldNotBeFound("subthemeCreationDate.lessThan=" + DEFAULT_SUBTHEME_CREATION_DATE);

        // Get all the subthemeList where subthemeCreationDate is less than UPDATED_SUBTHEME_CREATION_DATE
        defaultSubthemeShouldBeFound("subthemeCreationDate.lessThan=" + UPDATED_SUBTHEME_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllSubthemesBySubthemeCreationDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeCreationDate is greater than DEFAULT_SUBTHEME_CREATION_DATE
        defaultSubthemeShouldNotBeFound("subthemeCreationDate.greaterThan=" + DEFAULT_SUBTHEME_CREATION_DATE);

        // Get all the subthemeList where subthemeCreationDate is greater than SMALLER_SUBTHEME_CREATION_DATE
        defaultSubthemeShouldBeFound("subthemeCreationDate.greaterThan=" + SMALLER_SUBTHEME_CREATION_DATE);
    }


    @Test
    @Transactional
    public void getAllSubthemesBySubthemeModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeModifiedBy equals to DEFAULT_SUBTHEME_MODIFIED_BY
        defaultSubthemeShouldBeFound("subthemeModifiedBy.equals=" + DEFAULT_SUBTHEME_MODIFIED_BY);

        // Get all the subthemeList where subthemeModifiedBy equals to UPDATED_SUBTHEME_MODIFIED_BY
        defaultSubthemeShouldNotBeFound("subthemeModifiedBy.equals=" + UPDATED_SUBTHEME_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSubthemesBySubthemeModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeModifiedBy not equals to DEFAULT_SUBTHEME_MODIFIED_BY
        defaultSubthemeShouldNotBeFound("subthemeModifiedBy.notEquals=" + DEFAULT_SUBTHEME_MODIFIED_BY);

        // Get all the subthemeList where subthemeModifiedBy not equals to UPDATED_SUBTHEME_MODIFIED_BY
        defaultSubthemeShouldBeFound("subthemeModifiedBy.notEquals=" + UPDATED_SUBTHEME_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSubthemesBySubthemeModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeModifiedBy in DEFAULT_SUBTHEME_MODIFIED_BY or UPDATED_SUBTHEME_MODIFIED_BY
        defaultSubthemeShouldBeFound("subthemeModifiedBy.in=" + DEFAULT_SUBTHEME_MODIFIED_BY + "," + UPDATED_SUBTHEME_MODIFIED_BY);

        // Get all the subthemeList where subthemeModifiedBy equals to UPDATED_SUBTHEME_MODIFIED_BY
        defaultSubthemeShouldNotBeFound("subthemeModifiedBy.in=" + UPDATED_SUBTHEME_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSubthemesBySubthemeModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeModifiedBy is not null
        defaultSubthemeShouldBeFound("subthemeModifiedBy.specified=true");

        // Get all the subthemeList where subthemeModifiedBy is null
        defaultSubthemeShouldNotBeFound("subthemeModifiedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllSubthemesBySubthemeModifiedByContainsSomething() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeModifiedBy contains DEFAULT_SUBTHEME_MODIFIED_BY
        defaultSubthemeShouldBeFound("subthemeModifiedBy.contains=" + DEFAULT_SUBTHEME_MODIFIED_BY);

        // Get all the subthemeList where subthemeModifiedBy contains UPDATED_SUBTHEME_MODIFIED_BY
        defaultSubthemeShouldNotBeFound("subthemeModifiedBy.contains=" + UPDATED_SUBTHEME_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSubthemesBySubthemeModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeModifiedBy does not contain DEFAULT_SUBTHEME_MODIFIED_BY
        defaultSubthemeShouldNotBeFound("subthemeModifiedBy.doesNotContain=" + DEFAULT_SUBTHEME_MODIFIED_BY);

        // Get all the subthemeList where subthemeModifiedBy does not contain UPDATED_SUBTHEME_MODIFIED_BY
        defaultSubthemeShouldBeFound("subthemeModifiedBy.doesNotContain=" + UPDATED_SUBTHEME_MODIFIED_BY);
    }


    @Test
    @Transactional
    public void getAllSubthemesBySubthemeModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeModifiedDate equals to DEFAULT_SUBTHEME_MODIFIED_DATE
        defaultSubthemeShouldBeFound("subthemeModifiedDate.equals=" + DEFAULT_SUBTHEME_MODIFIED_DATE);

        // Get all the subthemeList where subthemeModifiedDate equals to UPDATED_SUBTHEME_MODIFIED_DATE
        defaultSubthemeShouldNotBeFound("subthemeModifiedDate.equals=" + UPDATED_SUBTHEME_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllSubthemesBySubthemeModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeModifiedDate not equals to DEFAULT_SUBTHEME_MODIFIED_DATE
        defaultSubthemeShouldNotBeFound("subthemeModifiedDate.notEquals=" + DEFAULT_SUBTHEME_MODIFIED_DATE);

        // Get all the subthemeList where subthemeModifiedDate not equals to UPDATED_SUBTHEME_MODIFIED_DATE
        defaultSubthemeShouldBeFound("subthemeModifiedDate.notEquals=" + UPDATED_SUBTHEME_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllSubthemesBySubthemeModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeModifiedDate in DEFAULT_SUBTHEME_MODIFIED_DATE or UPDATED_SUBTHEME_MODIFIED_DATE
        defaultSubthemeShouldBeFound("subthemeModifiedDate.in=" + DEFAULT_SUBTHEME_MODIFIED_DATE + "," + UPDATED_SUBTHEME_MODIFIED_DATE);

        // Get all the subthemeList where subthemeModifiedDate equals to UPDATED_SUBTHEME_MODIFIED_DATE
        defaultSubthemeShouldNotBeFound("subthemeModifiedDate.in=" + UPDATED_SUBTHEME_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllSubthemesBySubthemeModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeModifiedDate is not null
        defaultSubthemeShouldBeFound("subthemeModifiedDate.specified=true");

        // Get all the subthemeList where subthemeModifiedDate is null
        defaultSubthemeShouldNotBeFound("subthemeModifiedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllSubthemesBySubthemeModifiedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeModifiedDate is greater than or equal to DEFAULT_SUBTHEME_MODIFIED_DATE
        defaultSubthemeShouldBeFound("subthemeModifiedDate.greaterThanOrEqual=" + DEFAULT_SUBTHEME_MODIFIED_DATE);

        // Get all the subthemeList where subthemeModifiedDate is greater than or equal to UPDATED_SUBTHEME_MODIFIED_DATE
        defaultSubthemeShouldNotBeFound("subthemeModifiedDate.greaterThanOrEqual=" + UPDATED_SUBTHEME_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllSubthemesBySubthemeModifiedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeModifiedDate is less than or equal to DEFAULT_SUBTHEME_MODIFIED_DATE
        defaultSubthemeShouldBeFound("subthemeModifiedDate.lessThanOrEqual=" + DEFAULT_SUBTHEME_MODIFIED_DATE);

        // Get all the subthemeList where subthemeModifiedDate is less than or equal to SMALLER_SUBTHEME_MODIFIED_DATE
        defaultSubthemeShouldNotBeFound("subthemeModifiedDate.lessThanOrEqual=" + SMALLER_SUBTHEME_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllSubthemesBySubthemeModifiedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeModifiedDate is less than DEFAULT_SUBTHEME_MODIFIED_DATE
        defaultSubthemeShouldNotBeFound("subthemeModifiedDate.lessThan=" + DEFAULT_SUBTHEME_MODIFIED_DATE);

        // Get all the subthemeList where subthemeModifiedDate is less than UPDATED_SUBTHEME_MODIFIED_DATE
        defaultSubthemeShouldBeFound("subthemeModifiedDate.lessThan=" + UPDATED_SUBTHEME_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllSubthemesBySubthemeModifiedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeModifiedDate is greater than DEFAULT_SUBTHEME_MODIFIED_DATE
        defaultSubthemeShouldNotBeFound("subthemeModifiedDate.greaterThan=" + DEFAULT_SUBTHEME_MODIFIED_DATE);

        // Get all the subthemeList where subthemeModifiedDate is greater than SMALLER_SUBTHEME_MODIFIED_DATE
        defaultSubthemeShouldBeFound("subthemeModifiedDate.greaterThan=" + SMALLER_SUBTHEME_MODIFIED_DATE);
    }


    @Test
    @Transactional
    public void getAllSubthemesBySubthemeMaxGradeIsEqualToSomething() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeMaxGrade equals to DEFAULT_SUBTHEME_MAX_GRADE
        defaultSubthemeShouldBeFound("subthemeMaxGrade.equals=" + DEFAULT_SUBTHEME_MAX_GRADE);

        // Get all the subthemeList where subthemeMaxGrade equals to UPDATED_SUBTHEME_MAX_GRADE
        defaultSubthemeShouldNotBeFound("subthemeMaxGrade.equals=" + UPDATED_SUBTHEME_MAX_GRADE);
    }

    @Test
    @Transactional
    public void getAllSubthemesBySubthemeMaxGradeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeMaxGrade not equals to DEFAULT_SUBTHEME_MAX_GRADE
        defaultSubthemeShouldNotBeFound("subthemeMaxGrade.notEquals=" + DEFAULT_SUBTHEME_MAX_GRADE);

        // Get all the subthemeList where subthemeMaxGrade not equals to UPDATED_SUBTHEME_MAX_GRADE
        defaultSubthemeShouldBeFound("subthemeMaxGrade.notEquals=" + UPDATED_SUBTHEME_MAX_GRADE);
    }

    @Test
    @Transactional
    public void getAllSubthemesBySubthemeMaxGradeIsInShouldWork() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeMaxGrade in DEFAULT_SUBTHEME_MAX_GRADE or UPDATED_SUBTHEME_MAX_GRADE
        defaultSubthemeShouldBeFound("subthemeMaxGrade.in=" + DEFAULT_SUBTHEME_MAX_GRADE + "," + UPDATED_SUBTHEME_MAX_GRADE);

        // Get all the subthemeList where subthemeMaxGrade equals to UPDATED_SUBTHEME_MAX_GRADE
        defaultSubthemeShouldNotBeFound("subthemeMaxGrade.in=" + UPDATED_SUBTHEME_MAX_GRADE);
    }

    @Test
    @Transactional
    public void getAllSubthemesBySubthemeMaxGradeIsNullOrNotNull() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeMaxGrade is not null
        defaultSubthemeShouldBeFound("subthemeMaxGrade.specified=true");

        // Get all the subthemeList where subthemeMaxGrade is null
        defaultSubthemeShouldNotBeFound("subthemeMaxGrade.specified=false");
    }

    @Test
    @Transactional
    public void getAllSubthemesBySubthemeMaxGradeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeMaxGrade is greater than or equal to DEFAULT_SUBTHEME_MAX_GRADE
        defaultSubthemeShouldBeFound("subthemeMaxGrade.greaterThanOrEqual=" + DEFAULT_SUBTHEME_MAX_GRADE);

        // Get all the subthemeList where subthemeMaxGrade is greater than or equal to UPDATED_SUBTHEME_MAX_GRADE
        defaultSubthemeShouldNotBeFound("subthemeMaxGrade.greaterThanOrEqual=" + UPDATED_SUBTHEME_MAX_GRADE);
    }

    @Test
    @Transactional
    public void getAllSubthemesBySubthemeMaxGradeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeMaxGrade is less than or equal to DEFAULT_SUBTHEME_MAX_GRADE
        defaultSubthemeShouldBeFound("subthemeMaxGrade.lessThanOrEqual=" + DEFAULT_SUBTHEME_MAX_GRADE);

        // Get all the subthemeList where subthemeMaxGrade is less than or equal to SMALLER_SUBTHEME_MAX_GRADE
        defaultSubthemeShouldNotBeFound("subthemeMaxGrade.lessThanOrEqual=" + SMALLER_SUBTHEME_MAX_GRADE);
    }

    @Test
    @Transactional
    public void getAllSubthemesBySubthemeMaxGradeIsLessThanSomething() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeMaxGrade is less than DEFAULT_SUBTHEME_MAX_GRADE
        defaultSubthemeShouldNotBeFound("subthemeMaxGrade.lessThan=" + DEFAULT_SUBTHEME_MAX_GRADE);

        // Get all the subthemeList where subthemeMaxGrade is less than UPDATED_SUBTHEME_MAX_GRADE
        defaultSubthemeShouldBeFound("subthemeMaxGrade.lessThan=" + UPDATED_SUBTHEME_MAX_GRADE);
    }

    @Test
    @Transactional
    public void getAllSubthemesBySubthemeMaxGradeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);

        // Get all the subthemeList where subthemeMaxGrade is greater than DEFAULT_SUBTHEME_MAX_GRADE
        defaultSubthemeShouldNotBeFound("subthemeMaxGrade.greaterThan=" + DEFAULT_SUBTHEME_MAX_GRADE);

        // Get all the subthemeList where subthemeMaxGrade is greater than SMALLER_SUBTHEME_MAX_GRADE
        defaultSubthemeShouldBeFound("subthemeMaxGrade.greaterThan=" + SMALLER_SUBTHEME_MAX_GRADE);
    }


    @Test
    @Transactional
    public void getAllSubthemesByThemeIsEqualToSomething() throws Exception {
        // Initialize the database
        subthemeRepository.saveAndFlush(subtheme);
        Theme theme = ThemeResourceIT.createEntity(em);
        em.persist(theme);
        em.flush();
        subtheme.setTheme(theme);
        subthemeRepository.saveAndFlush(subtheme);
        Long themeId = theme.getId();

        // Get all the subthemeList where theme equals to themeId
        defaultSubthemeShouldBeFound("themeId.equals=" + themeId);

        // Get all the subthemeList where theme equals to themeId + 1
        defaultSubthemeShouldNotBeFound("themeId.equals=" + (themeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSubthemeShouldBeFound(String filter) throws Exception {
        restSubthemeMockMvc.perform(get("/api/subthemes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subtheme.getId().intValue())))
            .andExpect(jsonPath("$.[*].subthemeName").value(hasItem(DEFAULT_SUBTHEME_NAME)))
            .andExpect(jsonPath("$.[*].subthemeDescription").value(hasItem(DEFAULT_SUBTHEME_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].subthemeCreatedBy").value(hasItem(DEFAULT_SUBTHEME_CREATED_BY)))
            .andExpect(jsonPath("$.[*].subthemeCreationDate").value(hasItem(DEFAULT_SUBTHEME_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].subthemeModifiedBy").value(hasItem(DEFAULT_SUBTHEME_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].subthemeModifiedDate").value(hasItem(DEFAULT_SUBTHEME_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].subthemeMaxGrade").value(hasItem(DEFAULT_SUBTHEME_MAX_GRADE)));

        // Check, that the count call also returns 1
        restSubthemeMockMvc.perform(get("/api/subthemes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSubthemeShouldNotBeFound(String filter) throws Exception {
        restSubthemeMockMvc.perform(get("/api/subthemes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSubthemeMockMvc.perform(get("/api/subthemes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSubtheme() throws Exception {
        // Get the subtheme
        restSubthemeMockMvc.perform(get("/api/subthemes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubtheme() throws Exception {
        // Initialize the database
        subthemeService.save(subtheme);

        int databaseSizeBeforeUpdate = subthemeRepository.findAll().size();

        // Update the subtheme
        Subtheme updatedSubtheme = subthemeRepository.findById(subtheme.getId()).get();
        // Disconnect from session so that the updates on updatedSubtheme are not directly saved in db
        em.detach(updatedSubtheme);
        updatedSubtheme
            .subthemeName(UPDATED_SUBTHEME_NAME)
            .subthemeDescription(UPDATED_SUBTHEME_DESCRIPTION)
            .subthemeCreatedBy(UPDATED_SUBTHEME_CREATED_BY)
            .subthemeCreationDate(UPDATED_SUBTHEME_CREATION_DATE)
            .subthemeModifiedBy(UPDATED_SUBTHEME_MODIFIED_BY)
            .subthemeModifiedDate(UPDATED_SUBTHEME_MODIFIED_DATE)
            .subthemeMaxGrade(UPDATED_SUBTHEME_MAX_GRADE);

        restSubthemeMockMvc.perform(put("/api/subthemes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSubtheme)))
            .andExpect(status().isOk());

        // Validate the Subtheme in the database
        List<Subtheme> subthemeList = subthemeRepository.findAll();
        assertThat(subthemeList).hasSize(databaseSizeBeforeUpdate);
        Subtheme testSubtheme = subthemeList.get(subthemeList.size() - 1);
        assertThat(testSubtheme.getSubthemeName()).isEqualTo(UPDATED_SUBTHEME_NAME);
        assertThat(testSubtheme.getSubthemeDescription()).isEqualTo(UPDATED_SUBTHEME_DESCRIPTION);
        assertThat(testSubtheme.getSubthemeCreatedBy()).isEqualTo(UPDATED_SUBTHEME_CREATED_BY);
        assertThat(testSubtheme.getSubthemeCreationDate()).isEqualTo(UPDATED_SUBTHEME_CREATION_DATE);
        assertThat(testSubtheme.getSubthemeModifiedBy()).isEqualTo(UPDATED_SUBTHEME_MODIFIED_BY);
        assertThat(testSubtheme.getSubthemeModifiedDate()).isEqualTo(UPDATED_SUBTHEME_MODIFIED_DATE);
        assertThat(testSubtheme.getSubthemeMaxGrade()).isEqualTo(UPDATED_SUBTHEME_MAX_GRADE);
    }

    @Test
    @Transactional
    public void updateNonExistingSubtheme() throws Exception {
        int databaseSizeBeforeUpdate = subthemeRepository.findAll().size();

        // Create the Subtheme

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubthemeMockMvc.perform(put("/api/subthemes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subtheme)))
            .andExpect(status().isBadRequest());

        // Validate the Subtheme in the database
        List<Subtheme> subthemeList = subthemeRepository.findAll();
        assertThat(subthemeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSubtheme() throws Exception {
        // Initialize the database
        subthemeService.save(subtheme);

        int databaseSizeBeforeDelete = subthemeRepository.findAll().size();

        // Delete the subtheme
        restSubthemeMockMvc.perform(delete("/api/subthemes/{id}", subtheme.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Subtheme> subthemeList = subthemeRepository.findAll();
        assertThat(subthemeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
