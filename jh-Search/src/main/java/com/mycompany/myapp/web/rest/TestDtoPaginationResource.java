package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.TestDtoPagination;
import com.mycompany.myapp.service.TestDtoPaginationService;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.web.rest.dto.TestDtoPaginationDTO;
import com.mycompany.myapp.web.rest.mapper.TestDtoPaginationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing TestDtoPagination.
 */
@RestController
@RequestMapping("/api")
public class TestDtoPaginationResource {

    private final Logger log = LoggerFactory.getLogger(TestDtoPaginationResource.class);
        
    @Inject
    private TestDtoPaginationService testDtoPaginationService;
    
    @Inject
    private TestDtoPaginationMapper testDtoPaginationMapper;
    
    /**
     * POST  /testDtoPaginations -> Create a new testDtoPagination.
     */
    @RequestMapping(value = "/testDtoPaginations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TestDtoPaginationDTO> createTestDtoPagination(@RequestBody TestDtoPaginationDTO testDtoPaginationDTO) throws URISyntaxException {
        log.debug("REST request to save TestDtoPagination : {}", testDtoPaginationDTO);
        if (testDtoPaginationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("testDtoPagination", "idexists", "A new testDtoPagination cannot already have an ID")).body(null);
        }
        TestDtoPaginationDTO result = testDtoPaginationService.save(testDtoPaginationDTO);
        return ResponseEntity.created(new URI("/api/testDtoPaginations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("testDtoPagination", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /testDtoPaginations -> Updates an existing testDtoPagination.
     */
    @RequestMapping(value = "/testDtoPaginations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TestDtoPaginationDTO> updateTestDtoPagination(@RequestBody TestDtoPaginationDTO testDtoPaginationDTO) throws URISyntaxException {
        log.debug("REST request to update TestDtoPagination : {}", testDtoPaginationDTO);
        if (testDtoPaginationDTO.getId() == null) {
            return createTestDtoPagination(testDtoPaginationDTO);
        }
        TestDtoPaginationDTO result = testDtoPaginationService.save(testDtoPaginationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("testDtoPagination", testDtoPaginationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /testDtoPaginations -> get all the testDtoPaginations.
     */
    @RequestMapping(value = "/testDtoPaginations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<TestDtoPaginationDTO>> getAllTestDtoPaginations(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of TestDtoPaginations");
        Page<TestDtoPagination> page = testDtoPaginationService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/testDtoPaginations");
        return new ResponseEntity<>(page.getContent().stream()
            .map(testDtoPaginationMapper::testDtoPaginationToTestDtoPaginationDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /testDtoPaginations/:id -> get the "id" testDtoPagination.
     */
    @RequestMapping(value = "/testDtoPaginations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TestDtoPaginationDTO> getTestDtoPagination(@PathVariable Long id) {
        log.debug("REST request to get TestDtoPagination : {}", id);
        TestDtoPaginationDTO testDtoPaginationDTO = testDtoPaginationService.findOne(id);
        return Optional.ofNullable(testDtoPaginationDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /testDtoPaginations/:id -> delete the "id" testDtoPagination.
     */
    @RequestMapping(value = "/testDtoPaginations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTestDtoPagination(@PathVariable Long id) {
        log.debug("REST request to delete TestDtoPagination : {}", id);
        testDtoPaginationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("testDtoPagination", id.toString())).build();
    }

    /**
     * SEARCH  /_search/testDtoPaginations/:query -> search for the testDtoPagination corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/testDtoPaginations/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<TestDtoPaginationDTO>> searchTestDtoPaginations(@PathVariable String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a search page of TestDtoPaginations for query {}", query);
        Page<TestDtoPagination> page = testDtoPaginationService.search(query, pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/testDtoPaginations");
        return new ResponseEntity<>(page.getContent().stream()
            .map(testDtoPaginationMapper::testDtoPaginationToTestDtoPaginationDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }
    
}
