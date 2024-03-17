package com.ravenpack.userflagapp.service.implementation;

import com.ravenpack.userflagapp.model.AggregatedUserMessageOutput;
import com.ravenpack.userflagapp.model.MessageScore;
import com.ravenpack.userflagapp.model.UserMessage;
import com.ravenpack.userflagapp.model.UserMessageInput;
import com.ravenpack.userflagapp.service.CsvHandlerService;
import com.ravenpack.userflagapp.service.ScoringService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserMessageServiceImplTest {

    @Mock
    private CsvHandlerService csvHandlerServiceMock;

    @Mock
    private ScoringService scoringServiceMock;

    @InjectMocks
    private UserMessageServiceImpl sut;

    @Test
    void getOffensiveMessageScoresCsvShouldCallTheCorrectServices() {
        final List<UserMessageInput> userMessageInputs = new ArrayList<>();
        final AggregatedUserMessageOutput aggregatedUserMessageOutput = new AggregatedUserMessageOutput("1", 1, 1f);

        final List<UserMessage> userMessage = new ArrayList<>();
        final Map<String, MessageScore> messageScore = Collections.EMPTY_MAP;


        when(csvHandlerServiceMock.userMessageInputs()).thenReturn(userMessageInputs);
        when(scoringServiceMock.getMessageScores(userMessage)).thenReturn(messageScore);

        sut.getOffensiveMessageScoresCsv();

        verify(csvHandlerServiceMock, atLeastOnce()).userMessageInputs();
        verify(csvHandlerServiceMock, atLeastOnce()).writeAggregateUserMessageScores(messageScore);
    }
}