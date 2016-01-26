package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.TestDtoServiceService;
import com.mycompany.myapp.domain.TestDtoService;
import com.mycompany.myapp.repository.TestDtoServiceRepository;
import com.mycompany.myapp.web.rest.dto.TestDtoServiceDTO;
import com.mycompany.myapp.web.rest.mapper.TestDtoServiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    
    /**
     * Save a testDtoService.
     * @return the persisted entity
     */
    public TestDtoServiceDTO save(TestDtoServiceDTO testDtoServiceDTO) {
        log.debug("Request to save TestDtoService : {}", testDtoServiceDTO);
        TestDtoService testDtoService = testDtoServiceMapper.testDtoServiceDTOToTestDtoService(testDtoServiceDTO);
        testDtoService = testDtoServiceRepository.save(testDtoService);
        TestDtoServiceDTO result = testDtoServiceMapper.testDtoServiceToTestDtoServiceDTO(testDtoService);
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
    }
}
