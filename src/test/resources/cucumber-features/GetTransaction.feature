Feature: Get transactions

  Scenario Outline: Get the transactions of a specific account from database sorting them

    Given User wants to get the transactions "<sorting>" of his account number "<accountIban>"
    When User gets the transactions
    Then The response is successful

    Examples:
      | accountIban     | sorting    |
      | ES3930294948393 | ascending  |
      | ES3930294948393 | descending |

  Scenario Outline: Get the transactions of a specific account from database without sorting them

    Given User wants to get the transactions of his account number "<accountIban>"
    When User gets the transactions
    Then The response is successful

    Examples:
      | accountIban     |
      | ES3930294948393 |