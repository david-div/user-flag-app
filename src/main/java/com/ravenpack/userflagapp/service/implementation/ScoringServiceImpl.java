package com.ravenpack.userflagapp.service.implementation;

import com.ravenpack.userflagapp.connector.MessageTranslationConnector;
import com.ravenpack.userflagapp.connector.ScoringConnector;
import com.ravenpack.userflagapp.model.MessageScore;
import com.ravenpack.userflagapp.model.UserMessage;
import com.ravenpack.userflagapp.service.ScoringService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An implementation of the {@link ScoringServiceImpl} responsible aggregating the message scores
 */
@Service
public class ScoringServiceImpl implements ScoringService {

    private final ScoringConnector scoringConnector;
    private final MessageTranslationConnector messageTranslationConnector;

    public ScoringServiceImpl(final ScoringConnector scoringConnector,
                              final MessageTranslationConnector messageTranslationConnector) {
        this.scoringConnector = scoringConnector;
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
    public Map<String, MessageScore> getMessageScores(final List<UserMessage> userMessages) {
        final Map<String, MessageScore> messageScore = new HashMap<>();
        final int totalStartingMessages = 1;

        for (UserMessage userMessage : userMessages) {
            final String translatedMessage = messageTranslationConnector.translate(userMessage.message());
            final float score = scoringConnector.getMessageScore(translatedMessage);
            final String userId = userMessage.userId();

            if (!messageScore.containsKey(userId)) {
                final MessageScore startingScore = new MessageScore(totalStartingMessages, score);
                messageScore.put(userId, startingScore);
            } else {
                incrementTotalMessageScore(messageScore, userId, score);
            }
        }

        return messageScore;
    }

    private static void incrementTotalMessageScore(final Map<String, MessageScore> messageScore, final String userId, float score) {
        final int messageIncrement = 1;

        final MessageScore currentMessageScore = messageScore.get(userId);
        final int totalMessages = currentMessageScore.totalMessages();
        final float totalScore = currentMessageScore.totalScore();

        final MessageScore updatedMessageScore = new MessageScore(totalMessages + messageIncrement, totalScore + score);

        messageScore.put(userId, updatedMessageScore);
    }
}
