@Story012
Feature: Project in category deletion
As a student,
I want to delete projects in a category,
so I can remove completed projects in a category

  Scenario Outline: Delete a project in a category
    Given a category with title "<category_title>" with project with "<project_title>" in it
    When I delete the project with title "<project_title>" from the category
    Then the category will no longer have the project with title "<project_title>"

    Examples: 
      | project_title | category_title     |
      | project7      | cat7               |
      | project8      | cat8               |
      | project9      | cat9               |

  Scenario Outline: Delete a project from a non-existant category
    Given a non-existant category
    When I delete the project with title "<project_title>" from the category
    Then I receive an error message <error>

    Examples: 
      | project_title  | error |
      | project10      | 404   |
      | project11      | 404   |
      | project12      | 404   |

  Scenario Outline: Delete a non existant project from a category
    Given a category with title "<category_title>"
    When I delete a non-existant project from the category
    Then I receive an error message <error>

    Examples: 
      | category_title | error     |
      | cat10          | 404       |
      | cat11          | 404       |
      | cat12          | 404       |

