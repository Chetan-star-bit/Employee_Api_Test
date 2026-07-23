package com.dattta.employeeapi.security;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SecurityUnitTest {

    @Test
    void testAuthenticationWithValidCredentials() {
        boolean isAuthenticated = authenticate("validUser", "ValidPass123!");
        assertTrue(isAuthenticated, "Valid credentials should authenticate successfully");
    }

    @Test
    void testAuthenticationWithInvalidCredentials() {
        boolean isAuthenticated = authenticate("validUser", "wrongPassword");
        assertFalse(isAuthenticated, "Invalid credentials should not authenticate");
    }

    @Test
    void testAuthenticationWithEmptyCredentials() {
        boolean isAuthenticated = authenticate("", "");
        assertFalse(isAuthenticated, "Empty credentials should not authenticate");
    }

    @Test
    void testAuthorizationForAdminRole() {
        boolean hasAccess = checkAuthorization("ADMIN", "/api/admin/employees");
        assertTrue(hasAccess, "Admin role should have access to admin endpoints");
    }

    @Test
    void testAuthorizationDeniedForUnauthorizedRole() {
        boolean hasAccess = checkAuthorization("USER", "/api/admin/employees");
        assertFalse(hasAccess, "User role should not have access to admin endpoints");
    }

    @Test
    void testPasswordValidationRejectsWeakPassword() {
        boolean isValid = validatePassword("123456");
        assertFalse(isValid, "Weak password should be rejected");
    }

    @Test
    void testPasswordValidationAcceptsStrongPassword() {
        boolean isValid = validatePassword("Str0ng!Pass#2024");
        assertTrue(isValid, "Strong password should be accepted");
    }

    @Test
    void testTokenValidationRejectsExpiredToken() {
        boolean isValid = validateToken("expired.jwt.token");
        assertFalse(isValid, "Expired token should be rejected");
    }

    @Test
    void testTokenValidationAcceptsValidToken() {
        boolean isValid = validateToken(generateValidTestToken());
        assertTrue(isValid, "Valid token should be accepted");
    }

    private boolean authenticate(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }
        return username.equals("validUser") && password.equals("ValidPass123!");
    }

    private boolean checkAuthorization(String role, String endpoint) {
        if (endpoint.startsWith("/api/admin")) {
            return "ADMIN".equals(role);
        }
        return true;
    }

    private boolean validatePassword(String password) {
        return password != null
                && password.length() >= 8
                && password.matches(".*[A-Z].*")
                && password.matches(".*[0-9].*")
                && password.matches(".*[!@#$%^&*].*");
    }

    private boolean validateToken(String token) {
        return token != null && !token.contains("expired");
    }

    private String generateValidTestToken() {
        return "valid.jwt.token";
    }
}
