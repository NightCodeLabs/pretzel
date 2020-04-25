Feature: Performance Test

  @Performance
  Scenario Outline:  Request a forced answer
    Given Multiple users are requesting for a forced answer
      | Max Users Load | Users Load Per Second | Test Time | Max RPS |Task  |
      |<Max Users Load>|<Users Load Per Second>|<Test Time>|<Max RPS>|<Task>|
    Then The answer is returned within the expected time
      | Expected Time |
      |<Expected Time>|

    Examples:
      |Max Users Load|Users Load Per Second | Test Time | Max RPS |Task         | Expected Time |
      |1             |                   1  |          1|        1|ForcedYes		|          10000|
      |2             |                   1  |          1|        2|ForcedNo	    |          10000|
