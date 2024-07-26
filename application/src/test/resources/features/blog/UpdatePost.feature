Feature: Update a blog post
  As a user
  I want to update an existing blog post

  Background:
    Given a blog post exists with the following details:
      | title              | content                                              | author           | image                                     | category        | tags                               |
      | Clean Architecture | A Craftsman's Guide to Software Structure and Design | Robert C. Martin | http://example.com/clean_architecture.jpg | Software Design | Clean Architecture,Software Design |

  Scenario: Successfully update an entire blog post
    When I update the blog post to have:
      | title                        | content                     | author         | image                            | category             | tags                                              |
      | Clean Architecture - Updated | This is the updated content | Antonio Alvino | http://example.com/new-image.jpg | Software Engineering | Clean Architecture,Software Design,Best Practices |
    Then the response status code should be 200
    And the response should contain the updated blog post with:
      | title                        | content                     | author         | image                            | category             | tags                                              |
      | Clean Architecture - Updated | This is the updated content | Antonio Alvino | http://example.com/new-image.jpg | Software Engineering | Clean Architecture,Software Design,Best Practices |

  Scenario: Change the title of a blog post
    When I update the blog post to have:
      | title                                  |
      | Clean Architecture - Partially Updated |
    Then the response status code should be 200
    And the response should contain the updated blog post with:
      | title                                  | content                                              | author           | image                                     | category        | tags                               |
      | Clean Architecture - Partially Updated | A Craftsman's Guide to Software Structure and Design | Robert C. Martin | http://example.com/clean_architecture.jpg | Software Design | Clean Architecture,Software Design |

  Scenario: Change the category of a blog post
    When I update the blog post to have:
      | category         |
      | Computer Science |
    Then the response status code should be 200
    And the response should contain the updated blog post with:
      | title              | content                                              | author           | image                                     | category         | tags                               |
      | Clean Architecture | A Craftsman's Guide to Software Structure and Design | Robert C. Martin | http://example.com/clean_architecture.jpg | Computer Science | Clean Architecture,Software Design |

  Scenario: Add tags to a blog post
    When I update the blog post to add tag "Best Practices"
    Then the response status code should be 200
    And the response should contain the updated blog post with:
      | title              | content                                              | author           | image                                     | category        | tags                                              |
      | Clean Architecture | A Craftsman's Guide to Software Structure and Design | Robert C. Martin | http://example.com/clean_architecture.jpg | Software Design | Clean Architecture,Software Design,Best Practices |

  Scenario: Fail to update a blog post with long content
    When I update the blog post to have:
      | title                        | content                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     | author         | image                            | category             | tags                                              |
      | Clean Architecture - Updated | aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa | Antonio Alvino | http://example.com/new-image.jpg | Software Engineering | Clean Architecture,Software Design,Best Practices |
    Then the response status code should be 400
    And the response should contain an error message "The content cannot be longer than 1024 characters"

#  Scenario: Remove tags from a blog post
#    When I update the blog post to remove tag "Software Design"
#    Then the response status code should be 200
#    And the response should contain the updated blog post with:
#      | title              | content                                              | author           | image                                     | category        | tags               |
#      | Clean Architecture | A Craftsman's Guide to Software Structure and Design | Robert C. Martin | http://example.com/clean_architecture.jpg | Software Design | Clean Architecture |
