package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.TestDtoPagination;
import com.mycompany.myapp.web.rest.dto.TestDtoPaginationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing TestDtoPagination.
 */
public interface TestDtoPaginationService {

    /**
     * Save a testDtoPagination.
     * @return the persisted entity
     */
    public TestDtoPaginationDTO save(TestDtoPaginationDTO testDtoPaginationDTO);

    /**
     *  get all the testDtoPaginations.
     *  @return the list of entities
     */
    public Page<TestDtoPagination> findAll(Pageable pageable);

    /**
     *  get the "id" testDtoPagination.
     *  @return the entity
     */
    public TestDtoPaginationDTO findOne(Long id);

    /**
     *  delete the "id" testDtoPagination.
     */
    public void delete(Long id);

    /**
     *  get all the testDtoPaginations.
     *  @return the list of entities
     */
    public Page<TestDtoPagination> search(String query, Pageable pageable);
}
