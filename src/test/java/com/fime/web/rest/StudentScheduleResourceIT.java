package com.fime.web.rest;

import com.fime.LearningPatternsApp;
import com.fime.domain.StudentSchedule;
import com.fime.domain.Group;
import com.fime.domain.Student;
import com.fime.repository.StudentScheduleRepository;
import com.fime.service.StudentScheduleService;
import com.fime.web.rest.errors.ExceptionTranslator;
import com.fime.service.dto.StudentScheduleCriteria;
import com.fime.service.StudentScheduleQueryService;

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
 * Integration tests for the {@link StudentScheduleResource} REST controller.
 */
@SpringBootTest(classes = LearningPatternsApp.class)
public class StudentScheduleResourceIT {

    private static final String DEFAULT_STUDENT_SCHEDULE_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_STUDENT_SCHEDULE_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_STUDENT_SCHEDULE_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STUDENT_SCHEDULE_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_STUDENT_SCHEDULE_CREATION_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_STUDENT_SCHEDULE_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_STUDENT_SCHEDULE_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_STUDENT_SCHEDULE_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STUDENT_SCHEDULE_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_STUDENT_SCHEDULE_MODIFIED_DATE = LocalDate.ofEpochDay(-1L);

    @Autowired
    private StudentScheduleRepository studentScheduleRepository;

    @Autowired
    private StudentScheduleService studentScheduleService;

    @Autowired
    private StudentScheduleQueryService studentScheduleQueryService;

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

    private MockMvc restStudentScheduleMockMvc;

