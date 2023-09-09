package com.globalLogic.usermircroservice.service.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SignupControllerE2ETest {

    @LocalServerPort
    private int port ;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void testSignUpEndpoint() {
        // Define la URL completa del endpoint que deseas probar
        String url = "http://localhost:" + port + "/api/signup";

        // Define el cuerpo de la solicitud (en formato JSON)
        String requestBody = "{\"name\": \"John Doe\", \"email\": \"john@example.com\", \"password\": \"Password123\", \"phones\": []}";

        // Configura el encabezado "Content-Type" para indicar JSON
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Crea una entidad de solicitud con el cuerpo y el encabezado
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Realiza la solicitud POST al endpoint con la entidad de solicitud
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        // Verifica el código de respuesta y el contenido de la respuesta si es necesario
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        // Verifica el contenido de la respuesta si es necesario
        // String responseBody = response.getBody();
        // assertEquals("Usuario registrado con éxito.", responseBody);
    }
}
