Feature: Create new account

  Scenario Outline: Create new account in database

    Given A user wants to create a new account in the system
    When User creates a new account with the following data: "<iban>" and "<balance>"
    Then The creation of the account is successful

    Examples:
      | iban                     | balance  |
      | ES7120385598691229744367 | 92847.30 |
      | ES2604878328848959824418 | 4392.95  |
      | ES4004873965688878451453 | 400.95   |
