@Api
Feature: Go Rest Api

  Scenario: Create a new user, retrieve the user list, and fetch user details
    Given I have access to the GoRest API
    When I send a POST request to create a new user with valid data
    Then I should receive a 201 response and a user ID
    When I send a GET request to fetch the list of users
    Then I should receive a 200 response and a list containing the created user
    When I send a GET request to fetch the details of the created user by ID
    Then I should receive a 200 response with the correct user details