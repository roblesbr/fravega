@Chrome

Feature: Fravega

  Scenario: Search and buy "Samsung Refrigerator"
    Given Im on the Fravega website
    When I search for "Heladera Samsung" in the search bar
    And I select the second product from the search results
    Then I check that the product has stock
    When I add the product to the shopping cart
    Then I verify that the product is in the shopping cart

   # Scenario: Filter products by category
   # Given Im on the Fravega website
   # When I select the category "Tecnologias"
   # And I filter products by the brand "Samsung"
   # Then I verify that all displayed products belong to the "Samsung" brand