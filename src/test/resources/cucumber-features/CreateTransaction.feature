Feature: Create new transaction

  Scenario Outline: Create new transaction in database

    Given User wants to create a new transaction with the following data: "<reference>", "<accountIban>", "<date>", "<amount>", "<fee>", "<description>"
    When User creates the new transaction
    Then The creation is successful

    Examples:
      | reference                            | accountIban     | date                     | amount  | fee  | description         |
      | 87a6107f-2d2f-4ef7-9e10-d2464189acc9 | ES3930294948393 | 2019-07-16T16:55:42.000Z | -3.59   | 1.59 | Payment restaurant  |
      |                                      | ES8914658276573 | 2020-07-25T16:55:42.000Z | 40.06   | 4.70 | Payment restaurant  |
      | 975fc780-266b-4e95-862d-9b18ed43e29c | ES3930294948393 |                          | -2.59   | 8.59 | Payment Starbucks   |
      | 4b6edcbe-67e9-47b2-a3a1-d1cff9b22d41 | ES8914658276573 | 2017-05-08T16:55:42.000Z | 11.80   |      | Payment supermarket |
      | 2aac90d4-ab7c-4175-a9a7-9dbcced9f65e | ES3930294948393 | 2020-03-24T16:55:42.000Z | -5.68   | 0.59 |                     |
      |                                      | ES8914658276573 |                          | 24.56   | 3.59 | Payment coffee shop |
      | f6090c15-c146-41d9-8874-537a21c498c3 | ES3930294948393 |                          | 45.44   |      | Payment restaurant  |
      |                                      | ES8914658276573 | 2020-12-02T16:55:42.000Z | -3.85   | 1.59 |                     |
      |                                      | ES3930294948393 |                          | 37.59   |      | Payment restaurant  |
      | 27d6c035-7bcf-4be2-8241-b1be38312217 | ES8914658276573 |                          | -5.54   |      |                     |
      |                                      | ES3930294948393 | 2021-10-11T16:55:42.000Z | 25.78   |      |                     |
      |                                      | ES8914658276573 |                          | 35.64   |      |                     |