 Project Content
 -------------------------------------------------------------------------------------------------------------------------
 
 The calculation of Single Digit, API, User CRUD, Cache, and  RSA Cryptography modules
 
 Single digit - DigitoUnico related classes
 --------------------------------------------------------------------------------------------------------------------------
 Given an integer, we need to find the single digit of the integer.
 If x has only one digit, then its unique digit is x.
 Otherwise, the single digit of x is equal to the single digit of the
 sum of x digits.
 For example, the single digit of 9875 will be calculated as:
 single_type (9875) 9 + 8 + 7 + 5 = 29
 single_digit (29) 2 + 9 = 11

 Given two numbers n and k, P must be created from the concatenation of the string
 n * k.
 Example:
 n = 9875 and k = 4 so p = 9875 9875 9875 9875
 singleType§ = singleType (9875987598759875)
 5 + 7 + 8 + 9 + 5 + 7 + 8 + 9 + 5 + 7 + 8 + 9 + 5 + 7 + 8 + 9 = 116
 
 User CRUD
 --------------------------------------------------------------------------------------------------------------------------
 A CRUD for users is created.
 A user has a name, email and a list of search results.
 Single digits "digitoUnicos" already calculated.
 Each object in the results list must contain which
 input parameters of the calculation and what the result
 
 Cache
 --------------------------------------------------------------------------------------------------------------------------
 A memory cache was created to store
 last 10 calculations performed for digitoUnicos, this cache is
 independent of user, that is, if a calculation has already been performed and
 is in the cache it will not be necessary to execute the digit function
 single. 
 
 Cryptography
 ----------------------------------------------------------------------------------------------------------------------------
 Used RSA Cryptography
 
 API
 ---------------------------------------------------------------------------------------------------------------------------
 Endpoints should are available to users' CRUD.
 An endpoint for calculating the single digit is provided.
 It was created an endpoint  created that retrieves all calculations
 for a particular user.
 It also contains an endpoint to send the public key of the
 user that will be used for encryption. This API
 receives a string that will contain the key.

 Compile and application execution 
 --------------------------------------------------------------------------------------------------------------------------

  Java8 is required (jdk8 to run the project)

 The project was developed in Spring Tool Suit 4, for STS or Eclipse with STS just click the right button in the project directory and in the option "Run as" click on "String Boot App"
 This form would be ideal for running the application.
 
  If this option is not available, it is also possible to execute it by clicking on "maven build ...", in which case spring-boot: run must be entered in the Goals field.
 
 Ensure that port 8080 is not being used at the time of testing


 How to perform unit tests on this project
----------------------------------------------------------------------------------------------------------------------------------------------------------------------
 Right-click on the project and click run as "JUnit"
 
 
 Additional comments
----------------------------------------------------------------------------------------------------------------------------------------------------------------------
Database access:
H2 memory bank used. Access its interface at:
http: // localhost: 8080 / h2-console
Fill in jdbc: h2: mem: testdb in JDBC URL to access it
API specifications (Swagger)
Once the api is running, access
interface:
http: // localhost: 8080 / swagger-ui.html
json:
http: // localhost: 8080 / v2 / api-docs

To test the implemented encryption I suggest using the jar that I implemented and it is inside the TestCriptografia folder that is in the root directory
from the project.

Within that folder is an EXECUTABLE JAR. Just run it via COMMAND PROMPT "java -jar SimulaCliente.jar".

It is a console application that generates a public and private key to simulate a client. It will also decrypt
information entered (used to validate the decrypt with the client's private key).

The generated public key is displayed at the prompt and must be entered in the postChallengeUserKey on the postman.

When sending the request sendCkeyPublicaUsuario the data of this user will be encrypted.

Send request searchUsuarioPorIdAposCriptografia below to the user and use the value as input in the application SimulaCliente.jar

The application will decrypt the information and it will be possible to verify that everything is correct.

To exit the application just enter "exit" when the application requests "Enter value to be decrypted:"
OR press CTRL + C

If you want to generate new client keys just delete privateClient.key and publicClient.key in the directory and execute
the SimulaCliente.jar application via prompt. As soon as the application is opened the new key will be generated. Don't forget to replace
the postman's request in that case.

I left the Client.java file that I used to make this application to better see how it works (if necessary).

Her implementation was based on the cryptography class of the main project.
