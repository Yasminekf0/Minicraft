Feature: Game Save System

  Background:
    Given the game is running

  Scenario: Saving the game via the options menu
    When I open the options menu
    And I navigate to the "Save" button
    And I press the enter key
    Then the current game state should be saved to a file

  Scenario: Creating a save file
    When I save the game
    Then a file should be created at the specified path
    And the file should contain the serialized game state

  Scenario: Overwriting a previous save
    Given a save file already exists
    When I save the game again
    Then the previous save file should be overwritten

  Scenario: Handling save errors
    Given the save directory does not exist or is not writable
    When I attempt to save the game
    Then I should receive a save error message
