Feature: Implementing Biomes which contain info about how they should be constructed
  Scenario: Plains has the correct Hash maps
    Given A Biome PLAINS
    When we request the tile HashMap
    When we request the block HashMap
    Then they correspond to the correct Maps
