package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.TestDtoService;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TestDtoService entity.
 */
public interface TestDtoServiceSearchRepository extends ElasticsearchRepository<TestDtoService, Long> {
}
