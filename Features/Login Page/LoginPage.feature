@TestScenario @Login
Feature: Login Functional Verification

  Scenario Outline: Verify that User is able to Login with Valid Credentials and pressing Login button.
    Given the Login screen is opened
    When enter "<username>" and "<password>"
    And click on "LoginSubmit" button
    Then home page is displayed

    Examples: 
      | username       | password       |
      | valid.username | valid.password |
