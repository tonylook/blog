Feature: Create a blog post
  As a user
  I want to create a new blog post

  Background:
    Given the "Technology" category and the "Mario Fusco" author exist:

  Scenario: Successfully create a new blog post
    When I create a new blog post with the following details:
      | title   | content                             | author      | image                        | category   | tags       |
      | My Post | This is the content of my new post. | Mario Fusco | http://example.com/image.jpg | Technology | tag1, tag2 |
    Then the response status code should be 201
    And the response should contain the new blog post with:
      | title   | content                             | author      | image                        | category   | tags       |
      | My Post | This is the content of my new post. | Mario Fusco | http://example.com/image.jpg | Technology | tag1, tag2 |

  Scenario: Fail to create a new blog post with missing title
    When I create a new blog post with the following details:
      | title | content                             | author      | image                        | category   | tags       |
      |       | This is the content of my new post. | Mario Fusco | http://example.com/image.jpg | Technology | tag1, tag2 |
    Then the response status code should be 400
    And the response should contain an error message "Title is required"

  Scenario: Fail to create a new blog post with content exceeding 1024 characters
    When I create a new blog post with the following details:
      | title   | content                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     | author      | image                        | category   | tags       |
      | My Post | aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa | Mario Fusco | http://example.com/image.jpg | Technology | tag1, tag2 |
    Then the response status code should be 400
    And the response should contain an error message "The content cannot be longer than 1024 characters"

  Scenario: Successfully create a new blog post with optional image
    When I create a new blog post with the following details:
      | title   | content                             | author      | image | category   | tags       |
      | My Post | This is the content of my new post. | Mario Fusco |       | Technology | tag1, tag2 |
    Then the response status code should be 201
    And the response should contain the new blog post with:
      | title   | content                             | author      | image | category   | tags       |
      | My Post | This is the content of my new post. | Mario Fusco |       | Technology | tag1, tag2 |