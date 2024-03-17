package com.ravenpack.userflagapp.service.implementation;

import com.ravenpack.userflagapp.connector.MessageTranslationConnector;
import com.ravenpack.userflagapp.connector.ScoringConnector;
import com.ravenpack.userflagapp.model.MessageScore;
import com.ravenpack.userflagapp.model.UserMessage;
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
    private MessageTranslationConnector messageTranslatorConnectorMock;

    @Mock
    private ScoringConnector scoringConnectorMock;

    @InjectMocks
    private ScoringServiceImpl sut;

    @Test
    void getAggregatedScoresShouldReTurnTheAggregatedScores() {
        when(messageTranslatorConnectorMock.translate(any(String.class))).thenReturn("message translated");
        when(scoringConnectorMock.getMessageScore(any(String.class))).thenReturn(1.0f);

        final Map<String, MessageScore> actual = sut.getMessageScores(userMessages());
        final Map<String, MessageScore> expected = messagesScores();

        assertThat(actual).isEqualTo(expected);
    }

    private static List<UserMessage> userMessages() {
        return List.of(
                new UserMessage("1", "message 1"),
                new UserMessage("2", "message 2 a"),
                new UserMessage("2", "message 2 b"),
                new UserMessage("3", "message 3 a"),
                new UserMessage("3", "message 3 b"),
                new UserMessage("3", "message 3 c"));
    }

    private static Map<String, MessageScore> messagesScores() {
        return new HashMap<>() {{
            put("1", new MessageScore(1, 1f));
            put("2", new MessageScore(2, 2f));
            put("3", new MessageScore(3, 3f));
        }};
    }
}