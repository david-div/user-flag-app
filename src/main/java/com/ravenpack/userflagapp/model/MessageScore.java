package com.ravenpack.userflagapp.model;

public record MessageScore(int totalMessages, float totalScore) {

    public float averageScore() {
        return totalScore / totalMessages;
    }

}
