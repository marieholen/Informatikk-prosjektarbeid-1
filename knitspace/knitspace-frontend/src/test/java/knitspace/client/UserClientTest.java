package knitspace.client;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import knitspace.exception.ApiException;
import knitspace.model.User;

@ExtendWith(MockitoExtension.class)
public class UserClientTest {
    @Mock
    private HttpClient mockHttpClient;

    @Mock
    private HttpResponse<String> mockResponse;

    private UserClient userClient;

    @BeforeEach
    void setup() {
        this.userClient = new UserClient(mockHttpClient);
    }

    @Test
    void testCreateAccountSuccess() throws IOException, InterruptedException, ApiException {
        String responseBody = "{\"userId\":\"42\",\"username\":\"testUser\",\"password\":\"testPassword\"}";
        when(mockHttpClient.<String>send(any(), any())).thenReturn(mockResponse);
        when(mockResponse.statusCode()).thenReturn(201);
        when(mockResponse.body()).thenReturn(responseBody);

        User user = userClient.createAccount("testUser", "testPassword");

        assertEquals("testUser", user.username());
        assertEquals("testPassword", user.password());
    }

    @Test
    void testCreateAccountFailiure() throws IOException, InterruptedException {
        when(mockHttpClient.<String>send(any(), any())).thenReturn(mockResponse);
        when(mockResponse.statusCode()).thenReturn(500);
        when(mockResponse.body()).thenReturn("Internal error");

        assertThrows(ApiException.class, () -> userClient.createAccount("failingUser", "failingPassword"));
    }

    @Test
    void testLoginSuccess() throws Exception {
        String responseBody = "{\"userId\":\"42\",\"username\":\"testUser\",\"password\":\"testPassword\"}";
        when(mockHttpClient.<String>send(any(), any())).thenReturn(mockResponse);
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn(responseBody);

        User user = userClient.login("testUser", "testPassword");

        assertEquals("testUser", user.username());
        assertEquals("testPassword", user.password());
    }

    @Test
    void testLoginFailure() throws Exception {
        when(mockHttpClient.<String>send(any(), any())).thenReturn(mockResponse);
        when(mockResponse.statusCode()).thenReturn(400);
        when(mockResponse.body()).thenReturn("Bad Request");

        assertThrows(ApiException.class, () -> userClient.login("failingUser", "testPassword"));
    }

    @Test
    void testCreateAccountAsyncSuccess() throws Exception {
        String responseBody = "{\"userId\":\"42\",\"username\":\"testAsyncUser\",\"password\":\"testAsyncPassword\"}";
        when(mockHttpClient.<String>send(any(), any())).thenReturn(mockResponse);
        when(mockResponse.statusCode()).thenReturn(201);
        when(mockResponse.body()).thenReturn(responseBody);

        CompletableFuture<User> future = userClient.createAccountAsync("testAsyncUser", "testAsyncPassword");
        User result = future.get();

        assertEquals("testAsyncUser", result.username());
        assertEquals("testAsyncPassword", result.password());
    }

    @Test
    void testCreateAsyncFailiureThrowsRuntimeException() throws Exception {
        when(mockHttpClient.send(any(), any())).thenThrow(new RuntimeException("Network failure"));

        CompletableFuture<User> futureUser = userClient.createAccountAsync("testUsername", "testPassword");

        assertThrows(RuntimeException.class, futureUser::join);
    }

    @Test
    void testLoginAsyncSuccess() throws Exception {
        String responseBody = "{\"userId\":\"42\",\"username\":\"testAsyncUser\",\"password\":\"testAsyncPassword\"}";
        when(mockHttpClient.<String>send(any(), any())).thenReturn(mockResponse);
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn(responseBody);

        CompletableFuture<User> future = userClient.loginAsync("testAsyncUser", "testAsyncPassword");
        User result = future.get();

        assertEquals("testAsyncUser", result.username());
        assertEquals("testAsyncPassword", result.password());
    }

    @Test
    void testLoginFailiureThrowsRuntimeException() throws Exception {
        when(mockHttpClient.send(any(), any())).thenThrow(new RuntimeException("Network failure"));

        CompletableFuture<User> futureUser = userClient.loginAsync("testUsername", "testPassword");

        assertThrows(RuntimeException.class, futureUser::join);
    }
}
