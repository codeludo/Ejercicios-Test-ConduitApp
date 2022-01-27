Feature: Home Work - favoritos & comentarios
As user of the api
I want to give a favorite an one article and delete it again
and make a comment post and delete it

    Background: Preconditions
        Given url appiURL
        * def tokenResponse = callonce read('classpath:conduitApp/helpers/createToken.feature')
        * def token = tokenResponse.authToken
        * def timeValidator = read('classpath:conduitApp/helpers/time-validator.js')
        * def slugResponse = callonce read('classpath:conduitApp/helpers/slug.feature')
        
    Scenario: Favorite articles
        # Step 1: Get articles of the global feed
        # Step 2: Get the favorites count and slug ID for the first artice, save it to variables
        * def slug = slugResponse.slugItem
        * def initialCountResponse = call read('classpath:conduitApp/helpers/favorites.feature')
        * def initialCount = initialCountResponse.count
        * print initialCount
        * if(initialCount == 1) karate.call('classpath:conduitApp/helpers/delete-favorite.feature')
        * def countResponse = call read('classpath:conduitApp/helpers/favorites.feature')
        * def count = countResponse.count
        * print count
        # Step 3: Make POST request to increse favorites count for the first article
        And path 'articles/'+slug+'/favorite'
        And header Authorization = 'Token '+ token
        When method Post
        # Step 4: Verify response schema
        Then status 200
        And match response.article ==
            """
        {
            "id": "#number",
            "slug": "#string",
            "title": "#string",
            "description": "#string",
            "body": "#string",
            "createdAt": "#? timeValidator(_)",
            "updatedAt": "#? timeValidator(_)",
            "authorId": "#number",
            "tagList": "#array",
            "author": {
            "username": "#string",
            "bio": "##string",
            "image": "#string",
            "following": "#boolean"
            },
            "favoritedBy": "#array",
            "favorited": "#boolean",
            "favoritesCount": "#number"
            }
            """
        # Step 5: Verify that favorites article incremented by 1
        * def finalCountResponse = call read('classpath:conduitApp/helpers/favorites.feature')
        * def finalCount = finalCountResponse.count
        And match finalCount == count + 1
        # Step 6: Get all favorite articles
        Given path 'articles'
        And params {favorited: Donatelo, limit: 10, offset: 0}
        And header Authorization = 'Token '+ token
        When method Get
        Then status 200
        # Step 7: Verify response schema
        And match response ==
            """
            {
                "articles": "#array",
                "articlesCount": "#number"
            }
            """
        # Step 8: Verify that slug ID from Step 2 exist in one of the favorite articles
        And match each response.articles[*].slug == slug



    Scenario: Comment articles
        * def commentGenerator = Java.type('conduitApp.helpers.DataGenerator')
        * def data = commentGenerator.randomComment()
        # Step 1: Get atricles of the global feed
        # Step 2: Get the slug ID for the first article, save it to variable
        # Already done with the favorite.feature
        * def slug = slugResponse.slugItem
        # Step 3: Make a GET call to 'comments' end-point to get all comments
        Given path 'articles/'+slug+'/comments'
        And header Authorization = 'Token '+ token
        When method get
        Then status 200
        * def commentaries = response
        # Step 4: Verify response schema
        And match each response.comments ==
        """
            {
			"id": "#number",
			"createdAt": "#? timeValidator(_)",
			"updatedAt": "#? timeValidator(_)",
			"body": "#string",
			"author": {
				"username": "#string",
				"bio": "##string",
				"image": "#string",
				"following": "#boolean"
			}
		}
        """
        # Step 5: Get the count of the comments array lentgh and save it into a variable
        * def arrayComments = response.comments
        * def initialCommentsCount = arrayComments.length
        * def comment = data.body
        # Step 6: Make a POST request to publish a new comment
        Given path 'articles/'+slug+'/comments'
        And header Authorization = 'Token '+ token
        And request
        """
            {
                "comment":{
                   "body":#(comment)
                }
             } 
        """
        When method Post
        Then status 200
        * def idLastComment = response.comment.id
        # Step 7: Verify response schema that should contain posted comment text
        # Step 8: Get the list of all comments for this article one more time
        Given path 'articles/'+slug+'/comments'
        And header Authorization = 'Token '+ token
        When method get
        Then status 200
        * def commentaries = response
        And match commentaries.comments[*].body contains any comment
        # Step 9: Verify number of comments increased by 1 (similar like we did with favorite counts)
        * def commentariesCount = commentaries.comments.length
        And match commentariesCount == initialCommentsCount + 1 
        # Step 10: Make a DELETE request to delete comment
        Given path 'articles/'+slug+'/comments/'+idLastComment
        And header Authorization = 'Token '+ token
        When method DELETE
        Then status 204
        # Step 11: Get all comments again and verify number of comments decreased by 1
        Given path 'articles/'+slug+'/comments'
        And header Authorization = 'Token '+ token
        When method get
        Then status 200
        * def allCommentaries = response
        * def allCommentariesCount = allCommentaries.comments.length
        And match allCommentariesCount == commentariesCount - 1
        * print 'OK'