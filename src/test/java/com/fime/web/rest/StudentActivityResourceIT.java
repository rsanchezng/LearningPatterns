package com.fime.web.rest;

import com.fime.LearningPatternsApp;
import com.fime.domain.StudentActivity;
import com.fime.domain.Activity;
import com.fime.domain.StudentSchedule;
import com.fime.repository.StudentActivityRepository;
import com.fime.service.StudentActivityService;
import com.fime.web.rest.errors.ExceptionTranslator;
import com.fime.service.dto.StudentActivityCriteria;
import com.fime.service.StudentActivityQueryService;

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
 * Integration tests for the {@link StudentActivityResource} REST controller.
 */
@SpringBootTest(classes = LearningPatternsApp.class)
public class StudentActivityResourceIT {

    private static final LocalDate DEFAULT_ACTIVITY_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTIVITY_START_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_ACTIVITY_START_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_ACTIVITY_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTIVITY_END_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_ACTIVITY_END_DATE = LocalDate.ofEpochDay(-1L);

    private static final Integer DEFAULT_ACTIVITY_GRADE = 1;
    private static final Integer UPDATED_ACTIVITY_GRADE = 2;
    private static final Integer SMALLER_ACTIVITY_GRADE = 1 - 1;

    private static final LocalDate DEFAULT_STUDENT_ACTIVITY_GRADE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STUDENT_ACTIVITY_GRADE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_STUDENT_ACTIVITY_GRADE_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_STUDENT_ACTIVITY_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STUDENT_ACTIVITY_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_STUDENT_ACTIVITY_CREATED_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_STUDENT_ACTIVITY_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_STUDENT_ACTIVITY_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_STUDENT_ACTIVITY_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STUDENT_ACTIVITY_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_STUDENT_ACTIVITY_MODIFIED_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_STUDENT_ACTIVITY_MODIFIED_BY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STUDENT_ACTIVITY_MODIFIED_BY = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_STUDENT_ACTIVITY_MODIFIED_BY = LocalDate.ofEpochDay(-1L);

    @Autowired
    private StudentActivityRepository studentActivityRepository;

    @Autowired
    private StudentActivityService studentActivityService;

    @Autowired
    private StudentActivityQueryService studentActivityQueryService;

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

    private MockMvc restStudentActivityMockMvc;

