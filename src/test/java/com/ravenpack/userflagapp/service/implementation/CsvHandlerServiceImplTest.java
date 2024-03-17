package com.ravenpack.userflagapp.service.implementation;

import com.ravenpack.userflagapp.model.AggregatedUserMessageOutput;
import com.ravenpack.userflagapp.model.UserMessageInput;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.File;
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
        final List<AggregatedUserMessageOutput> aggregatedUserMessageOutputs = List.of(
                new AggregatedUserMessageOutput("1", 2, 0.5f),
                new AggregatedUserMessageOutput("2", 4, 0.6f),
                new AggregatedUserMessageOutput("3", 6, 0.7f),
                new AggregatedUserMessageOutput("4", 8, 0.8f)
        );

        sut.writeAggregateUserMessageScores(aggregatedUserMessageOutputs);

        final File actual = new File(testFilePathOutput);

        assertThat(actual).exists();
        assertThat(linesOf(actual)).containsExactly(
                "user_id,total_messages,avg_score",
                "1,2,0.5",
                "2,4,0.6",
                "3,6,0.7",
                "4,8,0.8"
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