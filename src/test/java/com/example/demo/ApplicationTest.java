package com.example.demo;

import com.example.demo.controller.PostController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


@SpringBootTest
public class ApplicationTest {

    @Autowired
    PostController ctl;

    @MockBean
    PostRepository posts;

//    WebTestClient client;

    @BeforeEach
    void setUp() {
//        this.client = WebTestClient.bindToController(this.ctl)
//            .configureClient()
//            .build();
    }

    @Test
    public void getAllPosts() {
//        when(this.posts.findAll()).thenReturn(Flux.just(
//                Post.builder().id(UUID.randomUUID()).title("post one").content("content of post one").build(),
//                Post.builder().id(UUID.randomUUID()).title("post two").content("content of post two").build()
//        ));
//        client.get().uri("/posts")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody().jsonPath("$.size()").isEqualTo(2);
//
//        verify(this.posts, times(1)).findAll();
//        verifyNoMoreInteractions(this.posts);
    }

}