    private StudentActivity studentActivity;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudentActivityResource studentActivityResource = new StudentActivityResource(studentActivityService, studentActivityQueryService);
        this.restStudentActivityMockMvc = MockMvcBuilders.standaloneSetup(studentActivityResource)
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
    public static StudentActivity createEntity(EntityManager em) {
        StudentActivity studentActivity = new StudentActivity()
            .activityStartDate(DEFAULT_ACTIVITY_START_DATE)
            .activityEndDate(DEFAULT_ACTIVITY_END_DATE)
            .activityGrade(DEFAULT_ACTIVITY_GRADE)
            .studentActivityGradeDate(DEFAULT_STUDENT_ACTIVITY_GRADE_DATE)
            .studentActivityCreatedDate(DEFAULT_STUDENT_ACTIVITY_CREATED_DATE)
            .studentActivityCreatedBy(DEFAULT_STUDENT_ACTIVITY_CREATED_BY)
            .studentActivityModifiedDate(DEFAULT_STUDENT_ACTIVITY_MODIFIED_DATE)
            .studentActivityModifiedBy(DEFAULT_STUDENT_ACTIVITY_MODIFIED_BY);
        return studentActivity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudentActivity createUpdatedEntity(EntityManager em) {
        StudentActivity studentActivity = new StudentActivity()
            .activityStartDate(UPDATED_ACTIVITY_START_DATE)
            .activityEndDate(UPDATED_ACTIVITY_END_DATE)
            .activityGrade(UPDATED_ACTIVITY_GRADE)
            .studentActivityGradeDate(UPDATED_STUDENT_ACTIVITY_GRADE_DATE)
            .studentActivityCreatedDate(UPDATED_STUDENT_ACTIVITY_CREATED_DATE)
            .studentActivityCreatedBy(UPDATED_STUDENT_ACTIVITY_CREATED_BY)
            .studentActivityModifiedDate(UPDATED_STUDENT_ACTIVITY_MODIFIED_DATE)
            .studentActivityModifiedBy(UPDATED_STUDENT_ACTIVITY_MODIFIED_BY);
        return studentActivity;
    }

    @BeforeEach
    public void initTest() {
        studentActivity = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudentActivity() throws Exception {
        int databaseSizeBeforeCreate = studentActivityRepository.findAll().size();

        // Create the StudentActivity
        restStudentActivityMockMvc.perform(post("/api/student-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentActivity)))
            .andExpect(status().isCreated());

        // Validate the StudentActivity in the database
        List<StudentActivity> studentActivityList = studentActivityRepository.findAll();
        assertThat(studentActivityList).hasSize(databaseSizeBeforeCreate + 1);
        StudentActivity testStudentActivity = studentActivityList.get(studentActivityList.size() - 1);
        assertThat(testStudentActivity.getActivityStartDate()).isEqualTo(DEFAULT_ACTIVITY_START_DATE);
        assertThat(testStudentActivity.getActivityEndDate()).isEqualTo(DEFAULT_ACTIVITY_END_DATE);
        assertThat(testStudentActivity.getActivityGrade()).isEqualTo(DEFAULT_ACTIVITY_GRADE);
        assertThat(testStudentActivity.getStudentActivityGradeDate()).isEqualTo(DEFAULT_STUDENT_ACTIVITY_GRADE_DATE);
        assertThat(testStudentActivity.getStudentActivityCreatedDate()).isEqualTo(DEFAULT_STUDENT_ACTIVITY_CREATED_DATE);
        assertThat(testStudentActivity.getStudentActivityCreatedBy()).isEqualTo(DEFAULT_STUDENT_ACTIVITY_CREATED_BY);
        assertThat(testStudentActivity.getStudentActivityModifiedDate()).isEqualTo(DEFAULT_STUDENT_ACTIVITY_MODIFIED_DATE);
        assertThat(testStudentActivity.getStudentActivityModifiedBy()).isEqualTo(DEFAULT_STUDENT_ACTIVITY_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void createStudentActivityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentActivityRepository.findAll().size();

        // Create the StudentActivity with an existing ID
        studentActivity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentActivityMockMvc.perform(post("/api/student-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentActivity)))
            .andExpect(status().isBadRequest());

        // Validate the StudentActivity in the database
        List<StudentActivity> studentActivityList = studentActivityRepository.findAll();
        assertThat(studentActivityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStudentActivities() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList
        restStudentActivityMockMvc.perform(get("/api/student-activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentActivity.getId().intValue())))
            .andExpect(jsonPath("$.[*].activityStartDate").value(hasItem(DEFAULT_ACTIVITY_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].activityEndDate").value(hasItem(DEFAULT_ACTIVITY_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].activityGrade").value(hasItem(DEFAULT_ACTIVITY_GRADE)))
            .andExpect(jsonPath("$.[*].studentActivityGradeDate").value(hasItem(DEFAULT_STUDENT_ACTIVITY_GRADE_DATE.toString())))
            .andExpect(jsonPath("$.[*].studentActivityCreatedDate").value(hasItem(DEFAULT_STUDENT_ACTIVITY_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].studentActivityCreatedBy").value(hasItem(DEFAULT_STUDENT_ACTIVITY_CREATED_BY)))
            .andExpect(jsonPath("$.[*].studentActivityModifiedDate").value(hasItem(DEFAULT_STUDENT_ACTIVITY_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].studentActivityModifiedBy").value(hasItem(DEFAULT_STUDENT_ACTIVITY_MODIFIED_BY.toString())));
    }
    
    @Test
    @Transactional
    public void getStudentActivity() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get the studentActivity
        restStudentActivityMockMvc.perform(get("/api/student-activities/{id}", studentActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(studentActivity.getId().intValue()))
            .andExpect(jsonPath("$.activityStartDate").value(DEFAULT_ACTIVITY_START_DATE.toString()))
            .andExpect(jsonPath("$.activityEndDate").value(DEFAULT_ACTIVITY_END_DATE.toString()))
            .andExpect(jsonPath("$.activityGrade").value(DEFAULT_ACTIVITY_GRADE))
            .andExpect(jsonPath("$.studentActivityGradeDate").value(DEFAULT_STUDENT_ACTIVITY_GRADE_DATE.toString()))
            .andExpect(jsonPath("$.studentActivityCreatedDate").value(DEFAULT_STUDENT_ACTIVITY_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.studentActivityCreatedBy").value(DEFAULT_STUDENT_ACTIVITY_CREATED_BY))
            .andExpect(jsonPath("$.studentActivityModifiedDate").value(DEFAULT_STUDENT_ACTIVITY_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.studentActivityModifiedBy").value(DEFAULT_STUDENT_ACTIVITY_MODIFIED_BY.toString()));
    }


    @Test
    @Transactional
    public void getStudentActivitiesByIdFiltering() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        Long id = studentActivity.getId();

        defaultStudentActivityShouldBeFound("id.equals=" + id);
        defaultStudentActivityShouldNotBeFound("id.notEquals=" + id);

        defaultStudentActivityShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStudentActivityShouldNotBeFound("id.greaterThan=" + id);

        defaultStudentActivityShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStudentActivityShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllStudentActivitiesByActivityStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where activityStartDate equals to DEFAULT_ACTIVITY_START_DATE
        defaultStudentActivityShouldBeFound("activityStartDate.equals=" + DEFAULT_ACTIVITY_START_DATE);

        // Get all the studentActivityList where activityStartDate equals to UPDATED_ACTIVITY_START_DATE
        defaultStudentActivityShouldNotBeFound("activityStartDate.equals=" + UPDATED_ACTIVITY_START_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByActivityStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where activityStartDate not equals to DEFAULT_ACTIVITY_START_DATE
        defaultStudentActivityShouldNotBeFound("activityStartDate.notEquals=" + DEFAULT_ACTIVITY_START_DATE);

        // Get all the studentActivityList where activityStartDate not equals to UPDATED_ACTIVITY_START_DATE
        defaultStudentActivityShouldBeFound("activityStartDate.notEquals=" + UPDATED_ACTIVITY_START_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByActivityStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where activityStartDate in DEFAULT_ACTIVITY_START_DATE or UPDATED_ACTIVITY_START_DATE
        defaultStudentActivityShouldBeFound("activityStartDate.in=" + DEFAULT_ACTIVITY_START_DATE + "," + UPDATED_ACTIVITY_START_DATE);

        // Get all the studentActivityList where activityStartDate equals to UPDATED_ACTIVITY_START_DATE
        defaultStudentActivityShouldNotBeFound("activityStartDate.in=" + UPDATED_ACTIVITY_START_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByActivityStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where activityStartDate is not null
        defaultStudentActivityShouldBeFound("activityStartDate.specified=true");

        // Get all the studentActivityList where activityStartDate is null
        defaultStudentActivityShouldNotBeFound("activityStartDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByActivityStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where activityStartDate is greater than or equal to DEFAULT_ACTIVITY_START_DATE
        defaultStudentActivityShouldBeFound("activityStartDate.greaterThanOrEqual=" + DEFAULT_ACTIVITY_START_DATE);

        // Get all the studentActivityList where activityStartDate is greater than or equal to UPDATED_ACTIVITY_START_DATE
        defaultStudentActivityShouldNotBeFound("activityStartDate.greaterThanOrEqual=" + UPDATED_ACTIVITY_START_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByActivityStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where activityStartDate is less than or equal to DEFAULT_ACTIVITY_START_DATE
        defaultStudentActivityShouldBeFound("activityStartDate.lessThanOrEqual=" + DEFAULT_ACTIVITY_START_DATE);

        // Get all the studentActivityList where activityStartDate is less than or equal to SMALLER_ACTIVITY_START_DATE
        defaultStudentActivityShouldNotBeFound("activityStartDate.lessThanOrEqual=" + SMALLER_ACTIVITY_START_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByActivityStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where activityStartDate is less than DEFAULT_ACTIVITY_START_DATE
        defaultStudentActivityShouldNotBeFound("activityStartDate.lessThan=" + DEFAULT_ACTIVITY_START_DATE);

        // Get all the studentActivityList where activityStartDate is less than UPDATED_ACTIVITY_START_DATE
        defaultStudentActivityShouldBeFound("activityStartDate.lessThan=" + UPDATED_ACTIVITY_START_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByActivityStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where activityStartDate is greater than DEFAULT_ACTIVITY_START_DATE
        defaultStudentActivityShouldNotBeFound("activityStartDate.greaterThan=" + DEFAULT_ACTIVITY_START_DATE);

        // Get all the studentActivityList where activityStartDate is greater than SMALLER_ACTIVITY_START_DATE
        defaultStudentActivityShouldBeFound("activityStartDate.greaterThan=" + SMALLER_ACTIVITY_START_DATE);
    }


    @Test
    @Transactional
    public void getAllStudentActivitiesByActivityEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where activityEndDate equals to DEFAULT_ACTIVITY_END_DATE
        defaultStudentActivityShouldBeFound("activityEndDate.equals=" + DEFAULT_ACTIVITY_END_DATE);

        // Get all the studentActivityList where activityEndDate equals to UPDATED_ACTIVITY_END_DATE
        defaultStudentActivityShouldNotBeFound("activityEndDate.equals=" + UPDATED_ACTIVITY_END_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByActivityEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where activityEndDate not equals to DEFAULT_ACTIVITY_END_DATE
        defaultStudentActivityShouldNotBeFound("activityEndDate.notEquals=" + DEFAULT_ACTIVITY_END_DATE);

        // Get all the studentActivityList where activityEndDate not equals to UPDATED_ACTIVITY_END_DATE
        defaultStudentActivityShouldBeFound("activityEndDate.notEquals=" + UPDATED_ACTIVITY_END_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByActivityEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where activityEndDate in DEFAULT_ACTIVITY_END_DATE or UPDATED_ACTIVITY_END_DATE
        defaultStudentActivityShouldBeFound("activityEndDate.in=" + DEFAULT_ACTIVITY_END_DATE + "," + UPDATED_ACTIVITY_END_DATE);

        // Get all the studentActivityList where activityEndDate equals to UPDATED_ACTIVITY_END_DATE
        defaultStudentActivityShouldNotBeFound("activityEndDate.in=" + UPDATED_ACTIVITY_END_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByActivityEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where activityEndDate is not null
        defaultStudentActivityShouldBeFound("activityEndDate.specified=true");

        // Get all the studentActivityList where activityEndDate is null
        defaultStudentActivityShouldNotBeFound("activityEndDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByActivityEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where activityEndDate is greater than or equal to DEFAULT_ACTIVITY_END_DATE
        defaultStudentActivityShouldBeFound("activityEndDate.greaterThanOrEqual=" + DEFAULT_ACTIVITY_END_DATE);

        // Get all the studentActivityList where activityEndDate is greater than or equal to UPDATED_ACTIVITY_END_DATE
        defaultStudentActivityShouldNotBeFound("activityEndDate.greaterThanOrEqual=" + UPDATED_ACTIVITY_END_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByActivityEndDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where activityEndDate is less than or equal to DEFAULT_ACTIVITY_END_DATE
        defaultStudentActivityShouldBeFound("activityEndDate.lessThanOrEqual=" + DEFAULT_ACTIVITY_END_DATE);

        // Get all the studentActivityList where activityEndDate is less than or equal to SMALLER_ACTIVITY_END_DATE
        defaultStudentActivityShouldNotBeFound("activityEndDate.lessThanOrEqual=" + SMALLER_ACTIVITY_END_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByActivityEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where activityEndDate is less than DEFAULT_ACTIVITY_END_DATE
        defaultStudentActivityShouldNotBeFound("activityEndDate.lessThan=" + DEFAULT_ACTIVITY_END_DATE);

        // Get all the studentActivityList where activityEndDate is less than UPDATED_ACTIVITY_END_DATE
        defaultStudentActivityShouldBeFound("activityEndDate.lessThan=" + UPDATED_ACTIVITY_END_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByActivityEndDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where activityEndDate is greater than DEFAULT_ACTIVITY_END_DATE
        defaultStudentActivityShouldNotBeFound("activityEndDate.greaterThan=" + DEFAULT_ACTIVITY_END_DATE);

        // Get all the studentActivityList where activityEndDate is greater than SMALLER_ACTIVITY_END_DATE
        defaultStudentActivityShouldBeFound("activityEndDate.greaterThan=" + SMALLER_ACTIVITY_END_DATE);
    }


    @Test
    @Transactional
    public void getAllStudentActivitiesByActivityGradeIsEqualToSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where activityGrade equals to DEFAULT_ACTIVITY_GRADE
        defaultStudentActivityShouldBeFound("activityGrade.equals=" + DEFAULT_ACTIVITY_GRADE);

        // Get all the studentActivityList where activityGrade equals to UPDATED_ACTIVITY_GRADE
        defaultStudentActivityShouldNotBeFound("activityGrade.equals=" + UPDATED_ACTIVITY_GRADE);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByActivityGradeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where activityGrade not equals to DEFAULT_ACTIVITY_GRADE
        defaultStudentActivityShouldNotBeFound("activityGrade.notEquals=" + DEFAULT_ACTIVITY_GRADE);

        // Get all the studentActivityList where activityGrade not equals to UPDATED_ACTIVITY_GRADE
        defaultStudentActivityShouldBeFound("activityGrade.notEquals=" + UPDATED_ACTIVITY_GRADE);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByActivityGradeIsInShouldWork() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where activityGrade in DEFAULT_ACTIVITY_GRADE or UPDATED_ACTIVITY_GRADE
        defaultStudentActivityShouldBeFound("activityGrade.in=" + DEFAULT_ACTIVITY_GRADE + "," + UPDATED_ACTIVITY_GRADE);

        // Get all the studentActivityList where activityGrade equals to UPDATED_ACTIVITY_GRADE
        defaultStudentActivityShouldNotBeFound("activityGrade.in=" + UPDATED_ACTIVITY_GRADE);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByActivityGradeIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where activityGrade is not null
        defaultStudentActivityShouldBeFound("activityGrade.specified=true");

        // Get all the studentActivityList where activityGrade is null
        defaultStudentActivityShouldNotBeFound("activityGrade.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByActivityGradeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where activityGrade is greater than or equal to DEFAULT_ACTIVITY_GRADE
        defaultStudentActivityShouldBeFound("activityGrade.greaterThanOrEqual=" + DEFAULT_ACTIVITY_GRADE);

        // Get all the studentActivityList where activityGrade is greater than or equal to UPDATED_ACTIVITY_GRADE
        defaultStudentActivityShouldNotBeFound("activityGrade.greaterThanOrEqual=" + UPDATED_ACTIVITY_GRADE);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByActivityGradeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where activityGrade is less than or equal to DEFAULT_ACTIVITY_GRADE
        defaultStudentActivityShouldBeFound("activityGrade.lessThanOrEqual=" + DEFAULT_ACTIVITY_GRADE);

        // Get all the studentActivityList where activityGrade is less than or equal to SMALLER_ACTIVITY_GRADE
        defaultStudentActivityShouldNotBeFound("activityGrade.lessThanOrEqual=" + SMALLER_ACTIVITY_GRADE);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByActivityGradeIsLessThanSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where activityGrade is less than DEFAULT_ACTIVITY_GRADE
        defaultStudentActivityShouldNotBeFound("activityGrade.lessThan=" + DEFAULT_ACTIVITY_GRADE);

        // Get all the studentActivityList where activityGrade is less than UPDATED_ACTIVITY_GRADE
        defaultStudentActivityShouldBeFound("activityGrade.lessThan=" + UPDATED_ACTIVITY_GRADE);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByActivityGradeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where activityGrade is greater than DEFAULT_ACTIVITY_GRADE
        defaultStudentActivityShouldNotBeFound("activityGrade.greaterThan=" + DEFAULT_ACTIVITY_GRADE);

        // Get all the studentActivityList where activityGrade is greater than SMALLER_ACTIVITY_GRADE
        defaultStudentActivityShouldBeFound("activityGrade.greaterThan=" + SMALLER_ACTIVITY_GRADE);
    }


    @Test
    @Transactional
    public void getAllStudentActivitiesByStudentActivityGradeDateIsEqualToSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where studentActivityGradeDate equals to DEFAULT_STUDENT_ACTIVITY_GRADE_DATE
        defaultStudentActivityShouldBeFound("studentActivityGradeDate.equals=" + DEFAULT_STUDENT_ACTIVITY_GRADE_DATE);

        // Get all the studentActivityList where studentActivityGradeDate equals to UPDATED_STUDENT_ACTIVITY_GRADE_DATE
        defaultStudentActivityShouldNotBeFound("studentActivityGradeDate.equals=" + UPDATED_STUDENT_ACTIVITY_GRADE_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByStudentActivityGradeDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where studentActivityGradeDate not equals to DEFAULT_STUDENT_ACTIVITY_GRADE_DATE
        defaultStudentActivityShouldNotBeFound("studentActivityGradeDate.notEquals=" + DEFAULT_STUDENT_ACTIVITY_GRADE_DATE);

        // Get all the studentActivityList where studentActivityGradeDate not equals to UPDATED_STUDENT_ACTIVITY_GRADE_DATE
        defaultStudentActivityShouldBeFound("studentActivityGradeDate.notEquals=" + UPDATED_STUDENT_ACTIVITY_GRADE_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByStudentActivityGradeDateIsInShouldWork() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where studentActivityGradeDate in DEFAULT_STUDENT_ACTIVITY_GRADE_DATE or UPDATED_STUDENT_ACTIVITY_GRADE_DATE
        defaultStudentActivityShouldBeFound("studentActivityGradeDate.in=" + DEFAULT_STUDENT_ACTIVITY_GRADE_DATE + "," + UPDATED_STUDENT_ACTIVITY_GRADE_DATE);

        // Get all the studentActivityList where studentActivityGradeDate equals to UPDATED_STUDENT_ACTIVITY_GRADE_DATE
        defaultStudentActivityShouldNotBeFound("studentActivityGradeDate.in=" + UPDATED_STUDENT_ACTIVITY_GRADE_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByStudentActivityGradeDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where studentActivityGradeDate is not null
        defaultStudentActivityShouldBeFound("studentActivityGradeDate.specified=true");

        // Get all the studentActivityList where studentActivityGradeDate is null
        defaultStudentActivityShouldNotBeFound("studentActivityGradeDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByStudentActivityGradeDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where studentActivityGradeDate is greater than or equal to DEFAULT_STUDENT_ACTIVITY_GRADE_DATE
        defaultStudentActivityShouldBeFound("studentActivityGradeDate.greaterThanOrEqual=" + DEFAULT_STUDENT_ACTIVITY_GRADE_DATE);

        // Get all the studentActivityList where studentActivityGradeDate is greater than or equal to UPDATED_STUDENT_ACTIVITY_GRADE_DATE
        defaultStudentActivityShouldNotBeFound("studentActivityGradeDate.greaterThanOrEqual=" + UPDATED_STUDENT_ACTIVITY_GRADE_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByStudentActivityGradeDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where studentActivityGradeDate is less than or equal to DEFAULT_STUDENT_ACTIVITY_GRADE_DATE
        defaultStudentActivityShouldBeFound("studentActivityGradeDate.lessThanOrEqual=" + DEFAULT_STUDENT_ACTIVITY_GRADE_DATE);

        // Get all the studentActivityList where studentActivityGradeDate is less than or equal to SMALLER_STUDENT_ACTIVITY_GRADE_DATE
        defaultStudentActivityShouldNotBeFound("studentActivityGradeDate.lessThanOrEqual=" + SMALLER_STUDENT_ACTIVITY_GRADE_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByStudentActivityGradeDateIsLessThanSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where studentActivityGradeDate is less than DEFAULT_STUDENT_ACTIVITY_GRADE_DATE
        defaultStudentActivityShouldNotBeFound("studentActivityGradeDate.lessThan=" + DEFAULT_STUDENT_ACTIVITY_GRADE_DATE);

        // Get all the studentActivityList where studentActivityGradeDate is less than UPDATED_STUDENT_ACTIVITY_GRADE_DATE
        defaultStudentActivityShouldBeFound("studentActivityGradeDate.lessThan=" + UPDATED_STUDENT_ACTIVITY_GRADE_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByStudentActivityGradeDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where studentActivityGradeDate is greater than DEFAULT_STUDENT_ACTIVITY_GRADE_DATE
        defaultStudentActivityShouldNotBeFound("studentActivityGradeDate.greaterThan=" + DEFAULT_STUDENT_ACTIVITY_GRADE_DATE);

        // Get all the studentActivityList where studentActivityGradeDate is greater than SMALLER_STUDENT_ACTIVITY_GRADE_DATE
        defaultStudentActivityShouldBeFound("studentActivityGradeDate.greaterThan=" + SMALLER_STUDENT_ACTIVITY_GRADE_DATE);
    }


    @Test
    @Transactional
    public void getAllStudentActivitiesByStudentActivityCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where studentActivityCreatedDate equals to DEFAULT_STUDENT_ACTIVITY_CREATED_DATE
        defaultStudentActivityShouldBeFound("studentActivityCreatedDate.equals=" + DEFAULT_STUDENT_ACTIVITY_CREATED_DATE);

        // Get all the studentActivityList where studentActivityCreatedDate equals to UPDATED_STUDENT_ACTIVITY_CREATED_DATE
        defaultStudentActivityShouldNotBeFound("studentActivityCreatedDate.equals=" + UPDATED_STUDENT_ACTIVITY_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByStudentActivityCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where studentActivityCreatedDate not equals to DEFAULT_STUDENT_ACTIVITY_CREATED_DATE
        defaultStudentActivityShouldNotBeFound("studentActivityCreatedDate.notEquals=" + DEFAULT_STUDENT_ACTIVITY_CREATED_DATE);

        // Get all the studentActivityList where studentActivityCreatedDate not equals to UPDATED_STUDENT_ACTIVITY_CREATED_DATE
        defaultStudentActivityShouldBeFound("studentActivityCreatedDate.notEquals=" + UPDATED_STUDENT_ACTIVITY_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByStudentActivityCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where studentActivityCreatedDate in DEFAULT_STUDENT_ACTIVITY_CREATED_DATE or UPDATED_STUDENT_ACTIVITY_CREATED_DATE
        defaultStudentActivityShouldBeFound("studentActivityCreatedDate.in=" + DEFAULT_STUDENT_ACTIVITY_CREATED_DATE + "," + UPDATED_STUDENT_ACTIVITY_CREATED_DATE);

        // Get all the studentActivityList where studentActivityCreatedDate equals to UPDATED_STUDENT_ACTIVITY_CREATED_DATE
        defaultStudentActivityShouldNotBeFound("studentActivityCreatedDate.in=" + UPDATED_STUDENT_ACTIVITY_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByStudentActivityCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where studentActivityCreatedDate is not null
        defaultStudentActivityShouldBeFound("studentActivityCreatedDate.specified=true");

        // Get all the studentActivityList where studentActivityCreatedDate is null
        defaultStudentActivityShouldNotBeFound("studentActivityCreatedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByStudentActivityCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where studentActivityCreatedDate is greater than or equal to DEFAULT_STUDENT_ACTIVITY_CREATED_DATE
        defaultStudentActivityShouldBeFound("studentActivityCreatedDate.greaterThanOrEqual=" + DEFAULT_STUDENT_ACTIVITY_CREATED_DATE);

        // Get all the studentActivityList where studentActivityCreatedDate is greater than or equal to UPDATED_STUDENT_ACTIVITY_CREATED_DATE
        defaultStudentActivityShouldNotBeFound("studentActivityCreatedDate.greaterThanOrEqual=" + UPDATED_STUDENT_ACTIVITY_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByStudentActivityCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where studentActivityCreatedDate is less than or equal to DEFAULT_STUDENT_ACTIVITY_CREATED_DATE
        defaultStudentActivityShouldBeFound("studentActivityCreatedDate.lessThanOrEqual=" + DEFAULT_STUDENT_ACTIVITY_CREATED_DATE);

        // Get all the studentActivityList where studentActivityCreatedDate is less than or equal to SMALLER_STUDENT_ACTIVITY_CREATED_DATE
        defaultStudentActivityShouldNotBeFound("studentActivityCreatedDate.lessThanOrEqual=" + SMALLER_STUDENT_ACTIVITY_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByStudentActivityCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where studentActivityCreatedDate is less than DEFAULT_STUDENT_ACTIVITY_CREATED_DATE
        defaultStudentActivityShouldNotBeFound("studentActivityCreatedDate.lessThan=" + DEFAULT_STUDENT_ACTIVITY_CREATED_DATE);

        // Get all the studentActivityList where studentActivityCreatedDate is less than UPDATED_STUDENT_ACTIVITY_CREATED_DATE
        defaultStudentActivityShouldBeFound("studentActivityCreatedDate.lessThan=" + UPDATED_STUDENT_ACTIVITY_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByStudentActivityCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where studentActivityCreatedDate is greater than DEFAULT_STUDENT_ACTIVITY_CREATED_DATE
        defaultStudentActivityShouldNotBeFound("studentActivityCreatedDate.greaterThan=" + DEFAULT_STUDENT_ACTIVITY_CREATED_DATE);

        // Get all the studentActivityList where studentActivityCreatedDate is greater than SMALLER_STUDENT_ACTIVITY_CREATED_DATE
        defaultStudentActivityShouldBeFound("studentActivityCreatedDate.greaterThan=" + SMALLER_STUDENT_ACTIVITY_CREATED_DATE);
    }


    @Test
    @Transactional
    public void getAllStudentActivitiesByStudentActivityCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where studentActivityCreatedBy equals to DEFAULT_STUDENT_ACTIVITY_CREATED_BY
        defaultStudentActivityShouldBeFound("studentActivityCreatedBy.equals=" + DEFAULT_STUDENT_ACTIVITY_CREATED_BY);

        // Get all the studentActivityList where studentActivityCreatedBy equals to UPDATED_STUDENT_ACTIVITY_CREATED_BY
        defaultStudentActivityShouldNotBeFound("studentActivityCreatedBy.equals=" + UPDATED_STUDENT_ACTIVITY_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByStudentActivityCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where studentActivityCreatedBy not equals to DEFAULT_STUDENT_ACTIVITY_CREATED_BY
        defaultStudentActivityShouldNotBeFound("studentActivityCreatedBy.notEquals=" + DEFAULT_STUDENT_ACTIVITY_CREATED_BY);

        // Get all the studentActivityList where studentActivityCreatedBy not equals to UPDATED_STUDENT_ACTIVITY_CREATED_BY
        defaultStudentActivityShouldBeFound("studentActivityCreatedBy.notEquals=" + UPDATED_STUDENT_ACTIVITY_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByStudentActivityCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where studentActivityCreatedBy in DEFAULT_STUDENT_ACTIVITY_CREATED_BY or UPDATED_STUDENT_ACTIVITY_CREATED_BY
        defaultStudentActivityShouldBeFound("studentActivityCreatedBy.in=" + DEFAULT_STUDENT_ACTIVITY_CREATED_BY + "," + UPDATED_STUDENT_ACTIVITY_CREATED_BY);

        // Get all the studentActivityList where studentActivityCreatedBy equals to UPDATED_STUDENT_ACTIVITY_CREATED_BY
        defaultStudentActivityShouldNotBeFound("studentActivityCreatedBy.in=" + UPDATED_STUDENT_ACTIVITY_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByStudentActivityCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where studentActivityCreatedBy is not null
        defaultStudentActivityShouldBeFound("studentActivityCreatedBy.specified=true");

        // Get all the studentActivityList where studentActivityCreatedBy is null
        defaultStudentActivityShouldNotBeFound("studentActivityCreatedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllStudentActivitiesByStudentActivityCreatedByContainsSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where studentActivityCreatedBy contains DEFAULT_STUDENT_ACTIVITY_CREATED_BY
        defaultStudentActivityShouldBeFound("studentActivityCreatedBy.contains=" + DEFAULT_STUDENT_ACTIVITY_CREATED_BY);

        // Get all the studentActivityList where studentActivityCreatedBy contains UPDATED_STUDENT_ACTIVITY_CREATED_BY
        defaultStudentActivityShouldNotBeFound("studentActivityCreatedBy.contains=" + UPDATED_STUDENT_ACTIVITY_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByStudentActivityCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where studentActivityCreatedBy does not contain DEFAULT_STUDENT_ACTIVITY_CREATED_BY
        defaultStudentActivityShouldNotBeFound("studentActivityCreatedBy.doesNotContain=" + DEFAULT_STUDENT_ACTIVITY_CREATED_BY);

        // Get all the studentActivityList where studentActivityCreatedBy does not contain UPDATED_STUDENT_ACTIVITY_CREATED_BY
        defaultStudentActivityShouldBeFound("studentActivityCreatedBy.doesNotContain=" + UPDATED_STUDENT_ACTIVITY_CREATED_BY);
    }


    @Test
    @Transactional
    public void getAllStudentActivitiesByStudentActivityModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where studentActivityModifiedDate equals to DEFAULT_STUDENT_ACTIVITY_MODIFIED_DATE
        defaultStudentActivityShouldBeFound("studentActivityModifiedDate.equals=" + DEFAULT_STUDENT_ACTIVITY_MODIFIED_DATE);

        // Get all the studentActivityList where studentActivityModifiedDate equals to UPDATED_STUDENT_ACTIVITY_MODIFIED_DATE
        defaultStudentActivityShouldNotBeFound("studentActivityModifiedDate.equals=" + UPDATED_STUDENT_ACTIVITY_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByStudentActivityModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where studentActivityModifiedDate not equals to DEFAULT_STUDENT_ACTIVITY_MODIFIED_DATE
        defaultStudentActivityShouldNotBeFound("studentActivityModifiedDate.notEquals=" + DEFAULT_STUDENT_ACTIVITY_MODIFIED_DATE);

        // Get all the studentActivityList where studentActivityModifiedDate not equals to UPDATED_STUDENT_ACTIVITY_MODIFIED_DATE
        defaultStudentActivityShouldBeFound("studentActivityModifiedDate.notEquals=" + UPDATED_STUDENT_ACTIVITY_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByStudentActivityModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where studentActivityModifiedDate in DEFAULT_STUDENT_ACTIVITY_MODIFIED_DATE or UPDATED_STUDENT_ACTIVITY_MODIFIED_DATE
        defaultStudentActivityShouldBeFound("studentActivityModifiedDate.in=" + DEFAULT_STUDENT_ACTIVITY_MODIFIED_DATE + "," + UPDATED_STUDENT_ACTIVITY_MODIFIED_DATE);

        // Get all the studentActivityList where studentActivityModifiedDate equals to UPDATED_STUDENT_ACTIVITY_MODIFIED_DATE
        defaultStudentActivityShouldNotBeFound("studentActivityModifiedDate.in=" + UPDATED_STUDENT_ACTIVITY_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByStudentActivityModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where studentActivityModifiedDate is not null
        defaultStudentActivityShouldBeFound("studentActivityModifiedDate.specified=true");

        // Get all the studentActivityList where studentActivityModifiedDate is null
        defaultStudentActivityShouldNotBeFound("studentActivityModifiedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByStudentActivityModifiedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where studentActivityModifiedDate is greater than or equal to DEFAULT_STUDENT_ACTIVITY_MODIFIED_DATE
        defaultStudentActivityShouldBeFound("studentActivityModifiedDate.greaterThanOrEqual=" + DEFAULT_STUDENT_ACTIVITY_MODIFIED_DATE);

        // Get all the studentActivityList where studentActivityModifiedDate is greater than or equal to UPDATED_STUDENT_ACTIVITY_MODIFIED_DATE
        defaultStudentActivityShouldNotBeFound("studentActivityModifiedDate.greaterThanOrEqual=" + UPDATED_STUDENT_ACTIVITY_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByStudentActivityModifiedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where studentActivityModifiedDate is less than or equal to DEFAULT_STUDENT_ACTIVITY_MODIFIED_DATE
        defaultStudentActivityShouldBeFound("studentActivityModifiedDate.lessThanOrEqual=" + DEFAULT_STUDENT_ACTIVITY_MODIFIED_DATE);

        // Get all the studentActivityList where studentActivityModifiedDate is less than or equal to SMALLER_STUDENT_ACTIVITY_MODIFIED_DATE
        defaultStudentActivityShouldNotBeFound("studentActivityModifiedDate.lessThanOrEqual=" + SMALLER_STUDENT_ACTIVITY_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByStudentActivityModifiedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where studentActivityModifiedDate is less than DEFAULT_STUDENT_ACTIVITY_MODIFIED_DATE
        defaultStudentActivityShouldNotBeFound("studentActivityModifiedDate.lessThan=" + DEFAULT_STUDENT_ACTIVITY_MODIFIED_DATE);

        // Get all the studentActivityList where studentActivityModifiedDate is less than UPDATED_STUDENT_ACTIVITY_MODIFIED_DATE
        defaultStudentActivityShouldBeFound("studentActivityModifiedDate.lessThan=" + UPDATED_STUDENT_ACTIVITY_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByStudentActivityModifiedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where studentActivityModifiedDate is greater than DEFAULT_STUDENT_ACTIVITY_MODIFIED_DATE
        defaultStudentActivityShouldNotBeFound("studentActivityModifiedDate.greaterThan=" + DEFAULT_STUDENT_ACTIVITY_MODIFIED_DATE);

        // Get all the studentActivityList where studentActivityModifiedDate is greater than SMALLER_STUDENT_ACTIVITY_MODIFIED_DATE
        defaultStudentActivityShouldBeFound("studentActivityModifiedDate.greaterThan=" + SMALLER_STUDENT_ACTIVITY_MODIFIED_DATE);
    }


    @Test
    @Transactional
    public void getAllStudentActivitiesByStudentActivityModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where studentActivityModifiedBy equals to DEFAULT_STUDENT_ACTIVITY_MODIFIED_BY
        defaultStudentActivityShouldBeFound("studentActivityModifiedBy.equals=" + DEFAULT_STUDENT_ACTIVITY_MODIFIED_BY);

        // Get all the studentActivityList where studentActivityModifiedBy equals to UPDATED_STUDENT_ACTIVITY_MODIFIED_BY
        defaultStudentActivityShouldNotBeFound("studentActivityModifiedBy.equals=" + UPDATED_STUDENT_ACTIVITY_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByStudentActivityModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where studentActivityModifiedBy not equals to DEFAULT_STUDENT_ACTIVITY_MODIFIED_BY
        defaultStudentActivityShouldNotBeFound("studentActivityModifiedBy.notEquals=" + DEFAULT_STUDENT_ACTIVITY_MODIFIED_BY);

        // Get all the studentActivityList where studentActivityModifiedBy not equals to UPDATED_STUDENT_ACTIVITY_MODIFIED_BY
        defaultStudentActivityShouldBeFound("studentActivityModifiedBy.notEquals=" + UPDATED_STUDENT_ACTIVITY_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByStudentActivityModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where studentActivityModifiedBy in DEFAULT_STUDENT_ACTIVITY_MODIFIED_BY or UPDATED_STUDENT_ACTIVITY_MODIFIED_BY
        defaultStudentActivityShouldBeFound("studentActivityModifiedBy.in=" + DEFAULT_STUDENT_ACTIVITY_MODIFIED_BY + "," + UPDATED_STUDENT_ACTIVITY_MODIFIED_BY);

        // Get all the studentActivityList where studentActivityModifiedBy equals to UPDATED_STUDENT_ACTIVITY_MODIFIED_BY
        defaultStudentActivityShouldNotBeFound("studentActivityModifiedBy.in=" + UPDATED_STUDENT_ACTIVITY_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByStudentActivityModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where studentActivityModifiedBy is not null
        defaultStudentActivityShouldBeFound("studentActivityModifiedBy.specified=true");

        // Get all the studentActivityList where studentActivityModifiedBy is null
        defaultStudentActivityShouldNotBeFound("studentActivityModifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByStudentActivityModifiedByIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where studentActivityModifiedBy is greater than or equal to DEFAULT_STUDENT_ACTIVITY_MODIFIED_BY
        defaultStudentActivityShouldBeFound("studentActivityModifiedBy.greaterThanOrEqual=" + DEFAULT_STUDENT_ACTIVITY_MODIFIED_BY);

        // Get all the studentActivityList where studentActivityModifiedBy is greater than or equal to UPDATED_STUDENT_ACTIVITY_MODIFIED_BY
        defaultStudentActivityShouldNotBeFound("studentActivityModifiedBy.greaterThanOrEqual=" + UPDATED_STUDENT_ACTIVITY_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByStudentActivityModifiedByIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where studentActivityModifiedBy is less than or equal to DEFAULT_STUDENT_ACTIVITY_MODIFIED_BY
        defaultStudentActivityShouldBeFound("studentActivityModifiedBy.lessThanOrEqual=" + DEFAULT_STUDENT_ACTIVITY_MODIFIED_BY);

        // Get all the studentActivityList where studentActivityModifiedBy is less than or equal to SMALLER_STUDENT_ACTIVITY_MODIFIED_BY
        defaultStudentActivityShouldNotBeFound("studentActivityModifiedBy.lessThanOrEqual=" + SMALLER_STUDENT_ACTIVITY_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByStudentActivityModifiedByIsLessThanSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where studentActivityModifiedBy is less than DEFAULT_STUDENT_ACTIVITY_MODIFIED_BY
        defaultStudentActivityShouldNotBeFound("studentActivityModifiedBy.lessThan=" + DEFAULT_STUDENT_ACTIVITY_MODIFIED_BY);

        // Get all the studentActivityList where studentActivityModifiedBy is less than UPDATED_STUDENT_ACTIVITY_MODIFIED_BY
        defaultStudentActivityShouldBeFound("studentActivityModifiedBy.lessThan=" + UPDATED_STUDENT_ACTIVITY_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllStudentActivitiesByStudentActivityModifiedByIsGreaterThanSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

        // Get all the studentActivityList where studentActivityModifiedBy is greater than DEFAULT_STUDENT_ACTIVITY_MODIFIED_BY
        defaultStudentActivityShouldNotBeFound("studentActivityModifiedBy.greaterThan=" + DEFAULT_STUDENT_ACTIVITY_MODIFIED_BY);

        // Get all the studentActivityList where studentActivityModifiedBy is greater than SMALLER_STUDENT_ACTIVITY_MODIFIED_BY
        defaultStudentActivityShouldBeFound("studentActivityModifiedBy.greaterThan=" + SMALLER_STUDENT_ACTIVITY_MODIFIED_BY);
    }


    @Test
    @Transactional
    public void getAllStudentActivitiesByActivityIsEqualToSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);
        Activity activity = ActivityResourceIT.createEntity(em);
        em.persist(activity);
        em.flush();
        studentActivity.setActivity(activity);
        studentActivityRepository.saveAndFlush(studentActivity);
        Long activityId = activity.getId();

        // Get all the studentActivityList where activity equals to activityId
        defaultStudentActivityShouldBeFound("activityId.equals=" + activityId);

        // Get all the studentActivityList where activity equals to activityId + 1
        defaultStudentActivityShouldNotBeFound("activityId.equals=" + (activityId + 1));
    }


    @Test
    @Transactional
    public void getAllStudentActivitiesByStudentscheduleIsEqualToSomething() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);
        StudentSchedule studentschedule = StudentScheduleResourceIT.createEntity(em);
        em.persist(studentschedule);
        em.flush();
        studentActivity.setStudentschedule(studentschedule);
        studentActivityRepository.saveAndFlush(studentActivity);
        Long studentscheduleId = studentschedule.getId();

        // Get all the studentActivityList where studentschedule equals to studentscheduleId
        defaultStudentActivityShouldBeFound("studentscheduleId.equals=" + studentscheduleId);

        // Get all the studentActivityList where studentschedule equals to studentscheduleId + 1
        defaultStudentActivityShouldNotBeFound("studentscheduleId.equals=" + (studentscheduleId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStudentActivityShouldBeFound(String filter) throws Exception {
        restStudentActivityMockMvc.perform(get("/api/student-activities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentActivity.getId().intValue())))
            .andExpect(jsonPath("$.[*].activityStartDate").value(hasItem(DEFAULT_ACTIVITY_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].activityEndDate").value(hasItem(DEFAULT_ACTIVITY_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].activityGrade").value(hasItem(DEFAULT_ACTIVITY_GRADE)))
            .andExpect(jsonPath("$.[*].studentActivityGradeDate").value(hasItem(DEFAULT_STUDENT_ACTIVITY_GRADE_DATE.toString())))
            .andExpect(jsonPath("$.[*].studentActivityCreatedDate").value(hasItem(DEFAULT_STUDENT_ACTIVITY_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].studentActivityCreatedBy").value(hasItem(DEFAULT_STUDENT_ACTIVITY_CREATED_BY)))
            .andExpect(jsonPath("$.[*].studentActivityModifiedDate").value(hasItem(DEFAULT_STUDENT_ACTIVITY_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].studentActivityModifiedBy").value(hasItem(DEFAULT_STUDENT_ACTIVITY_MODIFIED_BY.toString())));

        // Check, that the count call also returns 1
        restStudentActivityMockMvc.perform(get("/api/student-activities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStudentActivityShouldNotBeFound(String filter) throws Exception {
        restStudentActivityMockMvc.perform(get("/api/student-activities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStudentActivityMockMvc.perform(get("/api/student-activities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingStudentActivity() throws Exception {
        // Get the studentActivity
        restStudentActivityMockMvc.perform(get("/api/student-activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudentActivity() throws Exception {
        // Initialize the database
        studentActivityService.save(studentActivity);

        int databaseSizeBeforeUpdate = studentActivityRepository.findAll().size();

        // Update the studentActivity
        StudentActivity updatedStudentActivity = studentActivityRepository.findById(studentActivity.getId()).get();
        // Disconnect from session so that the updates on updatedStudentActivity are not directly saved in db
        em.detach(updatedStudentActivity);
        updatedStudentActivity
            .activityStartDate(UPDATED_ACTIVITY_START_DATE)
            .activityEndDate(UPDATED_ACTIVITY_END_DATE)
            .activityGrade(UPDATED_ACTIVITY_GRADE)
            .studentActivityGradeDate(UPDATED_STUDENT_ACTIVITY_GRADE_DATE)
            .studentActivityCreatedDate(UPDATED_STUDENT_ACTIVITY_CREATED_DATE)
            .studentActivityCreatedBy(UPDATED_STUDENT_ACTIVITY_CREATED_BY)
            .studentActivityModifiedDate(UPDATED_STUDENT_ACTIVITY_MODIFIED_DATE)
            .studentActivityModifiedBy(UPDATED_STUDENT_ACTIVITY_MODIFIED_BY);

        restStudentActivityMockMvc.perform(put("/api/student-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStudentActivity)))
            .andExpect(status().isOk());

        // Validate the StudentActivity in the database
        List<StudentActivity> studentActivityList = studentActivityRepository.findAll();
        assertThat(studentActivityList).hasSize(databaseSizeBeforeUpdate);
        StudentActivity testStudentActivity = studentActivityList.get(studentActivityList.size() - 1);
        assertThat(testStudentActivity.getActivityStartDate()).isEqualTo(UPDATED_ACTIVITY_START_DATE);
        assertThat(testStudentActivity.getActivityEndDate()).isEqualTo(UPDATED_ACTIVITY_END_DATE);
        assertThat(testStudentActivity.getActivityGrade()).isEqualTo(UPDATED_ACTIVITY_GRADE);
        assertThat(testStudentActivity.getStudentActivityGradeDate()).isEqualTo(UPDATED_STUDENT_ACTIVITY_GRADE_DATE);
        assertThat(testStudentActivity.getStudentActivityCreatedDate()).isEqualTo(UPDATED_STUDENT_ACTIVITY_CREATED_DATE);
        assertThat(testStudentActivity.getStudentActivityCreatedBy()).isEqualTo(UPDATED_STUDENT_ACTIVITY_CREATED_BY);
        assertThat(testStudentActivity.getStudentActivityModifiedDate()).isEqualTo(UPDATED_STUDENT_ACTIVITY_MODIFIED_DATE);
        assertThat(testStudentActivity.getStudentActivityModifiedBy()).isEqualTo(UPDATED_STUDENT_ACTIVITY_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingStudentActivity() throws Exception {
        int databaseSizeBeforeUpdate = studentActivityRepository.findAll().size();

        // Create the StudentActivity

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentActivityMockMvc.perform(put("/api/student-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentActivity)))
            .andExpect(status().isBadRequest());

        // Validate the StudentActivity in the database
        List<StudentActivity> studentActivityList = studentActivityRepository.findAll();
        assertThat(studentActivityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStudentActivity() throws Exception {
        // Initialize the database
        studentActivityService.save(studentActivity);

        int databaseSizeBeforeDelete = studentActivityRepository.findAll().size();

        // Delete the studentActivity
        restStudentActivityMockMvc.perform(delete("/api/student-activities/{id}", studentActivity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StudentActivity> studentActivityList = studentActivityRepository.findAll();
        assertThat(studentActivityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
