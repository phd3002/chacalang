package com.thungcam.chacalang.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WardSimpleDTO {
    private Long id;
    private String name;

    public WardSimpleDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

