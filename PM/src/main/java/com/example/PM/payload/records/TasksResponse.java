package com.example.PM.payload.records;

import com.example.PM.entity.Teams;

import java.time.LocalDateTime;
import java.util.List;

public record TasksResponse(
        int taskId,
        String title,
        String description,
        LocalDateTime startTime,
        String complexity,
        TeamInfo team,
        List<AssignedUsers> assignedUsers
){}
