@Story08
Feature: Get not done todos from projects
As a student, i want to get all tasks of a project that are not completed

  Scenario Outline: Get incomplete tasks from a project that contains some
    Given the existing project "<project>" containing incomplete tasks "<one>" and "<two>"
    And containing complete task "<three>"
    When I request incomplete tasks from "<project>"
    Then I get "<one>" and "<two>" but not "<three>"

    Examples: 
      | project | one        | two        | three      |
      | PartB   | UserStory1 | UserStory2 | UserStory6 |
      | PartC   | UserStory3 | UserStory4 | UserStory7 |

  Scenario Outline: Get incomplete tasks from a project that dont contain any
    Given the existing project "<project>" containing completed tasks "<one>"
    When I request incomplete tasks from "<project>"
    Then I get an empty todo array

    Examples: 
      | project | one      |
      | PartA   | testing  |
      | PartD   | securing |

  Scenario Outline: Get incomplete tasks from inexistant project
    When I request incomplete tasks from "<project>"
    Then I get an error code <error>

    Examples: 
      | project | error |
      | PartE   |   404 |
      | PartF   |   404 |
