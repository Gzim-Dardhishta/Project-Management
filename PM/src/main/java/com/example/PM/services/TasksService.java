package com.example.PM.services;

import com.example.PM.entity.*;
import com.example.PM.exeptions.ResourceNotFoundException;
import com.example.PM.payload.records.AssignedUsers;
import com.example.PM.payload.records.TasksResponse;
import com.example.PM.payload.records.TeamInfo;
import com.example.PM.payload.request.AssignUsersToTask;
import com.example.PM.payload.request.CreateTask;
import com.example.PM.payload.request.EditTaskRequest;
import com.example.PM.repository.TasksRepository;
import com.example.PM.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TasksService {

    private final TasksRepository tasksRepository;
    private final TeamsService teamsService;
    private final UserRepository userRepository;

    public TasksService(TasksRepository tasksRepository, TeamsService teamsService, UserRepository userRepository) {
        this.tasksRepository = tasksRepository;
        this.teamsService = teamsService;
        this.userRepository = userRepository;
    }



    public List<TasksResponse> getAllTasks(int teamId) {
        List<Tasks> tasks = tasksRepository.findByTeamId(teamId);
        return tasks.stream()
                .map(this::mapTaskToTasksResponse)
                .collect(Collectors.toList());
    }

    public TasksResponse getTaskById(int taskId) {
        Tasks task = tasksRepository.findById(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task with id %s not found".formatted(taskId))
                );

        return mapTaskToTasksResponse(task);
    }

    public Tasks findTaskById(int taskId) {
        return tasksRepository.findById(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task with id %s not found".formatted(taskId))
                );
    }

    private TasksResponse mapTaskToTasksResponse(Tasks task) {
        List<AssignedUsers> assignedUsersList = task.getAssignedUsers().stream()
                .map(user -> new AssignedUsers(
                        user.getId(),
                        user.getName(),
                        user.getLastName(),
                        user.getEmail()
                )).collect(Collectors.toList());

        TeamInfo teamInfo = new TeamInfo(
                task.getTeam().getId(),
                task.getTeam().getGroupName(),
                task.getTeam().getTeamAdmin(),
                task.getTeam().getProfessor()
        );

        return new TasksResponse(
                task.getTaskId(),
                task.getTitle(),
                task.getDescription(),
                task.getStartDate(),
                task.getComplexity().toString(),
                teamInfo,
                assignedUsersList
        );
    }

    private void addUsersToTask(Tasks task, List<Integer> memberIds) {
        List<Integer> addedMembers = memberIds;

        if (addedMembers.size() == 1) {
            User user = userRepository.findById(addedMembers.get(0)).orElseThrow(() -> new ResourceNotFoundException("User not found"));
            task.addUser(user);
        } else {{
            Set<User> assignedUsers = new HashSet<>();
            addedMembers.forEach(m -> {
                User user = userRepository.findById(m).orElseThrow(() -> new ResourceNotFoundException("User not found"));
                assignedUsers.add(user);
            });
            task.setAssignedUsers(assignedUsers);
        }}
    }

    public void createTask(CreateTask taskRequest, int teamId) {

        Teams team = teamsService.getTeam(teamId);

        Tasks task = new Tasks();

        task.setTitle(taskRequest.title());
        task.setDescription(taskRequest.description());
        task.setTeam(team);
        task.setStartDate(LocalDateTime.now());
        task.setComplexity(
                switch (taskRequest.complexity()) {
                    case "MEDIUM" -> EComplexity.MEDIUM;
                    case "HARD" -> EComplexity.HARD;
                    default -> EComplexity.SIMPLE;
                }
        );
        task.setStatus(
                switch (taskRequest.status()) {
                    case "TODO" -> EStatus.TODO;
                    case "COMPLETED" -> EStatus.COMPLETED;
                    default -> EStatus.IN_PROGRESS;
                }
        );

        addUsersToTask(task, taskRequest.membersId());

        tasksRepository.save(task);
    }

    public void assignUsersToTask(int taskId, AssignUsersToTask assignUsersToTask) {

        Tasks task = findTaskById(taskId);

        addUsersToTask(task, assignUsersToTask.usersId());

        tasksRepository.save(task);
    }

    public void editTask(EditTaskRequest editTask, int taskId) {
        Tasks task = findTaskById(taskId);

        task.setTitle(editTask.title());
        task.setDescription(editTask.description());
        task.setStatus(
                switch (editTask.status()) {
                    case "TODO" -> EStatus.TODO;
                    case "COMPLETED" -> EStatus.COMPLETED;
                    default -> EStatus.IN_PROGRESS;
                }
        );

        addUsersToTask(task, editTask.membersId());

        tasksRepository.save(task);
    }

    public void deleteTask(int taskId) {
        Tasks taskToDelete = findTaskById(taskId);
        tasksRepository.delete(taskToDelete);
    }

    public void deleteTasksFromTeam(int teamId) {
        List<Tasks> tasksToDelete = tasksRepository.findByTeamId(teamId);
        tasksRepository.deleteAll(tasksToDelete);
    }
}
