package com.ravenpack.userflagapp.model;

public record AggregatedUserMessageOutput(String userId, int totalMessages, float avgScore) {}
