package com.rjglez.backend.bank.transactions.cucumbersteps;

import com.rjglez.backend.bank.transactions.application.response.TransactionResponse;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@RequiredArgsConstructor
public class GetTransactionStep {

    private String                                accountIban;
    private String                                sorting;
    private ResponseEntity<TransactionResponse[]> response;

    @Given("User wants to get the transactions {string} of his account number {string}")
    public void userWantsToGetTheTransactionsOfHisAccountNumber(String sortingMode, String account) {

        accountIban = account;
        sorting     = sortingMode;
    }

    @Given("User wants to get the transactions of his account number {string}")
    public void userWantsToGetTheTransactionsOfHisAccountNumber(String account) {

        accountIban = account;
    }

    @When("User gets the transactions")
    public void userGetsTheTransactions() {

        String getTransactionUrl = composeUrl(accountIban, sorting);

        RestTemplate restTemplate = new RestTemplate();

        response = restTemplate.exchange(getTransactionUrl, HttpMethod.GET, null, TransactionResponse[].class);
    }

    @Then("The response is successful")
    public void theResponseIsSuccessful() {

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertNotNull(response.getBody());
    }

    private String composeUrl(String accountIban, String sorting) {

        String getTransactionUrl = "http://localhost:8080/transaction";

        if (!Objects.isNull(accountIban) && !accountIban.isEmpty()) {
            getTransactionUrl = getTransactionUrl.concat("?accountIban=").concat(accountIban);
            if (!Objects.isNull(sorting) && !sorting.isEmpty()) {
                getTransactionUrl = getTransactionUrl.concat("&sorting=").concat(sorting);
            }
        } else {
            if (!Objects.isNull(sorting) && !sorting.isEmpty()) {
                getTransactionUrl = getTransactionUrl.concat("?sorting=").concat(sorting);
            }
        }

        return getTransactionUrl;
    }

}
