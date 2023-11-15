@Story09
#James Willems 260921223
Feature: Task relationship deletion
As a student, I want to delete the link between a todo and a project once it is completed

#Normal Flow

  Scenario Outline: Remove task from project containing others
    Given the existing project "<project>" containing incomplete tasks "<task1>" and "<task2>"
    When I remove the link between "<project>" and "<task1>"
    Then "<project>" will only contain "<task2>"

    Examples: 
      | project | task1       | task2      |
      | ECSE429 | exploratory | unit       |
      | ECSE321 | development | production |

#Alternate Flow

  Scenario Outline: Remove task from project containing none others
    Given the existing project "<project>" containing completed tasks "<one>"
    When I remove the link between "<project>" and "<one>"
    Then "<project>" will contain no tasks

    Examples: 
      | project | one              |
      | ECSE223 | ModelProgramming |
      | FACC300 | Econ4Eng         |

#Error Flow

  Scenario Outline: Remove task from non-existent project
    Given a task with existing description "<one>"
    When I remove the link between "<project>" and "<one>"
    Then I get an error code <error>

    Examples: 
      | one     | error |
      | hello   |   404 |
      | goodbye |   404 |
