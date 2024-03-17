package com.ravenpack.userflagapp.runner;

import com.ravenpack.userflagapp.service.UserMessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommandLineAppStartupRunnerTest {

    @Autowired
    UserMessageService userMessageService;

    @Test
    void run() {
        new CommandLineAppStartupRunner(userMessageService).run();
    }
}