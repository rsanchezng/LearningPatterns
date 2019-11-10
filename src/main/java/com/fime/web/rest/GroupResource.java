package com.fime.web.rest;

import com.fime.domain.Group;
import com.fime.repository.GroupRepository;
import com.fime.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.fime.domain.Group}.
 */
@RestController
@RequestMapping("/api")
public class GroupResource {

    private final Logger log = LoggerFactory.getLogger(GroupResource.class);

    private static final String ENTITY_NAME = "group";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GroupRepository groupRepository;

    public GroupResource(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    /**
     * {@code POST  /groups} : Create a new group.
     *
     * @param group the group to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new group, or with status {@code 400 (Bad Request)} if the group has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/groups")
    public ResponseEntity<Group> createGroup(@RequestBody Group group) throws URISyntaxException {
        log.debug("REST request to save Group : {}", group);
        if (group.getId() != null) {
            throw new BadRequestAlertException("A new group cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Group result = groupRepository.save(group);
        return ResponseEntity.created(new URI("/api/groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /groups} : Updates an existing group.
     *
     * @param group the group to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated group,
     * or with status {@code 400 (Bad Request)} if the group is not valid,
     * or with status {@code 500 (Internal Server Error)} if the group couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/groups")
    public ResponseEntity<Group> updateGroup(@RequestBody Group group) throws URISyntaxException {
        log.debug("REST request to update Group : {}", group);
        if (group.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Group result = groupRepository.save(group);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, group.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /groups} : get all the groups.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of groups in body.
     */
    @GetMapping("/groups")
    public List<Group> getAllGroups() {
        log.debug("REST request to get all Groups");
        return groupRepository.findAll();
    }

    /**
     * {@code GET  /groups/:id} : get the "id" group.
     *
     * @param id the id of the group to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the group, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/groups/{id}")
    public ResponseEntity<Group> getGroup(@PathVariable Long id) {
        log.debug("REST request to get Group : {}", id);
        Optional<Group> group = groupRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(group);
    }

    /**
     * {@code DELETE  /groups/:id} : delete the "id" group.
     *
     * @param id the id of the group to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/groups/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        log.debug("REST request to delete Group : {}", id);
        groupRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
