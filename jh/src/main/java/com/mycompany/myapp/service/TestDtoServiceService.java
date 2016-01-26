package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.TestDtoService;
import com.mycompany.myapp.web.rest.dto.TestDtoServiceDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing TestDtoService.
 */
public interface TestDtoServiceService {

    /**
     * Save a testDtoService.
     * @return the persisted entity
     */
    public TestDtoServiceDTO save(TestDtoServiceDTO testDtoServiceDTO);

    /**
     *  get all the testDtoServices.
     *  @return the list of entities
     */
    public List<TestDtoServiceDTO> findAll();

    /**
     *  get the "id" testDtoService.
     *  @return the entity
     */
    public TestDtoServiceDTO findOne(Long id);

    /**
     *  delete the "id" testDtoService.
     */
    public void delete(Long id);
}
