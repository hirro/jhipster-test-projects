package com.mycompany.myapp.service.search;

import com.mycompany.myapp.domain.TestDtoService;
import com.mycompany.myapp.repository.TestDtoServiceRepository;
import com.mycompany.myapp.repository.search.TestDtoServiceSearchRepository;
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
public class TestDtoServiceSynchronizationService
{
    
    private final Logger log = LoggerFactory.getLogger(TestDtoServiceSynchronizationService.class);

    private boolean startupSynchronizationStarted = false;

    @Inject
    private TestDtoServiceRepository testDtoServiceRepository;   

    @Inject
    private TestDtoServiceSearchRepository testDtoServiceSearchRepository;    

    @Scheduled(initialDelayString = "${jhipster.searchSynchronizer.initialDelay:60000}", fixedRateString = "${jhipster.searchSynchronizer.fixedRate:3600000}")
    void startupSynchronization() {
        if (!startupSynchronizationStarted) {
            startupSynchronizationStarted = true;
            log.info("Startup synchronization of TestDtoService");
            updateSearchRepository();
        }
    }

    @Scheduled(cron = "${jhipster.searchSynchronizer.cron:0 1 1 * * ?}")
    void scheduledSynchronization() {        
        log.info("Scheduled synchronization of TestDtoService");
        updateSearchRepository();
    }

    synchronized void updateSearchRepository() {
        int addedItems = 0;
        int updateItems = 0;
        int deletedItems = 0;
        long startTime = System.currentTimeMillis();

        // Add or update subscribers in search repository
        List<TestDtoService> entityList = testDtoServiceRepository.findAll();
        for (TestDtoService entity : entityList) {
            if (testDtoServiceSearchRepository.exists(entity.getId())) {
                updateItems++;
            } else {
                addedItems++;
            }
            testDtoServiceSearchRepository.save(entity);
        }

        // Find unreferenced subscribers in the search repository
        List<Long> deleteList = new ArrayList<>();
        Iterable<TestDtoService> entityIter = testDtoServiceSearchRepository.findAll();
        for (TestDtoService entity : entityIter) {
            if (!testDtoServiceRepository.exists(entity.getId())) {
                deleteList.add(entity.getId());
            }
        }

        // Delete the unreferenced items
        for (Long id : deleteList) {
            testDtoServiceSearchRepository.delete(id);
        }

        log.info("Completed synchronization of elasticsearch entities for class TestDtoService (added: [{}], updated: [{}], deleted: [{}], time: [{}] ms",
            addedItems, updateItems, deletedItems, System.currentTimeMillis() - startTime);
    }
}