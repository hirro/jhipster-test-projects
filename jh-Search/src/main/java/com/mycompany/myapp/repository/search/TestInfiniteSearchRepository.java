package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.TestInfinite;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TestInfinite entity.
 */
public interface TestInfiniteSearchRepository extends ElasticsearchRepository<TestInfinite, Long> {
}
