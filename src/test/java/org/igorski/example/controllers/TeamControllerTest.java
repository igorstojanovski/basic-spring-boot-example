package org.igorski.example.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.igorski.example.model.Team;
import org.igorski.example.model.User;
import org.igorski.example.model.requests.TeamMembersRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class TeamControllerTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldCreateTeam1() throws Exception {
        Team team = createTeam("Spaceballs");
        assertThat(team.getId()).isNotNull();
    }

    @Test
    public void shouldAddAndDeleteMembersToTeam() throws Exception {
        User userOne = createUser("igorski123");
        User userTwo = createUser("igorski1456");
        List<Long> membersToAdd = new ArrayList<>();
        membersToAdd.add(userOne.getId());
        membersToAdd.add(userTwo.getId());
        Team team = createTeam("Goonies");

        TeamMembersRequest teamMembersRequest = new TeamMembersRequest();
        teamMembersRequest.setMembers(membersToAdd);

        MvcResult addMembersResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/team/" + team.getId() + "/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(teamMembersRequest)))
                .andExpect(status().isOk())
                .andDo(document("addTeamMembers",
                        requestFields(
                                fieldWithPath("members")
                                        .description("List of member IDs of type long that should be added to the team.")
                        )))
                .andDo(getTeamResponseDoc("addTeamMembers"))
                .andDo(print())
                .andReturn();

        team = OBJECT_MAPPER.readValue(addMembersResult.getResponse().getContentAsString(), Team.class);
        assertThat(team.getMembers()).contains(userOne, userTwo);

        teamMembersRequest.getMembers().remove(userTwo.getId());

        MvcResult removeTeamMembersResult = mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/team/" + team.getId() + "/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(teamMembersRequest)))
                .andExpect(status().isOk())
                .andDo(document("removeTeamMembers",
                        requestFields(
                                fieldWithPath("members")
                                        .description("List of member IDs of type long that should be removed from the team.")
                        )))
                .andDo(getTeamResponseDoc("removeTeamMembers"))
                .andDo(print())
                .andReturn();

        team = OBJECT_MAPPER.readValue(removeTeamMembersResult.getResponse().getContentAsString(), Team.class);
        assertThat(team.getMembers()).containsOnly(userTwo);
    }

    private RestDocumentationResultHandler getTeamResponseDoc(String documentIdentifier) {
        return document(documentIdentifier,
                responseFields(
                        fieldWithPath("id")
                                .description("ID of the team."),
                        fieldWithPath("name")
                                .description("Name of the team."),
                        fieldWithPath("members")
                                .description("List of members in the team."),
                        fieldWithPath("members[].id")
                                .description("ID of the user."),
                        fieldWithPath("members[].username")
                                .description("The username of the user."),
                        fieldWithPath("members[].name")
                                .description("The name of the user."),
                        fieldWithPath("members[].surname")
                                .description("The surname of the user.")
                ));
    }

    @Test
    public void shouldCreateTeam() throws Exception {
        MvcResult storyResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/team/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"Spaceballs\"\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("team",
                        requestFields(
                                fieldWithPath("name")
                                        .description("The name of the team.")
                        )))
                .andDo(document("team",
                        responseFields(
                                fieldWithPath("id")
                                        .description("ID of the team."),
                                fieldWithPath("name")
                                        .description("Name of the team."),
                                fieldWithPath("members")
                                        .description("Empty list of team members.")
                        )))
                .andReturn();

        Team team = OBJECT_MAPPER.readValue(storyResult.getResponse().getContentAsString(), Team.class);
        assertThat(team.getId()).isNotNull();
    }

    private Team createTeam(final String teamName) throws Exception {
        MvcResult storyResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/team/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"" + teamName + "\"\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("team",
                        requestFields(
                                fieldWithPath("name")
                                        .description("The name of the team.")
                        ))).
                        andDo(document("team",
                                responseFields(
                                        fieldWithPath("id")
                                                .description("ID of the team."),
                                        fieldWithPath("name")
                                                .description("Name of the team."),
                                        fieldWithPath("members")
                                                .description("Empty list of team members.")
                                )))
                .andReturn();

        return OBJECT_MAPPER.readValue(storyResult.getResponse().getContentAsString(), Team.class);
    }

    private User createUser(String username) throws Exception {
        User user = new User();
        user.setName("Igor");
        user.setSurname("Stojanovski");
        user.setUsername(username);
        user.setPassword("1234#atdk");

        String postValue = OBJECT_MAPPER.writeValueAsString(user);

        MvcResult storyResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/user/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(postValue))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        return OBJECT_MAPPER.readValue(storyResult.getResponse().getContentAsString(), User.class);
    }
}