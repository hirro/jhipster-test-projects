package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.TestDefaultObject;
import com.mycompany.myapp.repository.TestDefaultObjectRepository;
import com.mycompany.myapp.repository.search.TestDefaultObjectSearchRepository;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing TestDefaultObject.
 */
@RestController
@RequestMapping("/api")
public class TestDefaultObjectResource {

    private final Logger log = LoggerFactory.getLogger(TestDefaultObjectResource.class);
        
    @Inject
    private TestDefaultObjectRepository testDefaultObjectRepository;
    
    @Inject
    private TestDefaultObjectSearchRepository testDefaultObjectSearchRepository;
    
    /**
     * POST  /testDefaultObjects -> Create a new testDefaultObject.
     */
    @RequestMapping(value = "/testDefaultObjects",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TestDefaultObject> createTestDefaultObject(@RequestBody TestDefaultObject testDefaultObject) throws URISyntaxException {
        log.debug("REST request to save TestDefaultObject : {}", testDefaultObject);
        if (testDefaultObject.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("testDefaultObject", "idexists", "A new testDefaultObject cannot already have an ID")).body(null);
        }
        TestDefaultObject result = testDefaultObjectRepository.save(testDefaultObject);
        testDefaultObjectSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/testDefaultObjects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("testDefaultObject", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /testDefaultObjects -> Updates an existing testDefaultObject.
     */
    @RequestMapping(value = "/testDefaultObjects",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TestDefaultObject> updateTestDefaultObject(@RequestBody TestDefaultObject testDefaultObject) throws URISyntaxException {
        log.debug("REST request to update TestDefaultObject : {}", testDefaultObject);
        if (testDefaultObject.getId() == null) {
            return createTestDefaultObject(testDefaultObject);
        }
        TestDefaultObject result = testDefaultObjectRepository.save(testDefaultObject);
        testDefaultObjectSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("testDefaultObject", testDefaultObject.getId().toString()))
            .body(result);
    }

    /**
     * GET  /testDefaultObjects -> get all the testDefaultObjects.
     */
    @RequestMapping(value = "/testDefaultObjects",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TestDefaultObject> getAllTestDefaultObjects() {
        log.debug("REST request to get all TestDefaultObjects");
        return testDefaultObjectRepository.findAll();
            }

    /**
     * GET  /testDefaultObjects/:id -> get the "id" testDefaultObject.
     */
    @RequestMapping(value = "/testDefaultObjects/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TestDefaultObject> getTestDefaultObject(@PathVariable Long id) {
        log.debug("REST request to get TestDefaultObject : {}", id);
        TestDefaultObject testDefaultObject = testDefaultObjectRepository.findOne(id);
        return Optional.ofNullable(testDefaultObject)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /testDefaultObjects/:id -> delete the "id" testDefaultObject.
     */
    @RequestMapping(value = "/testDefaultObjects/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTestDefaultObject(@PathVariable Long id) {
        log.debug("REST request to delete TestDefaultObject : {}", id);
        testDefaultObjectRepository.delete(id);
        testDefaultObjectSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("testDefaultObject", id.toString())).build();
    }

    /**
     * SEARCH  /_search/testDefaultObjects/:query -> search for the testDefaultObject corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/testDefaultObjects/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TestDefaultObject> searchTestDefaultObjects(@PathVariable String query) {
        log.debug("REST request to search TestDefaultObjects for query {}", query);
        return StreamSupport
            .stream(testDefaultObjectSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
            }
    
}
