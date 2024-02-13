package org.example.springsecurity.Const.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Permission {

    USER_READ("user:read"),
    USER_WRITE("user:write"),
    USER_DELETE("user:delete"),
    USER_UPDATE("user:update"),

    ADMIN_READ("admin:read"),
    ADMIN_WRITE("admin:write"),
    ADMIN_DELETE("admin:delete"),
    ADMIN_UPDATE("admin:update"),

    SUPER_ADMIN_READ("superAdmin:read"),
    SUPER_ADMIN_WRITE("superAdmin:write"),
    SUPER_ADMIN_DELETE("superAdmin:delete"),
    SUPER_ADMIN_UPDATE("superAdmin:update");


    private final String permission;

}
