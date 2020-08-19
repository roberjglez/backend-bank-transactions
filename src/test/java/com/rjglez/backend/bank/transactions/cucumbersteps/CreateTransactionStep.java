package com.rjglez.backend.bank.transactions.cucumbersteps;

import com.rjglez.backend.bank.transactions.infrastructure.presentation.NewTransactionPresentation;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class CreateTransactionStep {

    private ResponseEntity<Void> response;

    @Given("A user wants to create a new transaction in the system")
    public void aUserWantsToCreateANewTransactionInTheSystem() {
    }

    @And("User creates a new transaction in his account with the following data: {string}, {string}, {string}, {string}, {string}, {string}")
    public void userCreatesANewTransactionInHisAccountWithTheFollowingData(String reference, String iban, String date, String amount, String fee, String description) {

        String createNewTransactionUrl = "http://localhost:8080/transaction";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        NewTransactionPresentation newTransactionPresentation = NewTransactionPresentation.builder()
                                                                                          .reference(reference)
                                                                                          .accountIban(iban)
                                                                                          .date(date)
                                                                                          .amount(Double.parseDouble(amount))
                                                                                          .description(description)
                                                                                          .build();

        if (!fee.isEmpty()) {
            newTransactionPresentation.setFee(Double.parseDouble(fee));
        }

        HttpEntity<Object> entity = new HttpEntity<>(newTransactionPresentation, headers);

        response = restTemplate.exchange(createNewTransactionUrl, HttpMethod.POST, entity, Void.class);
    }

    @And("The creation of the transaction is successful")
    public void theCreationOfTheTransactionIsSuccessful() {

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        Assertions.assertNull(response.getBody());
    }
}
