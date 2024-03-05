package com.example.PM.services;

import com.example.PM.entity.DTO.UserDTO;
import com.example.PM.entity.ERole;
import com.example.PM.entity.Role;
import com.example.PM.entity.Teams;
import com.example.PM.entity.User;
import com.example.PM.exeptions.RequestValidationException;
import com.example.PM.exeptions.ResourceNotFoundException;
import com.example.PM.payload.request.CreateTeam;
import com.example.PM.payload.request.EditTeam;
import com.example.PM.repository.TeamsRepository;
import com.example.PM.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class TeamsService {

    private final TeamsRepository teamsRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public TeamsService(TeamsRepository teamsRepository, UserRepository userRepository, UserService userService) {
        this.teamsRepository = teamsRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public List<Teams> getAllTeams() {
        return teamsRepository.findAll();
//        return teamsRepository.findAll().stream().filter(team -> team.getMembers().size() > 1);
    }

    public Teams getTeam(int teamId) {
        return teamsRepository.findById(teamId).orElseThrow(() -> new ResourceNotFoundException("No team found with id %s".formatted(teamId)));
    }

    public void createTeam(CreateTeam request) {

        if (StringUtils.isBlank(request.groupName())) {
            throw new RequestValidationException("Group name cannot be empty.");
        }

        UserDetails loggedUser = userService.getLoggedInUser().get();
        User user = userRepository.findByUsername(loggedUser.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("No user found"));
        UserDTO professor = userService.getUser(request.professorId());


        Teams team = new Teams();
        team.setGroupName(request.groupName());
        team.setTeamAdmin(user.getName() + " " + user.getLastName());
        team.setProfessor(professor.name() + " " + professor.lastName());
        team.getMembers().add(user);
        teamsRepository.save(team);
    }

    public void addTeamMember(int teamId, int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("No user found with id [%s]".formatted(userId)));
        Teams teams = teamsRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("No team found with id [%s]".formatted(teamId)));

        teams.addUserToGroup(user);

        teamsRepository.save(teams);
    }

    public void editTeamName(int teamId, EditTeam request) {

        Teams team = getTeam(teamId);

        team.setGroupName(request.groupName());

        teamsRepository.save(team);
    }

    public void removeMembersFromTeam(int teamId) {

        Teams team = getTeam(teamId);
        team.removeMembersFromTeam();
        teamsRepository.save(team);
    }
}
