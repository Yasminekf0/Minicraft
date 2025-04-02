Feature: Entities moving diagonally
  Scenario: Up-right movement of the player
    Given a Player
    And a KeyController
    And his WorldPosition is 0 0
    And a KeyInput of "W"
    And a KeyInput of "D"
    When update Player
    Then the player should move both up and right