@Story06
#James Willems 260921223
Feature: Task description modification
As a student,
I modify a tasks description,
so I can better illustrate what I want to do

#Normal Flow

  Scenario Outline: Change a task's description
    Given a task with existing description "<existing_description>"
    When I change the tasks description to "<new_description>"
    Then the task will have a new description "<new_description>"

    Examples: 
      | existing_description | new_description |
      | CreateTask           | SeeTask         |
      | SeeTask              | ToDelete        |

#Alternate Flow

  Scenario Outline: Change a task's description with an empty one
    Given a task with existing description "<description>"
    When I change the tasks description to "<new_description>"
    Then the task will have a new description "<new_description>"

    Examples: 
      | description | new_description |
      | Bonsoir     |                 |
      | BomDia      |                 |
      | Bonjour     |                 |

#Error Flow

  Scenario Outline: Change a non-existent task's description
    Given a non-existing task with "<empty_description>"
    When I change the tasks description to "<new_description>"
    Then I receive an error message <error>

    Examples: 

    Examples: 
      | empty_description | new_description | error |
      |                   | Fill1           |   404 |
      |                   | Fill2           |   404 |
      |                   | Fill3           |   404 |
