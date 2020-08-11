package cucumber.steps;

import com.rjglez.backend.bank.transactions.infrastructure.presentation.NewTransactionPresentation;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RequiredArgsConstructor
public class CreateTransactionStep {

    private NewTransactionPresentation newTransactionPresentation;
    private ResponseEntity<Void>       response;

    @Given("User wants to create a new transaction with the following data")
    public void userWantsToCreateANewTransactionWithTheFollowingData(DataTable dataTable) {

        Map<String, String> dataMap = dataTable.asMaps().get(0);
        newTransactionPresentation = NewTransactionPresentation.builder()
                                                               .accountIban(dataMap.get("accountIban"))
                                                               .amount(Double.parseDouble(dataMap.get("amount")))
                                                               .build();


    }

    @When("User creates the new transaction")
    public void userCreatesTheNewTransaction() {

        String createNewTransactionUrl = "http://localhost:8080/transaction";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> entity = new HttpEntity<>(newTransactionPresentation, headers);

        response = restTemplate.exchange(createNewTransactionUrl, HttpMethod.POST, entity, Void.class);

    }

    @Then("The creation is successful")
    public void theCreationIsSuccessful() {

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        Assertions.assertNull(response.getBody());

    }
}