    private StudentSchedule studentSchedule;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudentScheduleResource studentScheduleResource = new StudentScheduleResource(studentScheduleService, studentScheduleQueryService);
        this.restStudentScheduleMockMvc = MockMvcBuilders.standaloneSetup(studentScheduleResource)
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
    public static StudentSchedule createEntity(EntityManager em) {
        StudentSchedule studentSchedule = new StudentSchedule()
            .studentScheduleCreatedBy(DEFAULT_STUDENT_SCHEDULE_CREATED_BY)
            .studentScheduleCreationDate(DEFAULT_STUDENT_SCHEDULE_CREATION_DATE)
            .studentScheduleModifiedBy(DEFAULT_STUDENT_SCHEDULE_MODIFIED_BY)
            .studentScheduleModifiedDate(DEFAULT_STUDENT_SCHEDULE_MODIFIED_DATE);
        return studentSchedule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudentSchedule createUpdatedEntity(EntityManager em) {
        StudentSchedule studentSchedule = new StudentSchedule()
            .studentScheduleCreatedBy(UPDATED_STUDENT_SCHEDULE_CREATED_BY)
            .studentScheduleCreationDate(UPDATED_STUDENT_SCHEDULE_CREATION_DATE)
            .studentScheduleModifiedBy(UPDATED_STUDENT_SCHEDULE_MODIFIED_BY)
            .studentScheduleModifiedDate(UPDATED_STUDENT_SCHEDULE_MODIFIED_DATE);
        return studentSchedule;
    }

    @BeforeEach
    public void initTest() {
        studentSchedule = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudentSchedule() throws Exception {
        int databaseSizeBeforeCreate = studentScheduleRepository.findAll().size();

        // Create the StudentSchedule
        restStudentScheduleMockMvc.perform(post("/api/student-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentSchedule)))
            .andExpect(status().isCreated());

        // Validate the StudentSchedule in the database
        List<StudentSchedule> studentScheduleList = studentScheduleRepository.findAll();
        assertThat(studentScheduleList).hasSize(databaseSizeBeforeCreate + 1);
        StudentSchedule testStudentSchedule = studentScheduleList.get(studentScheduleList.size() - 1);
        assertThat(testStudentSchedule.getStudentScheduleCreatedBy()).isEqualTo(DEFAULT_STUDENT_SCHEDULE_CREATED_BY);
        assertThat(testStudentSchedule.getStudentScheduleCreationDate()).isEqualTo(DEFAULT_STUDENT_SCHEDULE_CREATION_DATE);
        assertThat(testStudentSchedule.getStudentScheduleModifiedBy()).isEqualTo(DEFAULT_STUDENT_SCHEDULE_MODIFIED_BY);
        assertThat(testStudentSchedule.getStudentScheduleModifiedDate()).isEqualTo(DEFAULT_STUDENT_SCHEDULE_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createStudentScheduleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentScheduleRepository.findAll().size();

        // Create the StudentSchedule with an existing ID
        studentSchedule.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentScheduleMockMvc.perform(post("/api/student-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentSchedule)))
            .andExpect(status().isBadRequest());

        // Validate the StudentSchedule in the database
        List<StudentSchedule> studentScheduleList = studentScheduleRepository.findAll();
        assertThat(studentScheduleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStudentSchedules() throws Exception {
        // Initialize the database
        studentScheduleRepository.saveAndFlush(studentSchedule);

        // Get all the studentScheduleList
        restStudentScheduleMockMvc.perform(get("/api/student-schedules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentSchedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].studentScheduleCreatedBy").value(hasItem(DEFAULT_STUDENT_SCHEDULE_CREATED_BY)))
            .andExpect(jsonPath("$.[*].studentScheduleCreationDate").value(hasItem(DEFAULT_STUDENT_SCHEDULE_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].studentScheduleModifiedBy").value(hasItem(DEFAULT_STUDENT_SCHEDULE_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].studentScheduleModifiedDate").value(hasItem(DEFAULT_STUDENT_SCHEDULE_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getStudentSchedule() throws Exception {
        // Initialize the database
        studentScheduleRepository.saveAndFlush(studentSchedule);

        // Get the studentSchedule
        restStudentScheduleMockMvc.perform(get("/api/student-schedules/{id}", studentSchedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(studentSchedule.getId().intValue()))
            .andExpect(jsonPath("$.studentScheduleCreatedBy").value(DEFAULT_STUDENT_SCHEDULE_CREATED_BY))
            .andExpect(jsonPath("$.studentScheduleCreationDate").value(DEFAULT_STUDENT_SCHEDULE_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.studentScheduleModifiedBy").value(DEFAULT_STUDENT_SCHEDULE_MODIFIED_BY))
            .andExpect(jsonPath("$.studentScheduleModifiedDate").value(DEFAULT_STUDENT_SCHEDULE_MODIFIED_DATE.toString()));
    }


    @Test
    @Transactional
    public void getStudentSchedulesByIdFiltering() throws Exception {
        // Initialize the database
        studentScheduleRepository.saveAndFlush(studentSchedule);

        Long id = studentSchedule.getId();

        defaultStudentScheduleShouldBeFound("id.equals=" + id);
        defaultStudentScheduleShouldNotBeFound("id.notEquals=" + id);

        defaultStudentScheduleShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStudentScheduleShouldNotBeFound("id.greaterThan=" + id);

        defaultStudentScheduleShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStudentScheduleShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllStudentSchedulesByStudentScheduleCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        studentScheduleRepository.saveAndFlush(studentSchedule);

        // Get all the studentScheduleList where studentScheduleCreatedBy equals to DEFAULT_STUDENT_SCHEDULE_CREATED_BY
        defaultStudentScheduleShouldBeFound("studentScheduleCreatedBy.equals=" + DEFAULT_STUDENT_SCHEDULE_CREATED_BY);

        // Get all the studentScheduleList where studentScheduleCreatedBy equals to UPDATED_STUDENT_SCHEDULE_CREATED_BY
        defaultStudentScheduleShouldNotBeFound("studentScheduleCreatedBy.equals=" + UPDATED_STUDENT_SCHEDULE_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllStudentSchedulesByStudentScheduleCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentScheduleRepository.saveAndFlush(studentSchedule);

        // Get all the studentScheduleList where studentScheduleCreatedBy not equals to DEFAULT_STUDENT_SCHEDULE_CREATED_BY
        defaultStudentScheduleShouldNotBeFound("studentScheduleCreatedBy.notEquals=" + DEFAULT_STUDENT_SCHEDULE_CREATED_BY);

        // Get all the studentScheduleList where studentScheduleCreatedBy not equals to UPDATED_STUDENT_SCHEDULE_CREATED_BY
        defaultStudentScheduleShouldBeFound("studentScheduleCreatedBy.notEquals=" + UPDATED_STUDENT_SCHEDULE_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllStudentSchedulesByStudentScheduleCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        studentScheduleRepository.saveAndFlush(studentSchedule);

        // Get all the studentScheduleList where studentScheduleCreatedBy in DEFAULT_STUDENT_SCHEDULE_CREATED_BY or UPDATED_STUDENT_SCHEDULE_CREATED_BY
        defaultStudentScheduleShouldBeFound("studentScheduleCreatedBy.in=" + DEFAULT_STUDENT_SCHEDULE_CREATED_BY + "," + UPDATED_STUDENT_SCHEDULE_CREATED_BY);

        // Get all the studentScheduleList where studentScheduleCreatedBy equals to UPDATED_STUDENT_SCHEDULE_CREATED_BY
        defaultStudentScheduleShouldNotBeFound("studentScheduleCreatedBy.in=" + UPDATED_STUDENT_SCHEDULE_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllStudentSchedulesByStudentScheduleCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentScheduleRepository.saveAndFlush(studentSchedule);

        // Get all the studentScheduleList where studentScheduleCreatedBy is not null
        defaultStudentScheduleShouldBeFound("studentScheduleCreatedBy.specified=true");

        // Get all the studentScheduleList where studentScheduleCreatedBy is null
        defaultStudentScheduleShouldNotBeFound("studentScheduleCreatedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllStudentSchedulesByStudentScheduleCreatedByContainsSomething() throws Exception {
        // Initialize the database
        studentScheduleRepository.saveAndFlush(studentSchedule);

        // Get all the studentScheduleList where studentScheduleCreatedBy contains DEFAULT_STUDENT_SCHEDULE_CREATED_BY
        defaultStudentScheduleShouldBeFound("studentScheduleCreatedBy.contains=" + DEFAULT_STUDENT_SCHEDULE_CREATED_BY);

        // Get all the studentScheduleList where studentScheduleCreatedBy contains UPDATED_STUDENT_SCHEDULE_CREATED_BY
        defaultStudentScheduleShouldNotBeFound("studentScheduleCreatedBy.contains=" + UPDATED_STUDENT_SCHEDULE_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllStudentSchedulesByStudentScheduleCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        studentScheduleRepository.saveAndFlush(studentSchedule);

        // Get all the studentScheduleList where studentScheduleCreatedBy does not contain DEFAULT_STUDENT_SCHEDULE_CREATED_BY
        defaultStudentScheduleShouldNotBeFound("studentScheduleCreatedBy.doesNotContain=" + DEFAULT_STUDENT_SCHEDULE_CREATED_BY);

        // Get all the studentScheduleList where studentScheduleCreatedBy does not contain UPDATED_STUDENT_SCHEDULE_CREATED_BY
        defaultStudentScheduleShouldBeFound("studentScheduleCreatedBy.doesNotContain=" + UPDATED_STUDENT_SCHEDULE_CREATED_BY);
    }


    @Test
    @Transactional
    public void getAllStudentSchedulesByStudentScheduleCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        studentScheduleRepository.saveAndFlush(studentSchedule);

        // Get all the studentScheduleList where studentScheduleCreationDate equals to DEFAULT_STUDENT_SCHEDULE_CREATION_DATE
        defaultStudentScheduleShouldBeFound("studentScheduleCreationDate.equals=" + DEFAULT_STUDENT_SCHEDULE_CREATION_DATE);

        // Get all the studentScheduleList where studentScheduleCreationDate equals to UPDATED_STUDENT_SCHEDULE_CREATION_DATE
        defaultStudentScheduleShouldNotBeFound("studentScheduleCreationDate.equals=" + UPDATED_STUDENT_SCHEDULE_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentSchedulesByStudentScheduleCreationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentScheduleRepository.saveAndFlush(studentSchedule);

        // Get all the studentScheduleList where studentScheduleCreationDate not equals to DEFAULT_STUDENT_SCHEDULE_CREATION_DATE
        defaultStudentScheduleShouldNotBeFound("studentScheduleCreationDate.notEquals=" + DEFAULT_STUDENT_SCHEDULE_CREATION_DATE);

        // Get all the studentScheduleList where studentScheduleCreationDate not equals to UPDATED_STUDENT_SCHEDULE_CREATION_DATE
        defaultStudentScheduleShouldBeFound("studentScheduleCreationDate.notEquals=" + UPDATED_STUDENT_SCHEDULE_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentSchedulesByStudentScheduleCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        studentScheduleRepository.saveAndFlush(studentSchedule);

        // Get all the studentScheduleList where studentScheduleCreationDate in DEFAULT_STUDENT_SCHEDULE_CREATION_DATE or UPDATED_STUDENT_SCHEDULE_CREATION_DATE
        defaultStudentScheduleShouldBeFound("studentScheduleCreationDate.in=" + DEFAULT_STUDENT_SCHEDULE_CREATION_DATE + "," + UPDATED_STUDENT_SCHEDULE_CREATION_DATE);

        // Get all the studentScheduleList where studentScheduleCreationDate equals to UPDATED_STUDENT_SCHEDULE_CREATION_DATE
        defaultStudentScheduleShouldNotBeFound("studentScheduleCreationDate.in=" + UPDATED_STUDENT_SCHEDULE_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentSchedulesByStudentScheduleCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentScheduleRepository.saveAndFlush(studentSchedule);

        // Get all the studentScheduleList where studentScheduleCreationDate is not null
        defaultStudentScheduleShouldBeFound("studentScheduleCreationDate.specified=true");

        // Get all the studentScheduleList where studentScheduleCreationDate is null
        defaultStudentScheduleShouldNotBeFound("studentScheduleCreationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentSchedulesByStudentScheduleCreationDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentScheduleRepository.saveAndFlush(studentSchedule);

        // Get all the studentScheduleList where studentScheduleCreationDate is greater than or equal to DEFAULT_STUDENT_SCHEDULE_CREATION_DATE
        defaultStudentScheduleShouldBeFound("studentScheduleCreationDate.greaterThanOrEqual=" + DEFAULT_STUDENT_SCHEDULE_CREATION_DATE);

        // Get all the studentScheduleList where studentScheduleCreationDate is greater than or equal to UPDATED_STUDENT_SCHEDULE_CREATION_DATE
        defaultStudentScheduleShouldNotBeFound("studentScheduleCreationDate.greaterThanOrEqual=" + UPDATED_STUDENT_SCHEDULE_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentSchedulesByStudentScheduleCreationDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentScheduleRepository.saveAndFlush(studentSchedule);

        // Get all the studentScheduleList where studentScheduleCreationDate is less than or equal to DEFAULT_STUDENT_SCHEDULE_CREATION_DATE
        defaultStudentScheduleShouldBeFound("studentScheduleCreationDate.lessThanOrEqual=" + DEFAULT_STUDENT_SCHEDULE_CREATION_DATE);

        // Get all the studentScheduleList where studentScheduleCreationDate is less than or equal to SMALLER_STUDENT_SCHEDULE_CREATION_DATE
        defaultStudentScheduleShouldNotBeFound("studentScheduleCreationDate.lessThanOrEqual=" + SMALLER_STUDENT_SCHEDULE_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentSchedulesByStudentScheduleCreationDateIsLessThanSomething() throws Exception {
        // Initialize the database
        studentScheduleRepository.saveAndFlush(studentSchedule);

        // Get all the studentScheduleList where studentScheduleCreationDate is less than DEFAULT_STUDENT_SCHEDULE_CREATION_DATE
        defaultStudentScheduleShouldNotBeFound("studentScheduleCreationDate.lessThan=" + DEFAULT_STUDENT_SCHEDULE_CREATION_DATE);

        // Get all the studentScheduleList where studentScheduleCreationDate is less than UPDATED_STUDENT_SCHEDULE_CREATION_DATE
        defaultStudentScheduleShouldBeFound("studentScheduleCreationDate.lessThan=" + UPDATED_STUDENT_SCHEDULE_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentSchedulesByStudentScheduleCreationDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        studentScheduleRepository.saveAndFlush(studentSchedule);

        // Get all the studentScheduleList where studentScheduleCreationDate is greater than DEFAULT_STUDENT_SCHEDULE_CREATION_DATE
        defaultStudentScheduleShouldNotBeFound("studentScheduleCreationDate.greaterThan=" + DEFAULT_STUDENT_SCHEDULE_CREATION_DATE);

        // Get all the studentScheduleList where studentScheduleCreationDate is greater than SMALLER_STUDENT_SCHEDULE_CREATION_DATE
        defaultStudentScheduleShouldBeFound("studentScheduleCreationDate.greaterThan=" + SMALLER_STUDENT_SCHEDULE_CREATION_DATE);
    }


    @Test
    @Transactional
    public void getAllStudentSchedulesByStudentScheduleModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        studentScheduleRepository.saveAndFlush(studentSchedule);

        // Get all the studentScheduleList where studentScheduleModifiedBy equals to DEFAULT_STUDENT_SCHEDULE_MODIFIED_BY
        defaultStudentScheduleShouldBeFound("studentScheduleModifiedBy.equals=" + DEFAULT_STUDENT_SCHEDULE_MODIFIED_BY);

        // Get all the studentScheduleList where studentScheduleModifiedBy equals to UPDATED_STUDENT_SCHEDULE_MODIFIED_BY
        defaultStudentScheduleShouldNotBeFound("studentScheduleModifiedBy.equals=" + UPDATED_STUDENT_SCHEDULE_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllStudentSchedulesByStudentScheduleModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentScheduleRepository.saveAndFlush(studentSchedule);

        // Get all the studentScheduleList where studentScheduleModifiedBy not equals to DEFAULT_STUDENT_SCHEDULE_MODIFIED_BY
        defaultStudentScheduleShouldNotBeFound("studentScheduleModifiedBy.notEquals=" + DEFAULT_STUDENT_SCHEDULE_MODIFIED_BY);

        // Get all the studentScheduleList where studentScheduleModifiedBy not equals to UPDATED_STUDENT_SCHEDULE_MODIFIED_BY
        defaultStudentScheduleShouldBeFound("studentScheduleModifiedBy.notEquals=" + UPDATED_STUDENT_SCHEDULE_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllStudentSchedulesByStudentScheduleModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        studentScheduleRepository.saveAndFlush(studentSchedule);

        // Get all the studentScheduleList where studentScheduleModifiedBy in DEFAULT_STUDENT_SCHEDULE_MODIFIED_BY or UPDATED_STUDENT_SCHEDULE_MODIFIED_BY
        defaultStudentScheduleShouldBeFound("studentScheduleModifiedBy.in=" + DEFAULT_STUDENT_SCHEDULE_MODIFIED_BY + "," + UPDATED_STUDENT_SCHEDULE_MODIFIED_BY);

        // Get all the studentScheduleList where studentScheduleModifiedBy equals to UPDATED_STUDENT_SCHEDULE_MODIFIED_BY
        defaultStudentScheduleShouldNotBeFound("studentScheduleModifiedBy.in=" + UPDATED_STUDENT_SCHEDULE_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllStudentSchedulesByStudentScheduleModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentScheduleRepository.saveAndFlush(studentSchedule);

        // Get all the studentScheduleList where studentScheduleModifiedBy is not null
        defaultStudentScheduleShouldBeFound("studentScheduleModifiedBy.specified=true");

        // Get all the studentScheduleList where studentScheduleModifiedBy is null
        defaultStudentScheduleShouldNotBeFound("studentScheduleModifiedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllStudentSchedulesByStudentScheduleModifiedByContainsSomething() throws Exception {
        // Initialize the database
        studentScheduleRepository.saveAndFlush(studentSchedule);

        // Get all the studentScheduleList where studentScheduleModifiedBy contains DEFAULT_STUDENT_SCHEDULE_MODIFIED_BY
        defaultStudentScheduleShouldBeFound("studentScheduleModifiedBy.contains=" + DEFAULT_STUDENT_SCHEDULE_MODIFIED_BY);

        // Get all the studentScheduleList where studentScheduleModifiedBy contains UPDATED_STUDENT_SCHEDULE_MODIFIED_BY
        defaultStudentScheduleShouldNotBeFound("studentScheduleModifiedBy.contains=" + UPDATED_STUDENT_SCHEDULE_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllStudentSchedulesByStudentScheduleModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        studentScheduleRepository.saveAndFlush(studentSchedule);

        // Get all the studentScheduleList where studentScheduleModifiedBy does not contain DEFAULT_STUDENT_SCHEDULE_MODIFIED_BY
        defaultStudentScheduleShouldNotBeFound("studentScheduleModifiedBy.doesNotContain=" + DEFAULT_STUDENT_SCHEDULE_MODIFIED_BY);

        // Get all the studentScheduleList where studentScheduleModifiedBy does not contain UPDATED_STUDENT_SCHEDULE_MODIFIED_BY
        defaultStudentScheduleShouldBeFound("studentScheduleModifiedBy.doesNotContain=" + UPDATED_STUDENT_SCHEDULE_MODIFIED_BY);
    }


    @Test
    @Transactional
    public void getAllStudentSchedulesByStudentScheduleModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        studentScheduleRepository.saveAndFlush(studentSchedule);

        // Get all the studentScheduleList where studentScheduleModifiedDate equals to DEFAULT_STUDENT_SCHEDULE_MODIFIED_DATE
        defaultStudentScheduleShouldBeFound("studentScheduleModifiedDate.equals=" + DEFAULT_STUDENT_SCHEDULE_MODIFIED_DATE);

        // Get all the studentScheduleList where studentScheduleModifiedDate equals to UPDATED_STUDENT_SCHEDULE_MODIFIED_DATE
        defaultStudentScheduleShouldNotBeFound("studentScheduleModifiedDate.equals=" + UPDATED_STUDENT_SCHEDULE_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentSchedulesByStudentScheduleModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentScheduleRepository.saveAndFlush(studentSchedule);

        // Get all the studentScheduleList where studentScheduleModifiedDate not equals to DEFAULT_STUDENT_SCHEDULE_MODIFIED_DATE
        defaultStudentScheduleShouldNotBeFound("studentScheduleModifiedDate.notEquals=" + DEFAULT_STUDENT_SCHEDULE_MODIFIED_DATE);

        // Get all the studentScheduleList where studentScheduleModifiedDate not equals to UPDATED_STUDENT_SCHEDULE_MODIFIED_DATE
        defaultStudentScheduleShouldBeFound("studentScheduleModifiedDate.notEquals=" + UPDATED_STUDENT_SCHEDULE_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentSchedulesByStudentScheduleModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        studentScheduleRepository.saveAndFlush(studentSchedule);

        // Get all the studentScheduleList where studentScheduleModifiedDate in DEFAULT_STUDENT_SCHEDULE_MODIFIED_DATE or UPDATED_STUDENT_SCHEDULE_MODIFIED_DATE
        defaultStudentScheduleShouldBeFound("studentScheduleModifiedDate.in=" + DEFAULT_STUDENT_SCHEDULE_MODIFIED_DATE + "," + UPDATED_STUDENT_SCHEDULE_MODIFIED_DATE);

        // Get all the studentScheduleList where studentScheduleModifiedDate equals to UPDATED_STUDENT_SCHEDULE_MODIFIED_DATE
        defaultStudentScheduleShouldNotBeFound("studentScheduleModifiedDate.in=" + UPDATED_STUDENT_SCHEDULE_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentSchedulesByStudentScheduleModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentScheduleRepository.saveAndFlush(studentSchedule);

        // Get all the studentScheduleList where studentScheduleModifiedDate is not null
        defaultStudentScheduleShouldBeFound("studentScheduleModifiedDate.specified=true");

        // Get all the studentScheduleList where studentScheduleModifiedDate is null
        defaultStudentScheduleShouldNotBeFound("studentScheduleModifiedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentSchedulesByStudentScheduleModifiedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentScheduleRepository.saveAndFlush(studentSchedule);

        // Get all the studentScheduleList where studentScheduleModifiedDate is greater than or equal to DEFAULT_STUDENT_SCHEDULE_MODIFIED_DATE
        defaultStudentScheduleShouldBeFound("studentScheduleModifiedDate.greaterThanOrEqual=" + DEFAULT_STUDENT_SCHEDULE_MODIFIED_DATE);

        // Get all the studentScheduleList where studentScheduleModifiedDate is greater than or equal to UPDATED_STUDENT_SCHEDULE_MODIFIED_DATE
        defaultStudentScheduleShouldNotBeFound("studentScheduleModifiedDate.greaterThanOrEqual=" + UPDATED_STUDENT_SCHEDULE_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentSchedulesByStudentScheduleModifiedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentScheduleRepository.saveAndFlush(studentSchedule);

        // Get all the studentScheduleList where studentScheduleModifiedDate is less than or equal to DEFAULT_STUDENT_SCHEDULE_MODIFIED_DATE
        defaultStudentScheduleShouldBeFound("studentScheduleModifiedDate.lessThanOrEqual=" + DEFAULT_STUDENT_SCHEDULE_MODIFIED_DATE);

        // Get all the studentScheduleList where studentScheduleModifiedDate is less than or equal to SMALLER_STUDENT_SCHEDULE_MODIFIED_DATE
        defaultStudentScheduleShouldNotBeFound("studentScheduleModifiedDate.lessThanOrEqual=" + SMALLER_STUDENT_SCHEDULE_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentSchedulesByStudentScheduleModifiedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        studentScheduleRepository.saveAndFlush(studentSchedule);

        // Get all the studentScheduleList where studentScheduleModifiedDate is less than DEFAULT_STUDENT_SCHEDULE_MODIFIED_DATE
        defaultStudentScheduleShouldNotBeFound("studentScheduleModifiedDate.lessThan=" + DEFAULT_STUDENT_SCHEDULE_MODIFIED_DATE);

        // Get all the studentScheduleList where studentScheduleModifiedDate is less than UPDATED_STUDENT_SCHEDULE_MODIFIED_DATE
        defaultStudentScheduleShouldBeFound("studentScheduleModifiedDate.lessThan=" + UPDATED_STUDENT_SCHEDULE_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentSchedulesByStudentScheduleModifiedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        studentScheduleRepository.saveAndFlush(studentSchedule);

        // Get all the studentScheduleList where studentScheduleModifiedDate is greater than DEFAULT_STUDENT_SCHEDULE_MODIFIED_DATE
        defaultStudentScheduleShouldNotBeFound("studentScheduleModifiedDate.greaterThan=" + DEFAULT_STUDENT_SCHEDULE_MODIFIED_DATE);

        // Get all the studentScheduleList where studentScheduleModifiedDate is greater than SMALLER_STUDENT_SCHEDULE_MODIFIED_DATE
        defaultStudentScheduleShouldBeFound("studentScheduleModifiedDate.greaterThan=" + SMALLER_STUDENT_SCHEDULE_MODIFIED_DATE);
    }


    @Test
    @Transactional
    public void getAllStudentSchedulesByGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        studentScheduleRepository.saveAndFlush(studentSchedule);
        Group group = GroupResourceIT.createEntity(em);
        em.persist(group);
        em.flush();
        studentSchedule.setGroup(group);
        studentScheduleRepository.saveAndFlush(studentSchedule);
        Long groupId = group.getId();

        // Get all the studentScheduleList where group equals to groupId
        defaultStudentScheduleShouldBeFound("groupId.equals=" + groupId);

        // Get all the studentScheduleList where group equals to groupId + 1
        defaultStudentScheduleShouldNotBeFound("groupId.equals=" + (groupId + 1));
    }


    @Test
    @Transactional
    public void getAllStudentSchedulesByStudentIsEqualToSomething() throws Exception {
        // Initialize the database
        studentScheduleRepository.saveAndFlush(studentSchedule);
        Student student = StudentResourceIT.createEntity(em);
        em.persist(student);
        em.flush();
        studentSchedule.setStudent(student);
        studentScheduleRepository.saveAndFlush(studentSchedule);
        Long studentId = student.getId();

        // Get all the studentScheduleList where student equals to studentId
        defaultStudentScheduleShouldBeFound("studentId.equals=" + studentId);

        // Get all the studentScheduleList where student equals to studentId + 1
        defaultStudentScheduleShouldNotBeFound("studentId.equals=" + (studentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStudentScheduleShouldBeFound(String filter) throws Exception {
        restStudentScheduleMockMvc.perform(get("/api/student-schedules?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentSchedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].studentScheduleCreatedBy").value(hasItem(DEFAULT_STUDENT_SCHEDULE_CREATED_BY)))
            .andExpect(jsonPath("$.[*].studentScheduleCreationDate").value(hasItem(DEFAULT_STUDENT_SCHEDULE_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].studentScheduleModifiedBy").value(hasItem(DEFAULT_STUDENT_SCHEDULE_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].studentScheduleModifiedDate").value(hasItem(DEFAULT_STUDENT_SCHEDULE_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restStudentScheduleMockMvc.perform(get("/api/student-schedules/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStudentScheduleShouldNotBeFound(String filter) throws Exception {
        restStudentScheduleMockMvc.perform(get("/api/student-schedules?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStudentScheduleMockMvc.perform(get("/api/student-schedules/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingStudentSchedule() throws Exception {
        // Get the studentSchedule
        restStudentScheduleMockMvc.perform(get("/api/student-schedules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudentSchedule() throws Exception {
        // Initialize the database
        studentScheduleService.save(studentSchedule);

        int databaseSizeBeforeUpdate = studentScheduleRepository.findAll().size();

        // Update the studentSchedule
        StudentSchedule updatedStudentSchedule = studentScheduleRepository.findById(studentSchedule.getId()).get();
        // Disconnect from session so that the updates on updatedStudentSchedule are not directly saved in db
        em.detach(updatedStudentSchedule);
        updatedStudentSchedule
            .studentScheduleCreatedBy(UPDATED_STUDENT_SCHEDULE_CREATED_BY)
            .studentScheduleCreationDate(UPDATED_STUDENT_SCHEDULE_CREATION_DATE)
            .studentScheduleModifiedBy(UPDATED_STUDENT_SCHEDULE_MODIFIED_BY)
            .studentScheduleModifiedDate(UPDATED_STUDENT_SCHEDULE_MODIFIED_DATE);

        restStudentScheduleMockMvc.perform(put("/api/student-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStudentSchedule)))
            .andExpect(status().isOk());

        // Validate the StudentSchedule in the database
        List<StudentSchedule> studentScheduleList = studentScheduleRepository.findAll();
        assertThat(studentScheduleList).hasSize(databaseSizeBeforeUpdate);
        StudentSchedule testStudentSchedule = studentScheduleList.get(studentScheduleList.size() - 1);
        assertThat(testStudentSchedule.getStudentScheduleCreatedBy()).isEqualTo(UPDATED_STUDENT_SCHEDULE_CREATED_BY);
        assertThat(testStudentSchedule.getStudentScheduleCreationDate()).isEqualTo(UPDATED_STUDENT_SCHEDULE_CREATION_DATE);
        assertThat(testStudentSchedule.getStudentScheduleModifiedBy()).isEqualTo(UPDATED_STUDENT_SCHEDULE_MODIFIED_BY);
        assertThat(testStudentSchedule.getStudentScheduleModifiedDate()).isEqualTo(UPDATED_STUDENT_SCHEDULE_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingStudentSchedule() throws Exception {
        int databaseSizeBeforeUpdate = studentScheduleRepository.findAll().size();

        // Create the StudentSchedule

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentScheduleMockMvc.perform(put("/api/student-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentSchedule)))
            .andExpect(status().isBadRequest());

        // Validate the StudentSchedule in the database
        List<StudentSchedule> studentScheduleList = studentScheduleRepository.findAll();
        assertThat(studentScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStudentSchedule() throws Exception {
        // Initialize the database
        studentScheduleService.save(studentSchedule);

        int databaseSizeBeforeDelete = studentScheduleRepository.findAll().size();

        // Delete the studentSchedule
        restStudentScheduleMockMvc.perform(delete("/api/student-schedules/{id}", studentSchedule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StudentSchedule> studentScheduleList = studentScheduleRepository.findAll();
        assertThat(studentScheduleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
