package com.ravenpack.userflagapp.connector;

import com.ravenpack.userflagapp.connector.implementation.MessageTranslationConnectorImpl;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MessageTranslationConnectorImplTest {

    private final MessageTranslationConnectorImpl sut = new MessageTranslationConnectorImpl();

    @Test
    void translateShouldTranslateTheMessage() {
        assertThat(sut.translate("message")).isEqualTo("message translated");
    }
}