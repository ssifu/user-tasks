package com.samiulsifat.task_management.model;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.samiulsifat.task_management.model.Permission.*;
import static com.samiulsifat.task_management.model.Permission.USER_READ;

public enum Role {
    USER(
            Sets.newHashSet()
    ),
    ADMIN(
            Sets.newHashSet(
                    ADMIN_READ,
                    ADMIN_WRITE,
                    USER_WRITE,
                    USER_READ
        )
    )
    ;

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());
    }

    public String getRoleName() {
        return "ROLE_" + this.name();
    }
}
