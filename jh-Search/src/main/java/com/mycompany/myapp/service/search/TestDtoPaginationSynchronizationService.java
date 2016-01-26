package com.mycompany.myapp.service.search;

import com.mycompany.myapp.domain.TestDtoPagination;
import com.mycompany.myapp.repository.TestDtoPaginationRepository;
import com.mycompany.myapp.repository.search.TestDtoPaginationSearchRepository;
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
public class TestDtoPaginationSynchronizationService
{
    
    private final Logger log = LoggerFactory.getLogger(TestDtoPaginationSynchronizationService.class);

    private boolean startupSynchronizationStarted = false;

    @Inject
    private TestDtoPaginationRepository testDtoPaginationRepository;   

    @Inject
    private TestDtoPaginationSearchRepository testDtoPaginationSearchRepository;    

    @Scheduled(initialDelayString = "${jhipster.searchSynchronizer.initialDelay:60000}", fixedRateString = "${jhipster.searchSynchronizer.fixedRate:3600000}")
    void startupSynchronization() {
        if (!startupSynchronizationStarted) {
            startupSynchronizationStarted = true;
            log.info("Startup synchronization of TestDtoPagination");
            updateSearchRepository();
        }
    }

    @Scheduled(cron = "${jhipster.searchSynchronizer.cron:0 1 1 * * ?}")
    void scheduledSynchronization() {        
        log.info("Scheduled synchronization of TestDtoPagination");
        updateSearchRepository();
    }

    synchronized void updateSearchRepository() {
        int addedItems = 0;
        int updateItems = 0;
        int deletedItems = 0;
        long startTime = System.currentTimeMillis();

        // Add or update subscribers in search repository
        List<TestDtoPagination> entityList = testDtoPaginationRepository.findAll();
        for (TestDtoPagination entity : entityList) {
            if (testDtoPaginationSearchRepository.exists(entity.getId())) {
                updateItems++;
            } else {
                addedItems++;
            }
            testDtoPaginationSearchRepository.save(entity);
        }

        // Find unreferenced subscribers in the search repository
        List<Long> deleteList = new ArrayList<>();
        Iterable<TestDtoPagination> entityIter = testDtoPaginationSearchRepository.findAll();
        for (TestDtoPagination entity : entityIter) {
            if (!testDtoPaginationRepository.exists(entity.getId())) {
                deleteList.add(entity.getId());
            }
        }

        // Delete the unreferenced items
        for (Long id : deleteList) {
            testDtoPaginationSearchRepository.delete(id);
        }

        log.info("Completed synchronization of elasticsearch entities for class TestDtoPagination (added: [{}], updated: [{}], deleted: [{}], time: [{}] ms",
            addedItems, updateItems, deletedItems, System.currentTimeMillis() - startTime);
    }
}