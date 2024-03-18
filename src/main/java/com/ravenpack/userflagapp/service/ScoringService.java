package com.ravenpack.userflagapp.service;

import com.ravenpack.userflagapp.model.MessageScore;
import com.ravenpack.userflagapp.model.UserMessage;

import java.util.List;
import java.util.Map;

/**
 * Service used to score the user messages
 */
public interface ScoringService {
    Map<String, MessageScore> getMessageScores(List<UserMessage> userMessages);
}
