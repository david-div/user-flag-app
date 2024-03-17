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

    /**
     * Takes the user messages and returns the aggregate message score, which
     * consists of total number of messages and total score
     */
    @Override
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
}
