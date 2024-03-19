package com.ravenpack.userflagapp.service;

import com.ravenpack.userflagapp.model.AggregatedMessageScore;
import com.ravenpack.userflagapp.model.UserMessage;

import java.util.List;
import java.util.Map;

/**
 * Service used to score the user messages
 */
public interface MessageScoringService {
    Map<String, AggregatedMessageScore> getAggregatedMessageScores(List<UserMessage> userMessages);
}
