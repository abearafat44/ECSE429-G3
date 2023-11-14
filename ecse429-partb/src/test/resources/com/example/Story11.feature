@Story011
Feature: Task description modification
As a student,
I want to add projects to a category,
so I can better classify each project I am working on

  Scenario Outline: Add a project to a category
    Given a category with title "<category_title>"
    When I add the project with title "<project_title>" to the category
    Then the category will have the project with title "<project_title>"

    Examples: 
      | project_title | category_title     |
      | project1      | cat1               |
      | project2      | cat2               |
      | project3      | cat3               |

  Scenario Outline: Add a project to a non-existant category
    Given a non-existant category
    When I add the project with title "<project_title>" to the category
    Then I receive an error message <error>

    Examples: 
      | project_title | error |
      | project1      | 404   |
      | project2      | 404   |
      | project3      | 404   |

  Scenario Outline: Add a project with no title to a category
    Given a category with title "<category_title>"
    When I add the project with title "<project_title>" to the category
    Then the category will have the project with title "<project_title>"

    Examples: 
      | project_title | category_title     |
      |               | cat4               |
      |               | cat5               |
      |               | cat6               |

