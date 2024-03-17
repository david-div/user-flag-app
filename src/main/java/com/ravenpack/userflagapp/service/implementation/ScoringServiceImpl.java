package com.ravenpack.userflagapp.service.implementation;

import com.ravenpack.userflagapp.connector.MessageTranslationConnector;
import com.ravenpack.userflagapp.connector.ScoringConnector;
import com.ravenpack.userflagapp.model.AggregatedUserMessageOutput;
import com.ravenpack.userflagapp.model.MessageScore;
import com.ravenpack.userflagapp.model.UserMessage;
import com.ravenpack.userflagapp.service.ScoringService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service responsible for aggregation the message scores
 */
@Service
public class ScoringServiceImpl implements ScoringService {

    private final ScoringConnector scoringConnector;
    private final MessageTranslationConnector messageTranslationConnector;

    public ScoringServiceImpl(final ScoringConnector scoringConnector, MessageTranslationConnector messageTranslationConnector) {
        this.scoringConnector = scoringConnector;
        this.messageTranslationConnector = messageTranslationConnector;
    }

    @Override
    public List<AggregatedUserMessageOutput> getAggregatedScores(final List<UserMessage> userMessages) {
        final Map<String, MessageScore> messageScores = getMessageScores(userMessages);

//      TODO: need to check if opencsv can handle a map
        return aggregatedUserMessageMapper(messageScores);
    }

    /**
     * Takes the translated messages and returns the aggregate message score, which
     * consists of total number of messages and total score
     */
    public Map<String, MessageScore> getMessageScores(final List<UserMessage> userMessages) {
        final Map<String, MessageScore> messageScore = new HashMap<>();
        final int totalStartingMessages = 1;
        final int messageIncrement = 1;

        for (UserMessage userMessage : userMessages) {
            final String translatedMessage = messageTranslationConnector.translate(userMessage.message());
            final float score = scoringConnector.getMessageScore(translatedMessage);
            final String userId = userMessage.userId();

            if (!messageScore.containsKey(userId)) {
                final MessageScore startingScore = new MessageScore(totalStartingMessages, score);
                messageScore.put(userId, startingScore);
            } else {
                final MessageScore messageScoreUser = messageScore.get(userId);
                final int totalMessages = messageScoreUser.totalMessages();
                final float totalScore = messageScoreUser.totalScore();

                final MessageScore updatedMessageScore = new MessageScore(totalMessages + messageIncrement, totalScore + score);

                messageScore.put(userId, updatedMessageScore);
            }
        }

        return messageScore;
    }

    /**
     * TODO: consider removing this mapper and write straight to the csv
     */
    private List<AggregatedUserMessageOutput> aggregatedUserMessageMapper(Map<String, MessageScore> messageScoreMap) {
        return messageScoreMap
                .entrySet()
                .stream()
                .map(messageScore -> new AggregatedUserMessageOutput(messageScore.getKey(), messageScore.getValue().totalMessages(), messageScore.getValue().averageScore()))
                .collect(Collectors.toList());
    }

}
