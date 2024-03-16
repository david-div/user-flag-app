package com.ravenpack.userflagapp.service.implementation;

import com.ravenpack.userflagapp.model.UserMessageInput;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CSVHandlerServiceImplTest {

    private final static String testFilePath = "src/test/resources/input/input.csv";
    private final CSVHandlerServiceImpl sut = new CSVHandlerServiceImpl(testFilePath);

    @Test
    void userMessageInputsShouldMapTheCSVToUserMessageInput() {
        final List<UserMessageInput> actual = sut.userMessageInputs();

        assertThat(actual.size()).isEqualTo(4);
        assertThat(actual.get(0).getUserId()).isEqualTo("1");
        assertThat(actual.get(0).getMessage()).isEqualTo("this is my message");
    }

    @Test
    void writeAggregateUserMessageScores() {
    }
}