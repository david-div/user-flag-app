package com.ravenpack.userflagapp.model;

public record AggregatedMessageScore(int totalMessages, float totalScore) {

    public float averageScore() {
        return totalScore / totalMessages;
    }

}
