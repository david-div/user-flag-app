package com.ravenpack.userflagapp.connector.implementation;

import com.ravenpack.userflagapp.connector.ScoringConnector;
import com.ravenpack.userflagapp.service.implementation.ScoringServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.ravenpack.userflagapp.helper.LatencyHelper.MOCK_LATENCY_MS;

/**
 * Scoring connect used to call the external Scoring Service
 */
@Component
public class ScoringConnectorImpl implements ScoringConnector {

    public static Logger LOG = LoggerFactory.getLogger(ScoringServiceImpl.class);

    /**
     * This is currently a mock and will be implemented at a later date
     *
     * @param message a translated message
     * @return float value between 0 - 1
     */
    @Override
    @Cacheable
    public float getMessageScore(final String message) {
        LOG.info("Getting message score for message: [{}]", message);
        LOG.warn("End point is currently mocked");

        try {
            TimeUnit.MILLISECONDS.sleep(MOCK_LATENCY_MS);
            return new Random().nextFloat();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        
    }
}
