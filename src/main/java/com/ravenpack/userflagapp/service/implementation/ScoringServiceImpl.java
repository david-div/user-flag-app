package com.ravenpack.userflagapp.service.implementation;

import com.ravenpack.userflagapp.connector.ScoringConnector;
import com.ravenpack.userflagapp.model.AggregatedUserMessageOutput;
import com.ravenpack.userflagapp.model.MessageScore;
import com.ravenpack.userflagapp.model.TranslatedMessage;
import com.ravenpack.userflagapp.model.UserMessageInput;
import com.ravenpack.userflagapp.service.MessageTranslationService;
import com.ravenpack.userflagapp.service.ScoringService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service responsible for aggregation the message scores
 */
public class ScoringServiceImpl implements ScoringService {

    private final MessageTranslationService messageTranslationService;
    private final ScoringConnector scoringConnector;

    public ScoringServiceImpl(final MessageTranslationService messageTranslationService, final ScoringConnector scoringConnector) {
        this.messageTranslationService = messageTranslationService;
        this.scoringConnector = scoringConnector;
    }

    @Override
    public List<AggregatedUserMessageOutput> getAggregatedScores(final List<UserMessageInput> userMessageInputs) {
        final List<TranslatedMessage> translatedMessages = messageTranslationService.translateMessages(userMessageInputs);

        final Map<String, MessageScore> messageScores = getMessageScores(translatedMessages);

//      TODO: need to check if opencsv can handle a map
        return aggregatedUserMessageMapper(messageScores);
    }

    /**
     * Takes the translated messages and returns the aggregate message score, which
     * consists of total number of messages and total score
     */
    public Map<String, MessageScore> getMessageScores(final List<TranslatedMessage> translatedMessages) {
        final Map<String, MessageScore> messageScore = new HashMap<>();
        final int totalStartingMessages = 1;
        final int messageIncrement = 1;

        for (TranslatedMessage translatedMessage : translatedMessages) {

            float score = scoringConnector.getMessageScore(translatedMessage.message());
            final String userId = translatedMessage.userId();

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
