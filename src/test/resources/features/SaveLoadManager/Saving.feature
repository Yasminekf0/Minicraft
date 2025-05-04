Feature: Game Save System

  Background:
    Given the game is running

  Scenario: Creating a save file
    When I save the game
    Then a file should be created at the specified path
    And the file should contain the serialized game state

  Scenario: Overwriting a previous save
    Given a save file already exists
    When I save the game
    Then the previous save file should be overwritten


