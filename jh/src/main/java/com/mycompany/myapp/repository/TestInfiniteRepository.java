package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.TestInfinite;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TestInfinite entity.
 */
public interface TestInfiniteRepository extends JpaRepository<TestInfinite,Long> {

}
