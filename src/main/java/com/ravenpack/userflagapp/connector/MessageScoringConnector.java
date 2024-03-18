package com.ravenpack.userflagapp.connector;

/**
 * Interface for the scoring connector
 */
public interface MessageScoringConnector {
    float getMessageScore(String message);
}
