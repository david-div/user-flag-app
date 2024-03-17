package com.ravenpack.userflagapp.helper;

import java.util.Random;

/**
 * Helper class to mock the latency for external calls
 */
public class LatencyHelper {
    public static long MOCK_LATENCY_MS = mockLatencyDelayInMs();

    private static long mockLatencyDelayInMs() {
        final float minLatencyExpectedMs = 50f / 1000f;
        final float maxLatencyExpectedMs = 200f / 1000f;

        return (long) new Random().nextFloat(minLatencyExpectedMs, maxLatencyExpectedMs);
    }
}
