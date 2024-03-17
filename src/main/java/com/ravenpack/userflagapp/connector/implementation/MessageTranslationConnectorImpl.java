package com.ravenpack.userflagapp.connector.implementation;

import com.ravenpack.userflagapp.connector.MessageTranslationConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.ravenpack.userflagapp.helper.LatencyHelper.MOCK_LATENCY_MS;

@Component
public class MessageTranslationConnectorImpl implements MessageTranslationConnector {
    public static final Logger LOG = LoggerFactory.getLogger(MessageTranslationConnectorImpl.class);

    @Override
    @Cacheable
    public String translate(String message) {
        LOG.info("Translating message: [{}]", message);
        LOG.warn("End point is currently mocked");

        try {
            TimeUnit.MILLISECONDS.sleep(MOCK_LATENCY_MS);
            return message + " translated";
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
