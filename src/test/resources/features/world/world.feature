Feature: Implementing a world which contains all the information obout the map.

  Scenario: get a block

    Given a world with seed 132 and size 1000
    When We request block at coords 100 100
    Then a block is returned if it exists

  Scenario: check if Tile is walkable
    Given  a world with seed 69 and size 120
    And the Tile at coords 100 100 is walkable
    And there is no Block
    Then The tile should be walkable

  Scenario: break a block
    Given  a world with seed 69 and size 120
    And there is a Block at 100 100
    When we break the block
    Then there should be no block anymore

  Scenario: get Tiles
    Given a world with seed 132 and size 1000
    When We request the tiles
    Then the array of tiles is returned

  Scenario: get Blocks
    Given a world with seed 132 and size 1000
    When We request the blocks
    Then the array of blocks is returned
