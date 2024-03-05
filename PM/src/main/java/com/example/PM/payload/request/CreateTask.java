package com.example.PM.payload.request;

import java.util.List;

public record CreateTask(
        String title,
        String description,
        String complexity,
        String status,
        List<Integer> membersId
) {
}
