package knitspace.client;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import knitspace.exception.ApiException;
import knitspace.model.User;

@ExtendWith(MockitoExtension.class)
public class ApiClientTest {
    @Mock
    private HttpClient mockHttpClient;

    @Mock
    private HttpResponse<String> mockResponse;

    private ApiClient apiClient;

    @BeforeEach
    void setup() {
        this.apiClient = new ApiClient(mockHttpClient);
    }

    @Test
    void testCreateRequestBuilderUriAndHeadersAreCorrect() {
        HttpRequest request = ApiClient.createRequestBuilder("/users").GET().build();

        assertTrue(request.uri().toString().contains(ApiClient.BASE_URL),
            "Request should include base URL");
        assertTrue(request.uri().toString().endsWith("/users"),
            "Request should end with given uri");

        assertTrue(request.headers().firstValue("Content-Type").isPresent(),
            "Content-Type header should be set");
        assertTrue(request.headers().firstValue("Accept").isPresent(),
            "Accept header should be set");
    }

    @Test
    void testSerializeAndDeserializeUser() throws ApiException {
        User user = new User("42", "testUser", "testPassword");
        String json = apiClient.serialize(user);

        assertTrue(json.contains("\"username\":\"testUser\""),
            "Serialized JSON should contain username!");

        User deserializedUser = apiClient.deserialize(json, User.class);
        assertEquals("42", deserializedUser.userId());
        assertEquals("testUser", deserializedUser.username());
        assertEquals("testPassword", deserializedUser.password());
    }

    @Test
    void testHandleResponseSucessReturnsUser() throws ApiException {
        String mockJsonResponse = "{\"userId\":\"42\",\"username\":\"testUser\",\"password\":\"testPassword\"}";

        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn(mockJsonResponse);

        User result = ApiClient.handleResponse(mockResponse, User.class);

        assertEquals(result.userId(), "42");
        assertEquals(result.username(), "testUser");
        assertEquals(result.password(), "testPassword");
    }

    @Test
    void testDeserializeThrowsApiExceptionOnFail() throws ApiException {
        String invalidJson = "{this is not valid json!!}";

        ApiException e = assertThrows(ApiException.class, () -> {
            apiClient.deserialize(invalidJson, User.class);
        });

        assertTrue(e.getMessage().contains("Failed to deserialize JSON"));
    }

    @Test
    void testHandleResponseThrowsApiExceptionWhenFailingToParseResponse() throws ApiException {
        String invalidJson = "{this is not valid json!!}";
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn(invalidJson);

        ApiException e = assertThrows(ApiException.class, () -> {
            apiClient.handleResponse(mockResponse, User.class);
        });

        assertTrue(e.getMessage().contains("Failed to parse response"));
    }

    @Test
    void testHandleResponseThrowsApiExceptionWhenBadStatusCode() throws ApiException {
        String json = "{This is some random string that has to be passed}";
        when(mockResponse.statusCode()).thenReturn(400);
        when(mockResponse.body()).thenReturn(json);

        ApiException e = assertThrows(ApiException.class, () -> {
            apiClient.handleResponse(mockResponse, User.class);
        });

        assertTrue(e.getMessage().contains("HTTP 400:"));
    }

    @Test
    void testVoidClassReturnsNull() throws ApiException {
        String json = "{This is some random string that has to be passed}";
        when(mockResponse.statusCode()).thenReturn(200);

        var result = apiClient.handleResponse(mockResponse, Void.class);

        assertNull(result);
    }
}
