package com.example.PM.repository;

import com.example.PM.entity.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Tasks, Integer> {

    List<Tasks> findByTeamId(int teamId);
}
