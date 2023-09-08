# java-springboot

Requerimeintos:
java 8
gradle
git

Instalacion
clonar el repositorio https://github.com/javiervelaz/java-springboot

comandos:
ingresar por terminal al directorio del proyecto desde el lugar donde se descargo y ejecutar :
gradle buid
gradle bootRun

esto ejecuta la aplicacion en el puerto 8081

Endpoints:
SIGNUP
POST: http://localhost:8081/api/signup
payload:
{
    "name": "NombreUsuario",
    "email": "usuarioee@example.com",
    "password": "Aargen2023",
    "phones": [
        {
            "number": 1234567890,
            "citycode": 123,
            "countrycode": "AR"
        }
    ]
}
response:
{
    "id": "7315b5f4-cba8-46b0-b75e-9196c36683ea",
    "created": "2023-09-08T20:00:12.274+00:00",
    "lastLogin": "2023-09-08T20:00:12.274+00:00",
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c3VhcmlvZWVAZXhhbXBsZS5jb20iLCJpYXQiOjE2OTQyMDMyMTIsImV4cCI6MTY5NTA2NzIxMn0.kav9nQJVhUx40kRmjZxa2ofYgMaz0XdUPOPIOmhrZ27iLtSD6Y2xFD8grm_liKDf0IBxXbdYnrjNmuJVVqE5tA",
    "isActive": false
}
***************************************************************************************
LOGIN
POST:http://localhost:8081/api/login
payload:
{
    "token":"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c3VhcmlvZWVAZXhhbXBsZS5jb20iLCJpYXQiOjE2OTQyMDE2MDIsImV4cCI6MTY5NTA2NTYwMn0.aQ-IJcsuHWTAFumdfhLvtWvw3xVCeaHuPMB0TkOX0wGUokjh6l810Lndiv1LirVfOu1V0T7x-hcuLRgwQE5Hsg"
}
este enpoint con se termino de desarrollar.


