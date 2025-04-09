Feature: Game Load System

  Scenario: Loading a valid saved game
    Given a valid save file exists
    When I load the game
    Then the player and world state should match the saved state

  Scenario: Handling load errors
    Given the save file is missing or corrupted
    When I attempt to load the game
    Then I should receive a load error message