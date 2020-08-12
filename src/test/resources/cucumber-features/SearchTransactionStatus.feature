Feature: Get the status of a specific transaction

  Scenario Outline: Get the status of a specific transaction that is stored in the system

    Given User wants to create a new transaction with the following data: "<reference>", "<accountIban>", "<date>", "<amount>", "<fee>", "<description>"
    When User creates the new transaction
    And User gets the status of the transaction with reference "<reference>" and channel "<channel>"
    Then The response is successful and the status of the transaction is "<transactionStatus>"

    Examples:
      | reference                            | accountIban     | date                     | amount  | fee  | description         | channel  | transactionStatus |
      | d8348c39-40eb-4758-bd6a-031bc9447c25 | ES3930294948393 | 2019-07-16T16:55:42.000Z | -7.59  | 3.59 | Payment restaurant  | CLIENT   | SETTLED           |
      | 143b2919-6dc8-48c7-b08d-9b37cec36457 | ES8914658276573 | 2019-07-25T16:55:42.000Z | 40.06   | 4.70 | Payment restaurant  | ATM      | SETTLED           |
      | d7d2c5d8-4a3f-43dd-9284-c89abb33c8ca | ES3930294948393 | 2016-07-25T16:55:42.000Z | -2.59   | 8.59 | Payment Starbucks   | INTERNAL | SETTLED           |
      | 7f4b4d5e-7c1d-4547-9cdf-500bdd5e05b2 | ES8914658276573 | 2017-05-08T16:55:42.000Z | 11.80   |      | Payment supermarket |          | SETTLED           |
      | 1ff29a7e-a852-483d-b2d8-01d1b7651489 | ES3930294948393 | 2021-03-24T16:55:42.000Z | -5.68  | 0.59 |                     | CLIENT   | FUTURE            |
      | 0e1a3be2-3adf-4e72-80c3-5652c169524f | ES8914658276573 | 2021-03-24T16:55:42.000Z | 24.56   | 3.59 | Payment coffee shop | ATM      | PENDING           |
      | a15526de-32b9-4a39-8937-c1a38d502198 | ES3930294948393 | 2022-03-24T16:55:42.000Z | 45.44   |      | Payment restaurant  | INTERNAL | FUTURE            |
      | e2201871-f32f-4987-a18b-0d1f0b22601c | ES8914658276573 | 2020-12-02T16:55:42.000Z | -36.85  | 1.59 |                     |          | FUTURE            |
      | c9d71bbb-5652-432b-9ed8-280ef46a3d83 | ES3930294948393 |                          | 37.59   |      | Payment restaurant  | CLIENT   | PENDING           |
      | 680c7b0c-e454-46e0-ad4a-f82269ccee33 | ES8914658276573 |                          | -25.54  |      |                     | ATM      | PENDING           |
      | c3a08f39-6ddf-4f2c-95b2-6ebe43180d21 | ES3930294948393 |                          | 25.78   |      |                     | INTERNAL | PENDING           |
      | 87fc211b-8e8a-468f-a46e-bb4bb779442a | ES8914658276573 |                          | 35.64   |      |                     |          | PENDING           |

  Scenario Outline: Get the status of a specific transaction that is not stored in the system

    Given User wants to get the status of the transaction with reference "<reference>"
    When User gets the status of the transaction
    Then The response is successful and the status of the transaction is "INVALID"

    Examples:
      | reference                            |
      | 70df6521-f32a-48ec-a2cf-9ebba939e1fb |
      | a87b8d49-0f7e-42bb-abba-f2153544bfb0 |
      | b4498f94-19aa-4f6e-8803-95517a3329fd |