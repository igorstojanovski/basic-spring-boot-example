package org.igorski.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.igorski.example.model.User;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The name of the test should not really contain MockMvc. I do this here because
 * the same test will have different implementations and this one is based on MockMvc.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserResourceMockMvcIT {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @Before
    public void beforeEach() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
    }

    @Test
    public void shouldCreateUser() throws Exception {
        User createdUser = createUser();
        assertThat(createdUser.getId()).isNotNull();
        assertThat(createdUser.getPassword()).isNullOrEmpty();
    }

    @Test
    public void shouldGetCreatedUser() throws Exception {
        User createdUser = createUser();
        User receivedUser = getUser(createdUser.getId());

        assertThat(createdUser.getId()).isEqualTo(receivedUser.getId());
        assertThat(createdUser.getUsername()).isEqualTo(receivedUser.getUsername());
    }

    private User getUser(Long id) throws Exception {
        MvcResult receivedUser = this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/user/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        return OBJECT_MAPPER.readValue(receivedUser.getResponse().getContentAsString(), User.class);
    }

    private User createUser() throws Exception {
        User user = new User();
        user.setName("Igor");
        user.setSurname("Stojanovski");
        user.setUsername("igorski");
        user.setPassword("1234#atdk");

        String postValue = OBJECT_MAPPER.writeValueAsString(user);

        MvcResult storyResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/user/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(postValue))
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.name", is("Igor")))
                .andExpect(jsonPath("$.surname", is("Stojanovski")))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        return OBJECT_MAPPER.readValue(storyResult.getResponse().getContentAsString(), User.class);
    }
}