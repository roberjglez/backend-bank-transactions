package com.rjglez.backend.bank.transactions.infrastructure.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rjglez.backend.bank.transactions.application.use_case.CreateTransactionUseCase;
import com.rjglez.backend.bank.transactions.infrastructure.presentation.NewTransactionPresentation;
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
@WebMvcTest(value = CreateTransactionController.class)
public class CreateTransactionControllerIT {

    private String createTransactionEndpoint;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateTransactionUseCase createTransactionUseCase;

    @Before
    public void setUp() {
        createTransactionEndpoint = "http://localhost:8080/transaction";
    }

    @Test
    public void shouldReturn201Created() throws Exception {

        NewTransactionPresentation newTransactionPresentation = NewTransactionPresentation.builder()
                                                                                          .reference("85b37cac-e8c2-46f4-8c77-e11f0cff16b2")
                                                                                          .accountIban("ES3930294948393")
                                                                                          .date("2019-07-16T16:55:42.000Z")
                                                                                          .amount(42.33)
                                                                                          .fee(3.07)
                                                                                          .description("Payment in restaurant")
                                                                                          .build();

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post(createTransactionEndpoint)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(newTransactionPresentation)))
               .andExpect(status().isCreated());
    }

    @Test
    public void shouldThrowBadRequestWhenAccountIbanIsNull() throws Exception {

        NewTransactionPresentation newTransactionPresentation = NewTransactionPresentation.builder()
                                                                                          .reference("85b37cac-e8c2-46f4-8c77-e11f0cff16b2")
                                                                                          .date("2019-07-16T16:55:42.000Z")
                                                                                          .amount(42.33)
                                                                                          .fee(3.07)
                                                                                          .description("Payment in restaurant")
                                                                                          .build();

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post(createTransactionEndpoint)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(newTransactionPresentation)))
               .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldThrowBadRequestWhenAmountIsNull() throws Exception {

        NewTransactionPresentation newTransactionPresentation = NewTransactionPresentation.builder()
                                                                                          .reference("85b37cac-e8c2-46f4-8c77-e11f0cff16b2")
                                                                                          .accountIban("ES3930294948393")
                                                                                          .date("2019-07-16T16:55:42.000Z")
                                                                                          .fee(3.07)
                                                                                          .description("Payment in restaurant")
                                                                                          .build();

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post(createTransactionEndpoint)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(newTransactionPresentation)))
               .andExpect(status().isBadRequest());
    }
}
