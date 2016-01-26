package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.TestPage;
import com.mycompany.myapp.repository.TestPageRepository;
import com.mycompany.myapp.repository.search.TestPageSearchRepository;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing TestPage.
 */
@RestController
@RequestMapping("/api")
public class TestPageResource {

    private final Logger log = LoggerFactory.getLogger(TestPageResource.class);
        
    @Inject
    private TestPageRepository testPageRepository;
    
    @Inject
    private TestPageSearchRepository testPageSearchRepository;
    
    /**
     * POST  /testPages -> Create a new testPage.
     */
    @RequestMapping(value = "/testPages",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TestPage> createTestPage(@RequestBody TestPage testPage) throws URISyntaxException {
        log.debug("REST request to save TestPage : {}", testPage);
        if (testPage.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("testPage", "idexists", "A new testPage cannot already have an ID")).body(null);
        }
        TestPage result = testPageRepository.save(testPage);
        testPageSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/testPages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("testPage", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /testPages -> Updates an existing testPage.
     */
    @RequestMapping(value = "/testPages",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TestPage> updateTestPage(@RequestBody TestPage testPage) throws URISyntaxException {
        log.debug("REST request to update TestPage : {}", testPage);
        if (testPage.getId() == null) {
            return createTestPage(testPage);
        }
        TestPage result = testPageRepository.save(testPage);
        testPageSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("testPage", testPage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /testPages -> get all the testPages.
     */
    @RequestMapping(value = "/testPages",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TestPage>> getAllTestPages(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of TestPages");
        Page<TestPage> page = testPageRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/testPages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /testPages/:id -> get the "id" testPage.
     */
    @RequestMapping(value = "/testPages/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TestPage> getTestPage(@PathVariable Long id) {
        log.debug("REST request to get TestPage : {}", id);
        TestPage testPage = testPageRepository.findOne(id);
        return Optional.ofNullable(testPage)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /testPages/:id -> delete the "id" testPage.
     */
    @RequestMapping(value = "/testPages/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTestPage(@PathVariable Long id) {
        log.debug("REST request to delete TestPage : {}", id);
        testPageRepository.delete(id);
        testPageSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("testPage", id.toString())).build();
    }

    /**
     * SEARCH  /_search/testPages/:query -> search for the testPage corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/testPages/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TestPage>> searchTestPages(@PathVariable String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a search page of TestPages for query {}", query);
        Page<TestPage> page = testPageSearchRepository.search(queryStringQuery(query), pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/testPages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
}
