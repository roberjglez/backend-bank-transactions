Feature: Get transactions

  Scenario: Get the transactions of a specific account from database ascending

    Given User wants to get the transactions of his account number
    When User creates a new account with the following data: 'ES8701286135061655232527' and '5938.67'
    And User creates a new transaction in his account with the following data: 'fa09346e-84f9-4e24-99de-4962aed0c121', 'ES8701286135061655232527', '2019-07-16T16:55:42.000Z', '239.74', '3.80', 'Payment in restaurant'
    And User creates a new transaction in his account with the following data: '366d87b7-c58b-4f96-b613-47beeca467cd', 'ES8701286135061655232527', '2019-07-16T16:55:42.000Z', '25.00', '1.80', 'Payment in restaurant'
    And User creates a new transaction in his account with the following data: '81bf1038-3fa2-4cf5-a021-10fa9d474aa9', 'ES8701286135061655232527', '2019-07-16T16:55:42.000Z', '400.55', '0.60', 'Payment in restaurant'
    And User creates a new transaction in his account with the following data: '4f0a7426-a810-4321-9761-9e5f33ac6a3d', 'ES8701286135061655232527', '2019-07-16T16:55:42.000Z', '86.54', '0.00', 'Payment in restaurant'
    And User gets the transactions of his account number 'ES8701286135061655232527' 'ascending'
    Then The response is successful
    And The order of the transactions is: '366d87b7-c58b-4f96-b613-47beeca467cd', '4f0a7426-a810-4321-9761-9e5f33ac6a3d', 'fa09346e-84f9-4e24-99de-4962aed0c121' and '81bf1038-3fa2-4cf5-a021-10fa9d474aa9'

  Scenario: Get the transactions of a specific account from database descending

    Given User wants to get the transactions of his account number
    When User creates a new account with the following data: 'ES9300752497962198853561' and '5938.67'
    And User creates a new transaction in his account with the following data: '3a56640f-77ec-4db7-870f-0778097ff8ce', 'ES9300752497962198853561', '2019-07-16T16:55:42.000Z', '239.74', '3.80', 'Payment in restaurant'
    And User creates a new transaction in his account with the following data: '3ed6e63a-75cf-4156-ab5d-6b392a51a7c3', 'ES9300752497962198853561', '2019-07-16T16:55:42.000Z', '25.00', '1.80', 'Payment in restaurant'
    And User creates a new transaction in his account with the following data: 'd9c6dd8c-d9c2-4452-99c0-641f97d69e09', 'ES9300752497962198853561', '2019-07-16T16:55:42.000Z', '400.55', '0.60', 'Payment in restaurant'
    And User creates a new transaction in his account with the following data: '13243eb5-8b22-4911-b5e7-a30d85245d44', 'ES9300752497962198853561', '2019-07-16T16:55:42.000Z', '86.54', '0.00', 'Payment in restaurant'
    And User gets the transactions of his account number 'ES9300752497962198853561' 'descending'
    Then The response is successful
    And The order of the transactions is: 'd9c6dd8c-d9c2-4452-99c0-641f97d69e09', '3a56640f-77ec-4db7-870f-0778097ff8ce', '13243eb5-8b22-4911-b5e7-a30d85245d44' and '3ed6e63a-75cf-4156-ab5d-6b392a51a7c3'

  Scenario: Get the transactions of a specific account from database without sorting them

    Given User wants to get the transactions of his account number
    When User creates a new account with the following data: 'ES3900819879297551853354' and '5938.67'
    And User creates a new transaction in his account with the following data: '9305ecaf-5b4a-4c42-b050-588d7ca8822c', 'ES3900819879297551853354', '2019-07-16T16:55:42.000Z', '239.74', '3.80', 'Payment in restaurant'
    And User creates a new transaction in his account with the following data: '401c6d8a-6a6c-47be-9df3-952bb4dec9bd', 'ES3900819879297551853354', '2019-07-16T16:55:42.000Z', '25.00', '1.80', 'Payment in restaurant'
    And User creates a new transaction in his account with the following data: 'c2329b7d-e093-4eb2-ac86-2f507dec741a', 'ES3900819879297551853354', '2019-07-16T16:55:42.000Z', '400.55', '0.60', 'Payment in restaurant'
    And User creates a new transaction in his account with the following data: '70d6cdf1-ede9-4392-8abc-6bbfcda723db', 'ES3900819879297551853354', '2019-07-16T16:55:42.000Z', '86.54', '0.00', 'Payment in restaurant'
    And User gets the transactions of his account number 'ES3900819879297551853354'
    Then The response is successful
    And The order of the transactions is: '9305ecaf-5b4a-4c42-b050-588d7ca8822c', '401c6d8a-6a6c-47be-9df3-952bb4dec9bd', 'c2329b7d-e093-4eb2-ac86-2f507dec741a' and '70d6cdf1-ede9-4392-8abc-6bbfcda723db'