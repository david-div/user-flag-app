package com.ravenpack.userflagapp.service;

import com.ravenpack.userflagapp.model.MessageScoreOutput;
import com.ravenpack.userflagapp.model.UserMessageInput;

import java.util.List;
import java.util.Map;

/**
 * Manages the reading and writing of the csv file
 */
public interface CsvHandlerService {
    List<UserMessageInput> userMessageInputs();

    void writeAggregateUserMessageScores(Map<String, MessageScoreOutput> aggregatedUserMessageOutput);
}
