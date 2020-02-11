package com.fime.web.rest;

import com.fime.LearningPatternsApp;
import com.fime.domain.Teacher;
import com.fime.repository.TeacherRepository;
import com.fime.service.TeacherService;
import com.fime.web.rest.errors.ExceptionTranslator;
import com.fime.service.dto.TeacherCriteria;
import com.fime.service.TeacherQueryService;

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
 * Integration tests for the {@link TeacherResource} REST controller.
 */
@SpringBootTest(classes = LearningPatternsApp.class)
public class TeacherResourceIT {

    private static final String DEFAULT_TEACHER_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TEACHER_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TEACHER_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TEACHER_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TEACHER_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_TEACHER_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TEACHER_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_TEACHER_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_TEACHER_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_TEACHER_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TEACHER_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TEACHER_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_TEACHER_CREATION_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_TEACHER_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_TEACHER_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TEACHER_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TEACHER_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_TEACHER_MODIFIED_DATE = LocalDate.ofEpochDay(-1L);

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private TeacherQueryService teacherQueryService;

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

    private MockMvc restTeacherMockMvc;

    private Teacher teacher;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TeacherResource teacherResource = new TeacherResource(teacherService, teacherQueryService);
        this.restTeacherMockMvc = MockMvcBuilders.standaloneSetup(teacherResource)
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
    public static Teacher createEntity(EntityManager em) {
        Teacher teacher = new Teacher()
            .teacherFirstName(DEFAULT_TEACHER_FIRST_NAME)
            .teacherLastName(DEFAULT_TEACHER_LAST_NAME)
            .teacherEmail(DEFAULT_TEACHER_EMAIL)
            .teacherPassword(DEFAULT_TEACHER_PASSWORD)
            .teacherCreatedBy(DEFAULT_TEACHER_CREATED_BY)
            .teacherCreationDate(DEFAULT_TEACHER_CREATION_DATE)
            .teacherModifiedBy(DEFAULT_TEACHER_MODIFIED_BY)
            .teacherModifiedDate(DEFAULT_TEACHER_MODIFIED_DATE);
        return teacher;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Teacher createUpdatedEntity(EntityManager em) {
        Teacher teacher = new Teacher()
            .teacherFirstName(UPDATED_TEACHER_FIRST_NAME)
            .teacherLastName(UPDATED_TEACHER_LAST_NAME)
            .teacherEmail(UPDATED_TEACHER_EMAIL)
            .teacherPassword(UPDATED_TEACHER_PASSWORD)
            .teacherCreatedBy(UPDATED_TEACHER_CREATED_BY)
            .teacherCreationDate(UPDATED_TEACHER_CREATION_DATE)
            .teacherModifiedBy(UPDATED_TEACHER_MODIFIED_BY)
            .teacherModifiedDate(UPDATED_TEACHER_MODIFIED_DATE);
        return teacher;
    }

    @BeforeEach
    public void initTest() {
        teacher = createEntity(em);
    }

    @Test
    @Transactional
    public void createTeacher() throws Exception {
        int databaseSizeBeforeCreate = teacherRepository.findAll().size();

        // Create the Teacher
        restTeacherMockMvc.perform(post("/api/teachers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teacher)))
            .andExpect(status().isCreated());

        // Validate the Teacher in the database
        List<Teacher> teacherList = teacherRepository.findAll();
        assertThat(teacherList).hasSize(databaseSizeBeforeCreate + 1);
        Teacher testTeacher = teacherList.get(teacherList.size() - 1);
        assertThat(testTeacher.getTeacherFirstName()).isEqualTo(DEFAULT_TEACHER_FIRST_NAME);
        assertThat(testTeacher.getTeacherLastName()).isEqualTo(DEFAULT_TEACHER_LAST_NAME);
        assertThat(testTeacher.getTeacherEmail()).isEqualTo(DEFAULT_TEACHER_EMAIL);
        assertThat(testTeacher.getTeacherPassword()).isEqualTo(DEFAULT_TEACHER_PASSWORD);
        assertThat(testTeacher.getTeacherCreatedBy()).isEqualTo(DEFAULT_TEACHER_CREATED_BY);
        assertThat(testTeacher.getTeacherCreationDate()).isEqualTo(DEFAULT_TEACHER_CREATION_DATE);
        assertThat(testTeacher.getTeacherModifiedBy()).isEqualTo(DEFAULT_TEACHER_MODIFIED_BY);
        assertThat(testTeacher.getTeacherModifiedDate()).isEqualTo(DEFAULT_TEACHER_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createTeacherWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = teacherRepository.findAll().size();

        // Create the Teacher with an existing ID
        teacher.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeacherMockMvc.perform(post("/api/teachers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teacher)))
            .andExpect(status().isBadRequest());

        // Validate the Teacher in the database
        List<Teacher> teacherList = teacherRepository.findAll();
        assertThat(teacherList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTeachers() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList
        restTeacherMockMvc.perform(get("/api/teachers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teacher.getId().intValue())))
            .andExpect(jsonPath("$.[*].teacherFirstName").value(hasItem(DEFAULT_TEACHER_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].teacherLastName").value(hasItem(DEFAULT_TEACHER_LAST_NAME)))
            .andExpect(jsonPath("$.[*].teacherEmail").value(hasItem(DEFAULT_TEACHER_EMAIL)))
            .andExpect(jsonPath("$.[*].teacherPassword").value(hasItem(DEFAULT_TEACHER_PASSWORD)))
            .andExpect(jsonPath("$.[*].teacherCreatedBy").value(hasItem(DEFAULT_TEACHER_CREATED_BY)))
            .andExpect(jsonPath("$.[*].teacherCreationDate").value(hasItem(DEFAULT_TEACHER_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].teacherModifiedBy").value(hasItem(DEFAULT_TEACHER_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].teacherModifiedDate").value(hasItem(DEFAULT_TEACHER_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getTeacher() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get the teacher
        restTeacherMockMvc.perform(get("/api/teachers/{id}", teacher.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(teacher.getId().intValue()))
            .andExpect(jsonPath("$.teacherFirstName").value(DEFAULT_TEACHER_FIRST_NAME))
            .andExpect(jsonPath("$.teacherLastName").value(DEFAULT_TEACHER_LAST_NAME))
            .andExpect(jsonPath("$.teacherEmail").value(DEFAULT_TEACHER_EMAIL))
            .andExpect(jsonPath("$.teacherPassword").value(DEFAULT_TEACHER_PASSWORD))
            .andExpect(jsonPath("$.teacherCreatedBy").value(DEFAULT_TEACHER_CREATED_BY))
            .andExpect(jsonPath("$.teacherCreationDate").value(DEFAULT_TEACHER_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.teacherModifiedBy").value(DEFAULT_TEACHER_MODIFIED_BY))
            .andExpect(jsonPath("$.teacherModifiedDate").value(DEFAULT_TEACHER_MODIFIED_DATE.toString()));
    }


    @Test
    @Transactional
    public void getTeachersByIdFiltering() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        Long id = teacher.getId();

        defaultTeacherShouldBeFound("id.equals=" + id);
        defaultTeacherShouldNotBeFound("id.notEquals=" + id);

        defaultTeacherShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTeacherShouldNotBeFound("id.greaterThan=" + id);

        defaultTeacherShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTeacherShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTeachersByTeacherFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherFirstName equals to DEFAULT_TEACHER_FIRST_NAME
        defaultTeacherShouldBeFound("teacherFirstName.equals=" + DEFAULT_TEACHER_FIRST_NAME);

        // Get all the teacherList where teacherFirstName equals to UPDATED_TEACHER_FIRST_NAME
        defaultTeacherShouldNotBeFound("teacherFirstName.equals=" + UPDATED_TEACHER_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherFirstNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherFirstName not equals to DEFAULT_TEACHER_FIRST_NAME
        defaultTeacherShouldNotBeFound("teacherFirstName.notEquals=" + DEFAULT_TEACHER_FIRST_NAME);

        // Get all the teacherList where teacherFirstName not equals to UPDATED_TEACHER_FIRST_NAME
        defaultTeacherShouldBeFound("teacherFirstName.notEquals=" + UPDATED_TEACHER_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherFirstName in DEFAULT_TEACHER_FIRST_NAME or UPDATED_TEACHER_FIRST_NAME
        defaultTeacherShouldBeFound("teacherFirstName.in=" + DEFAULT_TEACHER_FIRST_NAME + "," + UPDATED_TEACHER_FIRST_NAME);

        // Get all the teacherList where teacherFirstName equals to UPDATED_TEACHER_FIRST_NAME
        defaultTeacherShouldNotBeFound("teacherFirstName.in=" + UPDATED_TEACHER_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherFirstName is not null
        defaultTeacherShouldBeFound("teacherFirstName.specified=true");

        // Get all the teacherList where teacherFirstName is null
        defaultTeacherShouldNotBeFound("teacherFirstName.specified=false");
    }
                @Test
    @Transactional
    public void getAllTeachersByTeacherFirstNameContainsSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherFirstName contains DEFAULT_TEACHER_FIRST_NAME
        defaultTeacherShouldBeFound("teacherFirstName.contains=" + DEFAULT_TEACHER_FIRST_NAME);

        // Get all the teacherList where teacherFirstName contains UPDATED_TEACHER_FIRST_NAME
        defaultTeacherShouldNotBeFound("teacherFirstName.contains=" + UPDATED_TEACHER_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherFirstName does not contain DEFAULT_TEACHER_FIRST_NAME
        defaultTeacherShouldNotBeFound("teacherFirstName.doesNotContain=" + DEFAULT_TEACHER_FIRST_NAME);

        // Get all the teacherList where teacherFirstName does not contain UPDATED_TEACHER_FIRST_NAME
        defaultTeacherShouldBeFound("teacherFirstName.doesNotContain=" + UPDATED_TEACHER_FIRST_NAME);
    }


    @Test
    @Transactional
    public void getAllTeachersByTeacherLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherLastName equals to DEFAULT_TEACHER_LAST_NAME
        defaultTeacherShouldBeFound("teacherLastName.equals=" + DEFAULT_TEACHER_LAST_NAME);

        // Get all the teacherList where teacherLastName equals to UPDATED_TEACHER_LAST_NAME
        defaultTeacherShouldNotBeFound("teacherLastName.equals=" + UPDATED_TEACHER_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherLastNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherLastName not equals to DEFAULT_TEACHER_LAST_NAME
        defaultTeacherShouldNotBeFound("teacherLastName.notEquals=" + DEFAULT_TEACHER_LAST_NAME);

        // Get all the teacherList where teacherLastName not equals to UPDATED_TEACHER_LAST_NAME
        defaultTeacherShouldBeFound("teacherLastName.notEquals=" + UPDATED_TEACHER_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherLastName in DEFAULT_TEACHER_LAST_NAME or UPDATED_TEACHER_LAST_NAME
        defaultTeacherShouldBeFound("teacherLastName.in=" + DEFAULT_TEACHER_LAST_NAME + "," + UPDATED_TEACHER_LAST_NAME);

        // Get all the teacherList where teacherLastName equals to UPDATED_TEACHER_LAST_NAME
        defaultTeacherShouldNotBeFound("teacherLastName.in=" + UPDATED_TEACHER_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherLastName is not null
        defaultTeacherShouldBeFound("teacherLastName.specified=true");

        // Get all the teacherList where teacherLastName is null
        defaultTeacherShouldNotBeFound("teacherLastName.specified=false");
    }
                @Test
    @Transactional
    public void getAllTeachersByTeacherLastNameContainsSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherLastName contains DEFAULT_TEACHER_LAST_NAME
        defaultTeacherShouldBeFound("teacherLastName.contains=" + DEFAULT_TEACHER_LAST_NAME);

        // Get all the teacherList where teacherLastName contains UPDATED_TEACHER_LAST_NAME
        defaultTeacherShouldNotBeFound("teacherLastName.contains=" + UPDATED_TEACHER_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherLastName does not contain DEFAULT_TEACHER_LAST_NAME
        defaultTeacherShouldNotBeFound("teacherLastName.doesNotContain=" + DEFAULT_TEACHER_LAST_NAME);

        // Get all the teacherList where teacherLastName does not contain UPDATED_TEACHER_LAST_NAME
        defaultTeacherShouldBeFound("teacherLastName.doesNotContain=" + UPDATED_TEACHER_LAST_NAME);
    }


    @Test
    @Transactional
    public void getAllTeachersByTeacherEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherEmail equals to DEFAULT_TEACHER_EMAIL
        defaultTeacherShouldBeFound("teacherEmail.equals=" + DEFAULT_TEACHER_EMAIL);

        // Get all the teacherList where teacherEmail equals to UPDATED_TEACHER_EMAIL
        defaultTeacherShouldNotBeFound("teacherEmail.equals=" + UPDATED_TEACHER_EMAIL);
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherEmail not equals to DEFAULT_TEACHER_EMAIL
        defaultTeacherShouldNotBeFound("teacherEmail.notEquals=" + DEFAULT_TEACHER_EMAIL);

        // Get all the teacherList where teacherEmail not equals to UPDATED_TEACHER_EMAIL
        defaultTeacherShouldBeFound("teacherEmail.notEquals=" + UPDATED_TEACHER_EMAIL);
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherEmailIsInShouldWork() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherEmail in DEFAULT_TEACHER_EMAIL or UPDATED_TEACHER_EMAIL
        defaultTeacherShouldBeFound("teacherEmail.in=" + DEFAULT_TEACHER_EMAIL + "," + UPDATED_TEACHER_EMAIL);

        // Get all the teacherList where teacherEmail equals to UPDATED_TEACHER_EMAIL
        defaultTeacherShouldNotBeFound("teacherEmail.in=" + UPDATED_TEACHER_EMAIL);
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherEmail is not null
        defaultTeacherShouldBeFound("teacherEmail.specified=true");

        // Get all the teacherList where teacherEmail is null
        defaultTeacherShouldNotBeFound("teacherEmail.specified=false");
    }
                @Test
    @Transactional
    public void getAllTeachersByTeacherEmailContainsSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherEmail contains DEFAULT_TEACHER_EMAIL
        defaultTeacherShouldBeFound("teacherEmail.contains=" + DEFAULT_TEACHER_EMAIL);

        // Get all the teacherList where teacherEmail contains UPDATED_TEACHER_EMAIL
        defaultTeacherShouldNotBeFound("teacherEmail.contains=" + UPDATED_TEACHER_EMAIL);
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherEmailNotContainsSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherEmail does not contain DEFAULT_TEACHER_EMAIL
        defaultTeacherShouldNotBeFound("teacherEmail.doesNotContain=" + DEFAULT_TEACHER_EMAIL);

        // Get all the teacherList where teacherEmail does not contain UPDATED_TEACHER_EMAIL
        defaultTeacherShouldBeFound("teacherEmail.doesNotContain=" + UPDATED_TEACHER_EMAIL);
    }


    @Test
    @Transactional
    public void getAllTeachersByTeacherPasswordIsEqualToSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherPassword equals to DEFAULT_TEACHER_PASSWORD
        defaultTeacherShouldBeFound("teacherPassword.equals=" + DEFAULT_TEACHER_PASSWORD);

        // Get all the teacherList where teacherPassword equals to UPDATED_TEACHER_PASSWORD
        defaultTeacherShouldNotBeFound("teacherPassword.equals=" + UPDATED_TEACHER_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherPasswordIsNotEqualToSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherPassword not equals to DEFAULT_TEACHER_PASSWORD
        defaultTeacherShouldNotBeFound("teacherPassword.notEquals=" + DEFAULT_TEACHER_PASSWORD);

        // Get all the teacherList where teacherPassword not equals to UPDATED_TEACHER_PASSWORD
        defaultTeacherShouldBeFound("teacherPassword.notEquals=" + UPDATED_TEACHER_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherPasswordIsInShouldWork() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherPassword in DEFAULT_TEACHER_PASSWORD or UPDATED_TEACHER_PASSWORD
        defaultTeacherShouldBeFound("teacherPassword.in=" + DEFAULT_TEACHER_PASSWORD + "," + UPDATED_TEACHER_PASSWORD);

        // Get all the teacherList where teacherPassword equals to UPDATED_TEACHER_PASSWORD
        defaultTeacherShouldNotBeFound("teacherPassword.in=" + UPDATED_TEACHER_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherPasswordIsNullOrNotNull() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherPassword is not null
        defaultTeacherShouldBeFound("teacherPassword.specified=true");

        // Get all the teacherList where teacherPassword is null
        defaultTeacherShouldNotBeFound("teacherPassword.specified=false");
    }
                @Test
    @Transactional
    public void getAllTeachersByTeacherPasswordContainsSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherPassword contains DEFAULT_TEACHER_PASSWORD
        defaultTeacherShouldBeFound("teacherPassword.contains=" + DEFAULT_TEACHER_PASSWORD);

        // Get all the teacherList where teacherPassword contains UPDATED_TEACHER_PASSWORD
        defaultTeacherShouldNotBeFound("teacherPassword.contains=" + UPDATED_TEACHER_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherPasswordNotContainsSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherPassword does not contain DEFAULT_TEACHER_PASSWORD
        defaultTeacherShouldNotBeFound("teacherPassword.doesNotContain=" + DEFAULT_TEACHER_PASSWORD);

        // Get all the teacherList where teacherPassword does not contain UPDATED_TEACHER_PASSWORD
        defaultTeacherShouldBeFound("teacherPassword.doesNotContain=" + UPDATED_TEACHER_PASSWORD);
    }


    @Test
    @Transactional
    public void getAllTeachersByTeacherCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherCreatedBy equals to DEFAULT_TEACHER_CREATED_BY
        defaultTeacherShouldBeFound("teacherCreatedBy.equals=" + DEFAULT_TEACHER_CREATED_BY);

        // Get all the teacherList where teacherCreatedBy equals to UPDATED_TEACHER_CREATED_BY
        defaultTeacherShouldNotBeFound("teacherCreatedBy.equals=" + UPDATED_TEACHER_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherCreatedBy not equals to DEFAULT_TEACHER_CREATED_BY
        defaultTeacherShouldNotBeFound("teacherCreatedBy.notEquals=" + DEFAULT_TEACHER_CREATED_BY);

        // Get all the teacherList where teacherCreatedBy not equals to UPDATED_TEACHER_CREATED_BY
        defaultTeacherShouldBeFound("teacherCreatedBy.notEquals=" + UPDATED_TEACHER_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherCreatedBy in DEFAULT_TEACHER_CREATED_BY or UPDATED_TEACHER_CREATED_BY
        defaultTeacherShouldBeFound("teacherCreatedBy.in=" + DEFAULT_TEACHER_CREATED_BY + "," + UPDATED_TEACHER_CREATED_BY);

        // Get all the teacherList where teacherCreatedBy equals to UPDATED_TEACHER_CREATED_BY
        defaultTeacherShouldNotBeFound("teacherCreatedBy.in=" + UPDATED_TEACHER_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherCreatedBy is not null
        defaultTeacherShouldBeFound("teacherCreatedBy.specified=true");

        // Get all the teacherList where teacherCreatedBy is null
        defaultTeacherShouldNotBeFound("teacherCreatedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllTeachersByTeacherCreatedByContainsSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherCreatedBy contains DEFAULT_TEACHER_CREATED_BY
        defaultTeacherShouldBeFound("teacherCreatedBy.contains=" + DEFAULT_TEACHER_CREATED_BY);

        // Get all the teacherList where teacherCreatedBy contains UPDATED_TEACHER_CREATED_BY
        defaultTeacherShouldNotBeFound("teacherCreatedBy.contains=" + UPDATED_TEACHER_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherCreatedBy does not contain DEFAULT_TEACHER_CREATED_BY
        defaultTeacherShouldNotBeFound("teacherCreatedBy.doesNotContain=" + DEFAULT_TEACHER_CREATED_BY);

        // Get all the teacherList where teacherCreatedBy does not contain UPDATED_TEACHER_CREATED_BY
        defaultTeacherShouldBeFound("teacherCreatedBy.doesNotContain=" + UPDATED_TEACHER_CREATED_BY);
    }


    @Test
    @Transactional
    public void getAllTeachersByTeacherCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherCreationDate equals to DEFAULT_TEACHER_CREATION_DATE
        defaultTeacherShouldBeFound("teacherCreationDate.equals=" + DEFAULT_TEACHER_CREATION_DATE);

        // Get all the teacherList where teacherCreationDate equals to UPDATED_TEACHER_CREATION_DATE
        defaultTeacherShouldNotBeFound("teacherCreationDate.equals=" + UPDATED_TEACHER_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherCreationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherCreationDate not equals to DEFAULT_TEACHER_CREATION_DATE
        defaultTeacherShouldNotBeFound("teacherCreationDate.notEquals=" + DEFAULT_TEACHER_CREATION_DATE);

        // Get all the teacherList where teacherCreationDate not equals to UPDATED_TEACHER_CREATION_DATE
        defaultTeacherShouldBeFound("teacherCreationDate.notEquals=" + UPDATED_TEACHER_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherCreationDate in DEFAULT_TEACHER_CREATION_DATE or UPDATED_TEACHER_CREATION_DATE
        defaultTeacherShouldBeFound("teacherCreationDate.in=" + DEFAULT_TEACHER_CREATION_DATE + "," + UPDATED_TEACHER_CREATION_DATE);

        // Get all the teacherList where teacherCreationDate equals to UPDATED_TEACHER_CREATION_DATE
        defaultTeacherShouldNotBeFound("teacherCreationDate.in=" + UPDATED_TEACHER_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherCreationDate is not null
        defaultTeacherShouldBeFound("teacherCreationDate.specified=true");

        // Get all the teacherList where teacherCreationDate is null
        defaultTeacherShouldNotBeFound("teacherCreationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherCreationDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherCreationDate is greater than or equal to DEFAULT_TEACHER_CREATION_DATE
        defaultTeacherShouldBeFound("teacherCreationDate.greaterThanOrEqual=" + DEFAULT_TEACHER_CREATION_DATE);

        // Get all the teacherList where teacherCreationDate is greater than or equal to UPDATED_TEACHER_CREATION_DATE
        defaultTeacherShouldNotBeFound("teacherCreationDate.greaterThanOrEqual=" + UPDATED_TEACHER_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherCreationDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherCreationDate is less than or equal to DEFAULT_TEACHER_CREATION_DATE
        defaultTeacherShouldBeFound("teacherCreationDate.lessThanOrEqual=" + DEFAULT_TEACHER_CREATION_DATE);

        // Get all the teacherList where teacherCreationDate is less than or equal to SMALLER_TEACHER_CREATION_DATE
        defaultTeacherShouldNotBeFound("teacherCreationDate.lessThanOrEqual=" + SMALLER_TEACHER_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherCreationDateIsLessThanSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherCreationDate is less than DEFAULT_TEACHER_CREATION_DATE
        defaultTeacherShouldNotBeFound("teacherCreationDate.lessThan=" + DEFAULT_TEACHER_CREATION_DATE);

        // Get all the teacherList where teacherCreationDate is less than UPDATED_TEACHER_CREATION_DATE
        defaultTeacherShouldBeFound("teacherCreationDate.lessThan=" + UPDATED_TEACHER_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherCreationDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherCreationDate is greater than DEFAULT_TEACHER_CREATION_DATE
        defaultTeacherShouldNotBeFound("teacherCreationDate.greaterThan=" + DEFAULT_TEACHER_CREATION_DATE);

        // Get all the teacherList where teacherCreationDate is greater than SMALLER_TEACHER_CREATION_DATE
        defaultTeacherShouldBeFound("teacherCreationDate.greaterThan=" + SMALLER_TEACHER_CREATION_DATE);
    }


    @Test
    @Transactional
    public void getAllTeachersByTeacherModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherModifiedBy equals to DEFAULT_TEACHER_MODIFIED_BY
        defaultTeacherShouldBeFound("teacherModifiedBy.equals=" + DEFAULT_TEACHER_MODIFIED_BY);

        // Get all the teacherList where teacherModifiedBy equals to UPDATED_TEACHER_MODIFIED_BY
        defaultTeacherShouldNotBeFound("teacherModifiedBy.equals=" + UPDATED_TEACHER_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherModifiedBy not equals to DEFAULT_TEACHER_MODIFIED_BY
        defaultTeacherShouldNotBeFound("teacherModifiedBy.notEquals=" + DEFAULT_TEACHER_MODIFIED_BY);

        // Get all the teacherList where teacherModifiedBy not equals to UPDATED_TEACHER_MODIFIED_BY
        defaultTeacherShouldBeFound("teacherModifiedBy.notEquals=" + UPDATED_TEACHER_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherModifiedBy in DEFAULT_TEACHER_MODIFIED_BY or UPDATED_TEACHER_MODIFIED_BY
        defaultTeacherShouldBeFound("teacherModifiedBy.in=" + DEFAULT_TEACHER_MODIFIED_BY + "," + UPDATED_TEACHER_MODIFIED_BY);

        // Get all the teacherList where teacherModifiedBy equals to UPDATED_TEACHER_MODIFIED_BY
        defaultTeacherShouldNotBeFound("teacherModifiedBy.in=" + UPDATED_TEACHER_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherModifiedBy is not null
        defaultTeacherShouldBeFound("teacherModifiedBy.specified=true");

        // Get all the teacherList where teacherModifiedBy is null
        defaultTeacherShouldNotBeFound("teacherModifiedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllTeachersByTeacherModifiedByContainsSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherModifiedBy contains DEFAULT_TEACHER_MODIFIED_BY
        defaultTeacherShouldBeFound("teacherModifiedBy.contains=" + DEFAULT_TEACHER_MODIFIED_BY);

        // Get all the teacherList where teacherModifiedBy contains UPDATED_TEACHER_MODIFIED_BY
        defaultTeacherShouldNotBeFound("teacherModifiedBy.contains=" + UPDATED_TEACHER_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherModifiedBy does not contain DEFAULT_TEACHER_MODIFIED_BY
        defaultTeacherShouldNotBeFound("teacherModifiedBy.doesNotContain=" + DEFAULT_TEACHER_MODIFIED_BY);

        // Get all the teacherList where teacherModifiedBy does not contain UPDATED_TEACHER_MODIFIED_BY
        defaultTeacherShouldBeFound("teacherModifiedBy.doesNotContain=" + UPDATED_TEACHER_MODIFIED_BY);
    }


    @Test
    @Transactional
    public void getAllTeachersByTeacherModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherModifiedDate equals to DEFAULT_TEACHER_MODIFIED_DATE
        defaultTeacherShouldBeFound("teacherModifiedDate.equals=" + DEFAULT_TEACHER_MODIFIED_DATE);

        // Get all the teacherList where teacherModifiedDate equals to UPDATED_TEACHER_MODIFIED_DATE
        defaultTeacherShouldNotBeFound("teacherModifiedDate.equals=" + UPDATED_TEACHER_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherModifiedDate not equals to DEFAULT_TEACHER_MODIFIED_DATE
        defaultTeacherShouldNotBeFound("teacherModifiedDate.notEquals=" + DEFAULT_TEACHER_MODIFIED_DATE);

        // Get all the teacherList where teacherModifiedDate not equals to UPDATED_TEACHER_MODIFIED_DATE
        defaultTeacherShouldBeFound("teacherModifiedDate.notEquals=" + UPDATED_TEACHER_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherModifiedDate in DEFAULT_TEACHER_MODIFIED_DATE or UPDATED_TEACHER_MODIFIED_DATE
        defaultTeacherShouldBeFound("teacherModifiedDate.in=" + DEFAULT_TEACHER_MODIFIED_DATE + "," + UPDATED_TEACHER_MODIFIED_DATE);

        // Get all the teacherList where teacherModifiedDate equals to UPDATED_TEACHER_MODIFIED_DATE
        defaultTeacherShouldNotBeFound("teacherModifiedDate.in=" + UPDATED_TEACHER_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherModifiedDate is not null
        defaultTeacherShouldBeFound("teacherModifiedDate.specified=true");

        // Get all the teacherList where teacherModifiedDate is null
        defaultTeacherShouldNotBeFound("teacherModifiedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherModifiedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherModifiedDate is greater than or equal to DEFAULT_TEACHER_MODIFIED_DATE
        defaultTeacherShouldBeFound("teacherModifiedDate.greaterThanOrEqual=" + DEFAULT_TEACHER_MODIFIED_DATE);

        // Get all the teacherList where teacherModifiedDate is greater than or equal to UPDATED_TEACHER_MODIFIED_DATE
        defaultTeacherShouldNotBeFound("teacherModifiedDate.greaterThanOrEqual=" + UPDATED_TEACHER_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherModifiedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherModifiedDate is less than or equal to DEFAULT_TEACHER_MODIFIED_DATE
        defaultTeacherShouldBeFound("teacherModifiedDate.lessThanOrEqual=" + DEFAULT_TEACHER_MODIFIED_DATE);

        // Get all the teacherList where teacherModifiedDate is less than or equal to SMALLER_TEACHER_MODIFIED_DATE
        defaultTeacherShouldNotBeFound("teacherModifiedDate.lessThanOrEqual=" + SMALLER_TEACHER_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherModifiedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherModifiedDate is less than DEFAULT_TEACHER_MODIFIED_DATE
        defaultTeacherShouldNotBeFound("teacherModifiedDate.lessThan=" + DEFAULT_TEACHER_MODIFIED_DATE);

        // Get all the teacherList where teacherModifiedDate is less than UPDATED_TEACHER_MODIFIED_DATE
        defaultTeacherShouldBeFound("teacherModifiedDate.lessThan=" + UPDATED_TEACHER_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherModifiedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherModifiedDate is greater than DEFAULT_TEACHER_MODIFIED_DATE
        defaultTeacherShouldNotBeFound("teacherModifiedDate.greaterThan=" + DEFAULT_TEACHER_MODIFIED_DATE);

        // Get all the teacherList where teacherModifiedDate is greater than SMALLER_TEACHER_MODIFIED_DATE
        defaultTeacherShouldBeFound("teacherModifiedDate.greaterThan=" + SMALLER_TEACHER_MODIFIED_DATE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTeacherShouldBeFound(String filter) throws Exception {
        restTeacherMockMvc.perform(get("/api/teachers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teacher.getId().intValue())))
            .andExpect(jsonPath("$.[*].teacherFirstName").value(hasItem(DEFAULT_TEACHER_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].teacherLastName").value(hasItem(DEFAULT_TEACHER_LAST_NAME)))
            .andExpect(jsonPath("$.[*].teacherEmail").value(hasItem(DEFAULT_TEACHER_EMAIL)))
            .andExpect(jsonPath("$.[*].teacherPassword").value(hasItem(DEFAULT_TEACHER_PASSWORD)))
            .andExpect(jsonPath("$.[*].teacherCreatedBy").value(hasItem(DEFAULT_TEACHER_CREATED_BY)))
            .andExpect(jsonPath("$.[*].teacherCreationDate").value(hasItem(DEFAULT_TEACHER_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].teacherModifiedBy").value(hasItem(DEFAULT_TEACHER_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].teacherModifiedDate").value(hasItem(DEFAULT_TEACHER_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restTeacherMockMvc.perform(get("/api/teachers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTeacherShouldNotBeFound(String filter) throws Exception {
        restTeacherMockMvc.perform(get("/api/teachers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTeacherMockMvc.perform(get("/api/teachers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTeacher() throws Exception {
        // Get the teacher
        restTeacherMockMvc.perform(get("/api/teachers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTeacher() throws Exception {
        // Initialize the database
        teacherService.save(teacher);

        int databaseSizeBeforeUpdate = teacherRepository.findAll().size();

        // Update the teacher
        Teacher updatedTeacher = teacherRepository.findById(teacher.getId()).get();
        // Disconnect from session so that the updates on updatedTeacher are not directly saved in db
        em.detach(updatedTeacher);
        updatedTeacher
            .teacherFirstName(UPDATED_TEACHER_FIRST_NAME)
            .teacherLastName(UPDATED_TEACHER_LAST_NAME)
            .teacherEmail(UPDATED_TEACHER_EMAIL)
            .teacherPassword(UPDATED_TEACHER_PASSWORD)
            .teacherCreatedBy(UPDATED_TEACHER_CREATED_BY)
            .teacherCreationDate(UPDATED_TEACHER_CREATION_DATE)
            .teacherModifiedBy(UPDATED_TEACHER_MODIFIED_BY)
            .teacherModifiedDate(UPDATED_TEACHER_MODIFIED_DATE);

        restTeacherMockMvc.perform(put("/api/teachers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTeacher)))
            .andExpect(status().isOk());

        // Validate the Teacher in the database
        List<Teacher> teacherList = teacherRepository.findAll();
        assertThat(teacherList).hasSize(databaseSizeBeforeUpdate);
        Teacher testTeacher = teacherList.get(teacherList.size() - 1);
        assertThat(testTeacher.getTeacherFirstName()).isEqualTo(UPDATED_TEACHER_FIRST_NAME);
        assertThat(testTeacher.getTeacherLastName()).isEqualTo(UPDATED_TEACHER_LAST_NAME);
        assertThat(testTeacher.getTeacherEmail()).isEqualTo(UPDATED_TEACHER_EMAIL);
        assertThat(testTeacher.getTeacherPassword()).isEqualTo(UPDATED_TEACHER_PASSWORD);
        assertThat(testTeacher.getTeacherCreatedBy()).isEqualTo(UPDATED_TEACHER_CREATED_BY);
        assertThat(testTeacher.getTeacherCreationDate()).isEqualTo(UPDATED_TEACHER_CREATION_DATE);
        assertThat(testTeacher.getTeacherModifiedBy()).isEqualTo(UPDATED_TEACHER_MODIFIED_BY);
        assertThat(testTeacher.getTeacherModifiedDate()).isEqualTo(UPDATED_TEACHER_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTeacher() throws Exception {
        int databaseSizeBeforeUpdate = teacherRepository.findAll().size();

        // Create the Teacher

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeacherMockMvc.perform(put("/api/teachers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teacher)))
            .andExpect(status().isBadRequest());

        // Validate the Teacher in the database
        List<Teacher> teacherList = teacherRepository.findAll();
        assertThat(teacherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTeacher() throws Exception {
        // Initialize the database
        teacherService.save(teacher);

        int databaseSizeBeforeDelete = teacherRepository.findAll().size();

        // Delete the teacher
        restTeacherMockMvc.perform(delete("/api/teachers/{id}", teacher.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Teacher> teacherList = teacherRepository.findAll();
        assertThat(teacherList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
