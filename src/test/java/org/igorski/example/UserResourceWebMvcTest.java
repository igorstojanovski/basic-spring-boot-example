package org.igorski.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.igorski.example.controllers.UserController;
import org.igorski.example.model.User;
import org.igorski.example.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserResourceWebMvcTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    private User testUser;

    @Before
    public void beforeEach() {
        testUser = new User();
        testUser.setName("Igor");
        testUser.setSurname("Stojanovski");
        testUser.setUsername("igorski");
        testUser.setPassword("1234#atdk");

        User createdUser = new User();
        createdUser.setId(101L);

        given(userService.createUser(testUser)).willReturn(createdUser);
    }

    @Test
    public void shouldCreateUser() throws Exception {
        User createdUser = createUser();
        assertThat(createdUser.getId()).isEqualTo(101L);
    }

    private User createUser() throws Exception {

        String postValue = OBJECT_MAPPER.writeValueAsString(testUser);

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