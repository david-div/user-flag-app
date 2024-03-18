package com.ravenpack.userflagapp.runner;

import com.ravenpack.userflagapp.service.UserMessageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Run on startup of the application
 */
@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    private final UserMessageService userMessageService;

    public CommandLineAppStartupRunner(final UserMessageService userMessageService) {
        this.userMessageService = userMessageService;
    }

    @Override
    public void run(String... args) {
        userMessageService.getOffensiveMessageScoresCsv();
    }
}
