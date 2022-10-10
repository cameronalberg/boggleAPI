package com.example.boggle;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BoggleAPIControllerTests {

    @LocalServerPort
    int randomServerPort;
    RestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        restTemplate = new RestTemplate();
    }

    @Test
    public void shuffleUsesDefaultValue() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + randomServerPort + "/shuffle";
        URI uri = new URI(baseUrl);

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        //Verify default board of size 4
        Assertions.assertEquals(200, result.getStatusCodeValue());
        Assertions.assertNotNull(result.getBody());
        String body = result.getBody();
        JSONObject json = (JSONObject) JSONValue.parse(body);
        Assertions.assertEquals(31, json.get("board").toString().length());
    }

    @Test
    public void shuffleUsesProvidedValue() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + randomServerPort + "/shuffle?size=6";
        URI uri = new URI(baseUrl);

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        //Verify default board of size 4
        Assertions.assertEquals(200, result.getStatusCodeValue());
        Assertions.assertNotNull(result.getBody());
        String body = result.getBody();
        JSONObject json = (JSONObject) JSONValue.parse(body);
        Assertions.assertEquals(71, json.get("board").toString().length());
    }

    @Test
    public void shuffleHandlesBadInput() throws URISyntaxException {
        String baseUrl = "http://localhost:" + randomServerPort + "/shuffle?size=";
        final String[] badInputs = {"49", "15", "3", "-1", "ba", "asdfe", "sda3ggggg"};

        for (String input : badInputs) {
            baseUrl += input;
            URI uri = new URI(baseUrl);
            try
            {
                restTemplate.getForEntity(uri, String.class);
                Assertions.fail();
            }
            catch(HttpClientErrorException ex)
            {
                //Verify bad request and invalid parameters
                Assertions.assertEquals(400, ex.getRawStatusCode());
                Assertions.assertTrue(ex.getResponseBodyAsString().contains("error"));
            }
        }
    }

    @Test
    public void solveParsesBoardCorrectly() throws URISyntaxException {
        String baseUrl = "http://localhost:" + randomServerPort + "/solve?board=test";
        URI uri = new URI(baseUrl);

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        //Verify OK response
        Assertions.assertEquals(200, result.getStatusCodeValue());
        Assertions.assertNotNull(result.getBody());
        String body = result.getBody();
        JSONObject json = (JSONObject) JSONValue.parse(body);

        //Verify board was parsed correctly
        Assertions.assertEquals("t-e-s-t", json.get("board").toString());

    }

    @Test
    public void solveFindsWords() throws URISyntaxException {
        String baseUrl = "http://localhost:" + randomServerPort + "/solve?board=test";
        URI uri = new URI(baseUrl);

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        //Verify OK response
        Assertions.assertEquals(200, result.getStatusCodeValue());
        Assertions.assertNotNull(result.getBody());
        String body = result.getBody();
        JSONObject json = (JSONObject) JSONValue.parse(body);

        //Verify words were found
        Assertions.assertNotNull(json.get("words"));
        Assertions.assertTrue(json.get("words").toString().contains("set"));
    }

    @Test
    public void solveHandlesBadInput() throws URISyntaxException {
        String baseUrl = "http://localhost:" + randomServerPort + "/solve?board=";
        final String[] badInputs = {"", "3", "-1", "ba", "asdfe", "sda3ggggg"};

        for (String input : badInputs) {
            baseUrl += input;
            URI uri = new URI(baseUrl);
            try
            {
                restTemplate.getForEntity(uri, String.class);
                Assertions.fail();
            }
            catch(HttpClientErrorException ex)
            {
                //Verify bad request and invalid parameters
                Assertions.assertEquals(400, ex.getRawStatusCode());
                Assertions.assertTrue(ex.getResponseBodyAsString().contains("error"));
            }
        }
    }


}
