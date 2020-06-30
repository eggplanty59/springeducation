package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ru.education.controllers.ProductController;
import ru.education.entity.Product;
import service.mock.MockProductService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ProductController.class, MockProductService.class})
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private ProductController productController;

    private MockMvc mockMvc;

    private final static String URL = "http://localhost:8080/api/v1/product";

    ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup(){
        this.mockMvc = standaloneSetup(productController).build();
    }

    @Test
    public void findAllTest() throws Exception {
        mockMvc.perform(get(URL))
                .andExpect(status().isOk());
    }

    //Поиск
    @Test
    public void findProductTest() throws Exception {
        mockMvc.perform(get(URL+"/1"))
                .andExpect(status().isOk());
    }

    //Создание
    @Test
    public void createTest() throws Exception {
        Product product = new Product(3, "testProduct");
        String requestJson = mapper.writeValueAsString(product);
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(requestJson))
            .andExpect(status().isCreated());
    }

    //редактирование
    @Test
    public void updateProductTest() throws Exception {
        Product product = new Product(1, "testProduct");
        String requestJson = mapper.writeValueAsString(product);
        mockMvc.perform(put(URL).contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isOk());
    }

    //удаление
    @Test
    public void deleteProductTest() throws Exception {
        mockMvc.perform(delete(URL + "/1"))
                .andExpect(status().isNoContent());
    }
}
