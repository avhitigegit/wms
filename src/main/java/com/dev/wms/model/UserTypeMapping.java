package com.dev.wms.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "user_type_map")
public class UserTypeMapping implements Serializable {
    @Id
    @Column(name = "user_type_map_seq")
    private String userTypeMappingSeq;
    @Column(name = "user_seq")
    private String userSeq;
    @Column(name = "type_seq")
    private String typeSeq;
}
