Feature: Implementing intelligent noisy world generation

  Scenario: Generation of 2D array
    Given a world size 50
    And a seed 23
    When World Generation is called
    Then a Tile 2D array is returned

  Scenario: Generation of square array
    Given a world size 50
    And a seed 23
    When World Generation is called
    Then a square array of the correct size is returned

  Scenario: Generation of same array
    Given a world size 50
    And a seed 29
    And a different world object with same seed
    When World Generation is called
    Then Both world arrays are the same

  Scenario: Generation of different arrays
    Given a world size 50
    And a seed 29
    And a different world object with different seed
    When World Generation is called
    Then Both world arrays are not the same
