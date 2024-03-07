package com.example.PM.controllers;


import com.example.PM.entity.Tasks;
import com.example.PM.payload.records.TasksResponse;
import com.example.PM.payload.request.AssignUsersToTask;
import com.example.PM.payload.request.CreateTask;
import com.example.PM.payload.request.EditTaskRequest;
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

    @GetMapping("team/{teamId}")
    public List<TasksResponse> getAllTasks(@PathVariable int teamId) {
        return tasksService.getAllTasks(teamId);
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

    @PostMapping("/task/{taskId}/assign-users")
    public ResponseEntity<?> assignUsersToTask(@RequestBody AssignUsersToTask assignUsersToTask, @PathVariable int taskId) {
        tasksService.assignUsersToTask(taskId, assignUsersToTask);

        return ResponseEntity.ok(new MessageResponse("Users assigned to task!!!"));
    }

    @PutMapping("task/{taskId}/edit")
    public ResponseEntity<?> editTask(@RequestBody EditTaskRequest editTask, @PathVariable int taskId) {
        tasksService.editTask(editTask, taskId);
        return ResponseEntity.ok(new MessageResponse("Task edited!!!"));
    }

    @DeleteMapping("task/{taskId}/delete")
    public ResponseEntity<?> deleteTask(@PathVariable int taskId) {
        tasksService.deleteTask(taskId);
        return ResponseEntity.ok(new MessageResponse("Task deleted!!!"));
    }

    @DeleteMapping("team/{teamId}/delete-tasks")
    public ResponseEntity<?> deleteTasksFromTeam(@PathVariable int teamId) {
        tasksService.deleteTasksFromTeam(teamId);
        return ResponseEntity.ok(
                new MessageResponse("Tasks from Team with id %s deleted!!!".formatted(teamId))
        );
    }
}
