package com.fime.web.rest;

import com.fime.LearningPatternsApp;
import com.fime.domain.Student;
import com.fime.repository.StudentRepository;
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
 * Integration tests for the {@link StudentResource} REST controller.
 */
@SpringBootTest(classes = LearningPatternsApp.class)
public class StudentResourceIT {

    private static final String DEFAULT_STUDENT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STUDENT_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STUDENT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STUDENT_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STUDENT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_STUDENT_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_STUDENT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_STUDENT_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_STUDENT_CREDITS = "AAAAAAAAAA";
    private static final String UPDATED_STUDENT_CREDITS = "BBBBBBBBBB";

    private static final String DEFAULT_STUDENT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_STUDENT_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_STUDENT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STUDENT_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_STUDENT_CREATION_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_STUDENT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_STUDENT_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_STUDENT_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STUDENT_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_STUDENT_MODIFIED_DATE = LocalDate.ofEpochDay(-1L);

    @Autowired
    private StudentRepository studentRepository;

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

    private MockMvc restStudentMockMvc;

    private Student student;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudentResource studentResource = new StudentResource(studentRepository);
        this.restStudentMockMvc = MockMvcBuilders.standaloneSetup(studentResource)
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
    public static Student createEntity(EntityManager em) {
        Student student = new Student()
            .studentFirstName(DEFAULT_STUDENT_FIRST_NAME)
            .studentLastName(DEFAULT_STUDENT_LAST_NAME)
            .studentEmail(DEFAULT_STUDENT_EMAIL)
            .studentPassword(DEFAULT_STUDENT_PASSWORD)
            .studentCredits(DEFAULT_STUDENT_CREDITS)
            .studentCreatedBy(DEFAULT_STUDENT_CREATED_BY)
            .studentCreationDate(DEFAULT_STUDENT_CREATION_DATE)
            .studentModifiedBy(DEFAULT_STUDENT_MODIFIED_BY)
            .studentModifiedDate(DEFAULT_STUDENT_MODIFIED_DATE);
        return student;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Student createUpdatedEntity(EntityManager em) {
        Student student = new Student()
            .studentFirstName(UPDATED_STUDENT_FIRST_NAME)
            .studentLastName(UPDATED_STUDENT_LAST_NAME)
            .studentEmail(UPDATED_STUDENT_EMAIL)
            .studentPassword(UPDATED_STUDENT_PASSWORD)
            .studentCredits(UPDATED_STUDENT_CREDITS)
            .studentCreatedBy(UPDATED_STUDENT_CREATED_BY)
            .studentCreationDate(UPDATED_STUDENT_CREATION_DATE)
            .studentModifiedBy(UPDATED_STUDENT_MODIFIED_BY)
            .studentModifiedDate(UPDATED_STUDENT_MODIFIED_DATE);
        return student;
    }

    @BeforeEach
    public void initTest() {
        student = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudent() throws Exception {
        int databaseSizeBeforeCreate = studentRepository.findAll().size();

        // Create the Student
        restStudentMockMvc.perform(post("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isCreated());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeCreate + 1);
        Student testStudent = studentList.get(studentList.size() - 1);
        assertThat(testStudent.getStudentFirstName()).isEqualTo(DEFAULT_STUDENT_FIRST_NAME);
        assertThat(testStudent.getStudentLastName()).isEqualTo(DEFAULT_STUDENT_LAST_NAME);
        assertThat(testStudent.getStudentEmail()).isEqualTo(DEFAULT_STUDENT_EMAIL);
        assertThat(testStudent.getStudentPassword()).isEqualTo(DEFAULT_STUDENT_PASSWORD);
        assertThat(testStudent.getStudentCredits()).isEqualTo(DEFAULT_STUDENT_CREDITS);
        assertThat(testStudent.getStudentCreatedBy()).isEqualTo(DEFAULT_STUDENT_CREATED_BY);
        assertThat(testStudent.getStudentCreationDate()).isEqualTo(DEFAULT_STUDENT_CREATION_DATE);
        assertThat(testStudent.getStudentModifiedBy()).isEqualTo(DEFAULT_STUDENT_MODIFIED_BY);
        assertThat(testStudent.getStudentModifiedDate()).isEqualTo(DEFAULT_STUDENT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createStudentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentRepository.findAll().size();

        // Create the Student with an existing ID
        student.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentMockMvc.perform(post("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStudents() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList
        restStudentMockMvc.perform(get("/api/students?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(student.getId().intValue())))
            .andExpect(jsonPath("$.[*].studentFirstName").value(hasItem(DEFAULT_STUDENT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].studentLastName").value(hasItem(DEFAULT_STUDENT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].studentEmail").value(hasItem(DEFAULT_STUDENT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].studentPassword").value(hasItem(DEFAULT_STUDENT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].studentCredits").value(hasItem(DEFAULT_STUDENT_CREDITS.toString())))
            .andExpect(jsonPath("$.[*].studentCreatedBy").value(hasItem(DEFAULT_STUDENT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].studentCreationDate").value(hasItem(DEFAULT_STUDENT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].studentModifiedBy").value(hasItem(DEFAULT_STUDENT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].studentModifiedDate").value(hasItem(DEFAULT_STUDENT_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get the student
        restStudentMockMvc.perform(get("/api/students/{id}", student.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(student.getId().intValue()))
            .andExpect(jsonPath("$.studentFirstName").value(DEFAULT_STUDENT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.studentLastName").value(DEFAULT_STUDENT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.studentEmail").value(DEFAULT_STUDENT_EMAIL.toString()))
            .andExpect(jsonPath("$.studentPassword").value(DEFAULT_STUDENT_PASSWORD.toString()))
            .andExpect(jsonPath("$.studentCredits").value(DEFAULT_STUDENT_CREDITS.toString()))
            .andExpect(jsonPath("$.studentCreatedBy").value(DEFAULT_STUDENT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.studentCreationDate").value(DEFAULT_STUDENT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.studentModifiedBy").value(DEFAULT_STUDENT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.studentModifiedDate").value(DEFAULT_STUDENT_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStudent() throws Exception {
        // Get the student
        restStudentMockMvc.perform(get("/api/students/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        int databaseSizeBeforeUpdate = studentRepository.findAll().size();

        // Update the student
        Student updatedStudent = studentRepository.findById(student.getId()).get();
        // Disconnect from session so that the updates on updatedStudent are not directly saved in db
        em.detach(updatedStudent);
        updatedStudent
            .studentFirstName(UPDATED_STUDENT_FIRST_NAME)
            .studentLastName(UPDATED_STUDENT_LAST_NAME)
            .studentEmail(UPDATED_STUDENT_EMAIL)
            .studentPassword(UPDATED_STUDENT_PASSWORD)
            .studentCredits(UPDATED_STUDENT_CREDITS)
            .studentCreatedBy(UPDATED_STUDENT_CREATED_BY)
            .studentCreationDate(UPDATED_STUDENT_CREATION_DATE)
            .studentModifiedBy(UPDATED_STUDENT_MODIFIED_BY)
            .studentModifiedDate(UPDATED_STUDENT_MODIFIED_DATE);

        restStudentMockMvc.perform(put("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStudent)))
            .andExpect(status().isOk());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
        Student testStudent = studentList.get(studentList.size() - 1);
        assertThat(testStudent.getStudentFirstName()).isEqualTo(UPDATED_STUDENT_FIRST_NAME);
        assertThat(testStudent.getStudentLastName()).isEqualTo(UPDATED_STUDENT_LAST_NAME);
        assertThat(testStudent.getStudentEmail()).isEqualTo(UPDATED_STUDENT_EMAIL);
        assertThat(testStudent.getStudentPassword()).isEqualTo(UPDATED_STUDENT_PASSWORD);
        assertThat(testStudent.getStudentCredits()).isEqualTo(UPDATED_STUDENT_CREDITS);
        assertThat(testStudent.getStudentCreatedBy()).isEqualTo(UPDATED_STUDENT_CREATED_BY);
        assertThat(testStudent.getStudentCreationDate()).isEqualTo(UPDATED_STUDENT_CREATION_DATE);
        assertThat(testStudent.getStudentModifiedBy()).isEqualTo(UPDATED_STUDENT_MODIFIED_BY);
        assertThat(testStudent.getStudentModifiedDate()).isEqualTo(UPDATED_STUDENT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingStudent() throws Exception {
        int databaseSizeBeforeUpdate = studentRepository.findAll().size();

        // Create the Student

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentMockMvc.perform(put("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        int databaseSizeBeforeDelete = studentRepository.findAll().size();

        // Delete the student
        restStudentMockMvc.perform(delete("/api/students/{id}", student.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Student.class);
        Student student1 = new Student();
        student1.setId(1L);
        Student student2 = new Student();
        student2.setId(student1.getId());
        assertThat(student1).isEqualTo(student2);
        student2.setId(2L);
        assertThat(student1).isNotEqualTo(student2);
        student1.setId(null);
        assertThat(student1).isNotEqualTo(student2);
    }
}
