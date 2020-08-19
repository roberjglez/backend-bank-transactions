package com.rjglez.backend.bank.transactions.cucumbersteps;

import com.rjglez.backend.bank.transactions.infrastructure.presentation.NewAccountPresentation;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class CreateAccountStep {

    private ResponseEntity<Void> response;

    @Given("A user wants to create a new account in the system")
    public void aUserWantsToCreateANewAccountInTheSystem() {
    }

    @When("User creates a new account with the following data: {string} and {string}")
    public void userCreatesANewAccountWithTheFollowingDataAnd(String iban, String balance) {

        String createNewAccountUrl = "http://localhost:8080/account";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        NewAccountPresentation newAccountPresentation = NewAccountPresentation.builder()
                                                                              .iban(iban)
                                                                              .balance(Double.parseDouble(balance))
                                                                              .build();

        HttpEntity<Object> entity = new HttpEntity<>(newAccountPresentation, headers);

        response = restTemplate.exchange(createNewAccountUrl, HttpMethod.POST, entity, Void.class);
    }

    @Then("The creation of the account is successful")
    public void theCreationOfTheAccountIsSuccessful() {

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        Assertions.assertNull(response.getBody());
    }
}
