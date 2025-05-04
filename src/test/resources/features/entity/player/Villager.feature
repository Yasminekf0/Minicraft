Feature: Villager

  Scenario: I Exist!
    Given the game is running
    When world is generated or loaded
    Then Villager Spawn

    Scenario: Path-finding
      Given the game is running
      When the villager exists
      Then change position