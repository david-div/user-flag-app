package com.ravenpack.userflagapp.service.implementation;

import com.ravenpack.userflagapp.connector.MessageScoringConnector;
import com.ravenpack.userflagapp.connector.MessageTranslationConnector;
import com.ravenpack.userflagapp.model.AggregatedMessageScore;
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
class MessageScoringServiceImplTest {

    @Mock
    private MessageTranslationConnector messageTranslatorConnectorMock;

    @Mock
    private MessageScoringConnector messageScoringConnectorMock;

    @InjectMocks
    private MessageScoringServiceImpl sut;

    @Test
    void getAggregatedScoresShouldReTurnTheAggregatedScores() {
        when(messageTranslatorConnectorMock.translate(any(String.class))).thenReturn("message translated");
        when(messageScoringConnectorMock.getMessageScore(any(String.class))).thenReturn(1.0f);

        final Map<String, AggregatedMessageScore> actual = sut.getAggregatedMessageScores(userMessages());
        final Map<String, AggregatedMessageScore> expected = messagesScores();

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

    private static Map<String, AggregatedMessageScore> messagesScores() {
        return new HashMap<>() {{
            put("1", new AggregatedMessageScore(1, 1f));
            put("2", new AggregatedMessageScore(2, 2f));
            put("3", new AggregatedMessageScore(3, 3f));
        }};
    }
}