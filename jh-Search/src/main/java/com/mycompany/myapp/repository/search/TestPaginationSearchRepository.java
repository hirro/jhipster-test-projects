package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.TestPagination;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TestPagination entity.
 */
public interface TestPaginationSearchRepository extends ElasticsearchRepository<TestPagination, Long> {
}
