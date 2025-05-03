Feature: Inventory Management

  Scenario: Switching between inventory sections
    Given the player is viewing the "Tools" section
    When the player switches section
    Then the current section should be "Blocks"

  Scenario: Selecting and using an item
    Given the player has a "Potion" in the "Potions" section with count 1
    When the player uses the selected item
    Then the "HealthPotion" count should be 0

  Scenario: Adding duplicate tools upgrades instead of stacking
    Given the player has one "Sword"
    When the player picks up another "Sword"
    Then the sword should be upgraded and count should still be 1

  Scenario: Cycling through items in a section
    Given the "Tools" section has more than one item
    When the player cycles to the next item
    Then the selected item should be different
