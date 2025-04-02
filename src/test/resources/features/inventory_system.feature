Feature: Inventory system

  Scenario: Player adds an item to the inventory
    Given the player has an empty inventory
    When the player picks up a "Sword" in "Tools"
    Then the inventory should contain "Sword" in "Tools"

  Scenario: Player views inventory sections
    Given the player has items in all inventory sections
    When the player switches to the "Tools" section
    Then the player should see tools in the inventory

  Scenario: Player upgrades a tool
    Given the player has a "Wooden Pickaxe" in "Tools" in their inventory
    And the player finds an upgrade chest
    When the player upgrades the "Wooden Pickaxe"
    Then the inventory should contain an "Iron Pickaxe" instead

  Scenario: Player selects an item from the inventory
    Given the player has "Sword" and "Pickaxe" in their inventory
    When the player selects the "Pickaxe"
    Then the selected item should be "Pickaxe"

  Scenario: Player switches between inventory sections
    Given the player is in the "Tools" inventory section
    When the player switches to the "Potions" section
    Then the player should see potions in the inventory

  Scenario: Player tries to pick up a duplicate tool
    Given the player has a "Sword" in "Tools" in their inventory
    When the player tries to pick up another "Sword" in "Tools"
    Then the inventory should still contain only one "Sword" in "Tools"