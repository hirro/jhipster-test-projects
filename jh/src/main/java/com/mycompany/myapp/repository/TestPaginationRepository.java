package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.TestPagination;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TestPagination entity.
 */
public interface TestPaginationRepository extends JpaRepository<TestPagination,Long> {

}
