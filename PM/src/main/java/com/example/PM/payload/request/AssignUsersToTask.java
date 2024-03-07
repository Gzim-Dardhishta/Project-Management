package com.example.PM.payload.request;

import java.util.List;

public record AssignUsersToTask(
        List<Integer> usersId
) {
}
