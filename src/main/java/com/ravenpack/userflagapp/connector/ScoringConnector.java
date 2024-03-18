package com.ravenpack.userflagapp.connector;

/**
 * Interface for the scoring connector
 */
public interface ScoringConnector {
    float getMessageScore(String message);
}
