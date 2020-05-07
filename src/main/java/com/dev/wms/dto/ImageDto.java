package com.dev.wms.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class ImageDto implements Serializable {
    private String imageSeq;
    private String name;
    private String url;
    private Integer imageTypeSeq;
    private Integer statusSeq;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
