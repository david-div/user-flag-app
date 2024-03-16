package com.ravenpack.userflagapp.connector.implementation;

import com.ravenpack.userflagapp.connector.ScoringConnector;
import com.ravenpack.userflagapp.service.implementation.ScoringServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;

import java.util.Random;

/**
 * Scoring connect used to call the external Scoring Service
 */
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

        return new Random().nextFloat();
    }
}
