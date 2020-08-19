package com.rjglez.backend.bank.transactions.cucumbersteps;

import com.rjglez.backend.bank.transactions.application.response.TransactionResponse;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@RequiredArgsConstructor
public class GetTransactionStep {

    private ResponseEntity<TransactionResponse[]> response;

    @Given("User wants to get the transactions of his account number")
    public void userWantsToGetTheTransactionsOfHisAccountNumber() {
    }

    @And("User gets the transactions of his account number {string} {string}")
    public void userGetsTheTransactionsOfHisAccountNumberES(String accountNumber, String sorting) {

        String getTransactionUrl = composeUrl(accountNumber, sorting);

        RestTemplate restTemplate = new RestTemplate();

        response = restTemplate.exchange(getTransactionUrl, HttpMethod.GET, null, TransactionResponse[].class);
    }

    @And("User gets the transactions of his account number {string}")
    public void userGetsTheTransactionsOfHisAccountNumberES(String accountNumber) {

        String getTransactionUrl = composeUrl(accountNumber, null);

        RestTemplate restTemplate = new RestTemplate();

        response = restTemplate.exchange(getTransactionUrl, HttpMethod.GET, null, TransactionResponse[].class);
    }

    @Then("The response is successful")
    public void theResponseIsSuccessful() {

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertNotNull(response.getBody());
    }

    @And("The order of the transactions is: {string}, {string}, {string} and {string}")
    public void theOrderOfTheTransactionsIs(String idFirstTransaction, String idSecondTransaction, String idThirdTransaction, String idFourthTransaction) {

        TransactionResponse[] transactions = response.getBody();
        Assertions.assertEquals(transactions.length, 4);
        Assertions.assertEquals(transactions[0].getReference(), idFirstTransaction);
        Assertions.assertEquals(transactions[1].getReference(), idSecondTransaction);
        Assertions.assertEquals(transactions[2].getReference(), idThirdTransaction);
        Assertions.assertEquals(transactions[3].getReference(), idFourthTransaction);


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
