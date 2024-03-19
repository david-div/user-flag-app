package com.ravenpack.userflagapp.service.implementation;

import com.ravenpack.userflagapp.connector.MessageScoreConnector;
import com.ravenpack.userflagapp.connector.MessageTranslationConnector;
import com.ravenpack.userflagapp.model.AggregatedMessageScore;
import com.ravenpack.userflagapp.model.UserMessage;
import com.ravenpack.userflagapp.service.MessageScoringService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * An implementation of the {@link MessageScoringServiceImpl} responsible aggregating the message scores
 */
@Service
public class MessageScoringServiceImpl implements MessageScoringService {

    public static final Logger LOG = LoggerFactory.getLogger(MessageScoringServiceImpl.class);
    private final MessageScoreConnector messageScoreConnector;
    private final MessageTranslationConnector messageTranslationConnector;

    public MessageScoringServiceImpl(final MessageScoreConnector messageScoreConnector,
                                     final MessageTranslationConnector messageTranslationConnector) {
        this.messageScoreConnector = messageScoreConnector;
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
        LOG.info("Aggregating the user messages [{}]", userMessages);
        final Map<String, AggregatedMessageScore> messageScore = new HashMap<>();
        final int totalStartingMessages = 1;

        userMessages.parallelStream().forEach(userMessage -> {
            final CompletableFuture<String> translatedMessage = messageTranslationConnector.translate(userMessage.message());

            final float score = getScore(translatedMessage);
            final String userId = userMessage.userId();

            if (!messageScore.containsKey(userId)) {
                final AggregatedMessageScore startingScore = new AggregatedMessageScore(totalStartingMessages, score);
                messageScore.put(userId, startingScore);
            } else {
                incrementTotalMessageScore(messageScore, userId, score);
            }
        });

        return messageScore;
    }

    private float getScore(final CompletableFuture<String> translatedMessage) {
        try {
            return messageScoreConnector.getMessageScore(translatedMessage.get()).join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
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
