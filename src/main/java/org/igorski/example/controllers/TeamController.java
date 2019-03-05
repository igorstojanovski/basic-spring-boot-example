package org.igorski.example.controllers;

import org.igorski.example.model.Team;
import org.igorski.example.model.requests.TeamCreationRequest;
import org.igorski.example.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestBody TeamCreationRequest teamRequest) {
        Team team = teamService.createTeam(teamRequest);
        return new ResponseEntity<>(team, HttpStatus.CREATED);
    }
}
