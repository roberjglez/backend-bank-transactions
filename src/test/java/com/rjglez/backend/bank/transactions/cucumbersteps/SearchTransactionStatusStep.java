package com.rjglez.backend.bank.transactions.cucumbersteps;

import com.rjglez.backend.bank.transactions.application.response.TransactionStatusResponse;
import cucumber.api.java.en.And;
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
public class SearchTransactionStatusStep {

    private ResponseEntity<TransactionStatusResponse> response;

    @Given("User wants to get the status of his transaction")
    public void userWantsToGetTheStatusOfHisTransaction() {
    }

    @When("User gets the status of his transaction with reference {string}")
    public void userGetsTheStatusOfHisTransactionWithReference(String reference) {

        String searchTransactionStatusUrl = composeUrl(reference, null);

        RestTemplate restTemplate = new RestTemplate();

        response = restTemplate.exchange(searchTransactionStatusUrl, HttpMethod.GET, null, TransactionStatusResponse.class);
    }

    @And("User gets the status of the transaction with reference {string} and channel {string}")
    public void userGetsTheStatusOfTheTransactionWithReferenceAndChannel(String reference, String channel) {

        String searchTransactionStatusUrl = composeUrl(reference, channel);

        RestTemplate restTemplate = new RestTemplate();

        response = restTemplate.exchange(searchTransactionStatusUrl, HttpMethod.GET, null, TransactionStatusResponse.class);
    }

    @Then("The status of the transaction is {string}")
    public void theStatusOfTheTransactionIs(String transactionStatus) {

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(response.getBody().getStatus().name(), transactionStatus);
    }

    private String composeUrl(String reference, String channel) {

        String searchTransactionStatusUrl = "http://localhost:8080/transaction-status";

        searchTransactionStatusUrl = searchTransactionStatusUrl.concat("?reference=").concat(reference);

        searchTransactionStatusUrl = !Objects.isNull(channel) && !channel.isEmpty() ? searchTransactionStatusUrl.concat("&channel=").concat(channel) : searchTransactionStatusUrl;

        return searchTransactionStatusUrl;
    }
}
