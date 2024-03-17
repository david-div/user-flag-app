package com.ravenpack.userflagapp.service.implementation;

import com.ravenpack.userflagapp.model.MessageScore;
import com.ravenpack.userflagapp.model.UserMessageInput;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.linesOf;

class CsvHandlerServiceImplTest {

    private final static String testFilePathInput = "src/test/resources/input/input.csv";
    private final static String testFilePathOutput = "src/test/resources/output/output.csv";
    private final CsvHandlerServiceImpl sut = new CsvHandlerServiceImpl(testFilePathInput, testFilePathOutput);

    @Test
    void userMessageInputsShouldMapTheCsvToUserMessageInput() {
        final List<UserMessageInput> actual = sut.userMessageInputs();

        assertThat(actual.size()).isEqualTo(4);
        assertThat(actual.get(0).getUserId()).isEqualTo("1");
        assertThat(actual.get(0).getMessage()).isEqualTo("this is my message");
    }

    @Test
    void writeAggregateUserMessageScoresShouldWriteTheCsv() {
        final HashMap<String, MessageScore> aggregatedUserMessageOutputs = new HashMap<>() {{
            put("1", new MessageScore(2, 1.5f));
            put("2", new MessageScore(4, 3.2f));
            put("3", new MessageScore(6, 6.0f));
            put("4", new MessageScore(8, 6.2f));
        }};

        sut.writeAggregateUserMessageScores(aggregatedUserMessageOutputs);

        final File actual = new File(testFilePathOutput);

        assertThat(actual).exists();
        assertThat(linesOf(actual)).containsExactly(
                "user_id,total_messages,avg_score",
                "1,2,0.75",
                "2,4,0.8",
                "3,6,1.0",
                "4,8,0.775"
        );
    }

    @AfterAll
    static void afterAll() {
        final File file = new File(testFilePathOutput);
        if (file.exists()) {
            file.delete();
        }
    }

}