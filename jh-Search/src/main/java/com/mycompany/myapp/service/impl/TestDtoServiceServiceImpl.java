package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.TestDtoService;
import com.mycompany.myapp.repository.TestDtoServiceRepository;
import com.mycompany.myapp.repository.search.TestDtoServiceSearchRepository;
import com.mycompany.myapp.service.TestDtoServiceService;
import com.mycompany.myapp.web.rest.dto.TestDtoServiceDTO;
import com.mycompany.myapp.web.rest.mapper.TestDtoServiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing TestDtoService.
 */
@Service
@Transactional
public class TestDtoServiceServiceImpl implements TestDtoServiceService{

    private final Logger log = LoggerFactory.getLogger(TestDtoServiceServiceImpl.class);

    @Inject
    private TestDtoServiceRepository testDtoServiceRepository;

    @Inject
    private TestDtoServiceMapper testDtoServiceMapper;

    @Inject
    private TestDtoServiceSearchRepository testDtoServiceSearchRepository;

    /**
     * Save a testDtoService.
     * @return the persisted entity
     */
    public TestDtoServiceDTO save(TestDtoServiceDTO testDtoServiceDTO) {
        log.debug("Request to save TestDtoService : {}", testDtoServiceDTO);
        TestDtoService testDtoService = testDtoServiceMapper.testDtoServiceDTOToTestDtoService(testDtoServiceDTO);
        testDtoService = testDtoServiceRepository.save(testDtoService);
        TestDtoServiceDTO result = testDtoServiceMapper.testDtoServiceToTestDtoServiceDTO(testDtoService);
        testDtoServiceSearchRepository.save(testDtoService);
        return result;
    }

    /**
     *  get all the testDtoServices.
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TestDtoServiceDTO> findAll() {
        log.debug("Request to get all TestDtoServices");
        List<TestDtoServiceDTO> result = testDtoServiceRepository.findAll().stream()
            .map(testDtoServiceMapper::testDtoServiceToTestDtoServiceDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     * search for the testDtoService corresponding
     * to the query.
     */
    @Transactional(readOnly = true)
    public List<TestDtoServiceDTO> search(String query) {
        log.debug("REST request to search TestDtoPaginations for query {}", query);
        List<TestDtoServiceDTO> result = new ArrayList<>();
        testDtoServiceSearchRepository.search(queryStringQuery(query)).forEach((item) -> {
            result.add(testDtoServiceMapper.testDtoServiceToTestDtoServiceDTO(item));
        });
        return result;
    }

    /**
     *  get one testDtoService by id.
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public TestDtoServiceDTO findOne(Long id) {
        log.debug("Request to get TestDtoService : {}", id);
        TestDtoService testDtoService = testDtoServiceRepository.findOne(id);
        TestDtoServiceDTO testDtoServiceDTO = testDtoServiceMapper.testDtoServiceToTestDtoServiceDTO(testDtoService);
        return testDtoServiceDTO;
    }

    /**
     *  delete the  testDtoService by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete TestDtoService : {}", id);
        testDtoServiceRepository.delete(id);
        testDtoServiceSearchRepository.delete(id);
    }

}
