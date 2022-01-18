Feature: Test for the home page

  Background: preconditios
    Given url appiURL
    * def tokenResponse = callonce read('classpath:conduitApp/helpers/createToken.feature')
    * def token = tokenResponse.authToken


  Scenario: get tags
    And path 'tags'
    When method get
    Then  status 200
    * print response

  Scenario: get tags with token
    And path 'tags'
    And header Authorization = 'Token '+token
    When method get
    Then  status 200
    And match response.tags contains ['welcome']
    And match response.tags !contains ['test']
    And match response.tags contains any ['test', 'welcome']
    And match response.tags == '#array'
    And match each response.tags == '#string'


    * print response

  Scenario: get articles
    * def timeValidator = read('classpath:conduitApp/helpers/time-validator.js')
    Given path 'articles'
    #    And param limit = 10
    #    And param offset = 0
    And params {limit: 10, offset: 0}
    And header Authorization = 'Token '+token
    When method get
    Then status 200
    And match response.articles == '#[4]'
    And match response == {'articles':'#[4]', 'articlesCount':4}
    And match response.articlesCount == 4
    And match response.articles[0].createdAt contains "2022"
    And match response.articles[*].favoritesCount contains 0
    And match response.articles[*].author.bio contains null
    And match response..bio contains null
    And match response..bio contains '##string'
    And match each response..following == false
    And match each response..following == '#boolean'
    And match each response..favoritesCount == '#number? _ >= 0'
    And match each response.articles ==
      """
      {
        "slug": "#string",
        "title": "#string",
        "description": "#string",
        "body": "#string",
        "createdAt": "#? timeValidator(_)",
        "updatedAt": "#? timeValidator(_)",
        "tagList": "#array",
        "author": {
          "username": "#string",
          "bio": "##string",
          "image": "#string",
          "following": "#boolean"
        },
        "favoritesCount": "#number",
        "favorited": "#boolean"
      }
      """