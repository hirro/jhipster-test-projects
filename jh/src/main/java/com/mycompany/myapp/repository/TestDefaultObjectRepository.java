package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.TestDefaultObject;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TestDefaultObject entity.
 */
public interface TestDefaultObjectRepository extends JpaRepository<TestDefaultObject,Long> {

}
