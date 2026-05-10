    @bdd
Feature: ParaBank Login Functionality
    @smoke 
Scenario: Successfully login 2
    Given : I am on ParaBank login page
    When : I login with username "valid-user" and password "valid-pass"
    Then : home page should be displayed
    @debug @happy
    
Scenario Outline: Invalid login
    Given I am on ParaBank login page
    When I login with username "<username>" and password "<password>"
    Then I should see an authentication error message

    Examples:
    | username       | password       |
    | invalid-user-1 | invalid-pass-1 |
    | invalid-user-2 | invalid-pass-2 |