Feature: Create new transaction

  Scenario: Create new transaction in database

    Given User wants to create a new transaction with the following data
      | accountIban     | amount |
      | ES3930294948393 | 37.59  |
    When User creates the new transaction
    Then The creation is successful