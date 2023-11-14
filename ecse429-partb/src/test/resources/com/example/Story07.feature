@Story07
Feature: Project deletion
As a student,
I want to delete projects I no longer need,
so I can manage my time

  Scenario Outline: I delete an existing project containing tasks
    Given the existing project "<project>" containing tasks
    When I delete the project "<project>"
    Then the project will no longer exist

    Examples: 
      | project  |
      | Attempt1 |
      | Attempt2 |

  Scenario Outline: I delete an empty existing project
    Given the empty existing project "<project>"
    When I delete the project "<project>"
    Then the project will no longer exist

    Examples: 
      | project  |
      | Attempt3 |
      | Attempt4 |

  Scenario Outline: I delete an unexisting project
    When i delete the project "<project>"
    Then I will get an error code <error>

    Examples: 
      | project  | error |
      | NoID1 |   404 |
      | NoID2 |   404 |
