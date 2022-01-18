Feature: Home Work - favoritos & comentarios

    Background: Preconditions
        * url appiURL
        * def tokenResponse = callonce read('classpath:conduitApp/helpers/createToken.feature')
        * def token = tokenResponse.authToken
        * def timeValidator = read('classpath:conduitApp/helpers/time-validator.js')

    Scenario: Favorite articles
        # Step 1: Get articles of the global feed
        Given path 'articles'
        And params {limit: 10, offset: 0}
        And header Authorization = 'Token '+ token
        When method get
        Then status 200
        # Step 2: Get the favorites count and slug ID for the first artice, save it to variables
        * def slug = response.articles[0].slug
        * def initialCount = response.articles[0].favoritesCount
        * print initialCount
        * if(initialCount == 1) karate.call('delete-favorite.feature')
        And match response.articles[0].title == "permanent test"
        # Step 3: Make POST request to increse favorites count for the first article
        Given path 'articles/'+slug+'/favorite'
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
        * def favoritesCount = 0
        And match initialCount == favoritesCount + 1
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
        Given path 'articles'
        And params {limit: 10, offset: 0}
        And header Authorization = 'Token '+ token
        When method get
        Then status 200
        # Step 2: Get the slug ID for the first arice, save it to variable
        * def slug = response.articles[0].slug
        # Step 3: Make a GET call to 'comments' end-point to get all comments
        Given path 'articles/'+slug+'/comments'
        And header Authorization = 'Token '+ token
        When method get
        Then status 200
        * def commentaries = response
        * print commentaries
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
        * print initialCommentsCount
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
        * print commentaries
        And match commentaries.comments[*].body contains any comment
        # Step 9: Verify number of comments increased by 1 (similar like we did with favorite counts)
        * def commentariesCount = commentaries.comments.length
        * print commentariesCount
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
        * print allCommentariesCount
        And match allCommentariesCount == commentariesCount - 1
        * print 'OK'