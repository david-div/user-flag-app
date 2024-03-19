package com.ravenpack.userflagapp.connector.implementation;

import com.ravenpack.userflagapp.connector.MessageTranslationConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static com.ravenpack.userflagapp.helper.LatencyHelper.MOCK_LATENCY_MS;

/**
 * An implementation of the {@link MessageTranslationConnector} that will make a call to the external service
 */
@Component
public class MessageTranslationConnectorImpl implements MessageTranslationConnector {

    public static final Logger LOG = LoggerFactory.getLogger(MessageTranslationConnectorImpl.class);

    /**
     * Currently mocked, which will just return the value of the string + " translated"
     *
     * @param message to be translated
     * @return translated message to English
     */
    @Override
    @Cacheable("translatedMessages")
    @Async("asyncTaskExecutor")
    public CompletableFuture<String> translate(String message) {
        LOG.info("Translating message: [{}]", message);
        LOG.warn("End point is currently mocked");

        try {
            TimeUnit.MILLISECONDS.sleep(MOCK_LATENCY_MS);
            return CompletableFuture.completedFuture(message + " translated");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
