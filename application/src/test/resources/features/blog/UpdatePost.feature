Feature: Update a blog post
  As a user
  I want to update an existing blog post

  Background:
    Given a blog post exists with the following details:
      | title              | content                                              | author           | image                                     | category        | tags                                |
      | Clean Architecture | A Craftsman's Guide to Software Structure and Design | Robert C. Martin | http://example.com/clean_architecture.jpg | Software Design | Clean Architecture, Software Design |

  Scenario: Successfully update a blog post
    When I update the blog post to have title "Clean Architecture - Updated", content "This is the updated content", author "Robert C. Martin", image "http://example.com/new-image.jpg", category "Software Engineering", and tags "Clean Architecture, Software Design, Best Practices"
    Then the response status should be 200
    And the response should contain the updated blog post with:
      | title                        | content                     | author           | image                            | category             | tags                                                |
      | Clean Architecture - Updated | This is the updated content | Robert C. Martin | http://example.com/new-image.jpg | Software Engineering | Clean Architecture, Software Design, Best Practices |

  Scenario: Partially update a blog post
    When I partially update the blog post to have title "Clean Architecture - Partially Updated"
    Then the response status should be 200
    And the response should contain the updated blog post with:
      | title                                  | content                                              | author           | image                                     | category        | tags                                |
      | Clean Architecture - Partially Updated | A Craftsman's Guide to Software Structure and Design | Robert C. Martin | http://example.com/clean_architecture.jpg | Software Design | Clean Architecture, Software Design |

  Scenario: Change the category of a blog post
    When I update the blog post to have category "Computer Science"
    Then the response status should be 200
    And the response should contain the updated blog post with:
      | title              | content                                              | author           | image                                     | category         | tags                                |
      | Clean Architecture | A Craftsman's Guide to Software Structure and Design | Robert C. Martin | http://example.com/clean_architecture.jpg | Computer Science | Clean Architecture, Software Design |

  Scenario: Add tags to a blog post
    When I update the blog post to add tags "Best Practices"
    Then the response status should be 200
    And the response should contain the updated blog post with:
      | title              | content                                              | author           | image                                     | category        | tags                                                |
      | Clean Architecture | A Craftsman's Guide to Software Structure and Design | Robert C. Martin | http://example.com/clean_architecture.jpg | Software Design | Clean Architecture, Software Design, Best Practices |

  Scenario: Remove tags from a blog post
    When I update the blog post to remove tags "Software Design"
    Then the response status should be 200
    And the response should contain the updated blog post with:
      | title              | content                                              | author           | image                                     | category        | tags               |
      | Clean Architecture | A Craftsman's Guide to Software Structure and Design | Robert C. Martin | http://example.com/clean_architecture.jpg | Software Design | Clean Architecture |
