package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.TestPage;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TestPage entity.
 */
public interface TestPageRepository extends JpaRepository<TestPage,Long> {

}
