package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.TestDtoPaginationService;
import com.mycompany.myapp.domain.TestDtoPagination;
import com.mycompany.myapp.repository.TestDtoPaginationRepository;
import com.mycompany.myapp.web.rest.dto.TestDtoPaginationDTO;
import com.mycompany.myapp.web.rest.mapper.TestDtoPaginationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    
    /**
     * Save a testDtoPagination.
     * @return the persisted entity
     */
    public TestDtoPaginationDTO save(TestDtoPaginationDTO testDtoPaginationDTO) {
        log.debug("Request to save TestDtoPagination : {}", testDtoPaginationDTO);
        TestDtoPagination testDtoPagination = testDtoPaginationMapper.testDtoPaginationDTOToTestDtoPagination(testDtoPaginationDTO);
        testDtoPagination = testDtoPaginationRepository.save(testDtoPagination);
        TestDtoPaginationDTO result = testDtoPaginationMapper.testDtoPaginationToTestDtoPaginationDTO(testDtoPagination);
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
    }
}
