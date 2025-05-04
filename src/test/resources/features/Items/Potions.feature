Feature: Potions

  Scenario: Health potion
    Given that the player has health potions in his inventory
    Given the player is at 5 health
    When the player uses a health potion
    Then the player health should be 10


  Scenario: Speed Potion
    Given that the player has speed potions in his inventory
    When the player uses a speed potion
    Then the player should increase the player speed