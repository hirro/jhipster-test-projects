package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.TestDtoPagination;
import com.mycompany.myapp.repository.TestDtoPaginationRepository;
import com.mycompany.myapp.repository.search.TestDtoPaginationSearchRepository;
import com.mycompany.myapp.service.TestDtoPaginationService;
import com.mycompany.myapp.web.rest.dto.TestDtoPaginationDTO;
import com.mycompany.myapp.web.rest.mapper.TestDtoPaginationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing TestDtoPagination.
 */
@Service
@Transactional
public class TestDtoPaginationServiceImpl implements TestDtoPaginationService{

    private final Logger log = LoggerFactory.getLogger(TestDtoPaginationServiceImpl.class);

    @Inject
    private TestDtoPaginationRepository testDtoPaginationRepository;

    @Inject
    private TestDtoPaginationMapper testDtoPaginationMapper;

    @Inject
    private TestDtoPaginationSearchRepository testDtoPaginationSearchRepository;

    /**
     * Save a testDtoPagination.
     * @return the persisted entity
     */
    public TestDtoPaginationDTO save(TestDtoPaginationDTO testDtoPaginationDTO) {
        log.debug("Request to save TestDtoPagination : {}", testDtoPaginationDTO);
        TestDtoPagination testDtoPagination = testDtoPaginationMapper.testDtoPaginationDTOToTestDtoPagination(testDtoPaginationDTO);
        testDtoPagination = testDtoPaginationRepository.save(testDtoPagination);
        TestDtoPaginationDTO result = testDtoPaginationMapper.testDtoPaginationToTestDtoPaginationDTO(testDtoPagination);
        testDtoPaginationSearchRepository.save(testDtoPagination);
        return result;
    }

    /**
     *  get all the testDtoPaginations.
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TestDtoPagination> findAll(Pageable pageable) {
        log.debug("Request to get all TestDtoPaginations");
        Page<TestDtoPagination> result = testDtoPaginationRepository.findAll(pageable);
        return result;
    }

    /**
     *  get one testDtoPagination by id.
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public TestDtoPaginationDTO findOne(Long id) {
        log.debug("Request to get TestDtoPagination : {}", id);
        TestDtoPagination testDtoPagination = testDtoPaginationRepository.findOne(id);
        TestDtoPaginationDTO testDtoPaginationDTO = testDtoPaginationMapper.testDtoPaginationToTestDtoPaginationDTO(testDtoPagination);
        return testDtoPaginationDTO;
    }

    /**
     *  delete the  testDtoPagination by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete TestDtoPagination : {}", id);
        testDtoPaginationRepository.delete(id);
        testDtoPaginationSearchRepository.delete(id);
    }

    /**
     * search for the testDtoPagination corresponding
     * to the query.
     */
    @Transactional(readOnly = true)
    public Page<TestDtoPagination> search(String query, Pageable pageable) {
        log.debug("REST request to search TestDtoPaginations for query {}", query);
        Page<TestDtoPagination> result = testDtoPaginationSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }

}
