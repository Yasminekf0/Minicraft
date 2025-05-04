Feature: Enemy

  Scenario: Follow Player
    Given the game is running
    When enemy in range of player
    Then enemy move towards player

  Scenario: Damage
    Given the game is running
    When enemy touches player
    Then Player take damage