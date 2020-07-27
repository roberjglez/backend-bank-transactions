package com.rjglez.backend.bank.transactions.infrastructure.web;

import com.rjglez.backend.bank.transactions.application.command.TransactionFinderCommand;
import com.rjglez.backend.bank.transactions.application.response.TransactionResponse;
import com.rjglez.backend.bank.transactions.application.use_case.GetTransactionUseCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = GetTransactionController.class)
public class GetTransactionControllerIT {

    private String getTransactionEndpoint;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetTransactionUseCase getTransactionUseCase;

    @Before
    public void setUp() {
        getTransactionEndpoint = "http://localhost:8080/transaction";
        Mockito.when(getTransactionUseCase.find(any(TransactionFinderCommand.class))).thenReturn(Collections.singletonList(TransactionResponse.builder()
                                                                                                                                              .reference("85b37cac-e8c2-46f4-8c77-e11f0cff16b2")
                                                                                                                                              .accountIban("ES3930294948393")
                                                                                                                                              .date("2019-07-16T16:55:42.000Z")
                                                                                                                                              .amount(42.33)
                                                                                                                                              .fee(3.07)
                                                                                                                                              .description("Payment in restaurant")
                                                                                                                                              .build()));
    }

    @Test
    public void shouldReturn200OKAndBody() throws Exception {

        mockMvc.perform(get(getTransactionEndpoint)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(jsonPath("$").exists())
               .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn200OKAndBodyWhenPassAccountIban() throws Exception {

        mockMvc.perform(get(getTransactionEndpoint)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("accountIban", "ES3930294948393"))
               .andExpect(jsonPath("$").exists())
               .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn200OKAndBodyWhenPassSorting() throws Exception {

        mockMvc.perform(get(getTransactionEndpoint)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("sorting", "ascending"))
               .andExpect(jsonPath("$").exists())
               .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn200OKAndBodyWhenPassAccountIbanAndSorting() throws Exception {

        mockMvc.perform(get(getTransactionEndpoint)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("accountIban", "ES3930294948393")
                .param("sorting", "ascending"))
               .andExpect(jsonPath("$").exists())
               .andExpect(status().isOk());
    }

}
