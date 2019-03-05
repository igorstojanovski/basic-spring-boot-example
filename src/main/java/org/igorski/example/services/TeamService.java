package org.igorski.example.services;

import org.igorski.example.model.Team;
import org.igorski.example.model.User;
import org.igorski.example.model.requests.TeamCreationRequest;
import org.igorski.example.repositories.TeamRepository;
import org.igorski.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private UserRepository userRepository;

    public Team createTeam(TeamCreationRequest request) {
        Team team = new Team();
        team.setName(request.getName());
        return teamRepository.save(team);
    }

    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    public Team addTeamMembers(Long teamId, List<Long> membersIdsToAdd) {
        Team team = teamRepository.findById(teamId).orElseThrow();
        Set<User> members = team.getMembers();
        Iterable<User> membersToAdd = userRepository.findAllById(membersIdsToAdd);

        for (User m : membersToAdd) {
            members.add(m);
        }

        return teamRepository.save(team);
    }

    public Team removeTeamMembers(Long teamId, List<Long> memberIdsToRemove) {
        Team team = teamRepository.findById(teamId).orElseThrow();
        Set<User> members = team.getMembers();
        Iterable<User> membersToRemove = userRepository.findAllById(memberIdsToRemove);

        for (User m : membersToRemove) {
            members.remove(m);
        }

        return teamRepository.save(team);
    }
}
