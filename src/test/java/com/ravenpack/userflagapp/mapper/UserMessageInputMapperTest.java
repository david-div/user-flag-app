package com.ravenpack.userflagapp.mapper;

import com.ravenpack.userflagapp.model.UserMessage;
import com.ravenpack.userflagapp.model.UserMessageInput;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserMessageInputMapperTest {

    @Test
    void fromUserMessageInputShouldReturnTheUserMessage() {
        final UserMessageInput userMessageInput = new UserMessageInput();
        userMessageInput.setUserId("id");
        userMessageInput.setMessage("message");

        final List<UserMessage> actual = UserMessageInputMapper.toUserMessage(List.of(userMessageInput));
        final List<UserMessage> expected = List.of(new UserMessage("id", "message"));

        assertThat(actual).isEqualTo(expected);
    }
}