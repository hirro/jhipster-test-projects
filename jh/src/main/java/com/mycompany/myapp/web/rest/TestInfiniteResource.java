package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.TestInfinite;
import com.mycompany.myapp.repository.TestInfiniteRepository;
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
 * REST controller for managing TestInfinite.
 */
@RestController
@RequestMapping("/api")
public class TestInfiniteResource {

    private final Logger log = LoggerFactory.getLogger(TestInfiniteResource.class);
        
    @Inject
    private TestInfiniteRepository testInfiniteRepository;
    
    /**
     * POST  /testInfinites -> Create a new testInfinite.
     */
    @RequestMapping(value = "/testInfinites",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TestInfinite> createTestInfinite(@RequestBody TestInfinite testInfinite) throws URISyntaxException {
        log.debug("REST request to save TestInfinite : {}", testInfinite);
        if (testInfinite.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("testInfinite", "idexists", "A new testInfinite cannot already have an ID")).body(null);
        }
        TestInfinite result = testInfiniteRepository.save(testInfinite);
        return ResponseEntity.created(new URI("/api/testInfinites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("testInfinite", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /testInfinites -> Updates an existing testInfinite.
     */
    @RequestMapping(value = "/testInfinites",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TestInfinite> updateTestInfinite(@RequestBody TestInfinite testInfinite) throws URISyntaxException {
        log.debug("REST request to update TestInfinite : {}", testInfinite);
        if (testInfinite.getId() == null) {
            return createTestInfinite(testInfinite);
        }
        TestInfinite result = testInfiniteRepository.save(testInfinite);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("testInfinite", testInfinite.getId().toString()))
            .body(result);
    }

    /**
     * GET  /testInfinites -> get all the testInfinites.
     */
    @RequestMapping(value = "/testInfinites",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TestInfinite>> getAllTestInfinites(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of TestInfinites");
        Page<TestInfinite> page = testInfiniteRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/testInfinites");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /testInfinites/:id -> get the "id" testInfinite.
     */
    @RequestMapping(value = "/testInfinites/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TestInfinite> getTestInfinite(@PathVariable Long id) {
        log.debug("REST request to get TestInfinite : {}", id);
        TestInfinite testInfinite = testInfiniteRepository.findOne(id);
        return Optional.ofNullable(testInfinite)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /testInfinites/:id -> delete the "id" testInfinite.
     */
    @RequestMapping(value = "/testInfinites/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTestInfinite(@PathVariable Long id) {
        log.debug("REST request to delete TestInfinite : {}", id);
        testInfiniteRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("testInfinite", id.toString())).build();
    }
}
