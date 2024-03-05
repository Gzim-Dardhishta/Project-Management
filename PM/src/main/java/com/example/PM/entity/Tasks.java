package com.example.PM.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;

@Entity
@Table(name = "tasks")
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int taskId;
    private String title;
    private String description;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime startDate;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EComplexity complexity;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EStatus status;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "teams_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Teams team;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "tasks_users",
            joinColumns = { @JoinColumn(name = "task_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") })
    private Set<User> assignedUsers = new HashSet<>();

    public Tasks() {
    }

    public Tasks(int taskId, String title, String description, LocalDateTime startDate, EComplexity complexity, Teams team, Set<User> assignedUsers) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.complexity = complexity;
        this.team = team;
        this.assignedUsers = assignedUsers;
    }

    public void addUser(User user) {
        this.assignedUsers.add(user);
    }
    public void removeUser(User user) {
        this.assignedUsers.remove(user);
    }
    public void removeAllUsers() {
        this.assignedUsers.clear();
    }

    public int getTaskId() {
        return taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public EComplexity getComplexity() {
        return complexity;
    }

    public void setComplexity(EComplexity complexity) {
        this.complexity = complexity;
    }

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    public Teams getTeam() {
        return team;
    }

    public void setTeam(Teams team) {
        this.team = team;
    }

    public Set<User> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(Set<User> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }
}
