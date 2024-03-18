package com.ravenpack.userflagapp.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.linesOf;

@SpringBootTest
class UserMessageServiceIT {

    private static final String testFilePathOutput = "src/test/resources/output/output-int-test.csv";

    @Autowired
    private UserMessageService userMessageService;

    @Test
    void getOffensiveMessageScoresCSVShouldWriteTheCsv() {
        userMessageService.getOffensiveMessageScoresCsv();

        final File file = new File(testFilePathOutput);

        assertThat(file).exists();
        assertThat(linesOf(file)).size().isEqualTo(4);

        final List<String> lineValues = List.of("user_id,total_messages,avg_score", "1,2", "2,2", "3,3");

        for (int i = 0; i < lineValues.size(); i++) {
            assertThat(linesOf(file).get(i)).contains(lineValues.get(i));
        }
    }

    @AfterAll
    static void afterAll() {
        final File file = new File(testFilePathOutput);
        if (file.exists()) {
            file.delete();
        }
    }
}