package com.dev.wms.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class ContactDto implements Serializable {
    private String contactSeq;
    private String contact_1;
    private String contact_2;
    private String contact_3;
    private Integer contactTypeSeq;
    private Integer statusSeq;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String userSeq;
}
