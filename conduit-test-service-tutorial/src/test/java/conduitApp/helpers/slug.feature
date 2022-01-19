Feature: slug test
    Background: Preconditions
        Given url appiURL
        * def tokenResponse = callonce read('classpath:conduitApp/helpers/createToken.feature')
        * def token = tokenResponse.authToken

    Scenario: get article slug test
        # Step 1: Get articles of the global feed
        And path 'articles'
        And params {limit: 10, offset: 0}
        And header Authorization = 'Token '+ token
        When method get
        Then status 200
        * def slugItem = response.articles[0].slug
        * print slugItem