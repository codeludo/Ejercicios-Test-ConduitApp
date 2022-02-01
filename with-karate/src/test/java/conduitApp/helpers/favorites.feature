Feature: favorites

    Background: preconditions
        Given url appiURL
        * def tokenResponse = callonce read('classpath:conduitApp/helpers/createToken.feature')
        * def token = tokenResponse.authToken

    Scenario: Counting first article number of favorites
        And path 'articles'
        And params {limit: 10, offset: 0}
        And header Authorization = 'Token '+ token
        When method get
        Then status 200
        * def count = response.articles[0].favoritesCount
        * print count