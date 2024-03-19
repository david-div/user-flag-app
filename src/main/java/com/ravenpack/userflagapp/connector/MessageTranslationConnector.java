package com.ravenpack.userflagapp.connector;

import java.util.concurrent.CompletableFuture;

/**
 * Interface for the Message translation connector
 */
public interface MessageTranslationConnector {
    CompletableFuture<String> translate(String message);
}
