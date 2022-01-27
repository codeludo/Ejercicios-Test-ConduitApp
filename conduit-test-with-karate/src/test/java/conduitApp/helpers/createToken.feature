Feature: create token

    Background: preconditions
        Given url appiURL

    Scenario: get token
        And request
            """
            {
                "user": {
                    "email": "jcam.qvision@gmail.com",
                    "password": "testerExtremo"
                }
            }
            """
        And path 'users/login'
        When method Post
        Then status 200
        * def authToken = response.user.token
        * print authToken
