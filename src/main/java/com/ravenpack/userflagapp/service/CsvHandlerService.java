package com.ravenpack.userflagapp.service;

import com.ravenpack.userflagapp.model.MessageScore;
import com.ravenpack.userflagapp.model.UserMessageInput;

import java.util.List;
import java.util.Map;

public interface CsvHandlerService {
    List<UserMessageInput> userMessageInputs();

    void writeAggregateUserMessageScores(Map<String, MessageScore> aggregatedUserMessageOutput);
}
