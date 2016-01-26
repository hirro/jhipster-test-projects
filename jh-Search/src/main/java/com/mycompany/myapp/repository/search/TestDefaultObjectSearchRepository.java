package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.TestDefaultObject;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TestDefaultObject entity.
 */
public interface TestDefaultObjectSearchRepository extends ElasticsearchRepository<TestDefaultObject, Long> {
}
