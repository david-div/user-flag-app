package com.ravenpack.userflagapp.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.linesOf;

@SpringBootTest
class UserMessageServiceIT {

    private static final String testFilePathOutput = "src/test/resources/output/output-int-test.csv";

    @Autowired
    private UserMessageService userMessageService;

    @Test
    void getOffensiveMessageScoresCSV() {
        userMessageService.getOffensiveMessageScoresCSV();

        final File file = new File(testFilePathOutput);

        assertThat(file).exists();
        assertThat(linesOf(file)).size().isEqualTo(4);
        assertThat(linesOf(file).get(0)).contains("user_id,total_messages,avg_score");
        assertThat(linesOf(file).get(1)).contains("1,2");
        assertThat(linesOf(file).get(2)).contains("2,2");
        assertThat(linesOf(file).get(3)).contains("3,3");
    }

    @AfterAll
    static void afterAll() {
        final File file = new File(testFilePathOutput);
        if (file.exists()) {
            file.delete();
        }
    }
}