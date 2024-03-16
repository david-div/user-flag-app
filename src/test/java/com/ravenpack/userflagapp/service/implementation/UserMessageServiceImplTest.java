package com.ravenpack.userflagapp.service.implementation;

import com.ravenpack.userflagapp.model.AggregatedUserMessageOutput;
import com.ravenpack.userflagapp.model.UserMessageInput;
import com.ravenpack.userflagapp.service.CSVHandlerService;
import com.ravenpack.userflagapp.service.ScoringService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserMessageServiceImplTest {

    @Mock
    private CSVHandlerService csvHandlerServiceMock;

    @Mock
    private ScoringService scoringServiceMock;

    @InjectMocks
    private UserMessageServiceImpl sut;

    @Test
    void getOffensiveMessageScoresCSVShouldCallTheCorrectServices() {
        final List<UserMessageInput> userMessageInputs = new ArrayList<>();
        final AggregatedUserMessageOutput aggregatedUserMessageOutput = new AggregatedUserMessageOutput("1", 1, 1f);

        when(csvHandlerServiceMock.userMessageInputs()).thenReturn(userMessageInputs);
        when(scoringServiceMock.getAggregatedScores(userMessageInputs)).thenReturn(List.of(aggregatedUserMessageOutput));

        sut.getOffensiveMessageScoresCSV();

        verify(csvHandlerServiceMock, atLeastOnce()).userMessageInputs();
        verify(csvHandlerServiceMock, atLeastOnce()).writeAggregateUserMessageScores(List.of(aggregatedUserMessageOutput));
    }
}