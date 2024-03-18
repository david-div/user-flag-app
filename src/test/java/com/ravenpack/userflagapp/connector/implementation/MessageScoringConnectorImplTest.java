package com.ravenpack.userflagapp.connector.implementation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MessageScoringConnectorImplTest {

    private final MessageScoringConnectorImpl sut = new MessageScoringConnectorImpl();

    @Test
    void messageScoreShouldReturnARandomFloat() {
        assertThat(sut.getMessageScore("message to be scored")).isBetween(0f, 1f);
    }
}