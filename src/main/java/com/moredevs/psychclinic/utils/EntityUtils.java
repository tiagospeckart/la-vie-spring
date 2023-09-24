package com.moredevs.psychclinic.utils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EntityUtils {

    public static <T, R> List<T> mergeAndUpdateSessions(List<T> existingSessions, List<R> newSessions,
                                                        Function<R, Integer> newSessionIdMapper,
                                                        Function<T, Integer> existingSessionIdMapper) {
        Map<Integer, T> mergedSessions = new HashMap<>();

        // Store existing sessions in the map
        for (T existingSession : existingSessions) {
            int id = existingSessionIdMapper.apply(existingSession);
            mergedSessions.put(id, existingSession);
        }

        // Add or update sessions based on newSessions list
        for (R newSession : newSessions) {
            int id = newSessionIdMapper.apply(newSession);
            // If session already exists, it remains unchanged for now
            // If session is new, it will be added with only its ID
            mergedSessions.putIfAbsent(id, null);
        }

        // Convert map values to a list
        return new ArrayList<>(mergedSessions.values());
    }
}