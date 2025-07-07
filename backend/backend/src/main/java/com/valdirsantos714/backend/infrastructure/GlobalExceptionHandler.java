package com.valdirsantos714.backend.infrastructure;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity trataErro404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity trataErro400(MethodArgumentNotValidException e) {
        var erros = e.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErrosValidacao::new).toList());

    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity trataErro500(RuntimeException e) {
        e.printStackTrace(); // Log the error for debugging purposes
        return ResponseEntity.status(500).body("Ocorreu um erro interno no servidor. Por favor, tente novamente mais tarde.");
    }

    //Record para capturar somente o campo de mensagem e o campo que faltou ser preenchido corretamente
    private record DadosErrosValidacao(String campo, String mensagem){
        public DadosErrosValidacao(FieldError e) {
            this(e.getField(), e.getDefaultMessage());
        }
    }
}
