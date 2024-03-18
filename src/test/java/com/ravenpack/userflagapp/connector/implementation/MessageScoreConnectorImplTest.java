package com.ravenpack.userflagapp.connector.implementation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MessageScoreConnectorImplTest {

    private final MessageScoreConnectorImpl sut = new MessageScoreConnectorImpl();

    @Test
    void messageScoreShouldReturnARandomFloat() {
        assertThat(sut.getMessageScore("message to be scored")).isBetween(0f, 1f);
    }
}