package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.TestDtoService;
import com.mycompany.myapp.service.TestDtoServiceService;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.dto.TestDtoServiceDTO;
import com.mycompany.myapp.web.rest.mapper.TestDtoServiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing TestDtoService.
 */
@RestController
@RequestMapping("/api")
public class TestDtoServiceResource {

    private final Logger log = LoggerFactory.getLogger(TestDtoServiceResource.class);
        
    @Inject
    private TestDtoServiceService testDtoServiceService;
    
    @Inject
    private TestDtoServiceMapper testDtoServiceMapper;
    
    /**
     * POST  /testDtoServices -> Create a new testDtoService.
     */
    @RequestMapping(value = "/testDtoServices",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TestDtoServiceDTO> createTestDtoService(@RequestBody TestDtoServiceDTO testDtoServiceDTO) throws URISyntaxException {
        log.debug("REST request to save TestDtoService : {}", testDtoServiceDTO);
        if (testDtoServiceDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("testDtoService", "idexists", "A new testDtoService cannot already have an ID")).body(null);
        }
        TestDtoServiceDTO result = testDtoServiceService.save(testDtoServiceDTO);
        return ResponseEntity.created(new URI("/api/testDtoServices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("testDtoService", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /testDtoServices -> Updates an existing testDtoService.
     */
    @RequestMapping(value = "/testDtoServices",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TestDtoServiceDTO> updateTestDtoService(@RequestBody TestDtoServiceDTO testDtoServiceDTO) throws URISyntaxException {
        log.debug("REST request to update TestDtoService : {}", testDtoServiceDTO);
        if (testDtoServiceDTO.getId() == null) {
            return createTestDtoService(testDtoServiceDTO);
        }
        TestDtoServiceDTO result = testDtoServiceService.save(testDtoServiceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("testDtoService", testDtoServiceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /testDtoServices -> get all the testDtoServices.
     */
    @RequestMapping(value = "/testDtoServices",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<TestDtoServiceDTO> getAllTestDtoServices() {
        log.debug("REST request to get all TestDtoServices");
        return testDtoServiceService.findAll();
            }

    /**
     * GET  /testDtoServices/:id -> get the "id" testDtoService.
     */
    @RequestMapping(value = "/testDtoServices/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TestDtoServiceDTO> getTestDtoService(@PathVariable Long id) {
        log.debug("REST request to get TestDtoService : {}", id);
        TestDtoServiceDTO testDtoServiceDTO = testDtoServiceService.findOne(id);
        return Optional.ofNullable(testDtoServiceDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /testDtoServices/:id -> delete the "id" testDtoService.
     */
    @RequestMapping(value = "/testDtoServices/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTestDtoService(@PathVariable Long id) {
        log.debug("REST request to delete TestDtoService : {}", id);
        testDtoServiceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("testDtoService", id.toString())).build();
    }

    /**
     * SEARCH  /_search/testDtoServices/:query -> search for the testDtoService corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/testDtoServices/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TestDtoServiceDTO> searchTestDtoServices(@PathVariable String query) {

        log.debug("Request to search TestDtoServices for query {}", query);
        return testDtoServiceService.search(query);
    }    
}
