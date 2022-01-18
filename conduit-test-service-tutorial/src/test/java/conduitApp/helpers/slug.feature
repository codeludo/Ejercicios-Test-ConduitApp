Feature: slug test
Background: Preconditions
    * url appiURL
    * def tokenResponse = callonce read('classpath:conduitApp/helpers/createToken.feature')
    * def token = tokenResponse.authToken

Scenario: get article slug test
    # Step 1: Get articles of the global feed
    Given path 'articles'
    And params {limit: 10, offset: 0}
    And header Authorization = 'Token '+ token
    When method get
    Then status 200
    * def slug = response.articles[0].slug