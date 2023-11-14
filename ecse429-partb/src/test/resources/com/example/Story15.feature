@Story015
Feature: List all categories in a project
As a student,
I want to list all categories in a project,
so I can see what the project is classified as

  Scenario Outline: List all categories in a project
    Given a project with title "<project_title>" with categories with "<category_title_1>" and "<category_title_2>" in it
    When I list all categories in the project
    Then the category list will have "<category_title_1>" and "<category_title_2>"

    Examples: 
      | project_title  | category_title_1 | category_title_2  |
      | pt1            | ct1              | ct4               |
      | pt2            | ct2              | ct5               |
      | pt3            | ct3              | ct6               |

  Scenario Outline: List all some empty title categories in a project
    Given a project with title "<project_title>" with categories with "<category_title_1>" and "<category_title_2>" in it
    When I list all categories in the project
    Then the category list will only have "<category_title_1>"

    Examples: 
      | project_title   | category_title_1  | category_title_2  |
      | pt11            | ct11              |                   |
      | pt21            | ct21              |                   |
      | pt31            | ct31              |                   |

