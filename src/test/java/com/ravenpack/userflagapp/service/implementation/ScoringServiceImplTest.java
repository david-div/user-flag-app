package com.ravenpack.userflagapp.service.implementation;

import com.ravenpack.userflagapp.connector.ScoringConnector;
import com.ravenpack.userflagapp.model.AggregatedUserMessageOutput;
import com.ravenpack.userflagapp.model.MessageScore;
import com.ravenpack.userflagapp.model.TranslatedMessage;
import com.ravenpack.userflagapp.model.UserMessageInput;
import com.ravenpack.userflagapp.service.MessageTranslationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScoringServiceImplTest {

    @Mock
    private MessageTranslationService messageTranslationServiceMock;

    @Mock
    private ScoringConnector scoringConnectorMock;

    @InjectMocks
    private ScoringServiceImpl sut;

    @Test
    void getMessageScoresShouldReturnTheMessageScoreMap() {
        when(scoringConnectorMock.getMessageScore(any())).thenReturn(1f);

        var actual = sut.getMessageScores(translatedMessages());
        var expected = messagesScores();

        assertThat(actual.get("1")).isEqualTo(expected.get("1"));
        assertThat(actual.get("2")).isEqualTo(expected.get("2"));
        assertThat(actual.get("3")).isEqualTo(expected.get("3"));
    }

    @Test
    void getAggregatedScoresShouldReTurnTheAggregatedScoresList() {
        when(messageTranslationServiceMock.translateMessages(any())).thenReturn(translatedMessages());
        when(scoringConnectorMock.getMessageScore(any())).thenReturn(0.5f);

        final List<AggregatedUserMessageOutput> actual = sut.getAggregatedScores(userMessageInput());

        final List<AggregatedUserMessageOutput> expected = List.of(
                new AggregatedUserMessageOutput("1", 1, 0.5f),
                new AggregatedUserMessageOutput("2", 2, 0.5f),
                new AggregatedUserMessageOutput("3", 3, 0.5f)
        );

        assertThat(actual).isEqualTo(expected);
    }

    private static List<TranslatedMessage> translatedMessages() {
        return List.of(
                new TranslatedMessage("1", "message 1"),
                new TranslatedMessage("2", "message 2 a"),
                new TranslatedMessage("2", "message 2 b"),
                new TranslatedMessage("3", "message 3 a"),
                new TranslatedMessage("3", "message 3 b"),
                new TranslatedMessage("3", "message 3 c"));
    }

    private static List<UserMessageInput> userMessageInput() {
        return List.of(
                new UserMessageInput("1", "message 1"),
                new UserMessageInput("2", "message 2 a"),
                new UserMessageInput("2", "message 2 b"),
                new UserMessageInput("3", "message 3 a"),
                new UserMessageInput("3", "message 3 b"),
                new UserMessageInput("3", "message 3 c"));
    }

    private static Map<String, MessageScore> messagesScores() {
        return new HashMap<>() {{
            put("1", new MessageScore(1, 1f));
            put("2", new MessageScore(2, 2f));
            put("3", new MessageScore(3, 3f));
        }};
    }
}