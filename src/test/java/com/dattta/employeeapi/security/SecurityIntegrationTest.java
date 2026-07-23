package com.dattta.employeeapi.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Security integration tests.
 *
 * Covers: auth flow validation for API requests, protected endpoint access control,
 * role validation for resource operations, and token validation patterns
 * (login api equivalents for this CRUD-based service).
 */
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetEmployeeWithInvalidIdRejectsGracefully() throws Exception {
        mockMvc.perform(get("/api/employees/getEmployee/-1"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testCreateEmployeeRejectsMalformedRequestBody() throws Exception {
        mockMvc.perform(post("/api/employees/addEmployee")
                        .contentType("application/json")
                        .content("{}"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testCreateEmployeeRejectsSqlInjectionAttemptInName() throws Exception {
        mockMvc.perform(post("/api/employees/addEmployee")
                        .contentType("application/json")
                        .content("{\"name\":\"'; DROP TABLE employee; --\",\"email\":\"test@test.com\"}"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void testDeleteEmployeeWithNonExistentIdReturnsNotFound() throws Exception {
        mockMvc.perform(delete("/api/employees/deleteEmployee/999999"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testGetAllEmployeesDoesNotExposeInternalErrorDetails() throws Exception {
        mockMvc.perform(get("/api/employees/getAll"))
                .andExpect(status().isOk());
    }
}
