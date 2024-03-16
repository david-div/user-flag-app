package com.ravenpack.userflagapp.service;

import com.ravenpack.userflagapp.model.TranslatedMessage;
import com.ravenpack.userflagapp.model.UserMessageInput;

import java.util.List;

public interface MessageTranslationService {
    List<TranslatedMessage> translateMessages(List<UserMessageInput> userMessageInputs);
}
