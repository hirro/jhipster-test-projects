package com.mycompany.myapp.service.search;

import com.mycompany.myapp.domain.TestPagination;
import com.mycompany.myapp.repository.TestPaginationRepository;
import com.mycompany.myapp.repository.search.TestPaginationSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for synchronizing data between database and elasticsearch.
 */
@Service
public class TestPaginationSynchronizationService
{
    
    private final Logger log = LoggerFactory.getLogger(TestPaginationSynchronizationService.class);

    private boolean startupSynchronizationStarted = false;

    @Inject
    private TestPaginationRepository testPaginationRepository;   

    @Inject
    private TestPaginationSearchRepository testPaginationSearchRepository;    

    @Scheduled(initialDelayString = "${jhipster.searchSynchronizer.initialDelay:60000}", fixedRateString = "${jhipster.searchSynchronizer.fixedRate:3600000}")
    void startupSynchronization() {
        if (!startupSynchronizationStarted) {
            startupSynchronizationStarted = true;
            log.info("Startup synchronization of TestPagination");
            updateSearchRepository();
        }
    }

    @Scheduled(cron = "${jhipster.searchSynchronizer.cron:0 1 1 * * ?}")
    void scheduledSynchronization() {        
        log.info("Scheduled synchronization of TestPagination");
        updateSearchRepository();
    }

    synchronized void updateSearchRepository() {
        int addedItems = 0;
        int updateItems = 0;
        int deletedItems = 0;
        long startTime = System.currentTimeMillis();

        // Add or update subscribers in search repository
        List<TestPagination> entityList = testPaginationRepository.findAll();
        for (TestPagination entity : entityList) {
            if (testPaginationSearchRepository.exists(entity.getId())) {
                updateItems++;
            } else {
                addedItems++;
            }
            testPaginationSearchRepository.save(entity);
        }

        // Find unreferenced subscribers in the search repository
        List<Long> deleteList = new ArrayList<>();
        Iterable<TestPagination> entityIter = testPaginationSearchRepository.findAll();
        for (TestPagination entity : entityIter) {
            if (!testPaginationRepository.exists(entity.getId())) {
                deleteList.add(entity.getId());
            }
        }

        // Delete the unreferenced items
        for (Long id : deleteList) {
            testPaginationSearchRepository.delete(id);
        }

        log.info("Completed synchronization of elasticsearch entities for class TestPagination (added: [{}], updated: [{}], deleted: [{}], time: [{}] ms",
            addedItems, updateItems, deletedItems, System.currentTimeMillis() - startTime);
    }
}