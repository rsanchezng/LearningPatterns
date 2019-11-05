package com.fime.web.rest;

import com.fime.LearningPatternsApp;
import com.fime.domain.StudentActivity;
import com.fime.repository.StudentActivityRepository;
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
 * Integration tests for the {@link StudentActivityResource} REST controller.
 */
@SpringBootTest(classes = LearningPatternsApp.class)
public class StudentActivityResourceIT {

    private static final LocalDate DEFAULT_ACTIVITY_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTIVITY_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ACTIVITY_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTIVITY_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_ACTIVITY_GRADE = 1;
    private static final Integer UPDATED_ACTIVITY_GRADE = 2;

    private static final LocalDate DEFAULT_STUDENT_ACTIVITY_GRADE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STUDENT_ACTIVITY_GRADE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_STUDENT_ACTIVITY_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STUDENT_ACTIVITY_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_STUDENT_ACTIVITY_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_STUDENT_ACTIVITY_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_STUDENT_ACTIVITY_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STUDENT_ACTIVITY_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_STUDENT_ACTIVITY_MODIFIED_BY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STUDENT_ACTIVITY_MODIFIED_BY = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private StudentActivityRepository studentActivityRepository;

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
        final StudentActivityResource studentActivityResource = new StudentActivityResource(studentActivityRepository);
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
    public void getNonExistingStudentActivity() throws Exception {
        // Get the studentActivity
        restStudentActivityMockMvc.perform(get("/api/student-activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudentActivity() throws Exception {
        // Initialize the database
        studentActivityRepository.saveAndFlush(studentActivity);

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
        studentActivityRepository.saveAndFlush(studentActivity);

        int databaseSizeBeforeDelete = studentActivityRepository.findAll().size();

        // Delete the studentActivity
        restStudentActivityMockMvc.perform(delete("/api/student-activities/{id}", studentActivity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StudentActivity> studentActivityList = studentActivityRepository.findAll();
        assertThat(studentActivityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentActivity.class);
        StudentActivity studentActivity1 = new StudentActivity();
        studentActivity1.setId(1L);
        StudentActivity studentActivity2 = new StudentActivity();
        studentActivity2.setId(studentActivity1.getId());
        assertThat(studentActivity1).isEqualTo(studentActivity2);
        studentActivity2.setId(2L);
        assertThat(studentActivity1).isNotEqualTo(studentActivity2);
        studentActivity1.setId(null);
        assertThat(studentActivity1).isNotEqualTo(studentActivity2);
    }
}
