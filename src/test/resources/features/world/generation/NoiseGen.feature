Feature: Implementing intelligent noisy Noise generation

  Scenario: Generation of 2D array
    Given a noise size 50
    And a noise seed 23
    When Noise Generation is called
    Then a Noise 2D array is returned

  Scenario: Generation of square array
    Given a noise size 50
    And a noise seed 23
    When Noise Generation is called
    Then a square array of the correct size is returned

  Scenario: Generation of same array
    Given a noise size 50
    And a noise seed 29
    And a different noise object with the same seed
    When Noise Generation is called
    Then Both noise arrays are the same

  Scenario: Generation of different arrays
    Given a noise size 50
    And a noise seed 29
    And a different noise object with different seed
    When Noise Generation is called
    Then Both noise arrays are not the same