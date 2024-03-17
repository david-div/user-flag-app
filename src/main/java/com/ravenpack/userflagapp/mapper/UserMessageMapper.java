package com.ravenpack.userflagapp.mapper;

import com.ravenpack.userflagapp.model.UserMessage;
import com.ravenpack.userflagapp.model.UserMessageInput;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper to map the csv object to userMessage input
 */
public class UserMessageMapper {

    public static List<UserMessage> fromUserMessageInput(List<UserMessageInput> userMessageInputs) {
        return userMessageInputs
                .stream()
                .map(userMessageInput -> new UserMessage(userMessageInput.getUserId(), userMessageInput.getMessage()))
                .collect(Collectors.toList());
    }
}
