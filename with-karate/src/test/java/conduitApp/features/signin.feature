Feature: Sign in user

Background: preconditios
    Given url appiURL


Scenario: user sign in
    Given path 'users/login'
    And request 
    """
        {
    "user": {
        "email": "jcam.qvision@gmail.com",
        "password": "testerExtremo"
    }
}
    """
    When method Post
    Then status 200