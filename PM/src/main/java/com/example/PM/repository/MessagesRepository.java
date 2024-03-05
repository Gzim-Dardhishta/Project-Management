package com.example.PM.repository;

import com.example.PM.entity.Messages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessagesRepository extends JpaRepository<Messages, Integer> {
    List<Messages> getMessagesByTeamId(int teamId);
}
