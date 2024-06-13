@Api
Feature: Go Rest Api

  Scenario: Get list of users and validate the first active user
    Given I send a GET request to "https://gorest.co.in/public/v1/users"
    When I get the list of users
    And I filter the active users
    And I get the details of the first active user
    And the user status should be "active"
    Then the response status code should be 200

  Scenario: Get list of users and update the name of the first user
    Given I send a GET request to "https://gorest.co.in/public/v1/users"
    When I get the list of users
    And I get the details of the first user
    And I update the user's name to "<INPUT NAME>" with email "jana.waters@hotmail.us" and status "active"
    Then  the response status code should be 200