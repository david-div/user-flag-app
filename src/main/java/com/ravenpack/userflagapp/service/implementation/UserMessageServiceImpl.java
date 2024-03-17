package com.ravenpack.userflagapp.service.implementation;

import com.ravenpack.userflagapp.mapper.UserMessageMapper;
import com.ravenpack.userflagapp.model.AggregatedUserMessageOutput;
import com.ravenpack.userflagapp.model.UserMessageInput;
import com.ravenpack.userflagapp.service.CSVHandlerService;
import com.ravenpack.userflagapp.service.ScoringService;
import com.ravenpack.userflagapp.service.UserMessageService;

import java.util.List;

public class UserMessageServiceImpl implements UserMessageService {

    private final CSVHandlerService csvHandlerService;
    private final ScoringService scoringService;

    public UserMessageServiceImpl(final CSVHandlerService csvHandlerService, final ScoringService scoringService) {
        this.csvHandlerService = csvHandlerService;
        this.scoringService = scoringService;
    }

    @Override
    public void getOffensiveMessageScoresCSV() {
        // getting the csv
        final List<UserMessageInput> userMessageInputs = csvHandlerService.userMessageInputs();
        // business logic
        final List<AggregatedUserMessageOutput> aggregateUserMessage = scoringService.getAggregatedScores(
                UserMessageMapper.fromUserMessageInput(userMessageInputs)
        );

        // writing the csv to file
        csvHandlerService.writeAggregateUserMessageScores(aggregateUserMessage);
    }
}
