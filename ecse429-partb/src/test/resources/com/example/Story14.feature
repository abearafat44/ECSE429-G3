@Story012
Feature: Category in project deletion
As a student,
I want to delete categories in a project,
so I can edit what a project is classified by

  Scenario Outline: Delete a category in a project
    Given a project with title "<project_title>" with category with "<category_title>" in it
    When I delete the category with title "<category_title>" from the project
    Then the project will no longer have the category with title "<category_title>"

    Examples: 
      | project_title | category_title       |
      | pid1          | catid1               |
      | pid2          | catid2               |
      | pid3          | catid3               |

  Scenario Outline: Delete a category from a non-existant project
    Given a non-existant project
    When I delete the category with title "<category>" from the project
    Then I receive an error message <error>

    Examples: 
      | category_title | error |
      | catid4         | 404   |
      | catid5         | 404   |
      | catid6         | 404   |

  Scenario Outline: Delete a non existant category from a project
    Given a project with title "<project_title>"
    When I delete a non-existant category from the project
    Then I receive an error message <error>

    Examples: 
      | project_title  | error     |
      | pid10          | 404       |
      | pid11          | 404       |
      | pid12          | 404       |

