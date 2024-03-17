package com.ravenpack.userflagapp.service.implementation;

import com.ravenpack.userflagapp.model.MessageScoreOutput;
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
        assertThat(actual.get(0).userId()).isEqualTo("1");
        assertThat(actual.get(0).message()).isEqualTo("this is my message");
    }

    @Test
    void writeAggregateUserMessageScoresShouldWriteTheCsv() {
        final HashMap<String, MessageScoreOutput> aggregatedMessageScoreOutput = new HashMap<>() {{
            put("1", new MessageScoreOutput(2, 1.5f));
            put("2", new MessageScoreOutput(4, 3.2f));
            put("3", new MessageScoreOutput(6, 6.0f));
            put("4", new MessageScoreOutput(8, 6.2f));
        }};

        sut.writeAggregateUserMessageScores(aggregatedMessageScoreOutput);

        final File actual = new File(testFilePathOutput);

        assertThat(actual).exists();
        assertThat(linesOf(actual)).containsExactly(
                "user_id,total_messages,avg_score",
                "1,2,1.5",
                "2,4,3.2",
                "3,6,6.0",
                "4,8,6.2"
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