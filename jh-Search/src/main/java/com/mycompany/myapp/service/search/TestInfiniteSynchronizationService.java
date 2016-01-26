package com.mycompany.myapp.service.search;

import com.mycompany.myapp.domain.TestInfinite;
import com.mycompany.myapp.repository.TestInfiniteRepository;
import com.mycompany.myapp.repository.search.TestInfiniteSearchRepository;
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
public class TestInfiniteSynchronizationService
{
    
    private final Logger log = LoggerFactory.getLogger(TestInfiniteSynchronizationService.class);

    private boolean startupSynchronizationStarted = false;

    @Inject
    private TestInfiniteRepository testInfiniteRepository;   

    @Inject
    private TestInfiniteSearchRepository testInfiniteSearchRepository;    

    @Scheduled(initialDelayString = "${jhipster.searchSynchronizer.initialDelay:60000}", fixedRateString = "${jhipster.searchSynchronizer.fixedRate:3600000}")
    void startupSynchronization() {
        if (!startupSynchronizationStarted) {
            startupSynchronizationStarted = true;
            log.info("Startup synchronization of TestInfinite");
            updateSearchRepository();
        }
    }

    @Scheduled(cron = "${jhipster.searchSynchronizer.cron:0 1 1 * * ?}")
    void scheduledSynchronization() {        
        log.info("Scheduled synchronization of TestInfinite");
        updateSearchRepository();
    }

    synchronized void updateSearchRepository() {
        int addedItems = 0;
        int updateItems = 0;
        int deletedItems = 0;
        long startTime = System.currentTimeMillis();

        // Add or update subscribers in search repository
        List<TestInfinite> entityList = testInfiniteRepository.findAll();
        for (TestInfinite entity : entityList) {
            if (testInfiniteSearchRepository.exists(entity.getId())) {
                updateItems++;
            } else {
                addedItems++;
            }
            testInfiniteSearchRepository.save(entity);
        }

        // Find unreferenced subscribers in the search repository
        List<Long> deleteList = new ArrayList<>();
        Iterable<TestInfinite> entityIter = testInfiniteSearchRepository.findAll();
        for (TestInfinite entity : entityIter) {
            if (!testInfiniteRepository.exists(entity.getId())) {
                deleteList.add(entity.getId());
            }
        }

        // Delete the unreferenced items
        for (Long id : deleteList) {
            testInfiniteSearchRepository.delete(id);
        }

        log.info("Completed synchronization of elasticsearch entities for class TestInfinite (added: [{}], updated: [{}], deleted: [{}], time: [{}] ms",
            addedItems, updateItems, deletedItems, System.currentTimeMillis() - startTime);
    }
}