package com.ravenpack.userflagapp.connector;

/**
 * Interface for the scoring connector
 */
public interface MessageScoreConnector {
    float getMessageScore(String message);
}
