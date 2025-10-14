package knitspace.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import knitspace.model.User;
import knitspace.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Unit tests for UserController.
 */
@WebMvcTest(UserController.class)
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private UserService userService;

  @Autowired
  private ObjectMapper objectMapper;

  private User testUser;

  @BeforeEach
  void setUp() {
    testUser = new User(UUID.randomUUID(), "testuser", "testpass");
  }

  @Test
  void login_ValidCredentials_ReturnsOk() throws Exception {
    User loginRequest = new User();
    loginRequest.setUsername("testuser");
    loginRequest.setPassword("testpass");

    when(userService.authenticateUser("testuser", "testpass"))
        .thenReturn(Optional.of(testUser));

    mockMvc.perform(post("/api/users/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(loginRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username").value("testuser"));
  }

  @Test
  void login_InvalidCredentials_ReturnsUnauthorized() throws Exception {
    User loginRequest = new User();
    loginRequest.setUsername("testuser");
    loginRequest.setPassword("wrongpass");

    when(userService.authenticateUser("testuser", "wrongpass"))
        .thenReturn(Optional.empty());

    mockMvc.perform(post("/api/users/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(loginRequest)))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void login_ServiceThrowsIOException_ReturnsInternalServerError() throws Exception {
    User loginRequest = new User();
    loginRequest.setUsername("testuser");
    loginRequest.setPassword("testpass");

    when(userService.authenticateUser("testuser", "testpass"))
        .thenThrow(new IOException("Database error"));

    mockMvc.perform(post("/api/users/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(loginRequest)))
        .andExpect(status().isInternalServerError());
  }

  @Test
  void createUser_NewUser_ReturnsCreated() throws Exception {
    User createRequest = new User();
    createRequest.setUsername("newuser");
    createRequest.setPassword("newpass");

    User createdUser = new User(UUID.randomUUID(), "newuser", "newpass");

    when(userService.createUser("newuser", "newpass"))
        .thenReturn(createdUser);

    mockMvc.perform(post("/api/users/create")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(createRequest)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.username").value("newuser"));
  }

  @Test
  void createUser_ExistingUsername_ThrowsError() throws Exception {
    User createRequest = new User();
    createRequest.setUsername("existinguser");
    createRequest.setPassword("password");

    when(userService.createUser("existinguser", "password"))
        .thenThrow(new IllegalArgumentException("Username already exists"));

    mockMvc.perform(post("/api/users/create")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(createRequest)))
        .andExpect(status().isConflict());
  }

  @Test
  void createUser_InternalServerError() throws Exception {
    User createRequest = new User();
    createRequest.setUsername("newuser");
    createRequest.setPassword("newpass");

    when(userService.createUser("newuser", "newpass"))
        .thenThrow(new IOException("Some error"));

    mockMvc.perform(post("/api/users/create")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(createRequest)))
        .andExpect(status().isInternalServerError());
  }
}