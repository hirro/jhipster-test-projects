package com.mycompany.myapp.service.search;

import com.mycompany.myapp.domain.TestPage;
import com.mycompany.myapp.repository.TestPageRepository;
import com.mycompany.myapp.repository.search.TestPageSearchRepository;
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
public class TestPageSynchronizationService
{
    
    private final Logger log = LoggerFactory.getLogger(TestPageSynchronizationService.class);

    private boolean startupSynchronizationStarted = false;

    @Inject
    private TestPageRepository testPageRepository;   

    @Inject
    private TestPageSearchRepository testPageSearchRepository;    

    @Scheduled(initialDelayString = "${jhipster.searchSynchronizer.initialDelay:60000}", fixedRateString = "${jhipster.searchSynchronizer.fixedRate:3600000}")
    void startupSynchronization() {
        if (!startupSynchronizationStarted) {
            startupSynchronizationStarted = true;
            log.info("Startup synchronization of TestPage");
            updateSearchRepository();
        }
    }

    @Scheduled(cron = "${jhipster.searchSynchronizer.cron:0 1 1 * * ?}")
    void scheduledSynchronization() {        
        log.info("Scheduled synchronization of TestPage");
        updateSearchRepository();
    }

    synchronized void updateSearchRepository() {
        int addedItems = 0;
        int updateItems = 0;
        int deletedItems = 0;
        long startTime = System.currentTimeMillis();

        // Add or update subscribers in search repository
        List<TestPage> entityList = testPageRepository.findAll();
        for (TestPage entity : entityList) {
            if (testPageSearchRepository.exists(entity.getId())) {
                updateItems++;
            } else {
                addedItems++;
            }
            testPageSearchRepository.save(entity);
        }

        // Find unreferenced subscribers in the search repository
        List<Long> deleteList = new ArrayList<>();
        Iterable<TestPage> entityIter = testPageSearchRepository.findAll();
        for (TestPage entity : entityIter) {
            if (!testPageRepository.exists(entity.getId())) {
                deleteList.add(entity.getId());
            }
        }

        // Delete the unreferenced items
        for (Long id : deleteList) {
            testPageSearchRepository.delete(id);
        }

        log.info("Completed synchronization of elasticsearch entities for class TestPage (added: [{}], updated: [{}], deleted: [{}], time: [{}] ms",
            addedItems, updateItems, deletedItems, System.currentTimeMillis() - startTime);
    }
}