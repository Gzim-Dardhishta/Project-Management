package com.example.PM.controllers;

import com.example.PM.entity.Teams;
import com.example.PM.exeptions.ResourceNotFoundException;
import com.example.PM.payload.request.AddMemberToGroup;
import com.example.PM.payload.request.CreateTeam;
import com.example.PM.payload.request.EditTeam;
import com.example.PM.payload.response.MessageResponse;
import com.example.PM.services.TeamsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/teams")
public class TeamsController {

    private final TeamsService teamsService;

    public TeamsController(TeamsService teamsService) {
        this.teamsService = teamsService;
    }

    @GetMapping
    public List<Teams> getAllTeams() {
        return teamsService.getAllTeams();
    }
//    public Stream<Teams> getAllTeams() {
//        return teamsService.getAllTeams();
//    }

    @GetMapping("team/{teamId}")
    public Teams getTeam(@PathVariable int teamId) {
        return teamsService.getTeam(teamId);
    }

    @PostMapping("/add")
    public MessageResponse addTeam(@RequestBody CreateTeam createTeam) {
        teamsService.createTeam(createTeam);
        return new MessageResponse("Team Created");
    }

    @PostMapping("/team/{teamId}/add-member")
    public  MessageResponse addMemberToTeam(@PathVariable int teamId, @RequestBody AddMemberToGroup userId) {
        teamsService.addTeamMember(teamId, userId.userId());
        return new MessageResponse("Member added to the group");
    }

    @PutMapping("/edit/{teamId}")
    public MessageResponse editTeam(@PathVariable int teamId, @RequestBody EditTeam editTeam) {
        teamsService.editTeamName(teamId, editTeam);
        return new MessageResponse("Team edited!!");
    }

    @DeleteMapping("/delete/{teamId}")
    public MessageResponse deleteTeam(@PathVariable int teamId) {
        teamsService.removeMembersFromTeam(teamId);
        return new MessageResponse("Members removed!!");
    }
}
