package com.ravenpack.userflagapp.service;

import com.ravenpack.userflagapp.model.AggregatedUserMessageOutput;
import com.ravenpack.userflagapp.model.UserMessageInput;

import java.util.List;

public interface CsvHandlerService {
    List<UserMessageInput> userMessageInputs();

    void writeAggregateUserMessageScores(List<AggregatedUserMessageOutput> aggregatedUserMessageOutput);
}
