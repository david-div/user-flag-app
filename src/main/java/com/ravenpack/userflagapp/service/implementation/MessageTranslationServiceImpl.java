package com.ravenpack.userflagapp.service.implementation;

import com.ravenpack.userflagapp.connector.MessageTranslationConnector;
import com.ravenpack.userflagapp.model.TranslatedMessage;
import com.ravenpack.userflagapp.model.UserMessage;
import com.ravenpack.userflagapp.service.MessageTranslationService;

import java.util.ArrayList;
import java.util.List;

/**
 * Service responsible for getting the translated message
 */
public class MessageTranslationServiceImpl implements MessageTranslationService {

    private final MessageTranslationConnector messageTranslationConnector;

    public MessageTranslationServiceImpl(final MessageTranslationConnector messageTranslationConnector) {
        this.messageTranslationConnector = messageTranslationConnector;
    }

    /**
     * We could call the scoring service here in the same loop?
     */
    @Override
    public List<TranslatedMessage> translateMessages(List<UserMessage> userMessage) {
        final List<TranslatedMessage> translatedMessages = new ArrayList<>();

        for (final UserMessage userMessageInput : userMessage) {
            final String translatedMessage = translateMessage(userMessageInput.message());
            translatedMessages.add(
                    new TranslatedMessage(userMessageInput.userId(), translatedMessage)
            );
        }

        return translatedMessages;
    }

    private String translateMessage(String message) {
        return messageTranslationConnector.translate(message);
    }
}
