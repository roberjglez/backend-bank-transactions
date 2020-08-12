package com.rjglez.backend.bank.transactions.infrastructure.web;

import com.rjglez.backend.bank.transactions.application.command.TransactionStatusFinderCommand;
import com.rjglez.backend.bank.transactions.application.response.TransactionStatusResponse;
import com.rjglez.backend.bank.transactions.application.use_case.SearchTransactionStatusUseCase;
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

import java.text.ParseException;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = SearchTransactionStatusController.class)
public class SearchTransactionStatusControllerIT {

    private String searchTransactionStatusEndpoint;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchTransactionStatusUseCase searchTransactionStatusUseCase;

    @Before
    public void setUp() throws ParseException {
        searchTransactionStatusEndpoint = "http://localhost:8080/transaction-status";
        Mockito.when(searchTransactionStatusUseCase.find(any(TransactionStatusFinderCommand.class))).thenReturn(TransactionStatusResponse.builder()
                                                                                                                                         .reference("85b37cac-e8c2-46f4-8c77-e11f0cff16b2")
                                                                                                                                         .amount(43.77)
                                                                                                                                         .fee(3.50)
                                                                                                                                         .status(TransactionStatusResponse.TransactionStatus.FUTURE)
                                                                                                                                         .build());
    }

    @Test
    public void shouldReturn200OKAndBodyWhenChannelIsNotNull() throws Exception {

        // THEN
        mockMvc.perform(get(searchTransactionStatusEndpoint)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("reference", "85b37cac-e8c2-46f4-8c77-e11f0cff16b2")
                .param("channel", "INTERNAL"))
               .andExpect(jsonPath("$").exists())
               .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn200OKAndBodyWhenChannelIsNull() throws Exception {

        // THEN
        mockMvc.perform(get(searchTransactionStatusEndpoint)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("reference", "85b37cac-e8c2-46f4-8c77-e11f0cff16b2"))
               .andExpect(jsonPath("$").exists())
               .andExpect(status().isOk());
    }

    @Test
    public void shouldThrowBadRequestWhenReferenceIsNull() throws Exception {

        // THEN
        mockMvc.perform(get(searchTransactionStatusEndpoint)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("channel", "ATM"))
               .andExpect(status().isBadRequest());
    }
}
