package com.ravenpack.userflagapp.connector.implementation;

import com.ravenpack.userflagapp.connector.MessageTranslationConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class MessageTranslationConnectorImpl implements MessageTranslationConnector {

    public static final Logger LOG = LoggerFactory.getLogger(MessageTranslationConnectorImpl.class);

    @Cacheable
    @Override
    public String translate(String message) {
        LOG.info("Translating message: [{}]", message);
        LOG.warn("End point is currently mocked");

        return message + " translated";
    }
}
