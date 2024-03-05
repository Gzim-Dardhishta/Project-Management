package com.example.PM.controllers;


import com.example.PM.entity.Tasks;
import com.example.PM.payload.records.TasksResponse;
import com.example.PM.payload.request.CreateTask;
import com.example.PM.payload.response.MessageResponse;
import com.example.PM.services.TasksService;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TasksController {

    private final TasksService tasksService;

    public TasksController(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    @GetMapping
    public List<TasksResponse> getAllTasks() {
        return tasksService.getAllTasks();
    }

    @GetMapping("task/{taskId}")
    public TasksResponse getTask(@PathVariable int taskId) {
        return tasksService.getTaskById(taskId);
    }

    @PostMapping("/team/{teamId}/add-task")
    public ResponseEntity<?> addNewTask(@RequestBody CreateTask createTask, @PathVariable int teamId) {
        tasksService.createTask(createTask, teamId);

        return ResponseEntity.ok(new MessageResponse("New task added!!!"));
    }
}
