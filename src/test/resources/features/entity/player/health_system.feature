Feature: Player Health System

  Scenario: Player takes damage
    Given the player starts with 10 health
    When the player takes 2 damage
    Then the player's health should be 8

  Scenario: Player heals
    Given the player has 5 health
    When the player heals by 3
    Then the player's health should be 8
