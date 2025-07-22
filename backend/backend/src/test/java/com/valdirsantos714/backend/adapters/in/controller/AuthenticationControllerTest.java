package com.valdirsantos714.backend.adapters.in.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.valdirsantos714.backend.adapters.in.dto.AuthenticationRequestDto;
import com.valdirsantos714.backend.adapters.in.dto.UserRequestDTO;
import com.valdirsantos714.backend.adapters.out.repository.UserRepositoryAdapter;
import com.valdirsantos714.backend.adapters.out.repository.entity.UserEntity;
import com.valdirsantos714.backend.adapters.out.repository.mapper.UserMapper;
import com.valdirsantos714.backend.application.core.domain.User;
import com.valdirsantos714.backend.application.core.domain.enums.UserRole;
import com.valdirsantos714.backend.application.service.UserServiceImpl;
import com.valdirsantos714.backend.configuration.TestSecurityConfiguration;
import com.valdirsantos714.backend.infrastructure.security.SecurityFilter;
import com.valdirsantos714.backend.infrastructure.security.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestSecurityConfiguration.class)
@WebMvcTest(AuthenticationController.class)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private UserServiceImpl userService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SecurityFilter securityFilter;

    @MockBean
    private UserRepositoryAdapter userRepositoryAdapter;

    @Test
    @DisplayName("should authenticate user and return 200 status with token")
    void shouldAuthenticateUser() throws Exception {
        // Given
        AuthenticationRequestDto requestDto = new AuthenticationRequestDto("test@example.com", "password123");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(requestDto.email(), requestDto.password());
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(requestDto.email());
        userEntity.setPassword(requestDto.password()); // In a real scenario, this would be encoded
        userEntity.setRole(UserRole.USER);

        Authentication authentication = Mockito.mock(Authentication.class);
        given(authentication.getPrincipal()).willReturn(userEntity);
        given(authenticationManager.authenticate(authenticationToken)).willReturn(authentication);
        given(tokenService.geraToken(any(UserEntity.class))).willReturn("mocked-jwt-token");

        // When + Then
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tokenJWT").value("mocked-jwt-token"));
    }

    @Test
    @DisplayName("should return 401 for invalid login credentials")
    void shouldReturn401ForInvalidLogin() throws Exception {
        // Given
        AuthenticationRequestDto requestDto = new AuthenticationRequestDto("invalid@example.com", "wrongpassword");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(requestDto.email(), requestDto.password());

        given(authenticationManager.authenticate(authenticationToken))
                .willThrow(new BadCredentialsException("Invalid credentials"));

        // When + Then
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("should register a new user and return 201 status")
    void shouldRegisterUser() throws Exception {
        // Given
        UserRequestDTO requestDTO = new UserRequestDTO(
                "New User",
                "newuser@example.com",
                "newpassword123"
        );

        User userDomain = UserMapper.toUser(requestDTO);
        userDomain.setId(1L);
        userDomain.setRole(UserRole.USER);

        given(userService.save(any(UserRequestDTO.class)))
                .willReturn(userDomain);

        // When + Then
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(requestDTO.name()))
                .andExpect(jsonPath("$.email").value(requestDTO.email()));
    }

    @Test
    @DisplayName("should return all users when authenticated as ADMIN")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldFindAllUsersAsAdmin() throws Exception {
        // Given
        User user1 = new User();
        user1.setId(1L);
        user1.setName("User 1");
        user1.setEmail("user1@example.com");
        user1.setRole(UserRole.USER);

        User user2 = new User();
        user2.setId(2L);
        user2.setName("User 2");
        user2.setEmail("user2@example.com");
        user2.setRole(UserRole.ADMIN);

        given(userService.findAll()).willReturn(List.of(user1, user2));

        // When + Then
        mockMvc.perform(get("/api/auth/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("User 1"))
                .andExpect(jsonPath("$[1].name").value("User 2"));
    }

    @Test
    @DisplayName("should return 403 when accessing all users without ADMIN role")
    void shouldReturn403WhenFindAllUsersWithoutAdminRole() throws Exception {
        // When + Then
        mockMvc.perform(get("/api/auth/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
