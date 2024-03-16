package com.ravenpack.userflagapp.service.implementation;

import com.ravenpack.userflagapp.connector.ScoringServiceConnector;
import com.ravenpack.userflagapp.model.MessageScore;
import com.ravenpack.userflagapp.model.TranslatedMessage;
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
    private MessageTranslationService messageTranslationService;

    @Mock
    private ScoringServiceConnector scoringServiceConnector;

    @InjectMocks
    private ScoringServiceImpl sut;

    @Test
    void getMessageScoresShouldReturnTheMessageScoreMap() {
        final List<TranslatedMessage> translatedMessages = getTranslatedMessages();

        when(scoringServiceConnector.messageScore(any())).thenReturn(1f);

        final Map<String, MessageScore> actual = sut.getMessageScores(translatedMessages);
        final Map<String, MessageScore> expected = getMessagesScores();

        assertThat(actual.get("1")).isEqualTo(expected.get("1"));
        assertThat(actual.get("2")).isEqualTo(expected.get("2"));
        assertThat(actual.get("3")).isEqualTo(expected.get("3"));
    }

    private static List<TranslatedMessage> getTranslatedMessages() {
        return List.of(
                new TranslatedMessage("1", "message 1"),
                new TranslatedMessage("2", "message 2 a"),
                new TranslatedMessage("2", "message 2 b"),
                new TranslatedMessage("3", "message 3 a"),
                new TranslatedMessage("3", "message 3 b"),
                new TranslatedMessage("3", "message 3 c"));
    }

    private static Map<String, MessageScore> getMessagesScores() {
        return new HashMap<>() {{
            put("1", new MessageScore(1, 1f));
            put("2", new MessageScore(2, 2f));
            put("3", new MessageScore(3, 3f));
        }};
    }
}