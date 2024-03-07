package com.example.PM.payload.request;

import java.util.List;

public record EditTaskRequest(
        String title,
        String description,
        String status,
        List<Integer> membersId
) {
}
