package com.ravenpack.userflagapp.service;

import com.ravenpack.userflagapp.connector.MessageTranslationConnector;
import com.ravenpack.userflagapp.model.TranslatedMessage;
import com.ravenpack.userflagapp.model.UserMessageInput;
import com.ravenpack.userflagapp.service.implementation.MessageTranslationServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageTranslationServiceImplTest {

    @Mock
    private MessageTranslationConnector messageTranslationConnectorMock;

    @InjectMocks
    private MessageTranslationServiceImpl sut;

    @Test
    void translateMessages() {
        final List<UserMessageInput> userMessageInputs = List.of(new UserMessageInput("1", "message"));

        when(messageTranslationConnectorMock.translate("message")).thenReturn("message translated");

        final List<TranslatedMessage> actual = sut.translateMessages(userMessageInputs);

        Assertions.assertThat(actual.get(0).message()).isEqualTo("message translated");
    }
}