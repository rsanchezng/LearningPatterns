package com.fime.web.rest;

import com.fime.LearningPatternsApp;
import com.fime.domain.Subject;
import com.fime.domain.Teacher;
import com.fime.repository.SubjectRepository;
import com.fime.service.SubjectService;
import com.fime.web.rest.errors.ExceptionTranslator;
import com.fime.service.dto.SubjectCriteria;
import com.fime.service.SubjectQueryService;

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
 * Integration tests for the {@link SubjectResource} REST controller.
 */
@SpringBootTest(classes = LearningPatternsApp.class)
public class SubjectResourceIT {

    private static final String DEFAULT_SUBJECT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SUBJECT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_SUBJECT_CREDITS = 1;
    private static final Integer UPDATED_SUBJECT_CREDITS = 2;
    private static final Integer SMALLER_SUBJECT_CREDITS = 1 - 1;

    private static final String DEFAULT_SUBJECT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_SUBJECT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SUBJECT_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_SUBJECT_CREATION_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_SUBJECT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_SUBJECT_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SUBJECT_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_SUBJECT_MODIFIED_DATE = LocalDate.ofEpochDay(-1L);

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SubjectQueryService subjectQueryService;

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

    private MockMvc restSubjectMockMvc;

    private Subject subject;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SubjectResource subjectResource = new SubjectResource(subjectService, subjectQueryService);
        this.restSubjectMockMvc = MockMvcBuilders.standaloneSetup(subjectResource)
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
    public static Subject createEntity(EntityManager em) {
        Subject subject = new Subject()
            .subjectName(DEFAULT_SUBJECT_NAME)
            .subjectDescription(DEFAULT_SUBJECT_DESCRIPTION)
            .subjectCredits(DEFAULT_SUBJECT_CREDITS)
            .subjectCreatedBy(DEFAULT_SUBJECT_CREATED_BY)
            .subjectCreationDate(DEFAULT_SUBJECT_CREATION_DATE)
            .subjectModifiedBy(DEFAULT_SUBJECT_MODIFIED_BY)
            .subjectModifiedDate(DEFAULT_SUBJECT_MODIFIED_DATE);
        return subject;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subject createUpdatedEntity(EntityManager em) {
        Subject subject = new Subject()
            .subjectName(UPDATED_SUBJECT_NAME)
            .subjectDescription(UPDATED_SUBJECT_DESCRIPTION)
            .subjectCredits(UPDATED_SUBJECT_CREDITS)
            .subjectCreatedBy(UPDATED_SUBJECT_CREATED_BY)
            .subjectCreationDate(UPDATED_SUBJECT_CREATION_DATE)
            .subjectModifiedBy(UPDATED_SUBJECT_MODIFIED_BY)
            .subjectModifiedDate(UPDATED_SUBJECT_MODIFIED_DATE);
        return subject;
    }

    @BeforeEach
    public void initTest() {
        subject = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubject() throws Exception {
        int databaseSizeBeforeCreate = subjectRepository.findAll().size();

        // Create the Subject
        restSubjectMockMvc.perform(post("/api/subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subject)))
            .andExpect(status().isCreated());

        // Validate the Subject in the database
        List<Subject> subjectList = subjectRepository.findAll();
        assertThat(subjectList).hasSize(databaseSizeBeforeCreate + 1);
        Subject testSubject = subjectList.get(subjectList.size() - 1);
        assertThat(testSubject.getSubjectName()).isEqualTo(DEFAULT_SUBJECT_NAME);
        assertThat(testSubject.getSubjectDescription()).isEqualTo(DEFAULT_SUBJECT_DESCRIPTION);
        assertThat(testSubject.getSubjectCredits()).isEqualTo(DEFAULT_SUBJECT_CREDITS);
        assertThat(testSubject.getSubjectCreatedBy()).isEqualTo(DEFAULT_SUBJECT_CREATED_BY);
        assertThat(testSubject.getSubjectCreationDate()).isEqualTo(DEFAULT_SUBJECT_CREATION_DATE);
        assertThat(testSubject.getSubjectModifiedBy()).isEqualTo(DEFAULT_SUBJECT_MODIFIED_BY);
        assertThat(testSubject.getSubjectModifiedDate()).isEqualTo(DEFAULT_SUBJECT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createSubjectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subjectRepository.findAll().size();

        // Create the Subject with an existing ID
        subject.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubjectMockMvc.perform(post("/api/subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subject)))
            .andExpect(status().isBadRequest());

        // Validate the Subject in the database
        List<Subject> subjectList = subjectRepository.findAll();
        assertThat(subjectList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSubjects() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList
        restSubjectMockMvc.perform(get("/api/subjects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subject.getId().intValue())))
            .andExpect(jsonPath("$.[*].subjectName").value(hasItem(DEFAULT_SUBJECT_NAME)))
            .andExpect(jsonPath("$.[*].subjectDescription").value(hasItem(DEFAULT_SUBJECT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].subjectCredits").value(hasItem(DEFAULT_SUBJECT_CREDITS)))
            .andExpect(jsonPath("$.[*].subjectCreatedBy").value(hasItem(DEFAULT_SUBJECT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].subjectCreationDate").value(hasItem(DEFAULT_SUBJECT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].subjectModifiedBy").value(hasItem(DEFAULT_SUBJECT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].subjectModifiedDate").value(hasItem(DEFAULT_SUBJECT_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getSubject() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get the subject
        restSubjectMockMvc.perform(get("/api/subjects/{id}", subject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(subject.getId().intValue()))
            .andExpect(jsonPath("$.subjectName").value(DEFAULT_SUBJECT_NAME))
            .andExpect(jsonPath("$.subjectDescription").value(DEFAULT_SUBJECT_DESCRIPTION))
            .andExpect(jsonPath("$.subjectCredits").value(DEFAULT_SUBJECT_CREDITS))
            .andExpect(jsonPath("$.subjectCreatedBy").value(DEFAULT_SUBJECT_CREATED_BY))
            .andExpect(jsonPath("$.subjectCreationDate").value(DEFAULT_SUBJECT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.subjectModifiedBy").value(DEFAULT_SUBJECT_MODIFIED_BY))
            .andExpect(jsonPath("$.subjectModifiedDate").value(DEFAULT_SUBJECT_MODIFIED_DATE.toString()));
    }


    @Test
    @Transactional
    public void getSubjectsByIdFiltering() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        Long id = subject.getId();

        defaultSubjectShouldBeFound("id.equals=" + id);
        defaultSubjectShouldNotBeFound("id.notEquals=" + id);

        defaultSubjectShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSubjectShouldNotBeFound("id.greaterThan=" + id);

        defaultSubjectShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSubjectShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSubjectsBySubjectNameIsEqualToSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectName equals to DEFAULT_SUBJECT_NAME
        defaultSubjectShouldBeFound("subjectName.equals=" + DEFAULT_SUBJECT_NAME);

        // Get all the subjectList where subjectName equals to UPDATED_SUBJECT_NAME
        defaultSubjectShouldNotBeFound("subjectName.equals=" + UPDATED_SUBJECT_NAME);
    }

    @Test
    @Transactional
    public void getAllSubjectsBySubjectNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectName not equals to DEFAULT_SUBJECT_NAME
        defaultSubjectShouldNotBeFound("subjectName.notEquals=" + DEFAULT_SUBJECT_NAME);

        // Get all the subjectList where subjectName not equals to UPDATED_SUBJECT_NAME
        defaultSubjectShouldBeFound("subjectName.notEquals=" + UPDATED_SUBJECT_NAME);
    }

    @Test
    @Transactional
    public void getAllSubjectsBySubjectNameIsInShouldWork() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectName in DEFAULT_SUBJECT_NAME or UPDATED_SUBJECT_NAME
        defaultSubjectShouldBeFound("subjectName.in=" + DEFAULT_SUBJECT_NAME + "," + UPDATED_SUBJECT_NAME);

        // Get all the subjectList where subjectName equals to UPDATED_SUBJECT_NAME
        defaultSubjectShouldNotBeFound("subjectName.in=" + UPDATED_SUBJECT_NAME);
    }

    @Test
    @Transactional
    public void getAllSubjectsBySubjectNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectName is not null
        defaultSubjectShouldBeFound("subjectName.specified=true");

        // Get all the subjectList where subjectName is null
        defaultSubjectShouldNotBeFound("subjectName.specified=false");
    }
                @Test
    @Transactional
    public void getAllSubjectsBySubjectNameContainsSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectName contains DEFAULT_SUBJECT_NAME
        defaultSubjectShouldBeFound("subjectName.contains=" + DEFAULT_SUBJECT_NAME);

        // Get all the subjectList where subjectName contains UPDATED_SUBJECT_NAME
        defaultSubjectShouldNotBeFound("subjectName.contains=" + UPDATED_SUBJECT_NAME);
    }

    @Test
    @Transactional
    public void getAllSubjectsBySubjectNameNotContainsSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectName does not contain DEFAULT_SUBJECT_NAME
        defaultSubjectShouldNotBeFound("subjectName.doesNotContain=" + DEFAULT_SUBJECT_NAME);

        // Get all the subjectList where subjectName does not contain UPDATED_SUBJECT_NAME
        defaultSubjectShouldBeFound("subjectName.doesNotContain=" + UPDATED_SUBJECT_NAME);
    }


    @Test
    @Transactional
    public void getAllSubjectsBySubjectDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectDescription equals to DEFAULT_SUBJECT_DESCRIPTION
        defaultSubjectShouldBeFound("subjectDescription.equals=" + DEFAULT_SUBJECT_DESCRIPTION);

        // Get all the subjectList where subjectDescription equals to UPDATED_SUBJECT_DESCRIPTION
        defaultSubjectShouldNotBeFound("subjectDescription.equals=" + UPDATED_SUBJECT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSubjectsBySubjectDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectDescription not equals to DEFAULT_SUBJECT_DESCRIPTION
        defaultSubjectShouldNotBeFound("subjectDescription.notEquals=" + DEFAULT_SUBJECT_DESCRIPTION);

        // Get all the subjectList where subjectDescription not equals to UPDATED_SUBJECT_DESCRIPTION
        defaultSubjectShouldBeFound("subjectDescription.notEquals=" + UPDATED_SUBJECT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSubjectsBySubjectDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectDescription in DEFAULT_SUBJECT_DESCRIPTION or UPDATED_SUBJECT_DESCRIPTION
        defaultSubjectShouldBeFound("subjectDescription.in=" + DEFAULT_SUBJECT_DESCRIPTION + "," + UPDATED_SUBJECT_DESCRIPTION);

        // Get all the subjectList where subjectDescription equals to UPDATED_SUBJECT_DESCRIPTION
        defaultSubjectShouldNotBeFound("subjectDescription.in=" + UPDATED_SUBJECT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSubjectsBySubjectDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectDescription is not null
        defaultSubjectShouldBeFound("subjectDescription.specified=true");

        // Get all the subjectList where subjectDescription is null
        defaultSubjectShouldNotBeFound("subjectDescription.specified=false");
    }
                @Test
    @Transactional
    public void getAllSubjectsBySubjectDescriptionContainsSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectDescription contains DEFAULT_SUBJECT_DESCRIPTION
        defaultSubjectShouldBeFound("subjectDescription.contains=" + DEFAULT_SUBJECT_DESCRIPTION);

        // Get all the subjectList where subjectDescription contains UPDATED_SUBJECT_DESCRIPTION
        defaultSubjectShouldNotBeFound("subjectDescription.contains=" + UPDATED_SUBJECT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSubjectsBySubjectDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectDescription does not contain DEFAULT_SUBJECT_DESCRIPTION
        defaultSubjectShouldNotBeFound("subjectDescription.doesNotContain=" + DEFAULT_SUBJECT_DESCRIPTION);

        // Get all the subjectList where subjectDescription does not contain UPDATED_SUBJECT_DESCRIPTION
        defaultSubjectShouldBeFound("subjectDescription.doesNotContain=" + UPDATED_SUBJECT_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllSubjectsBySubjectCreditsIsEqualToSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectCredits equals to DEFAULT_SUBJECT_CREDITS
        defaultSubjectShouldBeFound("subjectCredits.equals=" + DEFAULT_SUBJECT_CREDITS);

        // Get all the subjectList where subjectCredits equals to UPDATED_SUBJECT_CREDITS
        defaultSubjectShouldNotBeFound("subjectCredits.equals=" + UPDATED_SUBJECT_CREDITS);
    }

    @Test
    @Transactional
    public void getAllSubjectsBySubjectCreditsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectCredits not equals to DEFAULT_SUBJECT_CREDITS
        defaultSubjectShouldNotBeFound("subjectCredits.notEquals=" + DEFAULT_SUBJECT_CREDITS);

        // Get all the subjectList where subjectCredits not equals to UPDATED_SUBJECT_CREDITS
        defaultSubjectShouldBeFound("subjectCredits.notEquals=" + UPDATED_SUBJECT_CREDITS);
    }

    @Test
    @Transactional
    public void getAllSubjectsBySubjectCreditsIsInShouldWork() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectCredits in DEFAULT_SUBJECT_CREDITS or UPDATED_SUBJECT_CREDITS
        defaultSubjectShouldBeFound("subjectCredits.in=" + DEFAULT_SUBJECT_CREDITS + "," + UPDATED_SUBJECT_CREDITS);

        // Get all the subjectList where subjectCredits equals to UPDATED_SUBJECT_CREDITS
        defaultSubjectShouldNotBeFound("subjectCredits.in=" + UPDATED_SUBJECT_CREDITS);
    }

    @Test
    @Transactional
    public void getAllSubjectsBySubjectCreditsIsNullOrNotNull() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectCredits is not null
        defaultSubjectShouldBeFound("subjectCredits.specified=true");

        // Get all the subjectList where subjectCredits is null
        defaultSubjectShouldNotBeFound("subjectCredits.specified=false");
    }

    @Test
    @Transactional
    public void getAllSubjectsBySubjectCreditsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectCredits is greater than or equal to DEFAULT_SUBJECT_CREDITS
        defaultSubjectShouldBeFound("subjectCredits.greaterThanOrEqual=" + DEFAULT_SUBJECT_CREDITS);

        // Get all the subjectList where subjectCredits is greater than or equal to UPDATED_SUBJECT_CREDITS
        defaultSubjectShouldNotBeFound("subjectCredits.greaterThanOrEqual=" + UPDATED_SUBJECT_CREDITS);
    }

    @Test
    @Transactional
    public void getAllSubjectsBySubjectCreditsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectCredits is less than or equal to DEFAULT_SUBJECT_CREDITS
        defaultSubjectShouldBeFound("subjectCredits.lessThanOrEqual=" + DEFAULT_SUBJECT_CREDITS);

        // Get all the subjectList where subjectCredits is less than or equal to SMALLER_SUBJECT_CREDITS
        defaultSubjectShouldNotBeFound("subjectCredits.lessThanOrEqual=" + SMALLER_SUBJECT_CREDITS);
    }

    @Test
    @Transactional
    public void getAllSubjectsBySubjectCreditsIsLessThanSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectCredits is less than DEFAULT_SUBJECT_CREDITS
        defaultSubjectShouldNotBeFound("subjectCredits.lessThan=" + DEFAULT_SUBJECT_CREDITS);

        // Get all the subjectList where subjectCredits is less than UPDATED_SUBJECT_CREDITS
        defaultSubjectShouldBeFound("subjectCredits.lessThan=" + UPDATED_SUBJECT_CREDITS);
    }

    @Test
    @Transactional
    public void getAllSubjectsBySubjectCreditsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectCredits is greater than DEFAULT_SUBJECT_CREDITS
        defaultSubjectShouldNotBeFound("subjectCredits.greaterThan=" + DEFAULT_SUBJECT_CREDITS);

        // Get all the subjectList where subjectCredits is greater than SMALLER_SUBJECT_CREDITS
        defaultSubjectShouldBeFound("subjectCredits.greaterThan=" + SMALLER_SUBJECT_CREDITS);
    }


    @Test
    @Transactional
    public void getAllSubjectsBySubjectCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectCreatedBy equals to DEFAULT_SUBJECT_CREATED_BY
        defaultSubjectShouldBeFound("subjectCreatedBy.equals=" + DEFAULT_SUBJECT_CREATED_BY);

        // Get all the subjectList where subjectCreatedBy equals to UPDATED_SUBJECT_CREATED_BY
        defaultSubjectShouldNotBeFound("subjectCreatedBy.equals=" + UPDATED_SUBJECT_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSubjectsBySubjectCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectCreatedBy not equals to DEFAULT_SUBJECT_CREATED_BY
        defaultSubjectShouldNotBeFound("subjectCreatedBy.notEquals=" + DEFAULT_SUBJECT_CREATED_BY);

        // Get all the subjectList where subjectCreatedBy not equals to UPDATED_SUBJECT_CREATED_BY
        defaultSubjectShouldBeFound("subjectCreatedBy.notEquals=" + UPDATED_SUBJECT_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSubjectsBySubjectCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectCreatedBy in DEFAULT_SUBJECT_CREATED_BY or UPDATED_SUBJECT_CREATED_BY
        defaultSubjectShouldBeFound("subjectCreatedBy.in=" + DEFAULT_SUBJECT_CREATED_BY + "," + UPDATED_SUBJECT_CREATED_BY);

        // Get all the subjectList where subjectCreatedBy equals to UPDATED_SUBJECT_CREATED_BY
        defaultSubjectShouldNotBeFound("subjectCreatedBy.in=" + UPDATED_SUBJECT_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSubjectsBySubjectCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectCreatedBy is not null
        defaultSubjectShouldBeFound("subjectCreatedBy.specified=true");

        // Get all the subjectList where subjectCreatedBy is null
        defaultSubjectShouldNotBeFound("subjectCreatedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllSubjectsBySubjectCreatedByContainsSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectCreatedBy contains DEFAULT_SUBJECT_CREATED_BY
        defaultSubjectShouldBeFound("subjectCreatedBy.contains=" + DEFAULT_SUBJECT_CREATED_BY);

        // Get all the subjectList where subjectCreatedBy contains UPDATED_SUBJECT_CREATED_BY
        defaultSubjectShouldNotBeFound("subjectCreatedBy.contains=" + UPDATED_SUBJECT_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSubjectsBySubjectCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectCreatedBy does not contain DEFAULT_SUBJECT_CREATED_BY
        defaultSubjectShouldNotBeFound("subjectCreatedBy.doesNotContain=" + DEFAULT_SUBJECT_CREATED_BY);

        // Get all the subjectList where subjectCreatedBy does not contain UPDATED_SUBJECT_CREATED_BY
        defaultSubjectShouldBeFound("subjectCreatedBy.doesNotContain=" + UPDATED_SUBJECT_CREATED_BY);
    }


    @Test
    @Transactional
    public void getAllSubjectsBySubjectCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectCreationDate equals to DEFAULT_SUBJECT_CREATION_DATE
        defaultSubjectShouldBeFound("subjectCreationDate.equals=" + DEFAULT_SUBJECT_CREATION_DATE);

        // Get all the subjectList where subjectCreationDate equals to UPDATED_SUBJECT_CREATION_DATE
        defaultSubjectShouldNotBeFound("subjectCreationDate.equals=" + UPDATED_SUBJECT_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllSubjectsBySubjectCreationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectCreationDate not equals to DEFAULT_SUBJECT_CREATION_DATE
        defaultSubjectShouldNotBeFound("subjectCreationDate.notEquals=" + DEFAULT_SUBJECT_CREATION_DATE);

        // Get all the subjectList where subjectCreationDate not equals to UPDATED_SUBJECT_CREATION_DATE
        defaultSubjectShouldBeFound("subjectCreationDate.notEquals=" + UPDATED_SUBJECT_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllSubjectsBySubjectCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectCreationDate in DEFAULT_SUBJECT_CREATION_DATE or UPDATED_SUBJECT_CREATION_DATE
        defaultSubjectShouldBeFound("subjectCreationDate.in=" + DEFAULT_SUBJECT_CREATION_DATE + "," + UPDATED_SUBJECT_CREATION_DATE);

        // Get all the subjectList where subjectCreationDate equals to UPDATED_SUBJECT_CREATION_DATE
        defaultSubjectShouldNotBeFound("subjectCreationDate.in=" + UPDATED_SUBJECT_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllSubjectsBySubjectCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectCreationDate is not null
        defaultSubjectShouldBeFound("subjectCreationDate.specified=true");

        // Get all the subjectList where subjectCreationDate is null
        defaultSubjectShouldNotBeFound("subjectCreationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllSubjectsBySubjectCreationDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectCreationDate is greater than or equal to DEFAULT_SUBJECT_CREATION_DATE
        defaultSubjectShouldBeFound("subjectCreationDate.greaterThanOrEqual=" + DEFAULT_SUBJECT_CREATION_DATE);

        // Get all the subjectList where subjectCreationDate is greater than or equal to UPDATED_SUBJECT_CREATION_DATE
        defaultSubjectShouldNotBeFound("subjectCreationDate.greaterThanOrEqual=" + UPDATED_SUBJECT_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllSubjectsBySubjectCreationDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectCreationDate is less than or equal to DEFAULT_SUBJECT_CREATION_DATE
        defaultSubjectShouldBeFound("subjectCreationDate.lessThanOrEqual=" + DEFAULT_SUBJECT_CREATION_DATE);

        // Get all the subjectList where subjectCreationDate is less than or equal to SMALLER_SUBJECT_CREATION_DATE
        defaultSubjectShouldNotBeFound("subjectCreationDate.lessThanOrEqual=" + SMALLER_SUBJECT_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllSubjectsBySubjectCreationDateIsLessThanSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectCreationDate is less than DEFAULT_SUBJECT_CREATION_DATE
        defaultSubjectShouldNotBeFound("subjectCreationDate.lessThan=" + DEFAULT_SUBJECT_CREATION_DATE);

        // Get all the subjectList where subjectCreationDate is less than UPDATED_SUBJECT_CREATION_DATE
        defaultSubjectShouldBeFound("subjectCreationDate.lessThan=" + UPDATED_SUBJECT_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllSubjectsBySubjectCreationDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectCreationDate is greater than DEFAULT_SUBJECT_CREATION_DATE
        defaultSubjectShouldNotBeFound("subjectCreationDate.greaterThan=" + DEFAULT_SUBJECT_CREATION_DATE);

        // Get all the subjectList where subjectCreationDate is greater than SMALLER_SUBJECT_CREATION_DATE
        defaultSubjectShouldBeFound("subjectCreationDate.greaterThan=" + SMALLER_SUBJECT_CREATION_DATE);
    }


    @Test
    @Transactional
    public void getAllSubjectsBySubjectModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectModifiedBy equals to DEFAULT_SUBJECT_MODIFIED_BY
        defaultSubjectShouldBeFound("subjectModifiedBy.equals=" + DEFAULT_SUBJECT_MODIFIED_BY);

        // Get all the subjectList where subjectModifiedBy equals to UPDATED_SUBJECT_MODIFIED_BY
        defaultSubjectShouldNotBeFound("subjectModifiedBy.equals=" + UPDATED_SUBJECT_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSubjectsBySubjectModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectModifiedBy not equals to DEFAULT_SUBJECT_MODIFIED_BY
        defaultSubjectShouldNotBeFound("subjectModifiedBy.notEquals=" + DEFAULT_SUBJECT_MODIFIED_BY);

        // Get all the subjectList where subjectModifiedBy not equals to UPDATED_SUBJECT_MODIFIED_BY
        defaultSubjectShouldBeFound("subjectModifiedBy.notEquals=" + UPDATED_SUBJECT_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSubjectsBySubjectModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectModifiedBy in DEFAULT_SUBJECT_MODIFIED_BY or UPDATED_SUBJECT_MODIFIED_BY
        defaultSubjectShouldBeFound("subjectModifiedBy.in=" + DEFAULT_SUBJECT_MODIFIED_BY + "," + UPDATED_SUBJECT_MODIFIED_BY);

        // Get all the subjectList where subjectModifiedBy equals to UPDATED_SUBJECT_MODIFIED_BY
        defaultSubjectShouldNotBeFound("subjectModifiedBy.in=" + UPDATED_SUBJECT_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSubjectsBySubjectModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectModifiedBy is not null
        defaultSubjectShouldBeFound("subjectModifiedBy.specified=true");

        // Get all the subjectList where subjectModifiedBy is null
        defaultSubjectShouldNotBeFound("subjectModifiedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllSubjectsBySubjectModifiedByContainsSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectModifiedBy contains DEFAULT_SUBJECT_MODIFIED_BY
        defaultSubjectShouldBeFound("subjectModifiedBy.contains=" + DEFAULT_SUBJECT_MODIFIED_BY);

        // Get all the subjectList where subjectModifiedBy contains UPDATED_SUBJECT_MODIFIED_BY
        defaultSubjectShouldNotBeFound("subjectModifiedBy.contains=" + UPDATED_SUBJECT_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllSubjectsBySubjectModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectModifiedBy does not contain DEFAULT_SUBJECT_MODIFIED_BY
        defaultSubjectShouldNotBeFound("subjectModifiedBy.doesNotContain=" + DEFAULT_SUBJECT_MODIFIED_BY);

        // Get all the subjectList where subjectModifiedBy does not contain UPDATED_SUBJECT_MODIFIED_BY
        defaultSubjectShouldBeFound("subjectModifiedBy.doesNotContain=" + UPDATED_SUBJECT_MODIFIED_BY);
    }


    @Test
    @Transactional
    public void getAllSubjectsBySubjectModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectModifiedDate equals to DEFAULT_SUBJECT_MODIFIED_DATE
        defaultSubjectShouldBeFound("subjectModifiedDate.equals=" + DEFAULT_SUBJECT_MODIFIED_DATE);

        // Get all the subjectList where subjectModifiedDate equals to UPDATED_SUBJECT_MODIFIED_DATE
        defaultSubjectShouldNotBeFound("subjectModifiedDate.equals=" + UPDATED_SUBJECT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllSubjectsBySubjectModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectModifiedDate not equals to DEFAULT_SUBJECT_MODIFIED_DATE
        defaultSubjectShouldNotBeFound("subjectModifiedDate.notEquals=" + DEFAULT_SUBJECT_MODIFIED_DATE);

        // Get all the subjectList where subjectModifiedDate not equals to UPDATED_SUBJECT_MODIFIED_DATE
        defaultSubjectShouldBeFound("subjectModifiedDate.notEquals=" + UPDATED_SUBJECT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllSubjectsBySubjectModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectModifiedDate in DEFAULT_SUBJECT_MODIFIED_DATE or UPDATED_SUBJECT_MODIFIED_DATE
        defaultSubjectShouldBeFound("subjectModifiedDate.in=" + DEFAULT_SUBJECT_MODIFIED_DATE + "," + UPDATED_SUBJECT_MODIFIED_DATE);

        // Get all the subjectList where subjectModifiedDate equals to UPDATED_SUBJECT_MODIFIED_DATE
        defaultSubjectShouldNotBeFound("subjectModifiedDate.in=" + UPDATED_SUBJECT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllSubjectsBySubjectModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectModifiedDate is not null
        defaultSubjectShouldBeFound("subjectModifiedDate.specified=true");

        // Get all the subjectList where subjectModifiedDate is null
        defaultSubjectShouldNotBeFound("subjectModifiedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllSubjectsBySubjectModifiedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectModifiedDate is greater than or equal to DEFAULT_SUBJECT_MODIFIED_DATE
        defaultSubjectShouldBeFound("subjectModifiedDate.greaterThanOrEqual=" + DEFAULT_SUBJECT_MODIFIED_DATE);

        // Get all the subjectList where subjectModifiedDate is greater than or equal to UPDATED_SUBJECT_MODIFIED_DATE
        defaultSubjectShouldNotBeFound("subjectModifiedDate.greaterThanOrEqual=" + UPDATED_SUBJECT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllSubjectsBySubjectModifiedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectModifiedDate is less than or equal to DEFAULT_SUBJECT_MODIFIED_DATE
        defaultSubjectShouldBeFound("subjectModifiedDate.lessThanOrEqual=" + DEFAULT_SUBJECT_MODIFIED_DATE);

        // Get all the subjectList where subjectModifiedDate is less than or equal to SMALLER_SUBJECT_MODIFIED_DATE
        defaultSubjectShouldNotBeFound("subjectModifiedDate.lessThanOrEqual=" + SMALLER_SUBJECT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllSubjectsBySubjectModifiedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectModifiedDate is less than DEFAULT_SUBJECT_MODIFIED_DATE
        defaultSubjectShouldNotBeFound("subjectModifiedDate.lessThan=" + DEFAULT_SUBJECT_MODIFIED_DATE);

        // Get all the subjectList where subjectModifiedDate is less than UPDATED_SUBJECT_MODIFIED_DATE
        defaultSubjectShouldBeFound("subjectModifiedDate.lessThan=" + UPDATED_SUBJECT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllSubjectsBySubjectModifiedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjectList where subjectModifiedDate is greater than DEFAULT_SUBJECT_MODIFIED_DATE
        defaultSubjectShouldNotBeFound("subjectModifiedDate.greaterThan=" + DEFAULT_SUBJECT_MODIFIED_DATE);

        // Get all the subjectList where subjectModifiedDate is greater than SMALLER_SUBJECT_MODIFIED_DATE
        defaultSubjectShouldBeFound("subjectModifiedDate.greaterThan=" + SMALLER_SUBJECT_MODIFIED_DATE);
    }


    @Test
    @Transactional
    public void getAllSubjectsByTeacherIsEqualToSomething() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);
        Teacher teacher = TeacherResourceIT.createEntity(em);
        em.persist(teacher);
        em.flush();
        subject.setTeacher(teacher);
        subjectRepository.saveAndFlush(subject);
        Long teacherId = teacher.getId();

        // Get all the subjectList where teacher equals to teacherId
        defaultSubjectShouldBeFound("teacherId.equals=" + teacherId);

        // Get all the subjectList where teacher equals to teacherId + 1
        defaultSubjectShouldNotBeFound("teacherId.equals=" + (teacherId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSubjectShouldBeFound(String filter) throws Exception {
        restSubjectMockMvc.perform(get("/api/subjects?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subject.getId().intValue())))
            .andExpect(jsonPath("$.[*].subjectName").value(hasItem(DEFAULT_SUBJECT_NAME)))
            .andExpect(jsonPath("$.[*].subjectDescription").value(hasItem(DEFAULT_SUBJECT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].subjectCredits").value(hasItem(DEFAULT_SUBJECT_CREDITS)))
            .andExpect(jsonPath("$.[*].subjectCreatedBy").value(hasItem(DEFAULT_SUBJECT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].subjectCreationDate").value(hasItem(DEFAULT_SUBJECT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].subjectModifiedBy").value(hasItem(DEFAULT_SUBJECT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].subjectModifiedDate").value(hasItem(DEFAULT_SUBJECT_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restSubjectMockMvc.perform(get("/api/subjects/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSubjectShouldNotBeFound(String filter) throws Exception {
        restSubjectMockMvc.perform(get("/api/subjects?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSubjectMockMvc.perform(get("/api/subjects/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSubject() throws Exception {
        // Get the subject
        restSubjectMockMvc.perform(get("/api/subjects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubject() throws Exception {
        // Initialize the database
        subjectService.save(subject);

        int databaseSizeBeforeUpdate = subjectRepository.findAll().size();

        // Update the subject
        Subject updatedSubject = subjectRepository.findById(subject.getId()).get();
        // Disconnect from session so that the updates on updatedSubject are not directly saved in db
        em.detach(updatedSubject);
        updatedSubject
            .subjectName(UPDATED_SUBJECT_NAME)
            .subjectDescription(UPDATED_SUBJECT_DESCRIPTION)
            .subjectCredits(UPDATED_SUBJECT_CREDITS)
            .subjectCreatedBy(UPDATED_SUBJECT_CREATED_BY)
            .subjectCreationDate(UPDATED_SUBJECT_CREATION_DATE)
            .subjectModifiedBy(UPDATED_SUBJECT_MODIFIED_BY)
            .subjectModifiedDate(UPDATED_SUBJECT_MODIFIED_DATE);

        restSubjectMockMvc.perform(put("/api/subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSubject)))
            .andExpect(status().isOk());

        // Validate the Subject in the database
        List<Subject> subjectList = subjectRepository.findAll();
        assertThat(subjectList).hasSize(databaseSizeBeforeUpdate);
        Subject testSubject = subjectList.get(subjectList.size() - 1);
        assertThat(testSubject.getSubjectName()).isEqualTo(UPDATED_SUBJECT_NAME);
        assertThat(testSubject.getSubjectDescription()).isEqualTo(UPDATED_SUBJECT_DESCRIPTION);
        assertThat(testSubject.getSubjectCredits()).isEqualTo(UPDATED_SUBJECT_CREDITS);
        assertThat(testSubject.getSubjectCreatedBy()).isEqualTo(UPDATED_SUBJECT_CREATED_BY);
        assertThat(testSubject.getSubjectCreationDate()).isEqualTo(UPDATED_SUBJECT_CREATION_DATE);
        assertThat(testSubject.getSubjectModifiedBy()).isEqualTo(UPDATED_SUBJECT_MODIFIED_BY);
        assertThat(testSubject.getSubjectModifiedDate()).isEqualTo(UPDATED_SUBJECT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingSubject() throws Exception {
        int databaseSizeBeforeUpdate = subjectRepository.findAll().size();

        // Create the Subject

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubjectMockMvc.perform(put("/api/subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subject)))
            .andExpect(status().isBadRequest());

        // Validate the Subject in the database
        List<Subject> subjectList = subjectRepository.findAll();
        assertThat(subjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSubject() throws Exception {
        // Initialize the database
        subjectService.save(subject);

        int databaseSizeBeforeDelete = subjectRepository.findAll().size();

        // Delete the subject
        restSubjectMockMvc.perform(delete("/api/subjects/{id}", subject.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Subject> subjectList = subjectRepository.findAll();
        assertThat(subjectList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
