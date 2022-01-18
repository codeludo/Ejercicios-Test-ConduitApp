Feature: Sign up new user

    Background: preconditios
        Given url appiURL
        * def dataGenerator = Java.type('conduitApp.helpers.DataGenerator')
        * def data = dataGenerator.randomSignUpData()

    #escenario de errores de registro de cuenta
    Scenario Outline: sign up errores

        Given path 'users'
        * def email = data.email
        * def user = data.username
        And request
            """
            {"user":{
            "email": <email>,
            "password": <password>,
            "username": <username>
            }
            }
            """
        When method Post
        Then status 422
        And match response == <errormessage>

        Examples:
        |   error           |           email             |   password    |   username    |   errormessage                                      |
        |"username repetido"|       #(email)              |"testerExtremo"| "rubetusina"  |   {"errors":{"username":["has already been taken"]}}|
        |"email repetido"   |"1101jcam1.qvision@gmail.com"|"testerExtremo"|    #(user)    |   {"errors":{"email":["has already been taken"]}}   |
        |"email vacio"      |             ""              |"testerExtremo"|    #(user)    |   {"errors":{"email":["can't be blank"]}}           |
        |"password vacio"   |            #(email)         |     ""        |    #(user)    |   {"errors":{"password":["can't be blank"]}}        |
        |"username vacio"   |            #(email)         |"testerExtremo"|      ""       |   {"errors":{"username":["can't be blank"]}}        |

    #escenario usando datos random de la clase DataGenerator
    
    Scenario: new user sign up
        * def user = data.username
        * def email = data.email
        * def password = data.password

        Given path 'users'
        And request
            """
            {"user":{
            "email": #(email),
            "password": #(password),
            "username": #(user)
            }
            }
            """
        When method Post
        Then status 200
        And match response ==
            """
            {
                "user": {
                    "email": #(email),
                    "username": #(user),
                    "bio": "##string",
                    "image": "#string",
                    "token": "#string"
                }
            }
            """
        


            #no quitar anotacion porque muestra error
    @ignore
    #Escenario usando un map como variable userData
    Scenario: new user sign up
        * def userData =
            """
            {
                "email": "1110jcam1.qvision@gmail.com",
                "password": "testerExtremo",
                "username": "rubetusina11"
            }
            """
        Given path 'users'
        And request
            """
            {"user":{
            "email": #(userData.email),
            "password": #(userData.password),
            "username": #(userData.username)
            }
            }
            """
        When method Post
        Then status 200
        And match response ==
            """
            {
                "user": {
                    "email": #(userData.email),
                    "username": #(userData.username),
                    "bio": "##string",
                    "image": "#string",
                    "token": "#string"
                }
            }
            """
#no quitar anotacion porque muestra error
#test sin usar data generator
@ignore
Scenario: new user sign up
    Given path 'users'
    And request
        """
        {"user":{
        "email": "1101jcam1.qvision@gmail.com",
        "password": "testerExtremo",
        "username": "rubetusina"
        }
        }
        """
    When method Post
    Then status 200
    And match response ==
        """
        {
            "user": {
                "email": "#string",
                "username": "#string",
                "bio": "##string",
                "image": "#string",
                "token": "#string"
            }
        }
        """