package org.marcinski.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.marcinski.BookListApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = BookListApplication.class
)

@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class BookEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAllBooks() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/books")
                .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        System.out.println(mvcResult.getResponse().toString());
    }
}