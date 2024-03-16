package com.ravenpack.userflagapp.service;

import com.ravenpack.userflagapp.model.AggregatedUserMessageOutput;
import com.ravenpack.userflagapp.model.UserMessageInput;

import java.util.List;

public interface ScoringService {
    List<AggregatedUserMessageOutput> getAggregatedScores(List<UserMessageInput> userMessageInputs);
}
