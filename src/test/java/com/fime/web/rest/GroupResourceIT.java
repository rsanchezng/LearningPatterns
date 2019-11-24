package com.fime.web.rest;

import com.fime.LearningPatternsApp;
import com.fime.domain.Group;
import com.fime.domain.Subject;
import com.fime.domain.Teacher;
import com.fime.repository.GroupRepository;
import com.fime.service.GroupService;
import com.fime.web.rest.errors.ExceptionTranslator;
import com.fime.service.dto.GroupCriteria;
import com.fime.service.GroupQueryService;

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
 * Integration tests for the {@link GroupResource} REST controller.
 */
@SpringBootTest(classes = LearningPatternsApp.class)
public class GroupResourceIT {

    private static final String DEFAULT_GROUP_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_GROUP_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_GROUP_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_GROUP_CREATION_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_GROUP_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_GROUP_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_GROUP_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_GROUP_MODIFIED_DATE = LocalDate.ofEpochDay(-1L);

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupQueryService groupQueryService;

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

    private MockMvc restGroupMockMvc;

    private Group group;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GroupResource groupResource = new GroupResource(groupService, groupQueryService);
        this.restGroupMockMvc = MockMvcBuilders.standaloneSetup(groupResource)
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
    public static Group createEntity(EntityManager em) {
        Group group = new Group()
            .groupCreatedBy(DEFAULT_GROUP_CREATED_BY)
            .groupCreationDate(DEFAULT_GROUP_CREATION_DATE)
            .groupModifiedBy(DEFAULT_GROUP_MODIFIED_BY)
            .groupModifiedDate(DEFAULT_GROUP_MODIFIED_DATE);
        return group;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Group createUpdatedEntity(EntityManager em) {
        Group group = new Group()
            .groupCreatedBy(UPDATED_GROUP_CREATED_BY)
            .groupCreationDate(UPDATED_GROUP_CREATION_DATE)
            .groupModifiedBy(UPDATED_GROUP_MODIFIED_BY)
            .groupModifiedDate(UPDATED_GROUP_MODIFIED_DATE);
        return group;
    }

    @BeforeEach
    public void initTest() {
        group = createEntity(em);
    }

    @Test
    @Transactional
    public void createGroup() throws Exception {
        int databaseSizeBeforeCreate = groupRepository.findAll().size();

        // Create the Group
        restGroupMockMvc.perform(post("/api/groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(group)))
            .andExpect(status().isCreated());

        // Validate the Group in the database
        List<Group> groupList = groupRepository.findAll();
        assertThat(groupList).hasSize(databaseSizeBeforeCreate + 1);
        Group testGroup = groupList.get(groupList.size() - 1);
        assertThat(testGroup.getGroupCreatedBy()).isEqualTo(DEFAULT_GROUP_CREATED_BY);
        assertThat(testGroup.getGroupCreationDate()).isEqualTo(DEFAULT_GROUP_CREATION_DATE);
        assertThat(testGroup.getGroupModifiedBy()).isEqualTo(DEFAULT_GROUP_MODIFIED_BY);
        assertThat(testGroup.getGroupModifiedDate()).isEqualTo(DEFAULT_GROUP_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = groupRepository.findAll().size();

        // Create the Group with an existing ID
        group.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupMockMvc.perform(post("/api/groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(group)))
            .andExpect(status().isBadRequest());

        // Validate the Group in the database
        List<Group> groupList = groupRepository.findAll();
        assertThat(groupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGroups() throws Exception {
        // Initialize the database
        groupRepository.saveAndFlush(group);

        // Get all the groupList
        restGroupMockMvc.perform(get("/api/groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(group.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupCreatedBy").value(hasItem(DEFAULT_GROUP_CREATED_BY)))
            .andExpect(jsonPath("$.[*].groupCreationDate").value(hasItem(DEFAULT_GROUP_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].groupModifiedBy").value(hasItem(DEFAULT_GROUP_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].groupModifiedDate").value(hasItem(DEFAULT_GROUP_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getGroup() throws Exception {
        // Initialize the database
        groupRepository.saveAndFlush(group);

        // Get the group
        restGroupMockMvc.perform(get("/api/groups/{id}", group.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(group.getId().intValue()))
            .andExpect(jsonPath("$.groupCreatedBy").value(DEFAULT_GROUP_CREATED_BY))
            .andExpect(jsonPath("$.groupCreationDate").value(DEFAULT_GROUP_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.groupModifiedBy").value(DEFAULT_GROUP_MODIFIED_BY))
            .andExpect(jsonPath("$.groupModifiedDate").value(DEFAULT_GROUP_MODIFIED_DATE.toString()));
    }


    @Test
    @Transactional
    public void getGroupsByIdFiltering() throws Exception {
        // Initialize the database
        groupRepository.saveAndFlush(group);

        Long id = group.getId();

        defaultGroupShouldBeFound("id.equals=" + id);
        defaultGroupShouldNotBeFound("id.notEquals=" + id);

        defaultGroupShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultGroupShouldNotBeFound("id.greaterThan=" + id);

        defaultGroupShouldBeFound("id.lessThanOrEqual=" + id);
        defaultGroupShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllGroupsByGroupCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        groupRepository.saveAndFlush(group);

        // Get all the groupList where groupCreatedBy equals to DEFAULT_GROUP_CREATED_BY
        defaultGroupShouldBeFound("groupCreatedBy.equals=" + DEFAULT_GROUP_CREATED_BY);

        // Get all the groupList where groupCreatedBy equals to UPDATED_GROUP_CREATED_BY
        defaultGroupShouldNotBeFound("groupCreatedBy.equals=" + UPDATED_GROUP_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllGroupsByGroupCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        groupRepository.saveAndFlush(group);

        // Get all the groupList where groupCreatedBy not equals to DEFAULT_GROUP_CREATED_BY
        defaultGroupShouldNotBeFound("groupCreatedBy.notEquals=" + DEFAULT_GROUP_CREATED_BY);

        // Get all the groupList where groupCreatedBy not equals to UPDATED_GROUP_CREATED_BY
        defaultGroupShouldBeFound("groupCreatedBy.notEquals=" + UPDATED_GROUP_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllGroupsByGroupCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        groupRepository.saveAndFlush(group);

        // Get all the groupList where groupCreatedBy in DEFAULT_GROUP_CREATED_BY or UPDATED_GROUP_CREATED_BY
        defaultGroupShouldBeFound("groupCreatedBy.in=" + DEFAULT_GROUP_CREATED_BY + "," + UPDATED_GROUP_CREATED_BY);

        // Get all the groupList where groupCreatedBy equals to UPDATED_GROUP_CREATED_BY
        defaultGroupShouldNotBeFound("groupCreatedBy.in=" + UPDATED_GROUP_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllGroupsByGroupCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        groupRepository.saveAndFlush(group);

        // Get all the groupList where groupCreatedBy is not null
        defaultGroupShouldBeFound("groupCreatedBy.specified=true");

        // Get all the groupList where groupCreatedBy is null
        defaultGroupShouldNotBeFound("groupCreatedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllGroupsByGroupCreatedByContainsSomething() throws Exception {
        // Initialize the database
        groupRepository.saveAndFlush(group);

        // Get all the groupList where groupCreatedBy contains DEFAULT_GROUP_CREATED_BY
        defaultGroupShouldBeFound("groupCreatedBy.contains=" + DEFAULT_GROUP_CREATED_BY);

        // Get all the groupList where groupCreatedBy contains UPDATED_GROUP_CREATED_BY
        defaultGroupShouldNotBeFound("groupCreatedBy.contains=" + UPDATED_GROUP_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllGroupsByGroupCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        groupRepository.saveAndFlush(group);

        // Get all the groupList where groupCreatedBy does not contain DEFAULT_GROUP_CREATED_BY
        defaultGroupShouldNotBeFound("groupCreatedBy.doesNotContain=" + DEFAULT_GROUP_CREATED_BY);

        // Get all the groupList where groupCreatedBy does not contain UPDATED_GROUP_CREATED_BY
        defaultGroupShouldBeFound("groupCreatedBy.doesNotContain=" + UPDATED_GROUP_CREATED_BY);
    }


    @Test
    @Transactional
    public void getAllGroupsByGroupCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        groupRepository.saveAndFlush(group);

        // Get all the groupList where groupCreationDate equals to DEFAULT_GROUP_CREATION_DATE
        defaultGroupShouldBeFound("groupCreationDate.equals=" + DEFAULT_GROUP_CREATION_DATE);

        // Get all the groupList where groupCreationDate equals to UPDATED_GROUP_CREATION_DATE
        defaultGroupShouldNotBeFound("groupCreationDate.equals=" + UPDATED_GROUP_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllGroupsByGroupCreationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        groupRepository.saveAndFlush(group);

        // Get all the groupList where groupCreationDate not equals to DEFAULT_GROUP_CREATION_DATE
        defaultGroupShouldNotBeFound("groupCreationDate.notEquals=" + DEFAULT_GROUP_CREATION_DATE);

        // Get all the groupList where groupCreationDate not equals to UPDATED_GROUP_CREATION_DATE
        defaultGroupShouldBeFound("groupCreationDate.notEquals=" + UPDATED_GROUP_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllGroupsByGroupCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        groupRepository.saveAndFlush(group);

        // Get all the groupList where groupCreationDate in DEFAULT_GROUP_CREATION_DATE or UPDATED_GROUP_CREATION_DATE
        defaultGroupShouldBeFound("groupCreationDate.in=" + DEFAULT_GROUP_CREATION_DATE + "," + UPDATED_GROUP_CREATION_DATE);

        // Get all the groupList where groupCreationDate equals to UPDATED_GROUP_CREATION_DATE
        defaultGroupShouldNotBeFound("groupCreationDate.in=" + UPDATED_GROUP_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllGroupsByGroupCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        groupRepository.saveAndFlush(group);

        // Get all the groupList where groupCreationDate is not null
        defaultGroupShouldBeFound("groupCreationDate.specified=true");

        // Get all the groupList where groupCreationDate is null
        defaultGroupShouldNotBeFound("groupCreationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllGroupsByGroupCreationDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        groupRepository.saveAndFlush(group);

        // Get all the groupList where groupCreationDate is greater than or equal to DEFAULT_GROUP_CREATION_DATE
        defaultGroupShouldBeFound("groupCreationDate.greaterThanOrEqual=" + DEFAULT_GROUP_CREATION_DATE);

        // Get all the groupList where groupCreationDate is greater than or equal to UPDATED_GROUP_CREATION_DATE
        defaultGroupShouldNotBeFound("groupCreationDate.greaterThanOrEqual=" + UPDATED_GROUP_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllGroupsByGroupCreationDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        groupRepository.saveAndFlush(group);

        // Get all the groupList where groupCreationDate is less than or equal to DEFAULT_GROUP_CREATION_DATE
        defaultGroupShouldBeFound("groupCreationDate.lessThanOrEqual=" + DEFAULT_GROUP_CREATION_DATE);

        // Get all the groupList where groupCreationDate is less than or equal to SMALLER_GROUP_CREATION_DATE
        defaultGroupShouldNotBeFound("groupCreationDate.lessThanOrEqual=" + SMALLER_GROUP_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllGroupsByGroupCreationDateIsLessThanSomething() throws Exception {
        // Initialize the database
        groupRepository.saveAndFlush(group);

        // Get all the groupList where groupCreationDate is less than DEFAULT_GROUP_CREATION_DATE
        defaultGroupShouldNotBeFound("groupCreationDate.lessThan=" + DEFAULT_GROUP_CREATION_DATE);

        // Get all the groupList where groupCreationDate is less than UPDATED_GROUP_CREATION_DATE
        defaultGroupShouldBeFound("groupCreationDate.lessThan=" + UPDATED_GROUP_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllGroupsByGroupCreationDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        groupRepository.saveAndFlush(group);

        // Get all the groupList where groupCreationDate is greater than DEFAULT_GROUP_CREATION_DATE
        defaultGroupShouldNotBeFound("groupCreationDate.greaterThan=" + DEFAULT_GROUP_CREATION_DATE);

        // Get all the groupList where groupCreationDate is greater than SMALLER_GROUP_CREATION_DATE
        defaultGroupShouldBeFound("groupCreationDate.greaterThan=" + SMALLER_GROUP_CREATION_DATE);
    }


    @Test
    @Transactional
    public void getAllGroupsByGroupModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        groupRepository.saveAndFlush(group);

        // Get all the groupList where groupModifiedBy equals to DEFAULT_GROUP_MODIFIED_BY
        defaultGroupShouldBeFound("groupModifiedBy.equals=" + DEFAULT_GROUP_MODIFIED_BY);

        // Get all the groupList where groupModifiedBy equals to UPDATED_GROUP_MODIFIED_BY
        defaultGroupShouldNotBeFound("groupModifiedBy.equals=" + UPDATED_GROUP_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllGroupsByGroupModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        groupRepository.saveAndFlush(group);

        // Get all the groupList where groupModifiedBy not equals to DEFAULT_GROUP_MODIFIED_BY
        defaultGroupShouldNotBeFound("groupModifiedBy.notEquals=" + DEFAULT_GROUP_MODIFIED_BY);

        // Get all the groupList where groupModifiedBy not equals to UPDATED_GROUP_MODIFIED_BY
        defaultGroupShouldBeFound("groupModifiedBy.notEquals=" + UPDATED_GROUP_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllGroupsByGroupModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        groupRepository.saveAndFlush(group);

        // Get all the groupList where groupModifiedBy in DEFAULT_GROUP_MODIFIED_BY or UPDATED_GROUP_MODIFIED_BY
        defaultGroupShouldBeFound("groupModifiedBy.in=" + DEFAULT_GROUP_MODIFIED_BY + "," + UPDATED_GROUP_MODIFIED_BY);

        // Get all the groupList where groupModifiedBy equals to UPDATED_GROUP_MODIFIED_BY
        defaultGroupShouldNotBeFound("groupModifiedBy.in=" + UPDATED_GROUP_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllGroupsByGroupModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        groupRepository.saveAndFlush(group);

        // Get all the groupList where groupModifiedBy is not null
        defaultGroupShouldBeFound("groupModifiedBy.specified=true");

        // Get all the groupList where groupModifiedBy is null
        defaultGroupShouldNotBeFound("groupModifiedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllGroupsByGroupModifiedByContainsSomething() throws Exception {
        // Initialize the database
        groupRepository.saveAndFlush(group);

        // Get all the groupList where groupModifiedBy contains DEFAULT_GROUP_MODIFIED_BY
        defaultGroupShouldBeFound("groupModifiedBy.contains=" + DEFAULT_GROUP_MODIFIED_BY);

        // Get all the groupList where groupModifiedBy contains UPDATED_GROUP_MODIFIED_BY
        defaultGroupShouldNotBeFound("groupModifiedBy.contains=" + UPDATED_GROUP_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllGroupsByGroupModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        groupRepository.saveAndFlush(group);

        // Get all the groupList where groupModifiedBy does not contain DEFAULT_GROUP_MODIFIED_BY
        defaultGroupShouldNotBeFound("groupModifiedBy.doesNotContain=" + DEFAULT_GROUP_MODIFIED_BY);

        // Get all the groupList where groupModifiedBy does not contain UPDATED_GROUP_MODIFIED_BY
        defaultGroupShouldBeFound("groupModifiedBy.doesNotContain=" + UPDATED_GROUP_MODIFIED_BY);
    }


    @Test
    @Transactional
    public void getAllGroupsByGroupModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        groupRepository.saveAndFlush(group);

        // Get all the groupList where groupModifiedDate equals to DEFAULT_GROUP_MODIFIED_DATE
        defaultGroupShouldBeFound("groupModifiedDate.equals=" + DEFAULT_GROUP_MODIFIED_DATE);

        // Get all the groupList where groupModifiedDate equals to UPDATED_GROUP_MODIFIED_DATE
        defaultGroupShouldNotBeFound("groupModifiedDate.equals=" + UPDATED_GROUP_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllGroupsByGroupModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        groupRepository.saveAndFlush(group);

        // Get all the groupList where groupModifiedDate not equals to DEFAULT_GROUP_MODIFIED_DATE
        defaultGroupShouldNotBeFound("groupModifiedDate.notEquals=" + DEFAULT_GROUP_MODIFIED_DATE);

        // Get all the groupList where groupModifiedDate not equals to UPDATED_GROUP_MODIFIED_DATE
        defaultGroupShouldBeFound("groupModifiedDate.notEquals=" + UPDATED_GROUP_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllGroupsByGroupModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        groupRepository.saveAndFlush(group);

        // Get all the groupList where groupModifiedDate in DEFAULT_GROUP_MODIFIED_DATE or UPDATED_GROUP_MODIFIED_DATE
        defaultGroupShouldBeFound("groupModifiedDate.in=" + DEFAULT_GROUP_MODIFIED_DATE + "," + UPDATED_GROUP_MODIFIED_DATE);

        // Get all the groupList where groupModifiedDate equals to UPDATED_GROUP_MODIFIED_DATE
        defaultGroupShouldNotBeFound("groupModifiedDate.in=" + UPDATED_GROUP_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllGroupsByGroupModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        groupRepository.saveAndFlush(group);

        // Get all the groupList where groupModifiedDate is not null
        defaultGroupShouldBeFound("groupModifiedDate.specified=true");

        // Get all the groupList where groupModifiedDate is null
        defaultGroupShouldNotBeFound("groupModifiedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllGroupsByGroupModifiedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        groupRepository.saveAndFlush(group);

        // Get all the groupList where groupModifiedDate is greater than or equal to DEFAULT_GROUP_MODIFIED_DATE
        defaultGroupShouldBeFound("groupModifiedDate.greaterThanOrEqual=" + DEFAULT_GROUP_MODIFIED_DATE);

        // Get all the groupList where groupModifiedDate is greater than or equal to UPDATED_GROUP_MODIFIED_DATE
        defaultGroupShouldNotBeFound("groupModifiedDate.greaterThanOrEqual=" + UPDATED_GROUP_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllGroupsByGroupModifiedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        groupRepository.saveAndFlush(group);

        // Get all the groupList where groupModifiedDate is less than or equal to DEFAULT_GROUP_MODIFIED_DATE
        defaultGroupShouldBeFound("groupModifiedDate.lessThanOrEqual=" + DEFAULT_GROUP_MODIFIED_DATE);

        // Get all the groupList where groupModifiedDate is less than or equal to SMALLER_GROUP_MODIFIED_DATE
        defaultGroupShouldNotBeFound("groupModifiedDate.lessThanOrEqual=" + SMALLER_GROUP_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllGroupsByGroupModifiedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        groupRepository.saveAndFlush(group);

        // Get all the groupList where groupModifiedDate is less than DEFAULT_GROUP_MODIFIED_DATE
        defaultGroupShouldNotBeFound("groupModifiedDate.lessThan=" + DEFAULT_GROUP_MODIFIED_DATE);

        // Get all the groupList where groupModifiedDate is less than UPDATED_GROUP_MODIFIED_DATE
        defaultGroupShouldBeFound("groupModifiedDate.lessThan=" + UPDATED_GROUP_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllGroupsByGroupModifiedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        groupRepository.saveAndFlush(group);

        // Get all the groupList where groupModifiedDate is greater than DEFAULT_GROUP_MODIFIED_DATE
        defaultGroupShouldNotBeFound("groupModifiedDate.greaterThan=" + DEFAULT_GROUP_MODIFIED_DATE);

        // Get all the groupList where groupModifiedDate is greater than SMALLER_GROUP_MODIFIED_DATE
        defaultGroupShouldBeFound("groupModifiedDate.greaterThan=" + SMALLER_GROUP_MODIFIED_DATE);
    }


    @Test
    @Transactional
    public void getAllGroupsBySubjectIsEqualToSomething() throws Exception {
        // Initialize the database
        groupRepository.saveAndFlush(group);
        Subject subject = SubjectResourceIT.createEntity(em);
        em.persist(subject);
        em.flush();
        group.setSubject(subject);
        groupRepository.saveAndFlush(group);
        Long subjectId = subject.getId();

        // Get all the groupList where subject equals to subjectId
        defaultGroupShouldBeFound("subjectId.equals=" + subjectId);

        // Get all the groupList where subject equals to subjectId + 1
        defaultGroupShouldNotBeFound("subjectId.equals=" + (subjectId + 1));
    }


    @Test
    @Transactional
    public void getAllGroupsByTeacherIsEqualToSomething() throws Exception {
        // Initialize the database
        groupRepository.saveAndFlush(group);
        Teacher teacher = TeacherResourceIT.createEntity(em);
        em.persist(teacher);
        em.flush();
        group.setTeacher(teacher);
        groupRepository.saveAndFlush(group);
        Long teacherId = teacher.getId();

        // Get all the groupList where teacher equals to teacherId
        defaultGroupShouldBeFound("teacherId.equals=" + teacherId);

        // Get all the groupList where teacher equals to teacherId + 1
        defaultGroupShouldNotBeFound("teacherId.equals=" + (teacherId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultGroupShouldBeFound(String filter) throws Exception {
        restGroupMockMvc.perform(get("/api/groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(group.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupCreatedBy").value(hasItem(DEFAULT_GROUP_CREATED_BY)))
            .andExpect(jsonPath("$.[*].groupCreationDate").value(hasItem(DEFAULT_GROUP_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].groupModifiedBy").value(hasItem(DEFAULT_GROUP_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].groupModifiedDate").value(hasItem(DEFAULT_GROUP_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restGroupMockMvc.perform(get("/api/groups/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultGroupShouldNotBeFound(String filter) throws Exception {
        restGroupMockMvc.perform(get("/api/groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGroupMockMvc.perform(get("/api/groups/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingGroup() throws Exception {
        // Get the group
        restGroupMockMvc.perform(get("/api/groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGroup() throws Exception {
        // Initialize the database
        groupService.save(group);

        int databaseSizeBeforeUpdate = groupRepository.findAll().size();

        // Update the group
        Group updatedGroup = groupRepository.findById(group.getId()).get();
        // Disconnect from session so that the updates on updatedGroup are not directly saved in db
        em.detach(updatedGroup);
        updatedGroup
            .groupCreatedBy(UPDATED_GROUP_CREATED_BY)
            .groupCreationDate(UPDATED_GROUP_CREATION_DATE)
            .groupModifiedBy(UPDATED_GROUP_MODIFIED_BY)
            .groupModifiedDate(UPDATED_GROUP_MODIFIED_DATE);

        restGroupMockMvc.perform(put("/api/groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGroup)))
            .andExpect(status().isOk());

        // Validate the Group in the database
        List<Group> groupList = groupRepository.findAll();
        assertThat(groupList).hasSize(databaseSizeBeforeUpdate);
        Group testGroup = groupList.get(groupList.size() - 1);
        assertThat(testGroup.getGroupCreatedBy()).isEqualTo(UPDATED_GROUP_CREATED_BY);
        assertThat(testGroup.getGroupCreationDate()).isEqualTo(UPDATED_GROUP_CREATION_DATE);
        assertThat(testGroup.getGroupModifiedBy()).isEqualTo(UPDATED_GROUP_MODIFIED_BY);
        assertThat(testGroup.getGroupModifiedDate()).isEqualTo(UPDATED_GROUP_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingGroup() throws Exception {
        int databaseSizeBeforeUpdate = groupRepository.findAll().size();

        // Create the Group

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupMockMvc.perform(put("/api/groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(group)))
            .andExpect(status().isBadRequest());

        // Validate the Group in the database
        List<Group> groupList = groupRepository.findAll();
        assertThat(groupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGroup() throws Exception {
        // Initialize the database
        groupService.save(group);

        int databaseSizeBeforeDelete = groupRepository.findAll().size();

        // Delete the group
        restGroupMockMvc.perform(delete("/api/groups/{id}", group.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Group> groupList = groupRepository.findAll();
        assertThat(groupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
