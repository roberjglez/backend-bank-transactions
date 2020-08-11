Feature: Get the transactions of a specific account

  Scenario: Get the transactions of a specific account from database

    Given User wants to get the transactions of his account
      | accountIban     | sorting    |
      | ES3930294948393 | ascending  |
    When User gets the transactions
    Then The response is successful