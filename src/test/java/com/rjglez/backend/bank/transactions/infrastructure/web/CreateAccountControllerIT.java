package com.rjglez.backend.bank.transactions.infrastructure.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rjglez.backend.bank.transactions.application.use_case.CreateAccountUseCase;
import com.rjglez.backend.bank.transactions.infrastructure.presentation.NewAccountPresentation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = CreateAccountController.class)
public class CreateAccountControllerIT {

    private String createAccountEndpoint;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateAccountUseCase createAccountUseCase;

    @Before
    public void setUp() {
        createAccountEndpoint = "http://localhost:8080/account";
    }

    @Test
    public void shouldReturn201Created() throws Exception {

        NewAccountPresentation newAccountPresentation = NewAccountPresentation.builder()
                                                                              .iban("ES3930294948393")
                                                                              .balance(3000.45)
                                                                              .build();

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post(createAccountEndpoint)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(newAccountPresentation)))
               .andExpect(status().isCreated());
    }

    @Test
    public void shouldThrowBadRequestWhenIbanIsNull() throws Exception {

        NewAccountPresentation newAccountPresentation = NewAccountPresentation.builder()
                                                                              .balance(3000.45)
                                                                              .build();

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post(createAccountEndpoint)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(newAccountPresentation)))
               .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldThrowBadRequestWhenBalanceIsNull() throws Exception {

        NewAccountPresentation newAccountPresentation = NewAccountPresentation.builder()
                                                                              .iban("ES3930294948393")
                                                                              .build();

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post(createAccountEndpoint)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(newAccountPresentation)))
               .andExpect(status().isBadRequest());
    }
}
