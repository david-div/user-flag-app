package com.ravenpack.userflagapp.service;

import com.ravenpack.userflagapp.model.TranslatedMessage;
import com.ravenpack.userflagapp.model.UserMessage;

import java.util.List;

public interface MessageTranslationService {
    List<TranslatedMessage> translateMessages(List<UserMessage> userMessageInputs);
}
