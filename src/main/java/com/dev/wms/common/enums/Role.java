package com.dev.wms.common.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Role {

    ADMIN(1, "ADMIN"),
    CUSTOMER(2, "CUSTOMER"),
    VENDOR(3, "VENDOR");

    private final Integer roleSeq;
    private final String role;

    Role(Integer roleSeq, String role) {
        this.roleSeq = roleSeq;
        this.role = role;
    }

    public Integer getRoleSeq() {
        return roleSeq;
    }

    public String getRole() {
        return role;
    }
}
