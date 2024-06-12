@Chrome

Feature: Verify availability of the second item from the second results page on AliExpress

  Background: As a Customer
  I want to verify if the second item from the second results page
  when searching for "instax mini" on www.aliexpress.com has at least 1
  item available for purchase

  Scenario: Check availability of the second item from the second results page
    Given I am on the AliExpress website
    When I search for "instax mini"
    And I navigate to the second results page
    And I click on the second item
    Then I should see at least 1 item available for purchase for the second item
