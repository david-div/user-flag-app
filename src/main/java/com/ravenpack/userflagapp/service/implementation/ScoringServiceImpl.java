package com.ravenpack.userflagapp.service.implementation;

import com.ravenpack.userflagapp.connector.ScoringServiceConnector;
import com.ravenpack.userflagapp.model.AggregatedUserMessageOutput;
import com.ravenpack.userflagapp.model.MessageScore;
import com.ravenpack.userflagapp.model.TranslatedMessage;
import com.ravenpack.userflagapp.model.UserMessageInput;
import com.ravenpack.userflagapp.service.MessageTranslationService;
import com.ravenpack.userflagapp.service.ScoringService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoringServiceImpl implements ScoringService {

    private final MessageTranslationService messageTranslationService;
    private final ScoringServiceConnector scoringServiceConnector;

    public ScoringServiceImpl(final MessageTranslationService messageTranslationService, final ScoringServiceConnector scoringServiceConnector) {
        this.messageTranslationService = messageTranslationService;
        this.scoringServiceConnector = scoringServiceConnector;
    }

    @Override
    public List<AggregatedUserMessageOutput> getAggregatedScores(final List<UserMessageInput> userMessageInputs) {
        // make the call to the translation service
        final List<TranslatedMessage> translatedMessages = messageTranslationService.translateMessages(userMessageInputs);

        final Map<String, MessageScore> messageScores = getMessageScores(translatedMessages);

        // then make a call to the scoring service
        // TODO: need to check if opencsv can handle a map
        return aggregatedUserMessageMapper(messageScores);
    }

    /**
     * Takes the translated messages and returns the aggregate message score, which
     * consists of total number of messages and total score
     */
    public Map<String, MessageScore> getMessageScores(final List<TranslatedMessage> translatedMessages) {
        final Map<String, MessageScore> messageScore = new HashMap<>();
        for (TranslatedMessage translatedMessage : translatedMessages) {

            float score = scoringServiceConnector.messageScore(translatedMessage.message());
            final String userId = translatedMessage.userId();

            if (!messageScore.containsKey(userId)) {
                messageScore.put(userId, new MessageScore(1, score));
            } else {
                final MessageScore messageScoreUser = messageScore.get(userId);
                final int totalMessages = messageScoreUser.totalMessages();
                final float totalScore = messageScoreUser.totalScore();

                final MessageScore updatedMessageScore = new MessageScore(totalMessages + 1, totalScore + score);

                messageScore.put(userId, updatedMessageScore);
            }
        }

        return messageScore;
    }

    private List<AggregatedUserMessageOutput> aggregatedUserMessageMapper(Map<String, MessageScore> messageScoreMap) {
        return new ArrayList<>();
    }

}
