package com.valdirsantos714.backend.core.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CommomAtributes {
    private String name;
    private String description;
    private Double amount;
    private LocalDate date;
}
