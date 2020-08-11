package cucumber.steps;

import com.rjglez.backend.bank.transactions.application.response.TransactionResponse;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RequiredArgsConstructor
public class GetTransactionStep {

    private String                                accountIban;
    private String                                sorting;
    private ResponseEntity<TransactionResponse[]> response;

    @Given("User wants to get the transactions of his account")
    public void userWantsToGetTheTransactionsOfHisAccount(DataTable dataTable) {

        Map<String, String> dataMap = dataTable.asMaps().get(0);
        accountIban = dataMap.get("accountIban");
        sorting     = dataMap.get("sorting");
    }

    @When("User gets the transactions")
    public void userGetsTheTransactions() {

        String getTransactionUrl = "http://localhost:8080/transaction";

        getTransactionUrl = getTransactionUrl.concat("?accountIban=")
                                             .concat(accountIban).concat("&sorting=").concat(sorting);

        RestTemplate restTemplate = new RestTemplate();

        response = restTemplate.exchange(getTransactionUrl, HttpMethod.GET, null, TransactionResponse[].class);
    }

    @Then("The response is successful")
    public void theResponseIsSuccessful() {

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertNotNull(response.getBody());

    }
}
