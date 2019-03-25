package com.controllers;

import com.configurations.WebConfiguration;
import com.entity.GameplayEntity;
import com.repository.GameplayRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("com.configurations")
@ContextConfiguration(classes = {WebConfiguration.class})
public class GameControllerTest {

    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;


    private GameplayRepository gameplayRepository;

    @Autowired
    public void setWebApplicationContext(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }

    @Autowired
    public void setGameplayRepository(GameplayRepository gameplayRepository) {
        this.gameplayRepository = gameplayRepository;
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @After
    public void after() {
        this.gameplayRepository = null;
        this.webApplicationContext = null;
        this.mockMvc = null;
    }

    @Test
    public void givenWebApplicationContext_whenServletContext_thenItProvidesNumberGenerator() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(webApplicationContext.getBean("numberGenerator"));
    }

    @Test
    public void givenStartPageMapping_whenMockMVC_thenVerifyResponseAndReturnMessage() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value("_____H_E_L_L_O___M_Y___F_R_I_E_N_D____"))
                .andReturn();

        Assert.assertEquals("application/json;charset=UTF-8",
                mvcResult.getResponse().getContentType());
    }

    @Test
    public void givenPutMapping_whenMockMVC_thenReturnIdOfValueAndStatusOfResponse() throws Exception {
        this.mockMvc.perform(post("/add/1")).andDo(print())
                .andExpect(status().isOk()).andExpect(content()
                .contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value("200"));
    }

    @Test
    public void givenPutRandomMapping_whenMockMVC_thenReturnIdOfValueAndStatusOfResponse() throws Exception {

        this.mockMvc.perform(post("/randomNumber")).andDo(print())
                .andExpect(status().isOk()).andExpect(content()
                .contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value("200"));
    }

    @Test
    public void givenFindMappingForExistingIdWithExistingNumber_whenMockMVC_thenReturnExistingNumberAndResultWin()
            throws Exception {
        gameplayRepository.save(new GameplayEntity(1, 1));
        this.mockMvc.perform(get("/find/1/1")).andDo(print())
                .andExpect(status().isOk()).andExpect(content()
                .contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.guess").value("Your guess is: 1"))
                .andExpect(jsonPath("$.result").value("W_I_N_N_E_R_!"));
    }

    @Test
    public void givenFindMappingForExistingIdWithNotExistingNumber_whenMockMVC_thenReturnNotExistingNumberAndResultLoose()
            throws Exception {
        gameplayRepository.save(new GameplayEntity(1, 1));
        this.mockMvc.perform(get("/find/5/1")).andDo(print())
                .andExpect(status().isOk()).andExpect(content()
                .contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.guess").value("Your guess is: 5"))
                .andExpect(jsonPath("$.result").value("L_O_O_S_E"));
    }

    @Test
    public void givenFindMappingForNotExistingId_whenMockMVC_thenReturnIdOfValueAndStatusOfResponse()
            throws Exception {
        this.mockMvc.perform(get("/find/1/250")).andDo(print())
                .andExpect(status().isNotFound()).andExpect(content()
                .contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message").value("NO_NUMBER_WITH_SUCH_ID_FOUND"))
                .andExpect(jsonPath("$.status").value("404"));
    }


}