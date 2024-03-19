package com.ravenpack.userflagapp.connector;

import java.util.concurrent.CompletableFuture;

/**
 * Interface for the scoring connector
 */
public interface MessageScoreConnector {
    CompletableFuture<Float> getMessageScore(String message);
}
