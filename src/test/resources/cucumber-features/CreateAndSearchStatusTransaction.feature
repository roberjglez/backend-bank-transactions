Feature: Create new transaction and search transaction status

  Scenario Outline: Create new transaction in database and search its transaction status

    Given A user wants to create a new transaction in the system
    When User creates a new account with the following data: "<iban>" and "<balance>"
    And User creates a new transaction in his account with the following data: "<reference>", "<iban>", "<date>", "<amount>", "<fee>", "<description>"
    And User gets the status of the transaction with reference "<reference>" and channel "<channel>"
    Then The creation of the account is successful
    And The creation of the transaction is successful
    And The status of the transaction is "<transactionStatus>"

    Examples:
      | iban                     | balance | reference                            | date                     | amount  | fee  | description         | channel  | transactionStatus |
      | ES6201283359516794524341 | 3000.45 | 87a6107f-2d2f-4ef7-9e10-d2464189acc9 | 2019-07-16T16:55:42.000Z | -3.59   | 1.59 | Payment restaurant  | CLIENT   | SETTLED           |
      | ES9000755378065174325551 | 3927.35 | c3eadfc1-7d01-4822-80f1-89f610105fcd | 2016-07-25T16:55:42.000Z | 40.06   | 4.70 | Payment restaurant  | ATM      | SETTLED           |
      | ES7931903334883143458783 | 6937.34 | 975fc780-266b-4e95-862d-9b18ed43e29c | 2018-07-25T16:55:42.000Z | -2.59   | 8.59 | Payment Starbucks   | INTERNAL | SETTLED           |
      | ES1004871984257335398871 | 8937.78 | 4b6edcbe-67e9-47b2-a3a1-d1cff9b22d41 | 2017-05-08T16:55:42.000Z | 11.80   |      | Payment supermarket |          | SETTLED           |
      | ES0400818534826896923518 | 2749.94 | 2aac90d4-ab7c-4175-a9a7-9dbcced9f65e | 2022-03-24T16:55:42.000Z | -5.68   | 0.59 |                     | CLIENT   | FUTURE            |
      | ES7620803712451496958465 | 1394.63 | 84cffa6b-3238-42fb-b90a-bd79705714e8 | 2024-05-14T16:55:42.000Z | 24.56   | 3.59 | Payment coffee shop | ATM      | PENDING           |
      | ES9000754997024856838674 | 5839.72 | f6090c15-c146-41d9-8874-537a21c498c3 | 2021-08-29T16:55:42.000Z | 45.44   |      | Payment restaurant  | INTERNAL | FUTURE            |
      | ES8520953946124721515865 | 3859.27 | 5886fa52-8a6f-4e5c-a54c-ca48fb0fb8b6 | 2025-12-02T16:55:42.000Z | -3.85   | 1.59 |                     |          | FUTURE            |
      | ES6001823469344429714562 | 5967.37 | 6303c07e-98c2-4ca6-b4a3-ba0563e31c0f |                          | 37.59   |      | Payment restaurant  | CLIENT   | PENDING           |
      | ES7120381426576122838225 | 4729.57 | 27d6c035-7bcf-4be2-8241-b1be38312217 |                          | -5.54   |      |                     | ATM      | PENDING           |
      | ES1820801337316533996296 | 3829.33 | a4a527fb-5511-4ee0-b2a6-af90532443e1 |                          | 25.78   |      |                     | INTERNAL | PENDING           |
      | ES2131905879133629159782 | 3389.48 | 68808b63-1f1b-4184-948d-48891c464082 |                          | 35.64   |      |                     |          | PENDING           |

  Scenario Outline: Get the status of a specific transaction that is not stored in the system

    Given User wants to get the status of his transaction
    When User gets the status of his transaction with reference "<reference>"
    Then The status of the transaction is "INVALID"

    Examples:
      | reference                            |
      | 70df6521-f32a-48ec-a2cf-9ebba939e1fb |
      | a87b8d49-0f7e-42bb-abba-f2153544bfb0 |
      | b4498f94-19aa-4f6e-8803-95517a3329fd |