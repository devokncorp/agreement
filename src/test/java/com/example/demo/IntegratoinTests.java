package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.elasticsearch.RestClientBuilderCustomizer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegratoinTests {

    //    WebTestClient client;
    RestTemplate client;


    @BeforeEach
    public void setUp() {

    }

    @Test
    public void getAllPosts() {
//        client.get
//        client.get().uri("/posts")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody().jsonPath("$.size()").isEqualTo(2);
    }

}
