package com.fime.web.rest;

import com.fime.LearningPatternsApp;
import com.fime.domain.Activity;
import com.fime.repository.ActivityRepository;
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
 * Integration tests for the {@link ActivityResource} REST controller.
 */
@SpringBootTest(classes = LearningPatternsApp.class)
public class ActivityResourceIT {

    private static final String DEFAULT_ACTIVITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVITY_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITY_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_ACTIVITY_DURATION = 1;
    private static final Integer UPDATED_ACTIVITY_DURATION = 2;

    private static final Integer DEFAULT_ACTIVITY_UTILITY = 1;
    private static final Integer UPDATED_ACTIVITY_UTILITY = 2;

    private static final Integer DEFAULT_ACTIVITY_REQS_ID = 1;
    private static final Integer UPDATED_ACTIVITY_REQS_ID = 2;

    private static final String DEFAULT_ACTIVITY_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITY_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ACTIVITY_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTIVITY_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ACTIVITY_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITY_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ACTIVITY_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTIVITY_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ActivityRepository activityRepository;

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

    private MockMvc restActivityMockMvc;

    private Activity activity;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActivityResource activityResource = new ActivityResource(activityRepository);
        this.restActivityMockMvc = MockMvcBuilders.standaloneSetup(activityResource)
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
    public static Activity createEntity(EntityManager em) {
        Activity activity = new Activity()
            .activityName(DEFAULT_ACTIVITY_NAME)
            .activityDescription(DEFAULT_ACTIVITY_DESCRIPTION)
            .activityDuration(DEFAULT_ACTIVITY_DURATION)
            .activityUtility(DEFAULT_ACTIVITY_UTILITY)
            .activityReqsId(DEFAULT_ACTIVITY_REQS_ID)
            .activityCreatedBy(DEFAULT_ACTIVITY_CREATED_BY)
            .activityCreationDate(DEFAULT_ACTIVITY_CREATION_DATE)
            .activityModifiedBy(DEFAULT_ACTIVITY_MODIFIED_BY)
            .activityModifiedDate(DEFAULT_ACTIVITY_MODIFIED_DATE);
        return activity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Activity createUpdatedEntity(EntityManager em) {
        Activity activity = new Activity()
            .activityName(UPDATED_ACTIVITY_NAME)
            .activityDescription(UPDATED_ACTIVITY_DESCRIPTION)
            .activityDuration(UPDATED_ACTIVITY_DURATION)
            .activityUtility(UPDATED_ACTIVITY_UTILITY)
            .activityReqsId(UPDATED_ACTIVITY_REQS_ID)
            .activityCreatedBy(UPDATED_ACTIVITY_CREATED_BY)
            .activityCreationDate(UPDATED_ACTIVITY_CREATION_DATE)
            .activityModifiedBy(UPDATED_ACTIVITY_MODIFIED_BY)
            .activityModifiedDate(UPDATED_ACTIVITY_MODIFIED_DATE);
        return activity;
    }

    @BeforeEach
    public void initTest() {
        activity = createEntity(em);
    }

    @Test
    @Transactional
    public void createActivity() throws Exception {
        int databaseSizeBeforeCreate = activityRepository.findAll().size();

        // Create the Activity
        restActivityMockMvc.perform(post("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activity)))
            .andExpect(status().isCreated());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeCreate + 1);
        Activity testActivity = activityList.get(activityList.size() - 1);
        assertThat(testActivity.getActivityName()).isEqualTo(DEFAULT_ACTIVITY_NAME);
        assertThat(testActivity.getActivityDescription()).isEqualTo(DEFAULT_ACTIVITY_DESCRIPTION);
        assertThat(testActivity.getActivityDuration()).isEqualTo(DEFAULT_ACTIVITY_DURATION);
        assertThat(testActivity.getActivityUtility()).isEqualTo(DEFAULT_ACTIVITY_UTILITY);
        assertThat(testActivity.getActivityReqsId()).isEqualTo(DEFAULT_ACTIVITY_REQS_ID);
        assertThat(testActivity.getActivityCreatedBy()).isEqualTo(DEFAULT_ACTIVITY_CREATED_BY);
        assertThat(testActivity.getActivityCreationDate()).isEqualTo(DEFAULT_ACTIVITY_CREATION_DATE);
        assertThat(testActivity.getActivityModifiedBy()).isEqualTo(DEFAULT_ACTIVITY_MODIFIED_BY);
        assertThat(testActivity.getActivityModifiedDate()).isEqualTo(DEFAULT_ACTIVITY_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createActivityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = activityRepository.findAll().size();

        // Create the Activity with an existing ID
        activity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActivityMockMvc.perform(post("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activity)))
            .andExpect(status().isBadRequest());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllActivities() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList
        restActivityMockMvc.perform(get("/api/activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activity.getId().intValue())))
            .andExpect(jsonPath("$.[*].activityName").value(hasItem(DEFAULT_ACTIVITY_NAME)))
            .andExpect(jsonPath("$.[*].activityDescription").value(hasItem(DEFAULT_ACTIVITY_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].activityDuration").value(hasItem(DEFAULT_ACTIVITY_DURATION)))
            .andExpect(jsonPath("$.[*].activityUtility").value(hasItem(DEFAULT_ACTIVITY_UTILITY)))
            .andExpect(jsonPath("$.[*].activityReqsId").value(hasItem(DEFAULT_ACTIVITY_REQS_ID)))
            .andExpect(jsonPath("$.[*].activityCreatedBy").value(hasItem(DEFAULT_ACTIVITY_CREATED_BY)))
            .andExpect(jsonPath("$.[*].activityCreationDate").value(hasItem(DEFAULT_ACTIVITY_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].activityModifiedBy").value(hasItem(DEFAULT_ACTIVITY_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].activityModifiedDate").value(hasItem(DEFAULT_ACTIVITY_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getActivity() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get the activity
        restActivityMockMvc.perform(get("/api/activities/{id}", activity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(activity.getId().intValue()))
            .andExpect(jsonPath("$.activityName").value(DEFAULT_ACTIVITY_NAME))
            .andExpect(jsonPath("$.activityDescription").value(DEFAULT_ACTIVITY_DESCRIPTION))
            .andExpect(jsonPath("$.activityDuration").value(DEFAULT_ACTIVITY_DURATION))
            .andExpect(jsonPath("$.activityUtility").value(DEFAULT_ACTIVITY_UTILITY))
            .andExpect(jsonPath("$.activityReqsId").value(DEFAULT_ACTIVITY_REQS_ID))
            .andExpect(jsonPath("$.activityCreatedBy").value(DEFAULT_ACTIVITY_CREATED_BY))
            .andExpect(jsonPath("$.activityCreationDate").value(DEFAULT_ACTIVITY_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.activityModifiedBy").value(DEFAULT_ACTIVITY_MODIFIED_BY))
            .andExpect(jsonPath("$.activityModifiedDate").value(DEFAULT_ACTIVITY_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingActivity() throws Exception {
        // Get the activity
        restActivityMockMvc.perform(get("/api/activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActivity() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        int databaseSizeBeforeUpdate = activityRepository.findAll().size();

        // Update the activity
        Activity updatedActivity = activityRepository.findById(activity.getId()).get();
        // Disconnect from session so that the updates on updatedActivity are not directly saved in db
        em.detach(updatedActivity);
        updatedActivity
            .activityName(UPDATED_ACTIVITY_NAME)
            .activityDescription(UPDATED_ACTIVITY_DESCRIPTION)
            .activityDuration(UPDATED_ACTIVITY_DURATION)
            .activityUtility(UPDATED_ACTIVITY_UTILITY)
            .activityReqsId(UPDATED_ACTIVITY_REQS_ID)
            .activityCreatedBy(UPDATED_ACTIVITY_CREATED_BY)
            .activityCreationDate(UPDATED_ACTIVITY_CREATION_DATE)
            .activityModifiedBy(UPDATED_ACTIVITY_MODIFIED_BY)
            .activityModifiedDate(UPDATED_ACTIVITY_MODIFIED_DATE);

        restActivityMockMvc.perform(put("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedActivity)))
            .andExpect(status().isOk());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
        Activity testActivity = activityList.get(activityList.size() - 1);
        assertThat(testActivity.getActivityName()).isEqualTo(UPDATED_ACTIVITY_NAME);
        assertThat(testActivity.getActivityDescription()).isEqualTo(UPDATED_ACTIVITY_DESCRIPTION);
        assertThat(testActivity.getActivityDuration()).isEqualTo(UPDATED_ACTIVITY_DURATION);
        assertThat(testActivity.getActivityUtility()).isEqualTo(UPDATED_ACTIVITY_UTILITY);
        assertThat(testActivity.getActivityReqsId()).isEqualTo(UPDATED_ACTIVITY_REQS_ID);
        assertThat(testActivity.getActivityCreatedBy()).isEqualTo(UPDATED_ACTIVITY_CREATED_BY);
        assertThat(testActivity.getActivityCreationDate()).isEqualTo(UPDATED_ACTIVITY_CREATION_DATE);
        assertThat(testActivity.getActivityModifiedBy()).isEqualTo(UPDATED_ACTIVITY_MODIFIED_BY);
        assertThat(testActivity.getActivityModifiedDate()).isEqualTo(UPDATED_ACTIVITY_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingActivity() throws Exception {
        int databaseSizeBeforeUpdate = activityRepository.findAll().size();

        // Create the Activity

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivityMockMvc.perform(put("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activity)))
            .andExpect(status().isBadRequest());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteActivity() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        int databaseSizeBeforeDelete = activityRepository.findAll().size();

        // Delete the activity
        restActivityMockMvc.perform(delete("/api/activities/{id}", activity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Activity.class);
        Activity activity1 = new Activity();
        activity1.setId(1L);
        Activity activity2 = new Activity();
        activity2.setId(activity1.getId());
        assertThat(activity1).isEqualTo(activity2);
        activity2.setId(2L);
        assertThat(activity1).isNotEqualTo(activity2);
        activity1.setId(null);
        assertThat(activity1).isNotEqualTo(activity2);
    }
}
