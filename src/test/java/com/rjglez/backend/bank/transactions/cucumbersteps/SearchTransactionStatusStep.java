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

    private String                                    reference;
    private ResponseEntity<TransactionStatusResponse> response;

    @Given("User wants to get the status of the transaction with reference {string}")
    public void userWantsToGetTheStatusOfTheTransactionWithReference(String referenceProvided) {

        reference = referenceProvided;
    }

    @When("User gets the status of the transaction")
    public void userGetsTheStatusOfTheTransaction() {

        String searchTransactionStatusUrl = composeUrl(reference, null);

        RestTemplate restTemplate = new RestTemplate();

        response = restTemplate.exchange(searchTransactionStatusUrl, HttpMethod.GET, null, TransactionStatusResponse.class);
    }

    @And("User gets the status of the transaction with reference {string} and channel {string}")
    public void userGetsTheStatusOfTheTransactionWithReferenceAndChannel(String referenceProvided, String channel) {

        String searchTransactionStatusUrl = composeUrl(referenceProvided, channel);

        RestTemplate restTemplate = new RestTemplate();

        response = restTemplate.exchange(searchTransactionStatusUrl, HttpMethod.GET, null, TransactionStatusResponse.class);
    }

    @Then("The response is successful and the status of the transaction is {string}")
    public void theResponseIsSuccessfulAndTheStatusOfTheTransactionIs(String transactionStatus) {

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(response.getBody().getStatus().name(), transactionStatus);
    }

    private String composeUrl(String referenceProvided, String channel) {

        String searchTransactionStatusUrl = "http://localhost:8080/transaction-status";

        searchTransactionStatusUrl = searchTransactionStatusUrl.concat("?reference=").concat(referenceProvided);

        searchTransactionStatusUrl = !Objects.isNull(channel) && !channel.isEmpty() ? searchTransactionStatusUrl.concat("&channel=").concat(channel) : searchTransactionStatusUrl;

        return searchTransactionStatusUrl;
    }
}
