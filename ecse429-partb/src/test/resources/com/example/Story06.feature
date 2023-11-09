@Story06
Feature: Task description modification
As a student,
I modify a tasks description,
so I can better illustrate what I want to do
# Normal flow
  Background: 
    Given the application is running

  Scenario Outline: Change a task's description
    Given a task with existing description "<existing_description>"
    When I change the tasks description from "<existing_description>" to "<new_description>"
    Then the task will have a new description "<new_description>" and "<existing_description>" will be removed

    Examples: 
      | existing_description | new_description |
      | Create task          | See task        |
      | See task             | To delete       |

# Alternate flow

  Scenario Outline: Change a task's empty description
    Given a task with a description "<empty description>"
    When I change the tasks description from "<empty_description>" to "<new_description>"
    Then the task will have a new description "<new_description>" and "<empty_description>" will be removed

    Examples: 
      | empty_description | new_description |
      |                   | Fill 1          |
      |                   | Fill 2          |
      |                   | Fill 3          |

# Error flow

Scenario Outline: Change a non-existent task's description
  Given a non-existing task with "<empty_description>"
  When I change the tasks description from "<empty_description>" to "<new_description>"
  Then I receive an error message

  Examples:
      Examples: 
      | empty_description | new_description |
      |                   | Fill 1          |
      |                   | Fill 2          |
      |                   | Fill 3          |