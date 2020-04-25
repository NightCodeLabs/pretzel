Feature: Functional Test

  @Functional
  Scenario Outline: Request a Forced <answerType> Answer
    When a forced <answerType> is requested
    Then the corresponding <answerType> is returned

    Examples:
    |answerType|
    |yes       |
    |no        |
