package com.valdirsantos714.backend.adapters.in.controller;

import com.valdirsantos714.backend.adapters.in.dto.AuthenticationRequest;
import com.valdirsantos714.backend.adapters.in.dto.AuthenticationRequestDto;
import com.valdirsantos714.backend.adapters.in.dto.DadosAutenticacao;
import com.valdirsantos714.backend.adapters.in.dto.DadosToken;
import com.valdirsantos714.backend.adapters.out.repository.entity.UserEntity;
import com.valdirsantos714.backend.application.service.UserServiceImpl;
import com.valdirsantos714.backend.infrastructure.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/auth")
@CrossOrigin("localhost:4200")
public class AutenticantionController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserServiceImpl service;

    @Operation(summary = "Faz login do usuário",  responses = {
            @ApiResponse(description = "Requisição feita com sucesso", responseCode = "200"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
            @ApiResponse(responseCode = "403", description = "Requisição não autorizada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping("/login")
    public ResponseEntity efetuarLogin(@RequestBody @Valid AuthenticationRequestDto dto){
    try {
        System.out.println("Recebendo dados de autenticação: " + dto.email());
        var authenticationToken = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        var authentication = manager.authenticate(authenticationToken);
        System.out.println(authentication.getPrincipal());
        var tokenJWT = tokenService.geraToken((UserEntity) authentication.getPrincipal());
        return ResponseEntity.ok(new DadosToken(tokenJWT));

    }   catch (Exception e) {
        e.printStackTrace(); // Adicionado para depuração
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    }

    @Operation(summary = "Salva usuário",  responses = {
            @ApiResponse(description = "Requisição feita com sucesso", responseCode = "201"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
            @ApiResponse(responseCode = "403", description = "Requisição não autorizada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping("/register")
    public ResponseEntity efetuarCadastro(@RequestBody @Valid AuthenticationRequestDto dto, UriComponentsBuilder uriBuilder){

        var user = service.save(new Users(dto));
        var uri = uriBuilder.path("/admin/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosAdmin(user));

    }

    @Operation(security = { @SecurityRequirement(name = "bearer-key") },
            summary = "Retorna lista de todos os usuários",  responses = {
            @ApiResponse(description = "Requisição feita com sucesso", responseCode = "200"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
            @ApiResponse(responseCode = "403", description = "Requisição não autorizada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/all")
    public ResponseEntity findAllUsers() {
        var list = service.findAll();
        return ResponseEntity.ok(list.stream().map(DadosAdmin::new));
    }
}
