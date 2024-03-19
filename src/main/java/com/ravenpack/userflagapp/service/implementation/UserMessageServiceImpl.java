package com.ravenpack.userflagapp.service.implementation;

import com.ravenpack.userflagapp.mapper.AggregatedMessageScoreMapper;
import com.ravenpack.userflagapp.mapper.UserMessageInputMapper;
import com.ravenpack.userflagapp.model.AggregatedMessageScore;
import com.ravenpack.userflagapp.model.UserMessageInput;
import com.ravenpack.userflagapp.service.CsvHandlerService;
import com.ravenpack.userflagapp.service.MessageScoringService;
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
    private final MessageScoringService messageScoringService;

    public UserMessageServiceImpl(final CsvHandlerService csvHandlerService, final MessageScoringService messageScoringService) {
        this.csvHandlerService = csvHandlerService;
        this.messageScoringService = messageScoringService;
    }

    /**
     * Called on startup, which orchestrates the reading, aggregating messages
     * and writing of the csv.
     */
    @Override
    public void getOffensiveAggregatedMessageScoresCsv() {
        final List<UserMessageInput> userMessageInputs = csvHandlerService.userMessageInputs();

        final Map<String, AggregatedMessageScore> aggregateUserMessage;

        try {
            aggregateUserMessage = messageScoringService.getAggregatedMessageScores(
                    UserMessageInputMapper.toUserMessage(userMessageInputs)
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        csvHandlerService.writeAggregateUserMessageScores(
                AggregatedMessageScoreMapper.toMessageScoreOutput(aggregateUserMessage)
        );
    }
}
