package org.marcinski.validator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.marcinski.api.BookEndpoint;
import org.marcinski.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class ValidationTest {

    @Autowired
    private BookRepository bookRepository;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(
                        new BookEndpoint(bookRepository))
                .build();
    }

    @Test
    public void wrongIsbn() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders
                .post("/api/books")
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .content("{\"title\": \"some title\", \"isbn\": \"123\"}"))
                .andExpect(status().isOk());
    }
}
