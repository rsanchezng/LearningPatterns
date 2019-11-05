package com.fime.web.rest;

import com.fime.LearningPatternsApp;
import com.fime.domain.Subtheme;
import com.fime.repository.SubthemeRepository;
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

    private static final String DEFAULT_SUBTHEME_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_SUBTHEME_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_SUBTHEME_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SUBTHEME_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SubthemeRepository subthemeRepository;

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
        final SubthemeResource subthemeResource = new SubthemeResource(subthemeRepository);
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
            .subthemeModifiedDate(DEFAULT_SUBTHEME_MODIFIED_DATE);
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
            .subthemeModifiedDate(UPDATED_SUBTHEME_MODIFIED_DATE);
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
            .andExpect(jsonPath("$.[*].subthemeModifiedDate").value(hasItem(DEFAULT_SUBTHEME_MODIFIED_DATE.toString())));
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
            .andExpect(jsonPath("$.subthemeModifiedDate").value(DEFAULT_SUBTHEME_MODIFIED_DATE.toString()));
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
        subthemeRepository.saveAndFlush(subtheme);

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
            .subthemeModifiedDate(UPDATED_SUBTHEME_MODIFIED_DATE);

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
        subthemeRepository.saveAndFlush(subtheme);

        int databaseSizeBeforeDelete = subthemeRepository.findAll().size();

        // Delete the subtheme
        restSubthemeMockMvc.perform(delete("/api/subthemes/{id}", subtheme.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Subtheme> subthemeList = subthemeRepository.findAll();
        assertThat(subthemeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Subtheme.class);
        Subtheme subtheme1 = new Subtheme();
        subtheme1.setId(1L);
        Subtheme subtheme2 = new Subtheme();
        subtheme2.setId(subtheme1.getId());
        assertThat(subtheme1).isEqualTo(subtheme2);
        subtheme2.setId(2L);
        assertThat(subtheme1).isNotEqualTo(subtheme2);
        subtheme1.setId(null);
        assertThat(subtheme1).isNotEqualTo(subtheme2);
    }
}
