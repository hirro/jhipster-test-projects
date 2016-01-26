package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.TestPagination;
import com.mycompany.myapp.repository.TestPaginationRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TestPagination.
 */
@RestController
@RequestMapping("/api")
public class TestPaginationResource {

    private final Logger log = LoggerFactory.getLogger(TestPaginationResource.class);
        
    @Inject
    private TestPaginationRepository testPaginationRepository;
    
    /**
     * POST  /testPaginations -> Create a new testPagination.
     */
    @RequestMapping(value = "/testPaginations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TestPagination> createTestPagination(@RequestBody TestPagination testPagination) throws URISyntaxException {
        log.debug("REST request to save TestPagination : {}", testPagination);
        if (testPagination.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("testPagination", "idexists", "A new testPagination cannot already have an ID")).body(null);
        }
        TestPagination result = testPaginationRepository.save(testPagination);
        return ResponseEntity.created(new URI("/api/testPaginations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("testPagination", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /testPaginations -> Updates an existing testPagination.
     */
    @RequestMapping(value = "/testPaginations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TestPagination> updateTestPagination(@RequestBody TestPagination testPagination) throws URISyntaxException {
        log.debug("REST request to update TestPagination : {}", testPagination);
        if (testPagination.getId() == null) {
            return createTestPagination(testPagination);
        }
        TestPagination result = testPaginationRepository.save(testPagination);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("testPagination", testPagination.getId().toString()))
            .body(result);
    }

    /**
     * GET  /testPaginations -> get all the testPaginations.
     */
    @RequestMapping(value = "/testPaginations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TestPagination>> getAllTestPaginations(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of TestPaginations");
        Page<TestPagination> page = testPaginationRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/testPaginations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /testPaginations/:id -> get the "id" testPagination.
     */
    @RequestMapping(value = "/testPaginations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TestPagination> getTestPagination(@PathVariable Long id) {
        log.debug("REST request to get TestPagination : {}", id);
        TestPagination testPagination = testPaginationRepository.findOne(id);
        return Optional.ofNullable(testPagination)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /testPaginations/:id -> delete the "id" testPagination.
     */
    @RequestMapping(value = "/testPaginations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTestPagination(@PathVariable Long id) {
        log.debug("REST request to delete TestPagination : {}", id);
        testPaginationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("testPagination", id.toString())).build();
    }
}
