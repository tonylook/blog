Feature: Search for blog posts
  As a user
  I want to search for blog posts by title, category, or tags
  So that I can find relevant content

  Background:
    Given the following blog posts exist:
      | title                        | content                                       | author           | image                                       | category    | tags                        |
      | Clean Code                   | A Handbook of Agile Software Craftsmanship    | Robert C. Martin | http://example.com/clean_code.jpg           | Programming | Clean Code,Craftsmanship   |
      | The Pragmatic Programmer     | Your Journey to Mastery                       | Andrew Hunt      | http://example.com/pragmatic_programmer.jpg | Programming | Pragmatic,Mastery          |
      | Design Patterns              | Elements of Reusable Object-Oriented Software | Erich Gamma      | http://example.com/design_patterns.jpg      | Design      | Patterns,OO Design         |
      | Refactoring                  | Improving the Design of Existing Code         | Martin Fowler    | http://example.com/refactoring.jpg          | Programming | Refactoring,Design         |
      | Effective Java               | Best Practices for the Java Platform          | Joshua Bloch     | http://example.com/effective_java.jpg       | Java        | Java,Best Practices        |
      | Head First Design Patterns   | A Brain-Friendly Guide                        | Eric Freeman     | http://example.com/head_first_dp.jpg        | Design      | Patterns,Head First        |
      | Java Concurrency in Practice | Programming Multithreaded Applications        | Brian Goetz      | http://example.com/java_concurrency.jpg     | Java        | Concurrency,Multithreading |

  Scenario: Successfully search for blog posts by title
    When I search for blog posts with title "Java"
    Then the response status code should be 200
    And the response should contain:
      | title                        | content                                | author       | image                                   | category | tags                        |
      | Effective Java               | Best Practices for the Java Platform   | Joshua Bloch | http://example.com/effective_java.jpg   | Java     | Java,Best Practices        |
      | Java Concurrency in Practice | Programming Multithreaded Applications | Brian Goetz  | http://example.com/java_concurrency.jpg | Java     | Concurrency,Multithreading |

  Scenario: Successfully search for blog posts by category
    When I search for blog posts with category "Programming"
    Then the response status code should be 200
    And the response should contain:
      | title                    | content                                    | author           | image                                       | category    | tags                      |
      | Clean Code               | A Handbook of Agile Software Craftsmanship | Robert C. Martin | http://example.com/clean_code.jpg           | Programming | Clean Code,Craftsmanship |
      | The Pragmatic Programmer | Your Journey to Mastery                    | Andrew Hunt      | http://example.com/pragmatic_programmer.jpg | Programming | Pragmatic,Mastery        |
      | Refactoring              | Improving the Design of Existing Code      | Martin Fowler    | http://example.com/refactoring.jpg          | Programming | Refactoring,Design       |

  Scenario: Successfully search for blog posts by tags
    When I search for blog posts with tags "Java,Concurrency"
    Then the response status code should be 200
    And the response should contain:
      | title                        | content                                | author      | image                                   | category | tags                        |
      | Java Concurrency in Practice | Programming Multithreaded Applications | Brian Goetz | http://example.com/java_concurrency.jpg | Java     | Concurrency,Multithreading |

  Scenario: Successfully search for blog posts by title, category, and tags
    When I search for blog posts with title "Effective Java", category "Java", and tags "Java, Best Practices"
    Then the response status code should be 200
    And the response should contain:
      | title          | content                              | author       | image                                 | category | tags                 |
      | Effective Java | Best Practices for the Java Platform | Joshua Bloch | http://example.com/effective_java.jpg | Java     | Java,Best Practices |
