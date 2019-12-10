package com.trilogyed.controller;

        import com.fasterxml.jackson.databind.ObjectMapper;
        import com.trilogyed.model.LevelUp;
        import com.trilogyed.repository.LevelUpRepo;
        import org.junit.Before;
        import org.junit.Test;
        import org.junit.runner.RunWith;
        import org.springframework.amqp.rabbit.core.RabbitTemplate;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
        import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
        import org.springframework.boot.test.json.JacksonTester;
        import org.springframework.boot.test.mock.mockito.MockBean;
        import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.MediaType;
        import org.springframework.mock.web.MockHttpServletResponse;
        import org.springframework.test.context.junit4.SpringRunner;
        import org.springframework.test.web.servlet.MockMvc;

        import java.time.LocalDate;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Optional;

        import static org.assertj.core.api.Assertions.assertThat;
        import static org.mockito.BDDMockito.given;
        import static org.mockito.Mockito.times;
        import static org.mockito.Mockito.verify;
        import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@WebMvcTest(LevelUpController.class)
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
public class LevelUpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LevelUpRepo repo;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    private JacksonTester<LevelUp> jsonLevelUp;

    private JacksonTester<List<LevelUp>> jsonLevelUpList;

    @Before
    public void setUp() throws Exception {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void shouldRetrieveAllLevelUps() throws Exception {
        LevelUp levelUp1 = new LevelUp();
        LevelUp levelUp2 = new LevelUp();
        levelUp1.setId(1);
        levelUp2.setId(2);
        List<LevelUp> levelUps = new ArrayList<LevelUp>() {{
            add(levelUp1);
            add(levelUp2);
        }};

        given(repo.findAll())
                .willReturn(levelUps);

        MockHttpServletResponse allResponse = mockMvc.perform(
                get("/levelUps")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(allResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(allResponse.getContentAsString()).isEqualTo(jsonLevelUpList.write(levelUps).getJson());
    }

    @Test
    public void shouldRetrieveLevelUpById() throws Exception {
        LevelUp levelUp1 = new LevelUp(2, 200, LocalDate.of(2017, 2, 22));
        levelUp1.setId(1);

        given(repo.findById(1))
                .willReturn(Optional.of(levelUp1));

        given(repo.getOne(1))
                .willReturn(levelUp1);

        MockHttpServletResponse id1Response = mockMvc.perform(
                get("/levelUp/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(id1Response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(id1Response.getContentAsString()).isEqualTo(jsonLevelUp.write(levelUp1).getJson());
    }

    @Test
    public void shouldRetrieveLevelUpByCustomerId() throws Exception {
        LevelUp levelUp1 = new LevelUp();
        levelUp1.setCustomerId(1);

        given(repo.findByCustomerId(1))
                .willReturn(levelUp1);

        MockHttpServletResponse id1Response = mockMvc.perform(
                get("/levelUp/customer/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(id1Response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(id1Response.getContentAsString()).isEqualTo(jsonLevelUp.write(levelUp1).getJson());
    }

    @Test
    public void shouldCreateLevelUps() throws Exception {
        LevelUp levelUp1 = new LevelUp();
        LevelUp levelUpAdded = levelUp1;
        levelUpAdded.setId(1);

        given(repo.save(levelUp1))
                .willReturn(levelUpAdded);

        MockHttpServletResponse createdResponse = mockMvc.perform(
                post("/levelUp")
                        .content(jsonLevelUp.write(levelUp1).getJson())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(createdResponse.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(createdResponse.getContentAsString()).isEqualTo(jsonLevelUp.write(levelUpAdded).getJson());
    }

    @Test
    public void shouldUpdateLevelUps() throws Exception {
        LevelUp levelUp1 = new LevelUp();
        LevelUp levelUpAdded = levelUp1;
        levelUpAdded.setId(1);

        given(repo.save(levelUp1))
                .willReturn(levelUpAdded);

        LevelUp levelUp2 = new LevelUp();
        levelUp2.setId(1);

        MockHttpServletResponse updateResponse = mockMvc.perform(
                put("/levelUp")
                        .content(jsonLevelUp.write(levelUp2).getJson())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(updateResponse.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        verify(repo, times(1)).save(levelUp2);
    }

    @Test
    public void shouldDeleteLevelUps() throws Exception {
        LevelUp levelUp1 = new LevelUp();
        LevelUp levelUpAdded = levelUp1;
        levelUpAdded.setId(1);

        given(repo.save(levelUp1))
                .willReturn(levelUpAdded);

        MockHttpServletResponse deleteResponse = mockMvc.perform(
                delete("/levelUp/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(deleteResponse.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        verify(repo, times(1)).deleteById(levelUp1.getId());
    }
}