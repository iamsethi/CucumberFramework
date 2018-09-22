Feature: Automated End2End Tests
  Description: The purpose of this feature is to test End 2 End integration.

  Scenario Outline: Customer place an order by purchasing an item from search
    Given user is on Home Page
    When he search for "dress"
    And choose to buy the first item
    And moves to checkout from mini cart
    When a 'notify' message is sent to the green box with the properties
    And user select new delivery address
    And enter "<customer>" personal details on checkout page
    And select payment method as "check" payment
    And place the order

    Examples: 
      | customer |
      | Brian    |
