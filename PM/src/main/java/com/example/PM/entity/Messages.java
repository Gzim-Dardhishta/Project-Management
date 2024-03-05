package com.example.PM.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String messages;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime time;

    private String author;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "teams_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Teams team;

    public Messages() {
    }

    public Messages(int id, String messages, LocalDateTime time, String author) {
        this.id = id;
        this.messages = messages;
        this.time = time;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Teams getTeam() {
        return team;
    }

    public void setTeam(Teams team) {
        this.team = team;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Messages messages1)) return false;
        return getId() == messages1.getId() && Objects.equals(getMessages(), messages1.getMessages()) && Objects.equals(getTime(), messages1.getTime()) && Objects.equals(getAuthor(), messages1.getAuthor());
    }

    @Override
    public int hashCode() {
        return  Objects.hash(getId(), getMessages(), getTime(), getAuthor());
    }

    @Override
    public String toString() {
        return "Messages{" +
                "id=" + id +
                ", messages='" + messages + '\'' +
                ", time=" + time +
                ", author='" + author + '\'' +
                '}';
    }
}
