package com.ravenpack.userflagapp.mapper;

import com.ravenpack.userflagapp.model.AggregatedMessageScore;
import com.ravenpack.userflagapp.model.AggregatedMessageScoreOutput;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Mapper to map from message score
 */
public class AggregatedMessageScoreMapper {

    public static Map<String, AggregatedMessageScoreOutput> toMessageScoreOutput(final Map<String, AggregatedMessageScore> aggregatedMessageScore) {
        return aggregatedMessageScore
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> new AggregatedMessageScoreOutput(entry.getValue().totalMessages(), entry.getValue().averageScore()))
                );
    }
}
