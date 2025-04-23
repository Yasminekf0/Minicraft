Feature: Having a cycle that switches between day and night over time

  Scenario: Time increases as a result of a tick
    Given a dayCycleManager at time 0
    When a tick is called
    Then the time should increase by 1 tick worth of milliseconds

  Scenario: The sun is setting
    Given a dayCycleManager at sunset
    When a tick is called
    Then night filter level should increase
