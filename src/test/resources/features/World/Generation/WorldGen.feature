Feature: Implementing intelligent noisy world generation

  Scenario: Generation of world
    Given a world size 50
    When World Generation is called
    Then fill the 2D array with noisy Tiles
