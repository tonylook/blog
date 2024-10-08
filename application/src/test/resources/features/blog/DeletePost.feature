Feature: Delete a blog post
  As an admin
  I want to delete a blog post

  Background:
    Given the following blog posts exist:
      | title   | content      | author      | image                        | category   | tags       |
      | My Post | some content | Mario Fusco | http://example.com/image.jpg | Technology | tag1,tag2 |

  Scenario: Successfully delete a blog post
    Given I am an "admin"
    When I delete the blog post
    Then the response status code should be 204
    And the blog post should no longer exist

  Scenario: Fail to delete a blog post as a regular user
    Given I am an "user"
    When I delete the blog post
    Then the response status code should be 403
    And the response should contain an error message "Only admins can delete blog posts"
