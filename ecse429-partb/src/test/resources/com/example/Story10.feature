@Story10
#James Willems 260921223
Feature: As a student, I want to create todos to planify work

#Normal flow

  Scenario Outline: Creating a todo with all parameters

  Scenario Outline: Create a todo with all parameters defined
    When I create a task with title "<title>", doneStatus "<doneStatus>" and description "<description>"
    Then a task with "<title>", "<doneStatus>" and "<description>" is created

    Examples: 
      | title      | doneStatus | description |
      | FirstTask  | false      | trial       |
      | SecondTask | true       | hola        |

#Alternate Flow

  Scenario Outline: Create a todo with a parameters missing
    When I create a task with title "<title>" and doneStatus "<doneStatus>"
    Then a task with "<title>", "<doneStatus>" and "<description>" is created

    Examples: 
      | title      | doneStatus | description |
      | ThirdTask  | true       |             |
      | FourthTask | false      |             |

#Error flow

  Scenario Outline: Create a todo without a title
    When I create a task without a title and description "<description>"
    Then I get an error <error>

    Examples: 
      | description | error |
      | hola        |   400 |
      | bonsoir     |   400 |
