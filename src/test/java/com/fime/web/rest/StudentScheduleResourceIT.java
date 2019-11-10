package com.fime.web.rest;

import com.fime.LearningPatternsApp;
import com.fime.domain.StudentSchedule;
import com.fime.repository.StudentScheduleRepository;
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
        final StudentScheduleResource studentScheduleResource = new StudentScheduleResource(studentScheduleRepository);
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
            .andExpect(jsonPath("$.[*].studentScheduleCreatedBy").value(hasItem(DEFAULT_STUDENT_SCHEDULE_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].studentScheduleCreationDate").value(hasItem(DEFAULT_STUDENT_SCHEDULE_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].studentScheduleModifiedBy").value(hasItem(DEFAULT_STUDENT_SCHEDULE_MODIFIED_BY.toString())))
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
            .andExpect(jsonPath("$.studentScheduleCreatedBy").value(DEFAULT_STUDENT_SCHEDULE_CREATED_BY.toString()))
            .andExpect(jsonPath("$.studentScheduleCreationDate").value(DEFAULT_STUDENT_SCHEDULE_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.studentScheduleModifiedBy").value(DEFAULT_STUDENT_SCHEDULE_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.studentScheduleModifiedDate").value(DEFAULT_STUDENT_SCHEDULE_MODIFIED_DATE.toString()));
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
        studentScheduleRepository.saveAndFlush(studentSchedule);

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
        studentScheduleRepository.saveAndFlush(studentSchedule);

        int databaseSizeBeforeDelete = studentScheduleRepository.findAll().size();

        // Delete the studentSchedule
        restStudentScheduleMockMvc.perform(delete("/api/student-schedules/{id}", studentSchedule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StudentSchedule> studentScheduleList = studentScheduleRepository.findAll();
        assertThat(studentScheduleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentSchedule.class);
        StudentSchedule studentSchedule1 = new StudentSchedule();
        studentSchedule1.setId(1L);
        StudentSchedule studentSchedule2 = new StudentSchedule();
        studentSchedule2.setId(studentSchedule1.getId());
        assertThat(studentSchedule1).isEqualTo(studentSchedule2);
        studentSchedule2.setId(2L);
        assertThat(studentSchedule1).isNotEqualTo(studentSchedule2);
        studentSchedule1.setId(null);
        assertThat(studentSchedule1).isNotEqualTo(studentSchedule2);
    }
}
