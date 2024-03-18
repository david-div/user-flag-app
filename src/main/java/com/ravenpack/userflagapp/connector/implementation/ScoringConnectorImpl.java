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
 * An implementation of the {@link ScoringConnector} that will make a call to the external service
 */
@Component
public class ScoringConnectorImpl implements ScoringConnector {

    public static Logger LOG = LoggerFactory.getLogger(ScoringServiceImpl.class);

    /**
     * Currently mocked, which will return a random float between 0 - 1
     * @param message a message in english
     * @return float score how offensive the message is valued between 0 - 1
     */
    @Override
    @Cacheable("messageScores")
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
