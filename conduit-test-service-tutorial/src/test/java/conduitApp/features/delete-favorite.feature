Feature: delete favorite of first article if counts 1

    Background:
        * url appiURL
        * def tokenResponse = callonce read('classpath:conduitApp/helpers/createToken.feature')
        * def token = tokenResponse.authToken

    Scenario: delete favorite of first article if favoriteCount is 1
        Given path 'articles'
        And params {limit: 10, offset: 0}
        And header Authorization = 'Token '+ token
        When method get
        Then status 200
        * def slug = response.articles[0].slug

        Given path 'articles/'+slug+'/favorite'
        And header Authorization = 'Token '+ token
        When method delete
        Then status 200
        