@TestScenario @HomePage
Feature: Open Home Page
  Verify if user is able to open Home page

  Scenario: Open Home Page
    Given the "home" page is opened with user "valid.username"
    When click "logo" button
    Then home page is displayed