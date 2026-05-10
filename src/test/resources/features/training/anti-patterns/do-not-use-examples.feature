@training @bdd
Feature: Educational examples


  Scenario: Wrong wait strategy (DO NOT USE)
    Given I open a dynamic loading page
    When I wait with hard sleep for 2 seconds
    Then I expect the result to always be visible

  Scenario: Flaky test with non-deterministic expectation (DO NOT USE)
    Given I open a list page
    When I randomly click one item
    Then I expect the first item text to remain unchanged

  Scenario: Bad locator strategy (DO NOT USE)
    Given I open a login page
    When I locate the username field with a deep absolute xpath
    Then the test should still be maintainable

  Scenario: Test data dependency between tests (DO NOT USE)
    Given booking data is created by another test run
    When I read the old booking id from a shared file
    Then I expect the booking to always exist

  Scenario: Missing context switch in hybrid test (DO NOT USE)
    Given I am on a mobile hybrid screen
    When I try to click web element without switching context
    Then the step fails with no such element

  Scenario: Platform-specific locator not separated (DO NOT USE)
    Given I run a mobile scenario on iOS
    When I use Android-only locator strategy
    Then the element lookup fails on iOS
