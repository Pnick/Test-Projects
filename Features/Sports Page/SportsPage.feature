@TestScenario @SportsPage
Feature: Sports Page Testing
  Verify Sports Page functionality

  Scenario: Verify balance on Sports Page Header
    Given the "sports" page is opened with user "valid.username"
    Then sports page is displayed
    And player balance on web is correct