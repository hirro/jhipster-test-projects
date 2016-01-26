package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.TestDtoPagination;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TestDtoPagination entity.
 */
public interface TestDtoPaginationSearchRepository extends ElasticsearchRepository<TestDtoPagination, Long> {
}
