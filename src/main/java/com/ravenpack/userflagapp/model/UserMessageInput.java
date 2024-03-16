package com.ravenpack.userflagapp.model;

import com.opencsv.bean.CsvBindByName;

import java.util.Objects;

public class UserMessageInput {

    @CsvBindByName(column = "user_id", required = true)
    private String userId;

    @CsvBindByName(column = "message", required = true)
    private String message;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserMessageInput that = (UserMessageInput) o;
        return Objects.equals(userId, that.userId) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, message);
    }
}
