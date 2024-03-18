package com.ravenpack.userflagapp.mapper;

import com.ravenpack.userflagapp.model.AggregatedMessageScore;
import com.ravenpack.userflagapp.model.AggregatedMessageScoreOutput;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class AggregatedMessageScoreMapperTest {

    @Test
    void toMessageScoreOutput() {
        final HashMap<String, AggregatedMessageScore> messageScore = new HashMap<>() {{
            put("1", new AggregatedMessageScore(10, 10f));
            put("2", new AggregatedMessageScore(5, 1f));
        }};

        final HashMap<String, AggregatedMessageScoreOutput> expected = new HashMap<>() {{
            put("1", new AggregatedMessageScoreOutput(10, 1f));
            put("2", new AggregatedMessageScoreOutput(5, 0.2f));
        }};

        final Map<String, AggregatedMessageScoreOutput> actual = AggregatedMessageScoreMapper.toMessageScoreOutput(messageScore);

        assertThat(actual).isEqualTo(expected);
    }
}