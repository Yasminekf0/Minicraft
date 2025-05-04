Feature: Game Load System

  Scenario: Loading a saved game
    Given a saved game file exists at "saves/save1.dat"
    When I press the "Load Game" button
    Then the game state should be restored from the file

  Scenario: Handling load errors
    Given the save file is missing or corrupted
    When I attempt to load the game
    Then I should receive a load error message