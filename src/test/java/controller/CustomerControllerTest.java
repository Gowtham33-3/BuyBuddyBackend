package controller;

import com.example.buy_buddy_backend.BuyBuddyBackendApplication;
import com.example.buy_buddy_backend.controller.CustomerController;
import com.example.buy_buddy_backend.model.Customer;
import com.example.buy_buddy_backend.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

//@WebMvcTest(CustomerController.class)
@SpringBootTest(classes = {BuyBuddyBackendApplication.class})
@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    private MockMvc mvc;

    @MockBean
    private CustomerService customerService;
    @InjectMocks
    private CustomerController customerController;
    @BeforeEach
    public void setup() {
        // Initialize Mockito annotations
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void customerControllerFetchingCustomerById() throws Exception {
        // Create a sample customer
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setName("John Doe");
        customer1.setEmail("john.doe@example.com");
        customer1.setAddress("123 Elm Street, Springfield");
        customer1.setPhone("555-1234");

        // Mock the service method
        when(customerService.getCustomerById(eq(1L))).thenReturn(customer1);

        // Define the URI and perform the GET request
        URI uri = UriComponentsBuilder
                .fromPath("/api/customers/1")
                .build().toUri();

        MvcResult result = mvc.perform(get(uri)) // Use the correct URL path as needed
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"John Doe\",\"email\":\"john.doe@example.com\",\"address\":\"123 Elm Street, Springfield\",\"phone\":\"555-1234\"}"))
                .andReturn();

        // Assert status code
        int statusCode = result.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), statusCode);
    }
}
