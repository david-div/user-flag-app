package com.ravenpack.userflagapp.mapper;

import com.ravenpack.userflagapp.model.MessageScore;
import com.ravenpack.userflagapp.model.MessageScoreOutput;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Mapper to map the message score to the message score output
 */
public class MessageScoreMapper {

    public static Map<String, MessageScoreOutput> toMessageScoreOutput(final Map<String, MessageScore> messageScore) {
        return messageScore
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> new MessageScoreOutput(entry.getValue().totalMessages(), entry.getValue().averageScore()))
                );
    }
}
