package com.example.PM.payload.request;

public record CreateTeam(
        String groupName,
        int professorId
) {
}
