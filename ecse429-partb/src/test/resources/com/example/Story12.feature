@Story012
Feature: Project in category deletion
As a student,
I want to delete projects in a category,
so I can keep track of which project of a category is left

  Scenario Outline: Delete a project in a category
    Given a category with title "<category_title>" with project with "<project_title>" in it
    When I delete the project with title "<project_title>" from the category
    Then the category will no longer have the project with title "<project_title>"

    Examples: 
      | project_title | category_title     |
      | project10     | cat10              |
      | project20     | cat20              |
      | project30     | cat30              |

  Scenario Outline: Delete a project from a non-existant category
    Given a non-existant category
    When I delete the project with title "<project_title>" from the category
    Then I receive an error message <error>

    Examples: 
      | project_title | error |
      | project1      | 404   |
      | project2      | 404   |
      | project3      | 404   |

  Scenario Outline: Delete a non existant project from a category
    Given a category with title "<category_title>"
    When I delete a non-existant project from the category
    Then I receive an error message <error>

    Examples: 
      | category_title | error     |
      | cat20          | 404       |
      | cat21          | 404       |
      | cat22          | 404       |

