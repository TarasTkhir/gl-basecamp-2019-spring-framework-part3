package com.controllers;

import com.configurations.WebConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("com.configurations")
@ContextConfiguration(classes = {WebConfiguration.class})
public class ExceptionControllerTest {

    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    public void setWebApplicationContext(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @After
    public void after() {
        this.webApplicationContext = null;
        this.mockMvc = null;
    }

    @Test
    public void givenNotValidData_whenMockMVC_thenReturnJSONFromCustomException() throws Exception {
        this.mockMvc.perform(get("/find/3/600")).andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message").value("NO_NUMBER_WITH_SUCH_ID_FOUND"))
                .andExpect(jsonPath("$.status").value("404"));
    }
}