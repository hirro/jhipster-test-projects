package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.TestDtoPagination;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TestDtoPagination entity.
 */
public interface TestDtoPaginationRepository extends JpaRepository<TestDtoPagination,Long> {

}
