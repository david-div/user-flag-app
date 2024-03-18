package com.ravenpack.userflagapp.service.implementation;

import com.ravenpack.userflagapp.connector.MessageScoringConnector;
import com.ravenpack.userflagapp.connector.MessageTranslationConnector;
import com.ravenpack.userflagapp.model.AggregatedMessageScore;
import com.ravenpack.userflagapp.model.UserMessage;
import com.ravenpack.userflagapp.service.MessageScoringService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An implementation of the {@link MessageScoringServiceImpl} responsible aggregating the message scores
 */
@Service
public class MessageScoringServiceImpl implements MessageScoringService {

    private final MessageScoringConnector messageScoringConnector;
    private final MessageTranslationConnector messageTranslationConnector;

    public MessageScoringServiceImpl(final MessageScoringConnector messageScoringConnector,
                                     final MessageTranslationConnector messageTranslationConnector) {
        this.messageScoringConnector = messageScoringConnector;
        this.messageTranslationConnector = messageTranslationConnector;
    }

    /**
     * Aggregates the user messages, first calling the translation service to
     * translate the message, the making a call to get the message score.
     *
     * @param userMessages a list containing the user id and user message
     * @return a map by userId, with the total number of messages, score and average
     */
    @Override
    public Map<String, AggregatedMessageScore> getAggregatedMessageScores(final List<UserMessage> userMessages) {
        final Map<String, AggregatedMessageScore> messageScore = new HashMap<>();
        final int totalStartingMessages = 1;

        for (UserMessage userMessage : userMessages) {
            final String translatedMessage = messageTranslationConnector.translate(userMessage.message());
            final float score = messageScoringConnector.getMessageScore(translatedMessage);
            final String userId = userMessage.userId();

            if (!messageScore.containsKey(userId)) {
                final AggregatedMessageScore startingScore = new AggregatedMessageScore(totalStartingMessages, score);
                messageScore.put(userId, startingScore);
            } else {
                incrementTotalMessageScore(messageScore, userId, score);
            }
        }

        return messageScore;
    }

    private static void incrementTotalMessageScore(final Map<String, AggregatedMessageScore> messageScore, final String userId, float score) {
        final int messageIncrement = 1;

        final AggregatedMessageScore currentAggregatedMessageScore = messageScore.get(userId);
        final int totalMessages = currentAggregatedMessageScore.totalMessages();
        final float totalScore = currentAggregatedMessageScore.totalScore();

        final AggregatedMessageScore updatedAggregatedMessageScore = new AggregatedMessageScore(totalMessages + messageIncrement, totalScore + score);

        messageScore.put(userId, updatedAggregatedMessageScore);
    }
}
