package org.igorski.example.controllers;

import org.igorski.example.model.Team;
import org.igorski.example.model.requests.TeamMembersRequest;
import org.igorski.example.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/team/{teamId}/members")
public class TeamMembersController {

    @Autowired
    private TeamService teamService;

    @PostMapping
    public ResponseEntity<Team> addTeamMembers(@PathVariable Long teamId, @RequestBody TeamMembersRequest teamRequest) {
        Team team = teamService.addTeamMembers(teamId, teamRequest.getMembers());
        return new ResponseEntity<>(team, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Team> removeMembersFromTeam(@PathVariable Long teamId, @RequestBody TeamMembersRequest teamRequest) {
        Team team = teamService.removeTeamMembers(teamId, teamRequest.getMembers());
        return new ResponseEntity<>(team, HttpStatus.OK);
    }
}
