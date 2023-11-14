@Story013
Feature: List all projects in a category
As a student,
I want to list all projects in a category,
so I can keep track of which project of a category is left

  Scenario Outline: List all projects in a category
    Given a category with title "<category_title>" with projects with "<project_title_1>" and "<project_title_2>" in it
    When I list all projects in the category
    Then the list will have "<project_title_1>" and "<project_title_2>"

    Examples: 
      | category_title  | project_title_1        | project_title_2  |
      | cat13           | project13              | project16        |
      | cat14           | project14              | project17        |
      | cat15           | project15              | project18        |

  Scenario Outline: List all some empty title projects in a category
    Given a category with title "<category_title>" with projects with "<project_title_1>" and "<project_title_2>" in it
    When I list all projects in the category
    Then the list will have "<project_title_1>" and "<project_title_2>"

    Examples: 
      | category_title  | project_title_1        | project_title_2  |
      | cat13           | p1e                    |                  |
      | cat14           | p2e                    |                  |
      | cat15           | p3e                    |                  |

