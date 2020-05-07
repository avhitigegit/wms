package com.dev.wms.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class TypeDto implements Serializable {
    private String typeSeq;
    private String name;
    private String description;
    private String logo;
    private Integer statusSeq;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
