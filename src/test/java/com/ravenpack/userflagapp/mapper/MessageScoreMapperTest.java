package com.ravenpack.userflagapp.mapper;

import com.ravenpack.userflagapp.model.MessageScore;
import com.ravenpack.userflagapp.model.MessageScoreOutput;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class MessageScoreMapperTest {

    @Test
    void toMessageScoreOutput() {
        final HashMap<String, MessageScore> messageScore = new HashMap<>() {{
            put("1", new MessageScore(10, 10f));
            put("2", new MessageScore(5, 1f));
        }};

        final HashMap<String, MessageScoreOutput> expected = new HashMap<>() {{
            put("1", new MessageScoreOutput(10, 1f));
            put("2", new MessageScoreOutput(5, 0.2f));
        }};

        final Map<String, MessageScoreOutput> actual = MessageScoreMapper.toMessageScoreOutput(messageScore);

        assertThat(actual).isEqualTo(expected);
    }
}