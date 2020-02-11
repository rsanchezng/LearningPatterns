package com.fime.web.rest;

import com.fime.LearningPatternsApp;
import com.fime.domain.Student;
import com.fime.repository.StudentRepository;
import com.fime.service.StudentService;
import com.fime.web.rest.errors.ExceptionTranslator;
import com.fime.service.dto.StudentCriteria;
import com.fime.service.StudentQueryService;

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
    private StudentService studentService;

    @Autowired
    private StudentQueryService studentQueryService;

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
        final StudentResource studentResource = new StudentResource(studentService, studentQueryService);
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
            .andExpect(jsonPath("$.[*].studentFirstName").value(hasItem(DEFAULT_STUDENT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].studentLastName").value(hasItem(DEFAULT_STUDENT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].studentEmail").value(hasItem(DEFAULT_STUDENT_EMAIL)))
            .andExpect(jsonPath("$.[*].studentPassword").value(hasItem(DEFAULT_STUDENT_PASSWORD)))
            .andExpect(jsonPath("$.[*].studentCredits").value(hasItem(DEFAULT_STUDENT_CREDITS)))
            .andExpect(jsonPath("$.[*].studentCreatedBy").value(hasItem(DEFAULT_STUDENT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].studentCreationDate").value(hasItem(DEFAULT_STUDENT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].studentModifiedBy").value(hasItem(DEFAULT_STUDENT_MODIFIED_BY)))
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
            .andExpect(jsonPath("$.studentFirstName").value(DEFAULT_STUDENT_FIRST_NAME))
            .andExpect(jsonPath("$.studentLastName").value(DEFAULT_STUDENT_LAST_NAME))
            .andExpect(jsonPath("$.studentEmail").value(DEFAULT_STUDENT_EMAIL))
            .andExpect(jsonPath("$.studentPassword").value(DEFAULT_STUDENT_PASSWORD))
            .andExpect(jsonPath("$.studentCredits").value(DEFAULT_STUDENT_CREDITS))
            .andExpect(jsonPath("$.studentCreatedBy").value(DEFAULT_STUDENT_CREATED_BY))
            .andExpect(jsonPath("$.studentCreationDate").value(DEFAULT_STUDENT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.studentModifiedBy").value(DEFAULT_STUDENT_MODIFIED_BY))
            .andExpect(jsonPath("$.studentModifiedDate").value(DEFAULT_STUDENT_MODIFIED_DATE.toString()));
    }


    @Test
    @Transactional
    public void getStudentsByIdFiltering() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        Long id = student.getId();

        defaultStudentShouldBeFound("id.equals=" + id);
        defaultStudentShouldNotBeFound("id.notEquals=" + id);

        defaultStudentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStudentShouldNotBeFound("id.greaterThan=" + id);

        defaultStudentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStudentShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllStudentsByStudentFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentFirstName equals to DEFAULT_STUDENT_FIRST_NAME
        defaultStudentShouldBeFound("studentFirstName.equals=" + DEFAULT_STUDENT_FIRST_NAME);

        // Get all the studentList where studentFirstName equals to UPDATED_STUDENT_FIRST_NAME
        defaultStudentShouldNotBeFound("studentFirstName.equals=" + UPDATED_STUDENT_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentFirstNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentFirstName not equals to DEFAULT_STUDENT_FIRST_NAME
        defaultStudentShouldNotBeFound("studentFirstName.notEquals=" + DEFAULT_STUDENT_FIRST_NAME);

        // Get all the studentList where studentFirstName not equals to UPDATED_STUDENT_FIRST_NAME
        defaultStudentShouldBeFound("studentFirstName.notEquals=" + UPDATED_STUDENT_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentFirstName in DEFAULT_STUDENT_FIRST_NAME or UPDATED_STUDENT_FIRST_NAME
        defaultStudentShouldBeFound("studentFirstName.in=" + DEFAULT_STUDENT_FIRST_NAME + "," + UPDATED_STUDENT_FIRST_NAME);

        // Get all the studentList where studentFirstName equals to UPDATED_STUDENT_FIRST_NAME
        defaultStudentShouldNotBeFound("studentFirstName.in=" + UPDATED_STUDENT_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentFirstName is not null
        defaultStudentShouldBeFound("studentFirstName.specified=true");

        // Get all the studentList where studentFirstName is null
        defaultStudentShouldNotBeFound("studentFirstName.specified=false");
    }
                @Test
    @Transactional
    public void getAllStudentsByStudentFirstNameContainsSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentFirstName contains DEFAULT_STUDENT_FIRST_NAME
        defaultStudentShouldBeFound("studentFirstName.contains=" + DEFAULT_STUDENT_FIRST_NAME);

        // Get all the studentList where studentFirstName contains UPDATED_STUDENT_FIRST_NAME
        defaultStudentShouldNotBeFound("studentFirstName.contains=" + UPDATED_STUDENT_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentFirstName does not contain DEFAULT_STUDENT_FIRST_NAME
        defaultStudentShouldNotBeFound("studentFirstName.doesNotContain=" + DEFAULT_STUDENT_FIRST_NAME);

        // Get all the studentList where studentFirstName does not contain UPDATED_STUDENT_FIRST_NAME
        defaultStudentShouldBeFound("studentFirstName.doesNotContain=" + UPDATED_STUDENT_FIRST_NAME);
    }


    @Test
    @Transactional
    public void getAllStudentsByStudentLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentLastName equals to DEFAULT_STUDENT_LAST_NAME
        defaultStudentShouldBeFound("studentLastName.equals=" + DEFAULT_STUDENT_LAST_NAME);

        // Get all the studentList where studentLastName equals to UPDATED_STUDENT_LAST_NAME
        defaultStudentShouldNotBeFound("studentLastName.equals=" + UPDATED_STUDENT_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentLastNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentLastName not equals to DEFAULT_STUDENT_LAST_NAME
        defaultStudentShouldNotBeFound("studentLastName.notEquals=" + DEFAULT_STUDENT_LAST_NAME);

        // Get all the studentList where studentLastName not equals to UPDATED_STUDENT_LAST_NAME
        defaultStudentShouldBeFound("studentLastName.notEquals=" + UPDATED_STUDENT_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentLastName in DEFAULT_STUDENT_LAST_NAME or UPDATED_STUDENT_LAST_NAME
        defaultStudentShouldBeFound("studentLastName.in=" + DEFAULT_STUDENT_LAST_NAME + "," + UPDATED_STUDENT_LAST_NAME);

        // Get all the studentList where studentLastName equals to UPDATED_STUDENT_LAST_NAME
        defaultStudentShouldNotBeFound("studentLastName.in=" + UPDATED_STUDENT_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentLastName is not null
        defaultStudentShouldBeFound("studentLastName.specified=true");

        // Get all the studentList where studentLastName is null
        defaultStudentShouldNotBeFound("studentLastName.specified=false");
    }
                @Test
    @Transactional
    public void getAllStudentsByStudentLastNameContainsSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentLastName contains DEFAULT_STUDENT_LAST_NAME
        defaultStudentShouldBeFound("studentLastName.contains=" + DEFAULT_STUDENT_LAST_NAME);

        // Get all the studentList where studentLastName contains UPDATED_STUDENT_LAST_NAME
        defaultStudentShouldNotBeFound("studentLastName.contains=" + UPDATED_STUDENT_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentLastName does not contain DEFAULT_STUDENT_LAST_NAME
        defaultStudentShouldNotBeFound("studentLastName.doesNotContain=" + DEFAULT_STUDENT_LAST_NAME);

        // Get all the studentList where studentLastName does not contain UPDATED_STUDENT_LAST_NAME
        defaultStudentShouldBeFound("studentLastName.doesNotContain=" + UPDATED_STUDENT_LAST_NAME);
    }


    @Test
    @Transactional
    public void getAllStudentsByStudentEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentEmail equals to DEFAULT_STUDENT_EMAIL
        defaultStudentShouldBeFound("studentEmail.equals=" + DEFAULT_STUDENT_EMAIL);

        // Get all the studentList where studentEmail equals to UPDATED_STUDENT_EMAIL
        defaultStudentShouldNotBeFound("studentEmail.equals=" + UPDATED_STUDENT_EMAIL);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentEmail not equals to DEFAULT_STUDENT_EMAIL
        defaultStudentShouldNotBeFound("studentEmail.notEquals=" + DEFAULT_STUDENT_EMAIL);

        // Get all the studentList where studentEmail not equals to UPDATED_STUDENT_EMAIL
        defaultStudentShouldBeFound("studentEmail.notEquals=" + UPDATED_STUDENT_EMAIL);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentEmailIsInShouldWork() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentEmail in DEFAULT_STUDENT_EMAIL or UPDATED_STUDENT_EMAIL
        defaultStudentShouldBeFound("studentEmail.in=" + DEFAULT_STUDENT_EMAIL + "," + UPDATED_STUDENT_EMAIL);

        // Get all the studentList where studentEmail equals to UPDATED_STUDENT_EMAIL
        defaultStudentShouldNotBeFound("studentEmail.in=" + UPDATED_STUDENT_EMAIL);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentEmail is not null
        defaultStudentShouldBeFound("studentEmail.specified=true");

        // Get all the studentList where studentEmail is null
        defaultStudentShouldNotBeFound("studentEmail.specified=false");
    }
                @Test
    @Transactional
    public void getAllStudentsByStudentEmailContainsSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentEmail contains DEFAULT_STUDENT_EMAIL
        defaultStudentShouldBeFound("studentEmail.contains=" + DEFAULT_STUDENT_EMAIL);

        // Get all the studentList where studentEmail contains UPDATED_STUDENT_EMAIL
        defaultStudentShouldNotBeFound("studentEmail.contains=" + UPDATED_STUDENT_EMAIL);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentEmailNotContainsSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentEmail does not contain DEFAULT_STUDENT_EMAIL
        defaultStudentShouldNotBeFound("studentEmail.doesNotContain=" + DEFAULT_STUDENT_EMAIL);

        // Get all the studentList where studentEmail does not contain UPDATED_STUDENT_EMAIL
        defaultStudentShouldBeFound("studentEmail.doesNotContain=" + UPDATED_STUDENT_EMAIL);
    }


    @Test
    @Transactional
    public void getAllStudentsByStudentPasswordIsEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentPassword equals to DEFAULT_STUDENT_PASSWORD
        defaultStudentShouldBeFound("studentPassword.equals=" + DEFAULT_STUDENT_PASSWORD);

        // Get all the studentList where studentPassword equals to UPDATED_STUDENT_PASSWORD
        defaultStudentShouldNotBeFound("studentPassword.equals=" + UPDATED_STUDENT_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentPasswordIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentPassword not equals to DEFAULT_STUDENT_PASSWORD
        defaultStudentShouldNotBeFound("studentPassword.notEquals=" + DEFAULT_STUDENT_PASSWORD);

        // Get all the studentList where studentPassword not equals to UPDATED_STUDENT_PASSWORD
        defaultStudentShouldBeFound("studentPassword.notEquals=" + UPDATED_STUDENT_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentPasswordIsInShouldWork() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentPassword in DEFAULT_STUDENT_PASSWORD or UPDATED_STUDENT_PASSWORD
        defaultStudentShouldBeFound("studentPassword.in=" + DEFAULT_STUDENT_PASSWORD + "," + UPDATED_STUDENT_PASSWORD);

        // Get all the studentList where studentPassword equals to UPDATED_STUDENT_PASSWORD
        defaultStudentShouldNotBeFound("studentPassword.in=" + UPDATED_STUDENT_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentPasswordIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentPassword is not null
        defaultStudentShouldBeFound("studentPassword.specified=true");

        // Get all the studentList where studentPassword is null
        defaultStudentShouldNotBeFound("studentPassword.specified=false");
    }
                @Test
    @Transactional
    public void getAllStudentsByStudentPasswordContainsSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentPassword contains DEFAULT_STUDENT_PASSWORD
        defaultStudentShouldBeFound("studentPassword.contains=" + DEFAULT_STUDENT_PASSWORD);

        // Get all the studentList where studentPassword contains UPDATED_STUDENT_PASSWORD
        defaultStudentShouldNotBeFound("studentPassword.contains=" + UPDATED_STUDENT_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentPasswordNotContainsSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentPassword does not contain DEFAULT_STUDENT_PASSWORD
        defaultStudentShouldNotBeFound("studentPassword.doesNotContain=" + DEFAULT_STUDENT_PASSWORD);

        // Get all the studentList where studentPassword does not contain UPDATED_STUDENT_PASSWORD
        defaultStudentShouldBeFound("studentPassword.doesNotContain=" + UPDATED_STUDENT_PASSWORD);
    }


    @Test
    @Transactional
    public void getAllStudentsByStudentCreditsIsEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentCredits equals to DEFAULT_STUDENT_CREDITS
        defaultStudentShouldBeFound("studentCredits.equals=" + DEFAULT_STUDENT_CREDITS);

        // Get all the studentList where studentCredits equals to UPDATED_STUDENT_CREDITS
        defaultStudentShouldNotBeFound("studentCredits.equals=" + UPDATED_STUDENT_CREDITS);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentCreditsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentCredits not equals to DEFAULT_STUDENT_CREDITS
        defaultStudentShouldNotBeFound("studentCredits.notEquals=" + DEFAULT_STUDENT_CREDITS);

        // Get all the studentList where studentCredits not equals to UPDATED_STUDENT_CREDITS
        defaultStudentShouldBeFound("studentCredits.notEquals=" + UPDATED_STUDENT_CREDITS);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentCreditsIsInShouldWork() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentCredits in DEFAULT_STUDENT_CREDITS or UPDATED_STUDENT_CREDITS
        defaultStudentShouldBeFound("studentCredits.in=" + DEFAULT_STUDENT_CREDITS + "," + UPDATED_STUDENT_CREDITS);

        // Get all the studentList where studentCredits equals to UPDATED_STUDENT_CREDITS
        defaultStudentShouldNotBeFound("studentCredits.in=" + UPDATED_STUDENT_CREDITS);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentCreditsIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentCredits is not null
        defaultStudentShouldBeFound("studentCredits.specified=true");

        // Get all the studentList where studentCredits is null
        defaultStudentShouldNotBeFound("studentCredits.specified=false");
    }
                @Test
    @Transactional
    public void getAllStudentsByStudentCreditsContainsSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentCredits contains DEFAULT_STUDENT_CREDITS
        defaultStudentShouldBeFound("studentCredits.contains=" + DEFAULT_STUDENT_CREDITS);

        // Get all the studentList where studentCredits contains UPDATED_STUDENT_CREDITS
        defaultStudentShouldNotBeFound("studentCredits.contains=" + UPDATED_STUDENT_CREDITS);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentCreditsNotContainsSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentCredits does not contain DEFAULT_STUDENT_CREDITS
        defaultStudentShouldNotBeFound("studentCredits.doesNotContain=" + DEFAULT_STUDENT_CREDITS);

        // Get all the studentList where studentCredits does not contain UPDATED_STUDENT_CREDITS
        defaultStudentShouldBeFound("studentCredits.doesNotContain=" + UPDATED_STUDENT_CREDITS);
    }


    @Test
    @Transactional
    public void getAllStudentsByStudentCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentCreatedBy equals to DEFAULT_STUDENT_CREATED_BY
        defaultStudentShouldBeFound("studentCreatedBy.equals=" + DEFAULT_STUDENT_CREATED_BY);

        // Get all the studentList where studentCreatedBy equals to UPDATED_STUDENT_CREATED_BY
        defaultStudentShouldNotBeFound("studentCreatedBy.equals=" + UPDATED_STUDENT_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentCreatedBy not equals to DEFAULT_STUDENT_CREATED_BY
        defaultStudentShouldNotBeFound("studentCreatedBy.notEquals=" + DEFAULT_STUDENT_CREATED_BY);

        // Get all the studentList where studentCreatedBy not equals to UPDATED_STUDENT_CREATED_BY
        defaultStudentShouldBeFound("studentCreatedBy.notEquals=" + UPDATED_STUDENT_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentCreatedBy in DEFAULT_STUDENT_CREATED_BY or UPDATED_STUDENT_CREATED_BY
        defaultStudentShouldBeFound("studentCreatedBy.in=" + DEFAULT_STUDENT_CREATED_BY + "," + UPDATED_STUDENT_CREATED_BY);

        // Get all the studentList where studentCreatedBy equals to UPDATED_STUDENT_CREATED_BY
        defaultStudentShouldNotBeFound("studentCreatedBy.in=" + UPDATED_STUDENT_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentCreatedBy is not null
        defaultStudentShouldBeFound("studentCreatedBy.specified=true");

        // Get all the studentList where studentCreatedBy is null
        defaultStudentShouldNotBeFound("studentCreatedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllStudentsByStudentCreatedByContainsSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentCreatedBy contains DEFAULT_STUDENT_CREATED_BY
        defaultStudentShouldBeFound("studentCreatedBy.contains=" + DEFAULT_STUDENT_CREATED_BY);

        // Get all the studentList where studentCreatedBy contains UPDATED_STUDENT_CREATED_BY
        defaultStudentShouldNotBeFound("studentCreatedBy.contains=" + UPDATED_STUDENT_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentCreatedBy does not contain DEFAULT_STUDENT_CREATED_BY
        defaultStudentShouldNotBeFound("studentCreatedBy.doesNotContain=" + DEFAULT_STUDENT_CREATED_BY);

        // Get all the studentList where studentCreatedBy does not contain UPDATED_STUDENT_CREATED_BY
        defaultStudentShouldBeFound("studentCreatedBy.doesNotContain=" + UPDATED_STUDENT_CREATED_BY);
    }


    @Test
    @Transactional
    public void getAllStudentsByStudentCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentCreationDate equals to DEFAULT_STUDENT_CREATION_DATE
        defaultStudentShouldBeFound("studentCreationDate.equals=" + DEFAULT_STUDENT_CREATION_DATE);

        // Get all the studentList where studentCreationDate equals to UPDATED_STUDENT_CREATION_DATE
        defaultStudentShouldNotBeFound("studentCreationDate.equals=" + UPDATED_STUDENT_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentCreationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentCreationDate not equals to DEFAULT_STUDENT_CREATION_DATE
        defaultStudentShouldNotBeFound("studentCreationDate.notEquals=" + DEFAULT_STUDENT_CREATION_DATE);

        // Get all the studentList where studentCreationDate not equals to UPDATED_STUDENT_CREATION_DATE
        defaultStudentShouldBeFound("studentCreationDate.notEquals=" + UPDATED_STUDENT_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentCreationDate in DEFAULT_STUDENT_CREATION_DATE or UPDATED_STUDENT_CREATION_DATE
        defaultStudentShouldBeFound("studentCreationDate.in=" + DEFAULT_STUDENT_CREATION_DATE + "," + UPDATED_STUDENT_CREATION_DATE);

        // Get all the studentList where studentCreationDate equals to UPDATED_STUDENT_CREATION_DATE
        defaultStudentShouldNotBeFound("studentCreationDate.in=" + UPDATED_STUDENT_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentCreationDate is not null
        defaultStudentShouldBeFound("studentCreationDate.specified=true");

        // Get all the studentList where studentCreationDate is null
        defaultStudentShouldNotBeFound("studentCreationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentCreationDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentCreationDate is greater than or equal to DEFAULT_STUDENT_CREATION_DATE
        defaultStudentShouldBeFound("studentCreationDate.greaterThanOrEqual=" + DEFAULT_STUDENT_CREATION_DATE);

        // Get all the studentList where studentCreationDate is greater than or equal to UPDATED_STUDENT_CREATION_DATE
        defaultStudentShouldNotBeFound("studentCreationDate.greaterThanOrEqual=" + UPDATED_STUDENT_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentCreationDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentCreationDate is less than or equal to DEFAULT_STUDENT_CREATION_DATE
        defaultStudentShouldBeFound("studentCreationDate.lessThanOrEqual=" + DEFAULT_STUDENT_CREATION_DATE);

        // Get all the studentList where studentCreationDate is less than or equal to SMALLER_STUDENT_CREATION_DATE
        defaultStudentShouldNotBeFound("studentCreationDate.lessThanOrEqual=" + SMALLER_STUDENT_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentCreationDateIsLessThanSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentCreationDate is less than DEFAULT_STUDENT_CREATION_DATE
        defaultStudentShouldNotBeFound("studentCreationDate.lessThan=" + DEFAULT_STUDENT_CREATION_DATE);

        // Get all the studentList where studentCreationDate is less than UPDATED_STUDENT_CREATION_DATE
        defaultStudentShouldBeFound("studentCreationDate.lessThan=" + UPDATED_STUDENT_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentCreationDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentCreationDate is greater than DEFAULT_STUDENT_CREATION_DATE
        defaultStudentShouldNotBeFound("studentCreationDate.greaterThan=" + DEFAULT_STUDENT_CREATION_DATE);

        // Get all the studentList where studentCreationDate is greater than SMALLER_STUDENT_CREATION_DATE
        defaultStudentShouldBeFound("studentCreationDate.greaterThan=" + SMALLER_STUDENT_CREATION_DATE);
    }


    @Test
    @Transactional
    public void getAllStudentsByStudentModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentModifiedBy equals to DEFAULT_STUDENT_MODIFIED_BY
        defaultStudentShouldBeFound("studentModifiedBy.equals=" + DEFAULT_STUDENT_MODIFIED_BY);

        // Get all the studentList where studentModifiedBy equals to UPDATED_STUDENT_MODIFIED_BY
        defaultStudentShouldNotBeFound("studentModifiedBy.equals=" + UPDATED_STUDENT_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentModifiedBy not equals to DEFAULT_STUDENT_MODIFIED_BY
        defaultStudentShouldNotBeFound("studentModifiedBy.notEquals=" + DEFAULT_STUDENT_MODIFIED_BY);

        // Get all the studentList where studentModifiedBy not equals to UPDATED_STUDENT_MODIFIED_BY
        defaultStudentShouldBeFound("studentModifiedBy.notEquals=" + UPDATED_STUDENT_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentModifiedBy in DEFAULT_STUDENT_MODIFIED_BY or UPDATED_STUDENT_MODIFIED_BY
        defaultStudentShouldBeFound("studentModifiedBy.in=" + DEFAULT_STUDENT_MODIFIED_BY + "," + UPDATED_STUDENT_MODIFIED_BY);

        // Get all the studentList where studentModifiedBy equals to UPDATED_STUDENT_MODIFIED_BY
        defaultStudentShouldNotBeFound("studentModifiedBy.in=" + UPDATED_STUDENT_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentModifiedBy is not null
        defaultStudentShouldBeFound("studentModifiedBy.specified=true");

        // Get all the studentList where studentModifiedBy is null
        defaultStudentShouldNotBeFound("studentModifiedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllStudentsByStudentModifiedByContainsSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentModifiedBy contains DEFAULT_STUDENT_MODIFIED_BY
        defaultStudentShouldBeFound("studentModifiedBy.contains=" + DEFAULT_STUDENT_MODIFIED_BY);

        // Get all the studentList where studentModifiedBy contains UPDATED_STUDENT_MODIFIED_BY
        defaultStudentShouldNotBeFound("studentModifiedBy.contains=" + UPDATED_STUDENT_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentModifiedBy does not contain DEFAULT_STUDENT_MODIFIED_BY
        defaultStudentShouldNotBeFound("studentModifiedBy.doesNotContain=" + DEFAULT_STUDENT_MODIFIED_BY);

        // Get all the studentList where studentModifiedBy does not contain UPDATED_STUDENT_MODIFIED_BY
        defaultStudentShouldBeFound("studentModifiedBy.doesNotContain=" + UPDATED_STUDENT_MODIFIED_BY);
    }


    @Test
    @Transactional
    public void getAllStudentsByStudentModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentModifiedDate equals to DEFAULT_STUDENT_MODIFIED_DATE
        defaultStudentShouldBeFound("studentModifiedDate.equals=" + DEFAULT_STUDENT_MODIFIED_DATE);

        // Get all the studentList where studentModifiedDate equals to UPDATED_STUDENT_MODIFIED_DATE
        defaultStudentShouldNotBeFound("studentModifiedDate.equals=" + UPDATED_STUDENT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentModifiedDate not equals to DEFAULT_STUDENT_MODIFIED_DATE
        defaultStudentShouldNotBeFound("studentModifiedDate.notEquals=" + DEFAULT_STUDENT_MODIFIED_DATE);

        // Get all the studentList where studentModifiedDate not equals to UPDATED_STUDENT_MODIFIED_DATE
        defaultStudentShouldBeFound("studentModifiedDate.notEquals=" + UPDATED_STUDENT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentModifiedDate in DEFAULT_STUDENT_MODIFIED_DATE or UPDATED_STUDENT_MODIFIED_DATE
        defaultStudentShouldBeFound("studentModifiedDate.in=" + DEFAULT_STUDENT_MODIFIED_DATE + "," + UPDATED_STUDENT_MODIFIED_DATE);

        // Get all the studentList where studentModifiedDate equals to UPDATED_STUDENT_MODIFIED_DATE
        defaultStudentShouldNotBeFound("studentModifiedDate.in=" + UPDATED_STUDENT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentModifiedDate is not null
        defaultStudentShouldBeFound("studentModifiedDate.specified=true");

        // Get all the studentList where studentModifiedDate is null
        defaultStudentShouldNotBeFound("studentModifiedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentModifiedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentModifiedDate is greater than or equal to DEFAULT_STUDENT_MODIFIED_DATE
        defaultStudentShouldBeFound("studentModifiedDate.greaterThanOrEqual=" + DEFAULT_STUDENT_MODIFIED_DATE);

        // Get all the studentList where studentModifiedDate is greater than or equal to UPDATED_STUDENT_MODIFIED_DATE
        defaultStudentShouldNotBeFound("studentModifiedDate.greaterThanOrEqual=" + UPDATED_STUDENT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentModifiedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentModifiedDate is less than or equal to DEFAULT_STUDENT_MODIFIED_DATE
        defaultStudentShouldBeFound("studentModifiedDate.lessThanOrEqual=" + DEFAULT_STUDENT_MODIFIED_DATE);

        // Get all the studentList where studentModifiedDate is less than or equal to SMALLER_STUDENT_MODIFIED_DATE
        defaultStudentShouldNotBeFound("studentModifiedDate.lessThanOrEqual=" + SMALLER_STUDENT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentModifiedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentModifiedDate is less than DEFAULT_STUDENT_MODIFIED_DATE
        defaultStudentShouldNotBeFound("studentModifiedDate.lessThan=" + DEFAULT_STUDENT_MODIFIED_DATE);

        // Get all the studentList where studentModifiedDate is less than UPDATED_STUDENT_MODIFIED_DATE
        defaultStudentShouldBeFound("studentModifiedDate.lessThan=" + UPDATED_STUDENT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllStudentsByStudentModifiedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where studentModifiedDate is greater than DEFAULT_STUDENT_MODIFIED_DATE
        defaultStudentShouldNotBeFound("studentModifiedDate.greaterThan=" + DEFAULT_STUDENT_MODIFIED_DATE);

        // Get all the studentList where studentModifiedDate is greater than SMALLER_STUDENT_MODIFIED_DATE
        defaultStudentShouldBeFound("studentModifiedDate.greaterThan=" + SMALLER_STUDENT_MODIFIED_DATE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStudentShouldBeFound(String filter) throws Exception {
        restStudentMockMvc.perform(get("/api/students?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(student.getId().intValue())))
            .andExpect(jsonPath("$.[*].studentFirstName").value(hasItem(DEFAULT_STUDENT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].studentLastName").value(hasItem(DEFAULT_STUDENT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].studentEmail").value(hasItem(DEFAULT_STUDENT_EMAIL)))
            .andExpect(jsonPath("$.[*].studentPassword").value(hasItem(DEFAULT_STUDENT_PASSWORD)))
            .andExpect(jsonPath("$.[*].studentCredits").value(hasItem(DEFAULT_STUDENT_CREDITS)))
            .andExpect(jsonPath("$.[*].studentCreatedBy").value(hasItem(DEFAULT_STUDENT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].studentCreationDate").value(hasItem(DEFAULT_STUDENT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].studentModifiedBy").value(hasItem(DEFAULT_STUDENT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].studentModifiedDate").value(hasItem(DEFAULT_STUDENT_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restStudentMockMvc.perform(get("/api/students/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStudentShouldNotBeFound(String filter) throws Exception {
        restStudentMockMvc.perform(get("/api/students?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStudentMockMvc.perform(get("/api/students/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
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
        studentService.save(student);

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
        studentService.save(student);

        int databaseSizeBeforeDelete = studentRepository.findAll().size();

        // Delete the student
        restStudentMockMvc.perform(delete("/api/students/{id}", student.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
