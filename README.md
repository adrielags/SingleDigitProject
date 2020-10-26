This project contains:

The calculation of Single Digit, API, User CRUD, Cache, and  RSA Cryptography modules

 Compile and application execution 
 --------------------------------------------------------------------------------------------------------------------------

Java8 is required (jdk8 to run the project)

 The project was developed in Spring Tool Suit 4, for STS or Eclipse with STS just click the right button in the project directory and in the option "Run as" click on "String Boot App"
 This form would be ideal for running the application.
 
 If this option is not available, it is also possible to execute it by clicking on "maven build ...", in which case spring-boot: run must be entered in the Goals field.
 
 Ensure that port 8080 is not being used at the time of testing

----------------------------------------------------------------------------------------------------------------------------------------------------------------------
2 - How to perform unitary tests on the project

 Right-click on the project and click run as "JUnit
----------------------------------------------------------------------------------------------------------------------------------------------------------------------
3 - Additional comments
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
----------------------------------------------------------------------------------------------------------------------------------------------------------------------
To test the implemented encryption I suggest using the jar that I implemented and it is inside the TestCriptografia folder that is in the root directory
from the project.
Within that folder is an EXECUTABLE JAR. Just run it via COMMAND PROMPT "java -jar SimulaCliente.jar".
It is a console application that generates a public and private key to simulate a client. It will also decrypt
information entered (used to validate the decrypt with the client's private key).
The generated public key is displayed at the prompt and must be entered in the postChallengeUserKey on the postman.
When sending the request sendCkeyPublicaUsuario the data of this user will be encrypted.
Send request searchUsuarioPorIdAposCriptografia below to the user and use the value as input in the application SimulaCliente.jar
the application will decrypt the information and it will be possible to verify that everything is correct.
to exit the application just enter "exit" when the application requests "Enter value to be decrypted:"
OR press CTRL + C
If you want to generate new client keys just delete privateClient.key and publicClient.key in the directory and execute
the SimulaCliente.jar application via prompt. As soon as the application is opened the new key will be generated. Don't forget to replace
the postman's request in that case.
I left the Client.java file that I used to make this application to better see how it works (if necessary).
Her implementation was based on the cryptography class of the main project.
