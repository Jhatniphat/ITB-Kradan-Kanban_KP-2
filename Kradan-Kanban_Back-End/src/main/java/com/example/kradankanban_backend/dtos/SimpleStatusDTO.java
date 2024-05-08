package com.example.kradankanban_backend.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class SimpleStatusDTO {
    private int id;
    private String StatusName;
    private String StatusDescription;
}
