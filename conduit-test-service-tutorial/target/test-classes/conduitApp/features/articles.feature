Feature: Test for the articles

    Background: preconditios
        Given url appiURL
        * def tokenResponse = callonce read('classpath:conduitApp/helpers/createToken.feature')
        * def token = tokenResponse.authToken
        * def body = read('classpath:conduitApp/helpers/newArticleBody.json')
        * def bodyGenerated = Java.type('conduitApp.helpers.DataGenerator')
        * set body.article.title = bodyGenerated.articleBodyGenerator().title
        * set body.article.description = bodyGenerated.articleBodyGenerator().description
        * set body.article.body = bodyGenerated.articleBodyGenerator().body


    Scenario: create articles
        Given path 'articles'
        And header Authorization = 'Token '+ token
        And request body
        When method Post
        Then status 200
        * def articleId = response.article.slug


        # Given path 'articles'
        # And header Authorization = 'Token '+ token
        # And request body
        # When method Post
        # Then status 200


        Given path 'articles'
        And params {limit: 10, offset: 0}
        And header Authorization = 'Token '+token
        When method get
        Then status 200
        And match response.articles[0].title == body.article.title


        Given path 'articles',articleId
        And header Authorization = 'Token '+ token
        When method Delete
        Then status 204

        Given path 'articles'
        And params {limit: 10, offset: 0}
        And header Authorization = 'Token '+token
        When method get
        Then status 200
        And match response.articles[0].title != body.article.title

