package com.ravenpack.userflagapp.connector.implementation;

import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

class MessageScoreConnectorImplTest {

    private final MessageScoreConnectorImpl sut = new MessageScoreConnectorImpl();

    @Test
    void messageScoreShouldReturnARandomFloat() {
        final Predicate<Float> floatPredicate = value -> value >= 0f && value <= 1f;

        assertThat(sut.getMessageScore("message to be scored")).isCompletedWithValueMatching(floatPredicate);
    }
}