package com.ravenpack.userflagapp.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

import java.util.Objects;


public class AggregatedUserMessageOutput {

    @CsvBindByName(column = "user_id")
    @CsvBindByPosition(position = 0)
    final String userId;

    @CsvBindByName(column = "total_messages")
    @CsvBindByPosition(position = 1)
    final int getTotalMessages;

    @CsvBindByName(column = "avg_score")
    @CsvBindByPosition(position = 2)
    final float avgScore;

    public AggregatedUserMessageOutput(String userId, int getTotalMessages, float avgScore) {
        this.userId = userId;
        this.getTotalMessages = getTotalMessages;
        this.avgScore = avgScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AggregatedUserMessageOutput that = (AggregatedUserMessageOutput) o;
        return getTotalMessages == that.getTotalMessages && Float.compare(avgScore, that.avgScore) == 0 && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, getTotalMessages, avgScore);
    }

    @Override
    public String toString() {
        return "AggregatedUserMessageOutput{" +
                "userId='" + userId + '\'' +
                ", getTotalMessages=" + getTotalMessages +
                ", avgScore=" + avgScore +
                '}';
    }
}