# SpringBoot-Authentication

Add New User Api:

	This Api will add the new user to the Application and then respond with the “JWT_TOKEN” .In Case if the given userId is already exist then it will respond with the error message like “User Id already exist”…

	API: http://localhost:8080/addnewuser
	Method: “POST”
	headers: content-type: “application/json”
	Body:  
		{
			"userid": “user_id”,
    			"password”:”password”

		}

	Example :
		 {
         		"userid":  “Akila”,
    			"password”: ”Akila@123”
		}


Authenticate Api: 

	This Api is used to check whether the given user credential is exist in the Application, if the user credential is correct then it will respond with “JWT_TOKEN” or else it will respond with the error message like “Incorrect username or password”…
	
	API: http://localhost:8080/authenticate 
	Method: “POST”
	headers: content-type: “application/json”
	Body:  
		{
			"userid": “user_id”,
    			"password”:”password”

		}

	
Welcome API:

  This Api  will be available only for the authorized users. This Api will be respond with the “Welcome” message.

	API: http://localhost:8080/welcome
	Method:  “GET”
	headers: Authorization : “Bearer <JWT_TOKEN>”

 JWT_TOKEN : This is the token which we get after the successful authentication using Authenticate Api or Add New User Api.
