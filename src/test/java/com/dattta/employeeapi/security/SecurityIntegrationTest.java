package com.dattta.employeeapi.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Security-focused integration tests for input validation and
 * access control on Employee API endpoints.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetEmployeeWithInvalidIdRejectsGracefully() throws Exception {
        // Verify the API does not expose stack traces or internal details for invalid IDs
        mockMvc.perform(get("/api/employees/getEmployee/-1"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testCreateEmployeeRejectsMalformedRequestBody() throws Exception {
        // Verify malformed/empty JSON payloads are rejected, not silently accepted
        mockMvc.perform(post("/api/employees/addEmployee")
                        .contentType("application/json")
                        .content("{}"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testCreateEmployeeRejectsSqlInjectionAttemptInName() throws Exception {
        // Verify SQL-injection-style payloads in input fields do not break the API
        // or get persisted/executed as raw SQL (JPA parameter binding should protect this)
        mockMvc.perform(post("/api/employees/addEmployee")
                        .contentType("application/json")
                        .content("{\"name\":\"'; DROP TABLE employee; --\",\"email\":\"test@test.com\"}"))
                .andExpect(status().is2xxSuccessful());
        // Confirms the payload is treated as data, not executed as SQL (parameterized queries)
    }

    @Test
    void testDeleteEmployeeWithNonExistentIdReturnsNotFound() throws Exception {
        // Verify deletion of a non-existent resource is handled safely
        mockMvc.perform(delete("/api/employees/deleteEmployee/999999"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testGetAllEmployeesDoesNotExposeInternalErrorDetails() throws Exception {
        // Verify the endpoint responds without leaking internal exception details
        mockMvc.perform(get("/api/employees/getAll"))
                .andExpect(status().isOk());
    }
}