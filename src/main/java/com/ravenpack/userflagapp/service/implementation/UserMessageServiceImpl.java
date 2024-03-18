package com.ravenpack.userflagapp.service.implementation;

import com.ravenpack.userflagapp.mapper.MessageScoreMapper;
import com.ravenpack.userflagapp.mapper.UserMessageInputMapper;
import com.ravenpack.userflagapp.model.MessageScore;
import com.ravenpack.userflagapp.model.UserMessageInput;
import com.ravenpack.userflagapp.service.CsvHandlerService;
import com.ravenpack.userflagapp.service.ScoringService;
import com.ravenpack.userflagapp.service.UserMessageService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * An implementation of the {@link UserMessageServiceImpl} the entry point and orchestration
 * of the application
 */
@Service
public class UserMessageServiceImpl implements UserMessageService {

    private final CsvHandlerService csvHandlerService;
    private final ScoringService scoringService;

    public UserMessageServiceImpl(final CsvHandlerService csvHandlerService, final ScoringService scoringService) {
        this.csvHandlerService = csvHandlerService;
        this.scoringService = scoringService;
    }

    /**
     * Called on startup, which orchestrates the reading, aggregating messages
     * and writing of the csv.
     */
    @Override
    public void getOffensiveMessageScoresCsv() {
        final List<UserMessageInput> userMessageInputs = csvHandlerService.userMessageInputs();

        final Map<String, MessageScore> aggregateUserMessage = scoringService.getMessageScores(
                UserMessageInputMapper.toUserMessage(userMessageInputs)
        );

        csvHandlerService.writeAggregateUserMessageScores(
                MessageScoreMapper.toMessageScoreOutput(aggregateUserMessage)
        );
    }
}
