package com.dev.wms.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class DescriptionDto implements Serializable {
    private String descriptionSeq;
    private String website;
    private String facebook;
    private String linkedin;
    private String description_1;
    private String description_2;
    private Integer statusSeq;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String userSeq;
}
