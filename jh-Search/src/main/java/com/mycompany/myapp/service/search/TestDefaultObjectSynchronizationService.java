package com.mycompany.myapp.service.search;

import com.mycompany.myapp.domain.TestDefaultObject;
import com.mycompany.myapp.repository.TestDefaultObjectRepository;
import com.mycompany.myapp.repository.search.TestDefaultObjectSearchRepository;
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
public class TestDefaultObjectSynchronizationService
{
    
    private final Logger log = LoggerFactory.getLogger(TestDefaultObjectSynchronizationService.class);

    private boolean startupSynchronizationStarted = false;

    @Inject
    private TestDefaultObjectRepository testDefaultObjectRepository;   

    @Inject
    private TestDefaultObjectSearchRepository testDefaultObjectSearchRepository;    

    @Scheduled(initialDelayString = "${jhipster.searchSynchronizer.initialDelay:60000}", fixedRateString = "${jhipster.searchSynchronizer.fixedRate:3600000}")
    void startupSynchronization() {
        if (!startupSynchronizationStarted) {
            startupSynchronizationStarted = true;
            log.info("Startup synchronization of TestDefaultObject");
            updateSearchRepository();
        }
    }

    @Scheduled(cron = "${jhipster.searchSynchronizer.cron:0 1 1 * * ?}")
    void scheduledSynchronization() {        
        log.info("Scheduled synchronization of TestDefaultObject");
        updateSearchRepository();
    }

    synchronized void updateSearchRepository() {
        int addedItems = 0;
        int updateItems = 0;
        int deletedItems = 0;
        long startTime = System.currentTimeMillis();

        // Add or update subscribers in search repository
        List<TestDefaultObject> entityList = testDefaultObjectRepository.findAll();
        for (TestDefaultObject entity : entityList) {
            if (testDefaultObjectSearchRepository.exists(entity.getId())) {
                updateItems++;
            } else {
                addedItems++;
            }
            testDefaultObjectSearchRepository.save(entity);
        }

        // Find unreferenced subscribers in the search repository
        List<Long> deleteList = new ArrayList<>();
        Iterable<TestDefaultObject> entityIter = testDefaultObjectSearchRepository.findAll();
        for (TestDefaultObject entity : entityIter) {
            if (!testDefaultObjectRepository.exists(entity.getId())) {
                deleteList.add(entity.getId());
            }
        }

        // Delete the unreferenced items
        for (Long id : deleteList) {
            testDefaultObjectSearchRepository.delete(id);
        }

        log.info("Completed synchronization of elasticsearch entities for class TestDefaultObject (added: [{}], updated: [{}], deleted: [{}], time: [{}] ms",
            addedItems, updateItems, deletedItems, System.currentTimeMillis() - startTime);
    }
}