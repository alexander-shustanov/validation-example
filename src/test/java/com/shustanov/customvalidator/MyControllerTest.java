package com.shustanov.customvalidator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the {@link MyController}
 */
@WebMvcTest({MyController.class})
public class MyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {

    }

    @Test
    public void validCardNumber() throws Exception {
        mockMvc.perform(post("/card")
                .param("cardNumber", "1111-1111-1111-1111"))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    public void invalidCardNumber() throws Exception {
        mockMvc.perform(post("/card")
                .param("cardNumber", "1111-1111-1111"))
            .andExpect(status().is(400))
            .andDo(print());
    }

    @Test
    public void validPassword() throws Exception {
        mockMvc.perform(post("/password")
                .param("password", "Passw0rd!"))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    public void invalidPassword() throws Exception {
        mockMvc.perform(post("/password")
                .param("password", "passw0rd!"))
            .andExpect(status().is(400))
            .andDo(print());
    }
}
