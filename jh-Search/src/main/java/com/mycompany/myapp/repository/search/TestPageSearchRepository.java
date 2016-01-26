package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.TestPage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TestPage entity.
 */
public interface TestPageSearchRepository extends ElasticsearchRepository<TestPage, Long> {
}
