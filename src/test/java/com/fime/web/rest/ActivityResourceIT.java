package com.fime.web.rest;

import com.fime.LearningPatternsApp;
import com.fime.domain.Activity;
import com.fime.domain.Subtheme;
import com.fime.repository.ActivityRepository;
import com.fime.service.ActivityService;
import com.fime.web.rest.errors.ExceptionTranslator;
import com.fime.service.dto.ActivityCriteria;
import com.fime.service.ActivityQueryService;

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
    private static final Integer SMALLER_ACTIVITY_DURATION = 1 - 1;

    private static final Integer DEFAULT_ACTIVITY_UTILITY = 1;
    private static final Integer UPDATED_ACTIVITY_UTILITY = 2;
    private static final Integer SMALLER_ACTIVITY_UTILITY = 1 - 1;

    private static final Integer DEFAULT_ACTIVITY_REQS_ID = 1;
    private static final Integer UPDATED_ACTIVITY_REQS_ID = 2;
    private static final Integer SMALLER_ACTIVITY_REQS_ID = 1 - 1;

    private static final String DEFAULT_ACTIVITY_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITY_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ACTIVITY_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTIVITY_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_ACTIVITY_CREATION_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_ACTIVITY_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITY_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ACTIVITY_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTIVITY_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_ACTIVITY_MODIFIED_DATE = LocalDate.ofEpochDay(-1L);

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityQueryService activityQueryService;

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
        final ActivityResource activityResource = new ActivityResource(activityService, activityQueryService);
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
    public void getActivitiesByIdFiltering() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        Long id = activity.getId();

        defaultActivityShouldBeFound("id.equals=" + id);
        defaultActivityShouldNotBeFound("id.notEquals=" + id);

        defaultActivityShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultActivityShouldNotBeFound("id.greaterThan=" + id);

        defaultActivityShouldBeFound("id.lessThanOrEqual=" + id);
        defaultActivityShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllActivitiesByActivityNameIsEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityName equals to DEFAULT_ACTIVITY_NAME
        defaultActivityShouldBeFound("activityName.equals=" + DEFAULT_ACTIVITY_NAME);

        // Get all the activityList where activityName equals to UPDATED_ACTIVITY_NAME
        defaultActivityShouldNotBeFound("activityName.equals=" + UPDATED_ACTIVITY_NAME);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityName not equals to DEFAULT_ACTIVITY_NAME
        defaultActivityShouldNotBeFound("activityName.notEquals=" + DEFAULT_ACTIVITY_NAME);

        // Get all the activityList where activityName not equals to UPDATED_ACTIVITY_NAME
        defaultActivityShouldBeFound("activityName.notEquals=" + UPDATED_ACTIVITY_NAME);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityNameIsInShouldWork() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityName in DEFAULT_ACTIVITY_NAME or UPDATED_ACTIVITY_NAME
        defaultActivityShouldBeFound("activityName.in=" + DEFAULT_ACTIVITY_NAME + "," + UPDATED_ACTIVITY_NAME);

        // Get all the activityList where activityName equals to UPDATED_ACTIVITY_NAME
        defaultActivityShouldNotBeFound("activityName.in=" + UPDATED_ACTIVITY_NAME);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityName is not null
        defaultActivityShouldBeFound("activityName.specified=true");

        // Get all the activityList where activityName is null
        defaultActivityShouldNotBeFound("activityName.specified=false");
    }
                @Test
    @Transactional
    public void getAllActivitiesByActivityNameContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityName contains DEFAULT_ACTIVITY_NAME
        defaultActivityShouldBeFound("activityName.contains=" + DEFAULT_ACTIVITY_NAME);

        // Get all the activityList where activityName contains UPDATED_ACTIVITY_NAME
        defaultActivityShouldNotBeFound("activityName.contains=" + UPDATED_ACTIVITY_NAME);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityNameNotContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityName does not contain DEFAULT_ACTIVITY_NAME
        defaultActivityShouldNotBeFound("activityName.doesNotContain=" + DEFAULT_ACTIVITY_NAME);

        // Get all the activityList where activityName does not contain UPDATED_ACTIVITY_NAME
        defaultActivityShouldBeFound("activityName.doesNotContain=" + UPDATED_ACTIVITY_NAME);
    }


    @Test
    @Transactional
    public void getAllActivitiesByActivityDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityDescription equals to DEFAULT_ACTIVITY_DESCRIPTION
        defaultActivityShouldBeFound("activityDescription.equals=" + DEFAULT_ACTIVITY_DESCRIPTION);

        // Get all the activityList where activityDescription equals to UPDATED_ACTIVITY_DESCRIPTION
        defaultActivityShouldNotBeFound("activityDescription.equals=" + UPDATED_ACTIVITY_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityDescription not equals to DEFAULT_ACTIVITY_DESCRIPTION
        defaultActivityShouldNotBeFound("activityDescription.notEquals=" + DEFAULT_ACTIVITY_DESCRIPTION);

        // Get all the activityList where activityDescription not equals to UPDATED_ACTIVITY_DESCRIPTION
        defaultActivityShouldBeFound("activityDescription.notEquals=" + UPDATED_ACTIVITY_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityDescription in DEFAULT_ACTIVITY_DESCRIPTION or UPDATED_ACTIVITY_DESCRIPTION
        defaultActivityShouldBeFound("activityDescription.in=" + DEFAULT_ACTIVITY_DESCRIPTION + "," + UPDATED_ACTIVITY_DESCRIPTION);

        // Get all the activityList where activityDescription equals to UPDATED_ACTIVITY_DESCRIPTION
        defaultActivityShouldNotBeFound("activityDescription.in=" + UPDATED_ACTIVITY_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityDescription is not null
        defaultActivityShouldBeFound("activityDescription.specified=true");

        // Get all the activityList where activityDescription is null
        defaultActivityShouldNotBeFound("activityDescription.specified=false");
    }
                @Test
    @Transactional
    public void getAllActivitiesByActivityDescriptionContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityDescription contains DEFAULT_ACTIVITY_DESCRIPTION
        defaultActivityShouldBeFound("activityDescription.contains=" + DEFAULT_ACTIVITY_DESCRIPTION);

        // Get all the activityList where activityDescription contains UPDATED_ACTIVITY_DESCRIPTION
        defaultActivityShouldNotBeFound("activityDescription.contains=" + UPDATED_ACTIVITY_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityDescription does not contain DEFAULT_ACTIVITY_DESCRIPTION
        defaultActivityShouldNotBeFound("activityDescription.doesNotContain=" + DEFAULT_ACTIVITY_DESCRIPTION);

        // Get all the activityList where activityDescription does not contain UPDATED_ACTIVITY_DESCRIPTION
        defaultActivityShouldBeFound("activityDescription.doesNotContain=" + UPDATED_ACTIVITY_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllActivitiesByActivityDurationIsEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityDuration equals to DEFAULT_ACTIVITY_DURATION
        defaultActivityShouldBeFound("activityDuration.equals=" + DEFAULT_ACTIVITY_DURATION);

        // Get all the activityList where activityDuration equals to UPDATED_ACTIVITY_DURATION
        defaultActivityShouldNotBeFound("activityDuration.equals=" + UPDATED_ACTIVITY_DURATION);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityDurationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityDuration not equals to DEFAULT_ACTIVITY_DURATION
        defaultActivityShouldNotBeFound("activityDuration.notEquals=" + DEFAULT_ACTIVITY_DURATION);

        // Get all the activityList where activityDuration not equals to UPDATED_ACTIVITY_DURATION
        defaultActivityShouldBeFound("activityDuration.notEquals=" + UPDATED_ACTIVITY_DURATION);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityDurationIsInShouldWork() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityDuration in DEFAULT_ACTIVITY_DURATION or UPDATED_ACTIVITY_DURATION
        defaultActivityShouldBeFound("activityDuration.in=" + DEFAULT_ACTIVITY_DURATION + "," + UPDATED_ACTIVITY_DURATION);

        // Get all the activityList where activityDuration equals to UPDATED_ACTIVITY_DURATION
        defaultActivityShouldNotBeFound("activityDuration.in=" + UPDATED_ACTIVITY_DURATION);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityDurationIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityDuration is not null
        defaultActivityShouldBeFound("activityDuration.specified=true");

        // Get all the activityList where activityDuration is null
        defaultActivityShouldNotBeFound("activityDuration.specified=false");
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityDurationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityDuration is greater than or equal to DEFAULT_ACTIVITY_DURATION
        defaultActivityShouldBeFound("activityDuration.greaterThanOrEqual=" + DEFAULT_ACTIVITY_DURATION);

        // Get all the activityList where activityDuration is greater than or equal to UPDATED_ACTIVITY_DURATION
        defaultActivityShouldNotBeFound("activityDuration.greaterThanOrEqual=" + UPDATED_ACTIVITY_DURATION);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityDurationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityDuration is less than or equal to DEFAULT_ACTIVITY_DURATION
        defaultActivityShouldBeFound("activityDuration.lessThanOrEqual=" + DEFAULT_ACTIVITY_DURATION);

        // Get all the activityList where activityDuration is less than or equal to SMALLER_ACTIVITY_DURATION
        defaultActivityShouldNotBeFound("activityDuration.lessThanOrEqual=" + SMALLER_ACTIVITY_DURATION);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityDurationIsLessThanSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityDuration is less than DEFAULT_ACTIVITY_DURATION
        defaultActivityShouldNotBeFound("activityDuration.lessThan=" + DEFAULT_ACTIVITY_DURATION);

        // Get all the activityList where activityDuration is less than UPDATED_ACTIVITY_DURATION
        defaultActivityShouldBeFound("activityDuration.lessThan=" + UPDATED_ACTIVITY_DURATION);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityDurationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityDuration is greater than DEFAULT_ACTIVITY_DURATION
        defaultActivityShouldNotBeFound("activityDuration.greaterThan=" + DEFAULT_ACTIVITY_DURATION);

        // Get all the activityList where activityDuration is greater than SMALLER_ACTIVITY_DURATION
        defaultActivityShouldBeFound("activityDuration.greaterThan=" + SMALLER_ACTIVITY_DURATION);
    }


    @Test
    @Transactional
    public void getAllActivitiesByActivityUtilityIsEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityUtility equals to DEFAULT_ACTIVITY_UTILITY
        defaultActivityShouldBeFound("activityUtility.equals=" + DEFAULT_ACTIVITY_UTILITY);

        // Get all the activityList where activityUtility equals to UPDATED_ACTIVITY_UTILITY
        defaultActivityShouldNotBeFound("activityUtility.equals=" + UPDATED_ACTIVITY_UTILITY);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityUtilityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityUtility not equals to DEFAULT_ACTIVITY_UTILITY
        defaultActivityShouldNotBeFound("activityUtility.notEquals=" + DEFAULT_ACTIVITY_UTILITY);

        // Get all the activityList where activityUtility not equals to UPDATED_ACTIVITY_UTILITY
        defaultActivityShouldBeFound("activityUtility.notEquals=" + UPDATED_ACTIVITY_UTILITY);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityUtilityIsInShouldWork() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityUtility in DEFAULT_ACTIVITY_UTILITY or UPDATED_ACTIVITY_UTILITY
        defaultActivityShouldBeFound("activityUtility.in=" + DEFAULT_ACTIVITY_UTILITY + "," + UPDATED_ACTIVITY_UTILITY);

        // Get all the activityList where activityUtility equals to UPDATED_ACTIVITY_UTILITY
        defaultActivityShouldNotBeFound("activityUtility.in=" + UPDATED_ACTIVITY_UTILITY);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityUtilityIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityUtility is not null
        defaultActivityShouldBeFound("activityUtility.specified=true");

        // Get all the activityList where activityUtility is null
        defaultActivityShouldNotBeFound("activityUtility.specified=false");
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityUtilityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityUtility is greater than or equal to DEFAULT_ACTIVITY_UTILITY
        defaultActivityShouldBeFound("activityUtility.greaterThanOrEqual=" + DEFAULT_ACTIVITY_UTILITY);

        // Get all the activityList where activityUtility is greater than or equal to UPDATED_ACTIVITY_UTILITY
        defaultActivityShouldNotBeFound("activityUtility.greaterThanOrEqual=" + UPDATED_ACTIVITY_UTILITY);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityUtilityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityUtility is less than or equal to DEFAULT_ACTIVITY_UTILITY
        defaultActivityShouldBeFound("activityUtility.lessThanOrEqual=" + DEFAULT_ACTIVITY_UTILITY);

        // Get all the activityList where activityUtility is less than or equal to SMALLER_ACTIVITY_UTILITY
        defaultActivityShouldNotBeFound("activityUtility.lessThanOrEqual=" + SMALLER_ACTIVITY_UTILITY);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityUtilityIsLessThanSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityUtility is less than DEFAULT_ACTIVITY_UTILITY
        defaultActivityShouldNotBeFound("activityUtility.lessThan=" + DEFAULT_ACTIVITY_UTILITY);

        // Get all the activityList where activityUtility is less than UPDATED_ACTIVITY_UTILITY
        defaultActivityShouldBeFound("activityUtility.lessThan=" + UPDATED_ACTIVITY_UTILITY);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityUtilityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityUtility is greater than DEFAULT_ACTIVITY_UTILITY
        defaultActivityShouldNotBeFound("activityUtility.greaterThan=" + DEFAULT_ACTIVITY_UTILITY);

        // Get all the activityList where activityUtility is greater than SMALLER_ACTIVITY_UTILITY
        defaultActivityShouldBeFound("activityUtility.greaterThan=" + SMALLER_ACTIVITY_UTILITY);
    }


    @Test
    @Transactional
    public void getAllActivitiesByActivityReqsIdIsEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityReqsId equals to DEFAULT_ACTIVITY_REQS_ID
        defaultActivityShouldBeFound("activityReqsId.equals=" + DEFAULT_ACTIVITY_REQS_ID);

        // Get all the activityList where activityReqsId equals to UPDATED_ACTIVITY_REQS_ID
        defaultActivityShouldNotBeFound("activityReqsId.equals=" + UPDATED_ACTIVITY_REQS_ID);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityReqsIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityReqsId not equals to DEFAULT_ACTIVITY_REQS_ID
        defaultActivityShouldNotBeFound("activityReqsId.notEquals=" + DEFAULT_ACTIVITY_REQS_ID);

        // Get all the activityList where activityReqsId not equals to UPDATED_ACTIVITY_REQS_ID
        defaultActivityShouldBeFound("activityReqsId.notEquals=" + UPDATED_ACTIVITY_REQS_ID);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityReqsIdIsInShouldWork() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityReqsId in DEFAULT_ACTIVITY_REQS_ID or UPDATED_ACTIVITY_REQS_ID
        defaultActivityShouldBeFound("activityReqsId.in=" + DEFAULT_ACTIVITY_REQS_ID + "," + UPDATED_ACTIVITY_REQS_ID);

        // Get all the activityList where activityReqsId equals to UPDATED_ACTIVITY_REQS_ID
        defaultActivityShouldNotBeFound("activityReqsId.in=" + UPDATED_ACTIVITY_REQS_ID);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityReqsIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityReqsId is not null
        defaultActivityShouldBeFound("activityReqsId.specified=true");

        // Get all the activityList where activityReqsId is null
        defaultActivityShouldNotBeFound("activityReqsId.specified=false");
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityReqsIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityReqsId is greater than or equal to DEFAULT_ACTIVITY_REQS_ID
        defaultActivityShouldBeFound("activityReqsId.greaterThanOrEqual=" + DEFAULT_ACTIVITY_REQS_ID);

        // Get all the activityList where activityReqsId is greater than or equal to UPDATED_ACTIVITY_REQS_ID
        defaultActivityShouldNotBeFound("activityReqsId.greaterThanOrEqual=" + UPDATED_ACTIVITY_REQS_ID);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityReqsIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityReqsId is less than or equal to DEFAULT_ACTIVITY_REQS_ID
        defaultActivityShouldBeFound("activityReqsId.lessThanOrEqual=" + DEFAULT_ACTIVITY_REQS_ID);

        // Get all the activityList where activityReqsId is less than or equal to SMALLER_ACTIVITY_REQS_ID
        defaultActivityShouldNotBeFound("activityReqsId.lessThanOrEqual=" + SMALLER_ACTIVITY_REQS_ID);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityReqsIdIsLessThanSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityReqsId is less than DEFAULT_ACTIVITY_REQS_ID
        defaultActivityShouldNotBeFound("activityReqsId.lessThan=" + DEFAULT_ACTIVITY_REQS_ID);

        // Get all the activityList where activityReqsId is less than UPDATED_ACTIVITY_REQS_ID
        defaultActivityShouldBeFound("activityReqsId.lessThan=" + UPDATED_ACTIVITY_REQS_ID);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityReqsIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityReqsId is greater than DEFAULT_ACTIVITY_REQS_ID
        defaultActivityShouldNotBeFound("activityReqsId.greaterThan=" + DEFAULT_ACTIVITY_REQS_ID);

        // Get all the activityList where activityReqsId is greater than SMALLER_ACTIVITY_REQS_ID
        defaultActivityShouldBeFound("activityReqsId.greaterThan=" + SMALLER_ACTIVITY_REQS_ID);
    }


    @Test
    @Transactional
    public void getAllActivitiesByActivityCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityCreatedBy equals to DEFAULT_ACTIVITY_CREATED_BY
        defaultActivityShouldBeFound("activityCreatedBy.equals=" + DEFAULT_ACTIVITY_CREATED_BY);

        // Get all the activityList where activityCreatedBy equals to UPDATED_ACTIVITY_CREATED_BY
        defaultActivityShouldNotBeFound("activityCreatedBy.equals=" + UPDATED_ACTIVITY_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityCreatedBy not equals to DEFAULT_ACTIVITY_CREATED_BY
        defaultActivityShouldNotBeFound("activityCreatedBy.notEquals=" + DEFAULT_ACTIVITY_CREATED_BY);

        // Get all the activityList where activityCreatedBy not equals to UPDATED_ACTIVITY_CREATED_BY
        defaultActivityShouldBeFound("activityCreatedBy.notEquals=" + UPDATED_ACTIVITY_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityCreatedBy in DEFAULT_ACTIVITY_CREATED_BY or UPDATED_ACTIVITY_CREATED_BY
        defaultActivityShouldBeFound("activityCreatedBy.in=" + DEFAULT_ACTIVITY_CREATED_BY + "," + UPDATED_ACTIVITY_CREATED_BY);

        // Get all the activityList where activityCreatedBy equals to UPDATED_ACTIVITY_CREATED_BY
        defaultActivityShouldNotBeFound("activityCreatedBy.in=" + UPDATED_ACTIVITY_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityCreatedBy is not null
        defaultActivityShouldBeFound("activityCreatedBy.specified=true");

        // Get all the activityList where activityCreatedBy is null
        defaultActivityShouldNotBeFound("activityCreatedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllActivitiesByActivityCreatedByContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityCreatedBy contains DEFAULT_ACTIVITY_CREATED_BY
        defaultActivityShouldBeFound("activityCreatedBy.contains=" + DEFAULT_ACTIVITY_CREATED_BY);

        // Get all the activityList where activityCreatedBy contains UPDATED_ACTIVITY_CREATED_BY
        defaultActivityShouldNotBeFound("activityCreatedBy.contains=" + UPDATED_ACTIVITY_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityCreatedBy does not contain DEFAULT_ACTIVITY_CREATED_BY
        defaultActivityShouldNotBeFound("activityCreatedBy.doesNotContain=" + DEFAULT_ACTIVITY_CREATED_BY);

        // Get all the activityList where activityCreatedBy does not contain UPDATED_ACTIVITY_CREATED_BY
        defaultActivityShouldBeFound("activityCreatedBy.doesNotContain=" + UPDATED_ACTIVITY_CREATED_BY);
    }


    @Test
    @Transactional
    public void getAllActivitiesByActivityCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityCreationDate equals to DEFAULT_ACTIVITY_CREATION_DATE
        defaultActivityShouldBeFound("activityCreationDate.equals=" + DEFAULT_ACTIVITY_CREATION_DATE);

        // Get all the activityList where activityCreationDate equals to UPDATED_ACTIVITY_CREATION_DATE
        defaultActivityShouldNotBeFound("activityCreationDate.equals=" + UPDATED_ACTIVITY_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityCreationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityCreationDate not equals to DEFAULT_ACTIVITY_CREATION_DATE
        defaultActivityShouldNotBeFound("activityCreationDate.notEquals=" + DEFAULT_ACTIVITY_CREATION_DATE);

        // Get all the activityList where activityCreationDate not equals to UPDATED_ACTIVITY_CREATION_DATE
        defaultActivityShouldBeFound("activityCreationDate.notEquals=" + UPDATED_ACTIVITY_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityCreationDate in DEFAULT_ACTIVITY_CREATION_DATE or UPDATED_ACTIVITY_CREATION_DATE
        defaultActivityShouldBeFound("activityCreationDate.in=" + DEFAULT_ACTIVITY_CREATION_DATE + "," + UPDATED_ACTIVITY_CREATION_DATE);

        // Get all the activityList where activityCreationDate equals to UPDATED_ACTIVITY_CREATION_DATE
        defaultActivityShouldNotBeFound("activityCreationDate.in=" + UPDATED_ACTIVITY_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityCreationDate is not null
        defaultActivityShouldBeFound("activityCreationDate.specified=true");

        // Get all the activityList where activityCreationDate is null
        defaultActivityShouldNotBeFound("activityCreationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityCreationDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityCreationDate is greater than or equal to DEFAULT_ACTIVITY_CREATION_DATE
        defaultActivityShouldBeFound("activityCreationDate.greaterThanOrEqual=" + DEFAULT_ACTIVITY_CREATION_DATE);

        // Get all the activityList where activityCreationDate is greater than or equal to UPDATED_ACTIVITY_CREATION_DATE
        defaultActivityShouldNotBeFound("activityCreationDate.greaterThanOrEqual=" + UPDATED_ACTIVITY_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityCreationDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityCreationDate is less than or equal to DEFAULT_ACTIVITY_CREATION_DATE
        defaultActivityShouldBeFound("activityCreationDate.lessThanOrEqual=" + DEFAULT_ACTIVITY_CREATION_DATE);

        // Get all the activityList where activityCreationDate is less than or equal to SMALLER_ACTIVITY_CREATION_DATE
        defaultActivityShouldNotBeFound("activityCreationDate.lessThanOrEqual=" + SMALLER_ACTIVITY_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityCreationDateIsLessThanSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityCreationDate is less than DEFAULT_ACTIVITY_CREATION_DATE
        defaultActivityShouldNotBeFound("activityCreationDate.lessThan=" + DEFAULT_ACTIVITY_CREATION_DATE);

        // Get all the activityList where activityCreationDate is less than UPDATED_ACTIVITY_CREATION_DATE
        defaultActivityShouldBeFound("activityCreationDate.lessThan=" + UPDATED_ACTIVITY_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityCreationDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityCreationDate is greater than DEFAULT_ACTIVITY_CREATION_DATE
        defaultActivityShouldNotBeFound("activityCreationDate.greaterThan=" + DEFAULT_ACTIVITY_CREATION_DATE);

        // Get all the activityList where activityCreationDate is greater than SMALLER_ACTIVITY_CREATION_DATE
        defaultActivityShouldBeFound("activityCreationDate.greaterThan=" + SMALLER_ACTIVITY_CREATION_DATE);
    }


    @Test
    @Transactional
    public void getAllActivitiesByActivityModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityModifiedBy equals to DEFAULT_ACTIVITY_MODIFIED_BY
        defaultActivityShouldBeFound("activityModifiedBy.equals=" + DEFAULT_ACTIVITY_MODIFIED_BY);

        // Get all the activityList where activityModifiedBy equals to UPDATED_ACTIVITY_MODIFIED_BY
        defaultActivityShouldNotBeFound("activityModifiedBy.equals=" + UPDATED_ACTIVITY_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityModifiedBy not equals to DEFAULT_ACTIVITY_MODIFIED_BY
        defaultActivityShouldNotBeFound("activityModifiedBy.notEquals=" + DEFAULT_ACTIVITY_MODIFIED_BY);

        // Get all the activityList where activityModifiedBy not equals to UPDATED_ACTIVITY_MODIFIED_BY
        defaultActivityShouldBeFound("activityModifiedBy.notEquals=" + UPDATED_ACTIVITY_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityModifiedBy in DEFAULT_ACTIVITY_MODIFIED_BY or UPDATED_ACTIVITY_MODIFIED_BY
        defaultActivityShouldBeFound("activityModifiedBy.in=" + DEFAULT_ACTIVITY_MODIFIED_BY + "," + UPDATED_ACTIVITY_MODIFIED_BY);

        // Get all the activityList where activityModifiedBy equals to UPDATED_ACTIVITY_MODIFIED_BY
        defaultActivityShouldNotBeFound("activityModifiedBy.in=" + UPDATED_ACTIVITY_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityModifiedBy is not null
        defaultActivityShouldBeFound("activityModifiedBy.specified=true");

        // Get all the activityList where activityModifiedBy is null
        defaultActivityShouldNotBeFound("activityModifiedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllActivitiesByActivityModifiedByContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityModifiedBy contains DEFAULT_ACTIVITY_MODIFIED_BY
        defaultActivityShouldBeFound("activityModifiedBy.contains=" + DEFAULT_ACTIVITY_MODIFIED_BY);

        // Get all the activityList where activityModifiedBy contains UPDATED_ACTIVITY_MODIFIED_BY
        defaultActivityShouldNotBeFound("activityModifiedBy.contains=" + UPDATED_ACTIVITY_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityModifiedBy does not contain DEFAULT_ACTIVITY_MODIFIED_BY
        defaultActivityShouldNotBeFound("activityModifiedBy.doesNotContain=" + DEFAULT_ACTIVITY_MODIFIED_BY);

        // Get all the activityList where activityModifiedBy does not contain UPDATED_ACTIVITY_MODIFIED_BY
        defaultActivityShouldBeFound("activityModifiedBy.doesNotContain=" + UPDATED_ACTIVITY_MODIFIED_BY);
    }


    @Test
    @Transactional
    public void getAllActivitiesByActivityModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityModifiedDate equals to DEFAULT_ACTIVITY_MODIFIED_DATE
        defaultActivityShouldBeFound("activityModifiedDate.equals=" + DEFAULT_ACTIVITY_MODIFIED_DATE);

        // Get all the activityList where activityModifiedDate equals to UPDATED_ACTIVITY_MODIFIED_DATE
        defaultActivityShouldNotBeFound("activityModifiedDate.equals=" + UPDATED_ACTIVITY_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityModifiedDate not equals to DEFAULT_ACTIVITY_MODIFIED_DATE
        defaultActivityShouldNotBeFound("activityModifiedDate.notEquals=" + DEFAULT_ACTIVITY_MODIFIED_DATE);

        // Get all the activityList where activityModifiedDate not equals to UPDATED_ACTIVITY_MODIFIED_DATE
        defaultActivityShouldBeFound("activityModifiedDate.notEquals=" + UPDATED_ACTIVITY_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityModifiedDate in DEFAULT_ACTIVITY_MODIFIED_DATE or UPDATED_ACTIVITY_MODIFIED_DATE
        defaultActivityShouldBeFound("activityModifiedDate.in=" + DEFAULT_ACTIVITY_MODIFIED_DATE + "," + UPDATED_ACTIVITY_MODIFIED_DATE);

        // Get all the activityList where activityModifiedDate equals to UPDATED_ACTIVITY_MODIFIED_DATE
        defaultActivityShouldNotBeFound("activityModifiedDate.in=" + UPDATED_ACTIVITY_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityModifiedDate is not null
        defaultActivityShouldBeFound("activityModifiedDate.specified=true");

        // Get all the activityList where activityModifiedDate is null
        defaultActivityShouldNotBeFound("activityModifiedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityModifiedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityModifiedDate is greater than or equal to DEFAULT_ACTIVITY_MODIFIED_DATE
        defaultActivityShouldBeFound("activityModifiedDate.greaterThanOrEqual=" + DEFAULT_ACTIVITY_MODIFIED_DATE);

        // Get all the activityList where activityModifiedDate is greater than or equal to UPDATED_ACTIVITY_MODIFIED_DATE
        defaultActivityShouldNotBeFound("activityModifiedDate.greaterThanOrEqual=" + UPDATED_ACTIVITY_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityModifiedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityModifiedDate is less than or equal to DEFAULT_ACTIVITY_MODIFIED_DATE
        defaultActivityShouldBeFound("activityModifiedDate.lessThanOrEqual=" + DEFAULT_ACTIVITY_MODIFIED_DATE);

        // Get all the activityList where activityModifiedDate is less than or equal to SMALLER_ACTIVITY_MODIFIED_DATE
        defaultActivityShouldNotBeFound("activityModifiedDate.lessThanOrEqual=" + SMALLER_ACTIVITY_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityModifiedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityModifiedDate is less than DEFAULT_ACTIVITY_MODIFIED_DATE
        defaultActivityShouldNotBeFound("activityModifiedDate.lessThan=" + DEFAULT_ACTIVITY_MODIFIED_DATE);

        // Get all the activityList where activityModifiedDate is less than UPDATED_ACTIVITY_MODIFIED_DATE
        defaultActivityShouldBeFound("activityModifiedDate.lessThan=" + UPDATED_ACTIVITY_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllActivitiesByActivityModifiedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList where activityModifiedDate is greater than DEFAULT_ACTIVITY_MODIFIED_DATE
        defaultActivityShouldNotBeFound("activityModifiedDate.greaterThan=" + DEFAULT_ACTIVITY_MODIFIED_DATE);

        // Get all the activityList where activityModifiedDate is greater than SMALLER_ACTIVITY_MODIFIED_DATE
        defaultActivityShouldBeFound("activityModifiedDate.greaterThan=" + SMALLER_ACTIVITY_MODIFIED_DATE);
    }


    @Test
    @Transactional
    public void getAllActivitiesBySubthemeIsEqualToSomething() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);
        Subtheme subtheme = SubthemeResourceIT.createEntity(em);
        em.persist(subtheme);
        em.flush();
        activity.setSubtheme(subtheme);
        activityRepository.saveAndFlush(activity);
        Long subthemeId = subtheme.getId();

        // Get all the activityList where subtheme equals to subthemeId
        defaultActivityShouldBeFound("subthemeId.equals=" + subthemeId);

        // Get all the activityList where subtheme equals to subthemeId + 1
        defaultActivityShouldNotBeFound("subthemeId.equals=" + (subthemeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultActivityShouldBeFound(String filter) throws Exception {
        restActivityMockMvc.perform(get("/api/activities?sort=id,desc&" + filter))
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

        // Check, that the count call also returns 1
        restActivityMockMvc.perform(get("/api/activities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultActivityShouldNotBeFound(String filter) throws Exception {
        restActivityMockMvc.perform(get("/api/activities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restActivityMockMvc.perform(get("/api/activities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
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
        activityService.save(activity);

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
        activityService.save(activity);

        int databaseSizeBeforeDelete = activityRepository.findAll().size();

        // Delete the activity
        restActivityMockMvc.perform(delete("/api/activities/{id}", activity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
