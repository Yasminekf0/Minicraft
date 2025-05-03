Feature:  Tools
  Scenario: Better Pickaxe
    Given that the player has a "Pickaxe" in "Tools" section in their inventory
    When the tool is upgraded
    Then the "Stone Pickaxe" should mine faster

  Scenario: Better Sword
    Given that the player has a "Sword" in "Tools" section in their inventory
    When the tool is upgraded
    Then the "Stone Sword" should do more damage

  Scenario: Better Axe
    Given that the player has a "Axe" in "Tools" section in their inventory
    When the tool is upgraded
    Then the "Stone Axe" should chop faster