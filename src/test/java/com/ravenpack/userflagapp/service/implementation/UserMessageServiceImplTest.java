package com.ravenpack.userflagapp.service.implementation;

import com.ravenpack.userflagapp.service.CSVHandlerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserMessageServiceImplTest {

    @Mock
    private CSVHandlerService csvHandlerServiceMock;

    @InjectMocks
    private UserMessageServiceImpl sut;

    @Test
    void getOffensiveMessageScoresCSV() {
        sut.getOffensiveMessageScoresCSV();

        verify(csvHandlerServiceMock, atLeastOnce()).userMessageInputs();
    }
}