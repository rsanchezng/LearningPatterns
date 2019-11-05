package com.fime.web.rest;

import com.fime.LearningPatternsApp;
import com.fime.domain.Subject;
import com.fime.repository.SubjectRepository;
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

    private static final String DEFAULT_SUBJECT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_SUBJECT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SUBJECT_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_SUBJECT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_SUBJECT_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SUBJECT_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SubjectRepository subjectRepository;

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
        final SubjectResource subjectResource = new SubjectResource(subjectRepository);
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
    public void getNonExistingSubject() throws Exception {
        // Get the subject
        restSubjectMockMvc.perform(get("/api/subjects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubject() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

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
        subjectRepository.saveAndFlush(subject);

        int databaseSizeBeforeDelete = subjectRepository.findAll().size();

        // Delete the subject
        restSubjectMockMvc.perform(delete("/api/subjects/{id}", subject.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Subject> subjectList = subjectRepository.findAll();
        assertThat(subjectList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Subject.class);
        Subject subject1 = new Subject();
        subject1.setId(1L);
        Subject subject2 = new Subject();
        subject2.setId(subject1.getId());
        assertThat(subject1).isEqualTo(subject2);
        subject2.setId(2L);
        assertThat(subject1).isNotEqualTo(subject2);
        subject1.setId(null);
        assertThat(subject1).isNotEqualTo(subject2);
    }
}
